package model;

public class Connections {
  private int id;
  private int eventId;
  private int calendarId;
  private int userId;

  public Connections(int eventId, int calendarId, int userId) {
    this.eventId = eventId;
    this.calendarId = calendarId;
    this.userId = userId;
  }

  public int getId() {
    return id;
  }

  public int getEventId() {
    return eventId;
  }

  public int getCalendarId() {
    return calendarId;
  }

  public int getUserId() {
    return userId;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setEventId(int eventId) {
    this.eventId = eventId;
  }

  public void setCalendarId(int calendarId) {
    this.calendarId = calendarId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  @Override
  public String toString() {
    return "Connections{" +
            "id=" + id +
            ", eventId=" + eventId +
            ", calendarId=" + calendarId +
            ", userId=" + userId +
            '}';
  }


}
