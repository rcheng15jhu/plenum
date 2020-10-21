package model;

import java.sql.Date;

public class Availability {
  private int id;
  private int calendarId;
  private int date;
  private int qAvail;

  public Availability(int calendarId, int date, int qAvail) {
    this.calendarId = calendarId;
    this.date = date;
    this.qAvail = qAvail;
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
            ", qAvail=" + qAvail +
            '}';
  }

  public int getDate() {
    return date;
  }

  public void setDate(int date) {
    this.date = date;
  }

  public int getqAvail() {
    return qAvail;
  }

  public void setqAvail(int qAvail) {
    this.qAvail = qAvail;
  }
}
