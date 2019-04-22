package facility.facilityOperation.neighbors;


import java.util.ArrayList;

public class FacilityNeighborsImpl implements FacilityNeighbors{

    private ArrayList<FacilityEdge> neighbors = new ArrayList<>();

    @Override
    public ArrayList<FacilityEdge> addNeighbor(FacilityEdge facilityEdge){
        neighbors.add(facilityEdge);
        return neighbors;
    }

    @Override
    public void neighborsPrinter(){
        System.out.println("Direct Links: ");
        for (FacilityEdge facEdge : neighbors){
            System.out.print(facEdge);
        }
        System.out.println(" ");
    }
}
