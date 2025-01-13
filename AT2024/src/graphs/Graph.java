package graphs;

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
		
		System.out.println(g.vertices.get("A").neighbors);

	}
	

}
