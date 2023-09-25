package dlnguyen.hw4;

import algs.hw4.map.FilterAirport;
import algs.hw4.map.FlightMap;
import algs.hw4.map.Information;
import edu.princeton.cs.algs4.AdjMatrixEdgeWeightedDigraph;
import edu.princeton.cs.algs4.DirectedEdge;
import edu.princeton.cs.algs4.FloydWarshall;

public class LongestOfShortest {
	
	public static AdjMatrixEdgeWeightedDigraph matrixRepresentationGraph(Information info) {
		AdjMatrixEdgeWeightedDigraph weightedGraph = new AdjMatrixEdgeWeightedDigraph(info.graph.V());
		
		for (int i = 0; i < info.graph.V(); i++) {
			for (Integer j : info.graph.adj(i)) {
				double distance = info.positions.get(i).distance(info.positions.get(j));
				weightedGraph.addEdge(new DirectedEdge(i, j, distance));
			}
		}
		
		return weightedGraph;
	}
	
	public static void table(Information info, String airline) {
		double currentMax = 0, totalEfficiency = 0, actualMax = 0;
		int from = -1;
		int to = -1;
		int total = info.graph.V() * (info.graph.V() - 1 ) / 2;
		AdjMatrixEdgeWeightedDigraph weightedGraph = matrixRepresentationGraph(info);
		FloydWarshall path = new FloydWarshall(weightedGraph);
		
		for (int i = 0; i < info.graph.V(); i++) {
			for (int j = i + 1; j < info.graph.V(); j++) {
				double currentDistance = path.dist(i, j);
				double actualDistance = info.positions.get(i).distance(info.positions.get(j));
				
				if (currentDistance != Double.POSITIVE_INFINITY) totalEfficiency += (currentDistance / actualDistance);
				else total--;
				
				if (currentMax < currentDistance && currentDistance != Double.POSITIVE_INFINITY) {
					from = i;
					to = j;
					currentMax = currentDistance;
					actualMax = actualDistance;
				}
			}
		}

		System.out.println(String.format("%s : Total Flight Distance is %.12f but airports are only", airline, currentMax));
		System.out.println(String.format("%.12f miles apart.", actualMax));
		for (DirectedEdge l : path.path(from, to)) {
			System.out.println(String.format("%s -> %s for %.12f", info.labels.get(l.from()), info.labels.get(l.to()),
					info.positions.get(l.from()).distance(info.positions.get(l.to()))));
		}
		System.out.println(String.format("Average Efficiency: %.12f", totalEfficiency / total));
		System.out.println();
	}
	
	
	public static void main(String[] args) {
		FilterAirport justLower48 = new FilterLower48();
		
		Information delta = FlightMap.undirectedGraphFromResources("delta.json", justLower48);
		Information southwest = FlightMap.undirectedGraphFromResources("southwest.json", justLower48);
		
		table(delta, "Delta");
		table(southwest, "Southwest");
	}
}
