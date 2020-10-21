package model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class AvailableDate {
    private int date;
    private List<Integer> times;

    public AvailableDate(){
        date = 0;
        times = new ArrayList<Integer>();
    }

    public AvailableDate(int date){
        this.date = date;
        times = new ArrayList<Integer>();
    }

    private int getDate(){
        return date;
    }

    private void addTime(int qAvail){
        if(!times.contains(qAvail)){
            times.add(qAvail);
        }
    }

    private void aggregateTimes(AvailableDate d, List<Availability> avails){
        for(Availability a: avails) {
            if(d.getDate() == a.getDate()) {
                d.addTime(a.getqAvail());
            }
        }
    }

    public List<AvailableDate> aggregateAvails(List<Availability> avails){
        List<AvailableDate> dateList = new ArrayList<AvailableDate>();
        HashSet<Integer> dates = new HashSet<Integer>();
        for(Availability a: avails) {
            if(!dates.contains(a.getDate())) {
                AvailableDate d = new AvailableDate(a.getDate());
                aggregateTimes(d, avails);
                dates.add(a.getDate());
                dateList.add(d);
            }
        }

        return dateList;
    }
}
