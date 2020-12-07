package model;

import java.util.List;

public class AggrAvail {
  private String eventTitle;
  private int startTime;
  private int endTime;
  private List<AvailableDates> calendars;

  public AggrAvail(String eventTitle, List<AvailableDates> calendars) {
    this.eventTitle = eventTitle;
    this.calendars = calendars;
  }

  public AggrAvail(Event event, List<AvailableDates> calendars) {
    this.eventTitle = event.getTitle();
    this.startTime = event.getStartTime();
    this.endTime = event.getEndTime();
    this.calendars = calendars;
  }
}
