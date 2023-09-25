package dlnguyen.hw4;

import algs.hw4.map.FilterAirport;
import algs.hw4.map.FlightMap;
import algs.hw4.map.Information;
import dlnguyen.hw4.FilterLower48;

public class Hub {
	public static void main(String[] args) {
		FilterAirport justLower48 = new FilterLower48();

		Information delta = FlightMap.undirectedGraphFromResources("delta.json", justLower48);
		Information southwest = FlightMap.undirectedGraphFromResources("southwest.json", justLower48);
		
		System.out.println("DELTA");
		for (Integer deltaKey : delta.labels.keys()) {
			if (delta.graph.degree(deltaKey) > 75) {
				System.out.println(delta.labels.get(deltaKey) + "\t" + delta.graph.degree(deltaKey));
			}
		}
		
		System.out.println("\n" + "SOUTHWEST");
		for (Integer southwestKey : southwest.labels.keys()) {
			if (southwest.graph.degree(southwestKey) > 75) {
				System.out.println(southwest.labels.get(southwestKey) + "\t" + southwest.graph.degree(southwestKey));
			}
		}
	}
}
