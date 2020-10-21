package model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Stream;

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

    private void aggregateTimes(List<Availability> avails){
        for(Availability a: avails) {
            if(getDate() == a.getDate()) {
                addTime(a.getqHour());
            }
        }
    }

    public static List<AvailableDate> aggregateAvails(List<Availability> avails){
        List<AvailableDate> dateList = new ArrayList<AvailableDate>();
        HashSet<Integer> dates = new HashSet<Integer>();
        for(Availability a: avails) {
            if(!dates.contains(a.getDate())) {
                AvailableDate d = new AvailableDate(a.getDate());
                d.aggregateTimes(avails);
                dates.add(a.getDate());
                dateList.add(d);
            }
        }

        return dateList;
    }

    public Stream<Availability> convertToAvailability(int calendarId) {
        return times.parallelStream()
                .map(time -> new Availability(calendarId, date, time));
    }
}
