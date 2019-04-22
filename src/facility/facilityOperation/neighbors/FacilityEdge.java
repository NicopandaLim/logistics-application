package facility.facilityOperation.neighbors;

import exceptions.DataValidationException;
import facility.facilityOperation.Facility;

public class FacilityEdge {
    private Facility target;
    private double weight = 0;

    // we don't need setter in this class, we can initialize in c'tor
    public FacilityEdge(Facility target, double weight) throws DataValidationException{
        setTarget(target);
        setWeight(weight);
    }

    public Facility getTarget(){
        return this.target;
    }

    public double getWeight(){
        return weight;
    }

    private void setTarget(Facility target){
        this.target = target;
    }

    private void setWeight(double weight) throws DataValidationException {
        if (weight <= 0)
            throw new DataValidationException(" Invalid distance value passed into"
                    + " FacilityEdge.setWeight()");
        this.weight = weight;
    }

    public double getDistanceInDay(){
        double rate = 8 * 50;
        return(weight/rate);
    }

    public String toString(){
        return String.format("%s (%1.1fd); ",this.target.getLocation(), this.getDistanceInDay());
     //   return this.target.getLocation() + " (" + this.getDistanceInDay() + "d); ";
    }
}
