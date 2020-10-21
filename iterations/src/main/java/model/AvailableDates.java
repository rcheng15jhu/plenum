package model;

import java.util.List;
import java.util.stream.Stream;

public class AvailableDates {
    private List<AvailableDate> dates;

    public static AvailableDates createFromAvailableDate(List<AvailableDate> dates) {
        AvailableDates a = new AvailableDates();
        a.dates = dates;
        return a;
    }

    public static AvailableDates createFromAvailability(List<Availability> avails) {
        AvailableDates a = new AvailableDates();
        a.dates = AvailableDate.aggregateAvails(avails);
        return a;
    }

    public Stream<Availability> getAvailabilityStream(int calendarId) {
        return dates.parallelStream()
                .flatMap(date -> date.convertToAvailability(calendarId));
    }

}
