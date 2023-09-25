package dlnguyen.hw4;

import algs.hw4.map.FilterAirport;
import algs.hw4.map.FlightMap;
import algs.hw4.map.Information;
import dlnguyen.hw4.FilterLower48;

public class Overlap {
	public static void main(String[] args) {
		FilterAirport justLower48 = new FilterLower48();
		
		Information delta = FlightMap.undirectedGraphFromResources("delta.json", justLower48);
		Information southwest = FlightMap.undirectedGraphFromResources("southwest.json", justLower48);

		for (Integer southwestKey : southwest.labels.keys()) {
			boolean keeptrack = true;
			
			String southwestInfo = southwest.labels.get(southwestKey);
			for (Integer deltaKey : delta.labels.keys()) {
				String deltaInfo = delta.labels.get(deltaKey);
				if (southwestInfo.equals(deltaInfo)) {
					keeptrack = false;
					break;
				}
			}
			if (keeptrack == true) {
				System.out.println(southwestInfo);
			}
		}
	}
}
