package dlnguyen.hw4;

import algs.hw4.map.FilterAirport;
import algs.hw4.map.FlightMap;
import algs.hw4.map.GPS;
import algs.hw4.map.Information;

public class FlightStats {
	public static void main(String[] args) {
		FilterAirport justLower48 = new FilterLower48();

		Information delta = FlightMap.undirectedGraphFromResources("delta.json", justLower48);
		Information southwest = FlightMap.undirectedGraphFromResources("southwest.json", justLower48);

		Histogram deltaHisto =  new Histogram();
		Histogram southwestHisto = new Histogram();

		int shortest = Integer.MAX_VALUE;
		int longest = 0;
		int shortestFrom = 0;
		int shortestTo = 0;
		int longestFrom = 0;
		int longestTo = 0;

		int total = 0;
		int count = 0;

		// get a start location
		for (int key : delta.labels.keys()) {			
			GPS start = new GPS(delta.positions.get(key).latitude, delta.positions.get(key).longitude);
			for (int adj : delta.graph.adj(key)) {
				GPS arrive = new GPS(delta.positions.get(adj).latitude, delta.positions.get(adj).longitude);
				int distance = (int) start.distance(arrive);
				if(adj < key)   deltaHisto.record(distance);
					count++;
					total += distance;

					if (distance < shortest) {
						shortest = distance;
						shortestFrom = key;
						shortestTo = adj;
					} 

					if(distance > longest) {
						longest = distance;
						longestFrom = key;
						longestTo = adj;
					}
				}
		}

		System.out.println("Shortest flight for Delta is from " + delta.labels.get(shortestFrom) + " to " + delta.labels.get(shortestTo) + " for " + shortest + " miles." );
		System.out.println("Longest flight for Delta is from " + delta.labels.get(longestFrom) + " to " + delta.labels.get(longestTo) + " for " + longest + " miles.");
		System.out.println("Average Delta flight distance = " + total/count + "\n");

		shortest = 9999;
		longest = 0;
		shortestFrom = 0;
		shortestTo = 0;
		longestFrom = 0;
		longestTo = 0;

		total = 0;
		count = 0;

		// get a start location
		for (int key : southwest.labels.keys()) {
			GPS gps1 = new GPS(southwest.positions.get(key).latitude, southwest.positions.get(key).longitude);
			// get possible arrive location
			for (int adj : southwest.graph.adj(key)) {
				GPS gps2 = new GPS(southwest.positions.get(adj).latitude, southwest.positions.get(adj).longitude);
				int distance = (int) gps1.distance(gps2);

				if (adj < key) 	southwestHisto.record(distance); 
					count++;
					total += distance;

					if (distance < shortest) {
						shortest = distance;
						shortestFrom = key;
						shortestTo = adj;
					} 

					if(distance > longest) {
						longest = distance;
						longestFrom = key;
						longestTo = adj;
					}
			}
		}

		System.out.println("Shortest flight for Southwest is from " + southwest.labels.get(shortestFrom) + " to " + southwest.labels.get(shortestTo) + " for " + shortest + " miles." );
		System.out.println("Longest flight for Southwest is from " + southwest.labels.get(longestFrom) + " to " + southwest.labels.get(longestTo) + " for " + longest + " miles.");
		System.out.println("Average Southwest flight distance = " + total/count  + "\n");

		deltaHisto.report(500);
		System.out.println();
		southwestHisto.report(500);
	}
}
