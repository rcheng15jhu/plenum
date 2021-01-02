package model;

import java.util.List;
import java.util.stream.Stream;

public class AvailableDates {
    private String userName;
    private String calendarTitle;
    private List<AvailableDate> dates;


    public AvailableDates(String userName, String calendarTitle) {
        this.userName = userName;
        this.calendarTitle = calendarTitle;
    }

    public static AvailableDates createFromAvailableDate(String userName, String calendarTitle, List<AvailableDate> dates) {
        AvailableDates a = new AvailableDates(userName, calendarTitle);
        a.dates = dates;
        return a;
    }


    public static AvailableDates createFromAvailability(String userName, String calendarTitle, List<Availability> avails) {
        AvailableDates a = new AvailableDates(userName, calendarTitle);
        a.dates = AvailableDate.aggregateAvails(avails);
        return a;
    }

    public Stream<Availability> getAvailabilityStream(int calendarId) {
        return dates.parallelStream()
                .flatMap(date -> date.convertToAvailability(calendarId));
    }

}
