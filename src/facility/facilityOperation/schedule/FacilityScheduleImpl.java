package facility.facilityOperation.schedule;

import java.util.ArrayList;
import java.util.HashMap;

public class FacilityScheduleImpl implements FacilitySchedule{

        private HashMap<Integer, Integer> scheduleList = new HashMap<>();

        // to get the last day to print to
        private int getLastDay(){
            int lastDay = 20;
            for(int day : scheduleList.keySet()){
                if(day > 20){
                    lastDay = day;
                }
            }
            return lastDay;
        }

        private ArrayList<Integer> createPrintList(int rate){
            ArrayList<Integer> valueList= new ArrayList<>();
            for(int i = 1; i <= getLastDay(); i++){
                if(scheduleList.containsKey(i)){
                    valueList.add(scheduleList.get(i));
                }else{
                    valueList.add(rate);
                }
            }
            return valueList;
        }

        public double processCheck(int processQuantity, String orderDay, int rate){
            int quantity = processQuantity;
            int day = Integer.parseInt(orderDay.substring(4));
            double processDay = day - 1;

            for(int i=day; ; i++){
                if(scheduleList.containsKey(i)){
                   if(quantity > scheduleList.get(i)){
                       quantity = quantity - scheduleList.get(i);
                       processDay = processDay + 1;
                   } else{
                       return (processDay + (quantity/rate));
                   }
                }else{
                    if (quantity > rate){
                        quantity = quantity - rate;
                        processDay = processDay + 1;
                    }else{
                        processDay = processDay + 1;
                        return (processDay);
                    }
                }
            }
        }

        public double getProcDays(int processQuantity, String orderDay, int rate){
            int quantity = processQuantity;
            int day = Integer.parseInt(orderDay.substring(4));
            double daysToProc = 0.0;

            for(int i=day; ; i++){
                if(scheduleList.containsKey(i)){
                    if(quantity > scheduleList.get(i)){
                        quantity = quantity - scheduleList.get(i);
                        daysToProc = daysToProc + ((double)scheduleList.get(i)/(double)rate);
                    } else{
                        return (daysToProc + ((double)quantity/(double)rate));
                    }
                }else{
                    if (quantity > rate){
                        quantity = quantity - rate;
                        daysToProc = daysToProc + 1;
                    }else{
                        double quant = (double)quantity;
                        double rat = (double)rate;
                        daysToProc = daysToProc + (quant/rat);
                        return (daysToProc);
                    }
                }
            }
        }

        public void updateSche(int itemQuantity, String orderDay, int rate){
            int itemNum = itemQuantity;
            int day = Integer.parseInt(orderDay.substring(4));
            for (int i=day; ; i++){
                if(scheduleList.containsKey(i)){
                    if(itemNum > scheduleList.get(i)){
                        itemNum = itemNum - scheduleList.get(i);
                        scheduleList.put(i, 0);
                    }else{
                        scheduleList.put(i, scheduleList.get(i)-itemNum);
                        return;
                    }
                }else{
                    if (itemNum > rate){
                        itemNum = itemNum - rate;
                        scheduleList.put(i, 0);
                    }else{
                        scheduleList.put(i, rate-itemNum);
                        return;
                    }
                }
            }
        }

        public void schedulePrinter(int rate){
            ArrayList<Integer> valueList = createPrintList(rate);
            System.out.println("Schedule:");
            System.out.print("Day:        ");
            for(int i=1; i <= getLastDay(); i++){
                System.out.print(String.format("%2d", i) + " ");
            }
            System.out.println("");
            System.out.print("Available:  ");
            for(long value : valueList){
                System.out.print(String.format("%2d", value) + " ");
            }
            System.out.println(" ");
        }
}
