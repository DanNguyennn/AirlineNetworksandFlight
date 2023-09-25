package dlnguyen.hw4;


import algs.days.day20.DepthFirstSearchNonRecursive;
import algs.hw4.map.GPS;
import algs.hw4.map.HighwayMap;
import algs.hw4.map.Information;
import edu.princeton.cs.algs4.BreadthFirstPaths;
import edu.princeton.cs.algs4.DepthFirstPaths;
import edu.princeton.cs.algs4.DijkstraUndirectedSP;
import edu.princeton.cs.algs4.Edge;
import edu.princeton.cs.algs4.EdgeWeightedGraph;


/**
 * Copy this class into USERID.hw4 and make changes.
 */
public class MapSearch {
	
	/** 
	 * This helper method returns the western-most vertex id in the Information, given its latitude and
	 * longitude.
	 * 
	 * https://en.wikipedia.org/wiki/Latitude
	 * https://en.wikipedia.org/wiki/Longitude
	 * 
	 */
	public static int westernMostVertex(Information info) {
		double min = 9999;
		int id = -1;
		for (int j = info.graph.V() - 1; j >= 0 ; j--) {
			GPS g = info.positions.get(j);
			if (g.longitude < min) {
				min = g.longitude;
				id = j;
			}
		}
		return id;
	}

	/** 
	 * This helper method returns the western-most vertex id in the Information, given its latitude and
	 * longitude.
	 * 
	 * https://en.wikipedia.org/wiki/Latitude
	 * https://en.wikipedia.org/wiki/Longitude
	 * 
	 */
	public static int easternMostVertex(Information info) {
		double max = -9999;
		int id = -1;
		for (int i = 0; i < info.graph.V(); i++) {
			GPS g = info.positions.get(i);
			if (g.longitude > max) {
				max = g.longitude;
				id = i;
			}
		}
		return id;
	}

	public static void main(String[] args) {
		Information info = HighwayMap.undirectedGraphFromResources("USA-lower48-natl-traveled.tmg");
		int west = westernMostVertex(info);
		int east = easternMostVertex(info);

		// DO SOME WORK HERE and have the output include things like this
		System.out.println("BreadthFirst Search from West to East:");
		BreadthFirstPaths bfs = new BreadthFirstPaths (info.graph, west);
		int BBB = bfs.distTo(east);    
		System.out.println("BFS: " + info.labels.get(west) + " to " + info.labels.get(east) + " has " + BBB + " edges.");  // should user input here?
		int last1 = -1;
	    double distance1 = 0;
		for (int id : bfs.pathTo(east)) {   
			if(last1 == -1) {
				last1 = id;
			} else {
				GPS gps = info.positions.get(id);
				distance1 += gps.distance(info.positions.get(last1));	
			}
			last1 = id;
		}
		System.out.println("BFS provides answer that is:" + distance1 + " miles");
		
		
		DepthFirstSearchNonRecursive dfs = new DepthFirstSearchNonRecursive(info.graph, west); 
		//if i keep track of the last id, then when i get an id, you have 2 ids
		int last2 = -1;
	    double distance2 = 0;
	    int count = 0;
		for (int id : dfs.pathTo(east)) {   
			if(last2 == -1) {
				last2 = id;
			} else {
				count++;
				GPS gps = info.positions.get(id);
				distance2 += gps.distance(info.positions.get(last2));	
			}
			last2 = id;
		}
		int DDD = count;
		System.out.println("DFS provides answer that is:" + distance2 + " miles with " + DDD + " total edges");
		
		
		EdgeWeightedGraph newWeightedGraph = new EdgeWeightedGraph(info.graph.V());
	
	    double weight = 0;
	    // go through each adj in the graph
		for (int k = 0; k < info.graph.V(); k++) {
			// make new adj for the weighted graph
			for (int adj: info.graph.adj(k)) {
					GPS gps = info.positions.get(adj);
					weight = gps.distance(info.positions.get(k));
					// make new edges
					Edge newEdge = new Edge(k, adj, weight);
					newWeightedGraph.addEdge(newEdge);
			}
		}
		DijkstraUndirectedSP dij = new DijkstraUndirectedSP (newWeightedGraph, west);
		double SSS = dij.distTo(east);
		int numEdge = 0;
		for (Edge e : dij.pathTo(east)) {
			numEdge++;
		}
		System.out.println("Shortest Distance via Dijkstra: " + SSS +" miles with " + numEdge + " total edges.");
	}
}


