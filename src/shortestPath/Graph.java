package shortestPath;

import exceptions.NullVertexException;

import java.text.NumberFormat;
import java.util.*;

public class Graph {
		   public final Map<String, Vertex> graph;
		 
		   public static class Edge {
		      public final String v1, v2;
		      public final double dist;
		      public Edge(String v1, String v2, double dist) {
		         this.v1 = v1;
		         this.v2 = v2;
		         this.dist = dist;
		      } 
		   }
		 
		  public static class Vertex implements Comparable<Vertex>{
			public final String name;
			public double dist = Double.MAX_VALUE;
			public Vertex previous = null;
			public final Map<Vertex, Double> neighbours = new HashMap<>();
		 
			public Vertex(String name)
			{
				this.name = name;
			}
		 
			private void printPath()
			{
				if (this == this.previous)
				{
					System.out.printf("• %s", this.name);
				}
				else if (this.previous == null)
				{
					System.out.printf("%s(unreached)", this.name);
				}
				else
				{
					this.previous.printPath();
					
					System.out.printf(" -> %s", this.name);
				}
			}
		 
			public int compareTo(Vertex other)
			{
				if (dist == other.dist)
					return name.compareTo(other.name);
		 
				return Double.compare(dist, other.dist);
			}
		 
			@Override public String toString()
			{
				return "(" + name + ", " + dist + ")";
			}
		}

		   public Graph(ArrayList<Edge> gRAPH2) {
		      graph = new HashMap<>(gRAPH2.size());
		 
		      for (Edge e : gRAPH2) {
		         if (!graph.containsKey(e.v1)) graph.put(e.v1, new Vertex(e.v1));
		         if (!graph.containsKey(e.v2)) graph.put(e.v2, new Vertex(e.v2));
		      }
		 
		      for (Edge e : gRAPH2) {
		         graph.get(e.v1).neighbours.put(graph.get(e.v2), e.dist);
		      }
		   }
		 
		   public void dijkstra(String startName) throws NullVertexException{

		      if (!graph.containsKey(startName))
		      	  throw new NullVertexException("Graph doesn't contain start vertex " + startName);


		      final Vertex source = graph.get(startName);
		      NavigableSet<Vertex> q = new TreeSet<>();
		 
		      for (Vertex v : graph.values()) {
		         v.previous = v == source ? source : null;
		         v.dist = v == source ? 0 : Double.MAX_VALUE;
		         q.add(v);
		      }
		      dijkstra(q);
		   }
		 
		   private void dijkstra(final NavigableSet<Vertex> q) {      
		      Vertex u, v;
		      while (!q.isEmpty()) {
		 
		         u = q.pollFirst(); 
		         if (u.dist == Double.MAX_VALUE) break;
		 
		         for (Map.Entry<Vertex, Double> a : u.neighbours.entrySet()) {
		            v = a.getKey(); 
		 
		            final double alternateDist = u.dist + a.getValue();
		            if (alternateDist < v.dist) { 
		               q.remove(v);
		               v.dist = alternateDist;
		               v.previous = u;
		               q.add(v);
		            } 
		         }
		      }
		   }
		 
		   public void printPath(String endName) throws NullVertexException {

			   if (!graph.containsKey(endName))
			   	   throw new NullVertexException("Graph doesn't contain end vertex " + endName);

			   graph.get(endName).printPath();
		      String distance = NumberFormat.getInstance().format(graph.get(endName).dist);
			   String usedDay = String.format("%.2f",(graph.get(endName).dist/400));
			  System.out.println(" = " + distance + " mi");
			  System.out.println("• " + distance + " mi / (8 hours per day * 50 mph) = " + usedDay + " days");
		      System.out.println();
		   }

		   public String getTraTime(String endName) throws NullVertexException{
			   if (!graph.containsKey(endName))
				   throw new NullVertexException("Graph doesn't contain end vertex " + endName);

			   String usedDay = String.format("%.2f",(graph.get(endName).dist/400));
			        return usedDay;
		   }

		}

