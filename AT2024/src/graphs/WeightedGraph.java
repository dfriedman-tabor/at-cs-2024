package graphs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class WeightedGraph<E> {
	
	HashMap<E, Vertex> vertices;
	
	public WeightedGraph() {
		vertices = new HashMap<E, Vertex>();
	}
	
	public void addVertex(E info) {
		vertices.put(info, new Vertex(info));
	}
	
	public void connect(E info1, E info2, int d) {
		Vertex v1 = vertices.get(info1);
		Vertex v2 = vertices.get(info2);
		
		if (v1 == null || v2 == null) {
			System.out.println("Vertex " + (v1==null? v1:v2).toString() + " not found");
			return;
		}
		
		Edge e = new Edge(v1, v2, d);
		
		v1.edges.add(e);
		v2.edges.add(e);
	}

	public class Edge {
		Vertex v1, v2;
		int dist;
		
		public Edge(Vertex v1, Vertex v2, int d) {
			this.v1 = v1; this.v2 = v2;
			this.dist = d;
		}
		
		public Vertex getNeighbor(Vertex v) {
			if (v.info.equals(v1.info)) {
				return v2;
			}
			return v1;
		}
		
	}
	
	public class Vertex {
		E info;
		HashSet<Edge> edges;
		
		public Vertex(E info) {
			this.info = info;
			edges = new HashSet<Edge>();
		}
	}

	
	public ArrayList<Vertex> BFSsearch(E start, E target) {
		
		if (vertices.get(start) == null) {
			System.out.println("Vertex " + start.toString() + " not found");
			return null;
		}
		if (vertices.get(target) == null) {
			System.out.println("Vertex " + target.toString() + " not found");
			return null;
		}
		
		ArrayList<Vertex> toVisit = new ArrayList<Vertex>();
		toVisit.add(vertices.get(start));
		
		HashSet<Vertex> visited = new HashSet<Vertex>();
		visited.add(vertices.get(start));
		
		HashMap<Vertex, Edge> leadsTo = new HashMap<Vertex, Edge>();
		
		while (!toVisit.isEmpty()) {
			
			Vertex curr = toVisit.remove(0);
			
			for (Edge e : curr.edges) {
				
				Vertex neighbor = e.getNeighbor(curr);
				
				if (visited.contains(neighbor)) continue;
				

				leadsTo.put(neighbor, e);
				
				if (neighbor.info.equals(target)) {
					
					return backtrace(neighbor, leadsTo);
				}
				
				else {
					toVisit.add(neighbor);
					visited.add(neighbor);
				}
			}
		}
		return null;
	}
	
	public ArrayList<Vertex> dijkstraSearch(E start, E target) {
		Vertex startVertex = vertices.get(start);
		
		if (startVertex == null) {
			System.out.println(start + " is not in the graph");
			return null;
		}		
		if (vertices.get(target) == null) {
			System.out.println(target + " is not in the graph");
			return null;
		}
		
		HashMap<Vertex, Double> distances = new HashMap<Vertex, Double>();
		HashMap<Vertex, Edge> leadsTo = new HashMap<Vertex, Edge>();
		HashSet<Vertex> visited = new HashSet<Vertex>();
		PriorityQueue<Vertex> toVisit = new PriorityQueue<Vertex>();
		toVisit.add(vertices.get(start), 0);
		
		for (Vertex v : vertices.values()) {
			distances.put(v,Double.MAX_VALUE);
		}
		distances.put(startVertex, 0.0);
		
		while (toVisit.size() > 0) {
			Vertex curr = toVisit.pop();
			visited.add(curr);
			
			if (curr.info.equals(target)) {
				return backtrace(curr, leadsTo);
			}
			
			for (Edge e : curr.edges) {
				Vertex neighbor = e.getNeighbor(curr);
				
				if (visited.contains(neighbor))
					continue;
				
				double dist = distances.get(curr) + e.dist;
				
				if (dist < distances.get(neighbor)) {
					distances.put(neighbor,dist);
					leadsTo.put(neighbor, e);
					
					toVisit.add(neighbor, dist);
				}
			}
		}
		return null;
	}
	
	public ArrayList<Vertex> backtrace(Vertex target, HashMap<Vertex, Edge> leadsTo) {
		
		Vertex curr = target;
		ArrayList<Vertex> path = new ArrayList<Vertex>();
		
		while (leadsTo.get(curr) != null) {
			path.add(0, curr);
			curr = leadsTo.get(curr).getNeighbor(curr);
		}
		path.add(0, curr);
		return path;
		
	}
	
	public static void main(String[] args) {
		WeightedGraph<String> g = new WeightedGraph<String>();
		
		g.addVertex("A");
		g.addVertex("B");
		g.addVertex("C");
		g.addVertex("D");
		g.addVertex("E");
		
		g.connect("A", "B", 10);
		g.connect("A", "D", 5);
		g.connect("D", "C", 7);
		g.connect("D", "E", 3);
		g.connect("E", "C", 19);
		
		for (WeightedGraph.Vertex v : g.dijkstraSearch("E", "C")) 
			System.out.println(v.info);
		
	}
}
