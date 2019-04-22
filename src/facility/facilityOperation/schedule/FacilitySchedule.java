package facility.facilityOperation.schedule;


public interface FacilitySchedule {

    double processCheck(int processQuantity, String orderDay, int rate);

    double getProcDays(int processQuantity, String orderDay, int rate);

    void updateSche(int itemQuantity, String orderDay, int rate);

    void schedulePrinter(int rate);
}
