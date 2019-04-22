package facility;


import exceptions.DataValidationException;
import facility.facilityOperation.Facility;
import facility.facilityOperation.FacilityImpl;

public class FacilityFactory {

    public static Facility createFacility(String location, int rate, int cost)
            throws DataValidationException {


        return new FacilityImpl(location, rate, cost);


    }


}
