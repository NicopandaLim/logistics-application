package facility.facilityOperation.neighbors;


import java.util.ArrayList;

public interface FacilityNeighbors {

    ArrayList<FacilityEdge> addNeighbor(FacilityEdge facilityEdge);

    void neighborsPrinter();
}