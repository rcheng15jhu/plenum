package model;

import java.util.List;

public class Event {
    private int id;
    private String title;
    private Range validTimeRange;
    //private List<Calendar> participantCalendars;

    public Event(int id) {
        this.id = id;
        title = "";
    }

    public Event(String title, int id) {
        this.title = title;
        this.id = id;
    }

    public Event(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Event{" +
                "title='" + title + '\'' +
                ", id=" + id +
                '}';
    }

//    public void addCalendar(Calendar calendar) {
//        participantCalendars.add(calendar);
//    }

  /*public List<Calendar> getCalendars() {
    return Collections.unmodifiableList(participantCalendars);
  }*/

  /*public AggregateCalendar getAggregateCalendar(UserCred user) {
    //Make a aggregateCalendar with everyone except the user passed in
    return null;
  }*/
}