package model;

public class Availability {
  private int id;
  private int calendarId;
  private int date;
  private int qHour;

  public Availability(int calendarId, int date, int qHour) {
    this.calendarId = calendarId;
    this.date = date;
    this.qHour = qHour;
  }

  public Availability(int id) {
    this.id = id;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getCalendarId() {
    return calendarId;
  }

  public void setCalendarId(int calendarId) {
    this.calendarId = calendarId;
  }

  @Override
  public String toString() {
    return "Availability{" +
            "id=" + id +
            ", calendarId=" + calendarId +
            ", date=" + date +
            ", qAvail=" + qHour +
            '}';
  }

  public int getDate() {
    return date;
  }

  public void setDate(int date) {
    this.date = date;
  }

  public int getqHour() {
    return qHour;
  }

  public void setqHour(int qHour) {
    this.qHour = qHour;
  }
}
