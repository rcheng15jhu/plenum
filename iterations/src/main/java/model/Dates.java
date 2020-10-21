package model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Dates {
    private int date;
    private List<Integer> times;

    public Dates(){
        date = 0;
        times = new ArrayList<Integer>();
    }

    public Dates(int date){
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

    private void aggregateTimes(Dates d, List<Availability> avails){
        for(Availability a: avails) {
            if(d.getDate() == a.getDate()) {
                System.out.println("adding time");
                d.addTime(a.getqAvail());
            }
        }
    }

    public List<Dates> aggregateAvails(List<Availability> avails){
        List<Dates> dateList = new ArrayList<Dates>();
        HashSet<Integer> dates = new HashSet<Integer>();
        for(Availability a: avails) {
            if(!dates.contains(a.getDate())) {
                Dates d = new Dates(a.getDate());
                aggregateTimes(d, avails);
                dates.add(a.getDate());
                dateList.add(d);
            }
        }

        return dateList;
    }
}
