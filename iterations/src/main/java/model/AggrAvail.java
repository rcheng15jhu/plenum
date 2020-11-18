package model;

import java.util.List;

public class AggrAvail {
  private String eventTitle;
  private List<AvailableDates> calendars;

  public AggrAvail(String eventTitle, List<AvailableDates> calendars) {
    this.eventTitle = eventTitle;
    this.calendars = calendars;
  }
}
