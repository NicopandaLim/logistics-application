
import exceptions.*;
import facility.FacilityManager;
import orderProcessor.OrderProcessManager;

public class Main {

    public static void main(String[] args)
            throws NullVertexException, NeighborsValidationException,
            DataValidationException, NullFacilityException{

        FacilityManager.getInstance().printReport();
        System.out.println("---------------------------------------------------------------------");
        OrderProcessManager po = new OrderProcessManager();
        po.toProcess();
        System.out.println("---------------------------------------------------------------------");
        FacilityManager.getInstance().printReport();


    }
}