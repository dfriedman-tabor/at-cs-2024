package graphs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Graph<T> {
	
	HashMap<T, Vertex> vertices = new HashMap<T, Vertex>();
	
	private class Vertex {
		
		T info;
		HashSet<Vertex> neighbors = new HashSet<Vertex>();
		
		public Vertex(T info) {
			this.info = info;
		}
	}
	
	public void addVertex(T info) {
		
		
		vertices.put(info, new Vertex(info));
		
	}
	
	public void connect(T info1, T info2) {
		
		Vertex v1 = vertices.get(info1);
		Vertex v2 = vertices.get(info2);
		
		boolean found = true;
		
		
		if (v1 == null) {
			found = false;
			System.out.println(info1 + " does not exist");
		}
		if (v2 == null) {
			found = false;
			System.out.println(info2 + " does not exist");
		}
		
		if (found) {
			v1.neighbors.add(v2);
			v2.neighbors.add(v1);
		}

	}
	
	public ArrayList<T> bfs(T start, T end) {
		
		Vertex startV = vertices.get(start);		
		Vertex endV = vertices.get(end);
		
		if (startV == null || endV == null) {
			return null;
		}

		ArrayList<Vertex> toVisit = new ArrayList<>();
		toVisit.add(startV);
		HashMap<Vertex, Vertex> cameFrom = new HashMap<Vertex, Vertex>();
		cameFrom.put(startV, null);
		
		while (toVisit.size() > 0) {
			
			Vertex curr = toVisit.remove(0);
			
			if (curr == endV) {
				return backtrace(cameFrom, endV);
			}
			
			for (Vertex v : curr.neighbors) {
				if (!cameFrom.containsKey(v)) {
					toVisit.add(v);
					cameFrom.put(v, curr);
				}
			}
		}
		
		return null;
	}
	
	public ArrayList<T> backtrace(HashMap<Vertex, Vertex> cameFrom, Vertex end) {
		
		Vertex curr = end;
		
		ArrayList<T> path = new ArrayList<T>();
		
		while (curr != null) {
			
			path.add(0, curr.info);
			curr = cameFrom.get(curr);
		}
		
		return path;
	}
	
	
	public static void main(String[] args) {
		
		Graph<String> g = new Graph<String>();
		
		g.addVertex("A");
		g.addVertex("B");
		g.addVertex("C");
		g.addVertex("D");
		g.addVertex("E");
		g.addVertex("F");
		
		g.connect("A", "E");
		g.connect("A", "B");
		g.connect("B", "F");
		g.connect("B", "C");
		g.connect("B", "D");
		g.connect("C", "D");
		
		System.out.println(g.bfs("A", "D"));

	}
	

}
