package shortestPath;


import exceptions.*;
import facility.FacilityManager;
import javafx.util.Pair;

import java.util.ArrayList;

public final class ShortestProcessor {

    FacilityManager facilityManager = FacilityManager.getInstance();
    ArrayList<Graph.Edge> graph = new ArrayList<>();

    public ShortestProcessor()
            throws NeighborsValidationException, DataValidationException, NullFacilityException{
        createGraph();
    }

    public double getDistOf2Facilities(String fac1, String fac2)
            throws NeighborsValidationException, DataValidationException, NullFacilityException{

        return  facilityManager.getDistOf2Facilities(fac1, fac2);

    }

    public void createGraph()
            throws NeighborsValidationException, DataValidationException, NullFacilityException{

        for(Pair<String, String> facs : facilityManager.createFacPairs()){
            String vi = facs.getKey();
            String v2 = facs.getValue();
            double dist = getDistOf2Facilities(facs.getKey(), facs.getValue());
            graph.add(new Graph.Edge(vi, v2, dist));
        }
    }

    //for test
    public void shortestPath(String start, String end) throws NullVertexException {
        System.out.println(start + " to " + end + ":");
        Graph g =  new Graph(graph);
        g.dijkstra(start);
        g.printPath(end);
    }

    public String getTravelTime(String start, String end) throws NullVertexException {
        Graph g =  new Graph(graph);
        g.dijkstra(start);
        String proceDays = g.getTraTime(end);

        return proceDays;
    }

}
