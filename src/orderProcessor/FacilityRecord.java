package orderProcessor;


public class FacilityRecord {

    String facHasItem;
    int itemsNumber;
    double processDay;
    double daysToProc;
    double travelDay;

    public FacilityRecord(String facility, int number,
                          double processDay, double days, double travelDay){
        setFacHasItem(facility);
        setItemsNumber(number);
        setProcessDay(processDay);
        setDaysToProc(days);
        setTravelDay(travelDay);

    }

    private void setFacHasItem(String facility){
        facHasItem = facility;
    }

    public void setItemsNumber(int number){
        itemsNumber = number;
    }

    private void setProcessDay(double processDay) {
        this.processDay = processDay/*Math.round(processDay*100.0)/100.0*/;
    }

    private void setDaysToProc(double days){daysToProc = days/*Math.round(days*100.0)/100.0*/;}

    private void setTravelDay(double travelDay) {
        this.travelDay = Math.ceil(travelDay);
    }


    public String getFacHasItem() {
        return facHasItem;
    }

    public int getItemsNumber() {
        return itemsNumber;
    }

    public double getDaysToProc(){
        return daysToProc;
    }

    public double getTravelDay() {
        return travelDay;
    }


    public String getArrivalDay() {
        int procDay = (int)processDay;
        int arrDayInt = (int)travelDay + procDay;
        String arrDay = "Day " + Integer.toString(arrDayInt);
        return arrDay;
    }

    public int getArrDayInt(){
        int arrDayInt = Integer.parseInt(getArrivalDay().substring(4));
        return arrDayInt;
    }


    public String toString(){
        return String.format("facility: %s, itemNum: %d, processDay: %f, daysToProcess: %f, travelDay: %f,"
                               +" arrivalDay: %s", facHasItem, itemsNumber, processDay, daysToProc,
                               travelDay, getArrivalDay());
    }

}
