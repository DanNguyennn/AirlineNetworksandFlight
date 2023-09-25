package dlnguyen.hw4;

import algs.days.day20.DepthFirstSearchNonRecursive;
import algs.hw4.map.FilterAirport;
import algs.hw4.map.FlightMap;
import algs.hw4.map.Information;

public class Connected {
	public static void main(String[] args) {
		FilterAirport justLower48 = new FilterLower48();

		Information delta = FlightMap.undirectedGraphFromResources("delta.json", justLower48);
		Information southwest = FlightMap.undirectedGraphFromResources("southwest.json", justLower48);

		int dBos = 0;
		int sBos = 0;
		for (Integer deltaKey : delta.labels.keys()) {
			String deltaInfo = delta.labels.get(deltaKey);

			if (deltaInfo.equals("KBOS")) {
				dBos = deltaKey;
				break;
			}
		}

		for (Integer southwestKey : southwest.labels.keys()) {
			String southwestInfo = delta.labels.get(southwestKey);

			if (southwestInfo.equals("KBOS")) {
				sBos = southwestKey;
				break;
			}
		}
		
        DepthFirstSearchNonRecursive deltaDFS = new DepthFirstSearchNonRecursive(delta.graph, dBos);
		DepthFirstSearchNonRecursive southwestDFS = new DepthFirstSearchNonRecursive(southwest.graph, sBos);
        
        String airline = "";
        String airline1 = "";
	        for (int key : delta.labels.keys()) {
	        	if (deltaDFS.hasPathTo(key) == false) {
	        		if (airline1 == "") {
	        			if (airline == "") {
	        				airline = "Delta";
	        		        System.out.println("The name of the airline is " + airline);
		        	        System.out.println("The airports that cannot be reached from KBOS using " + airline + " are:");
	        			}
	        			System.out.println(delta.labels.get(key)); 
	        		}
	        		else {
	        			System.out.println(delta.labels.get(key)); 
	        		}
	        	}
	        }
        
        for (int key : southwest.labels.keys()) {
        	if (southwestDFS.hasPathTo(key) == false) {
        		if (airline1 == "") {
        			if (airline == "") {
        				airline = "Southwest";
        		        System.out.println("The name of the airline is " + airline);
	        	        System.out.println("The airports that cannot be reached from KBOS using " + airline + " are:");
        			}
        			System.out.println(southwest.labels.get(key)); 
        		}
        		else {
        			System.out.println(southwest.labels.get(key)); 
        		}
        	}
        }   
	}
}
