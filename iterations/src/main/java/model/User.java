package model;

import java.util.Map;

public class User {

    private UserCred cred;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    //private Map<String, Object> connectedAccountAuthentications;
    private Map<String, Calendar> storedCalendars;
    private Map<String, Event> events;

    public void storeCalender(String name, Calendar calendar) {
        storedCalendars.put(name, calendar);
    }

    public void addEvent(String name, Event event) {
        events.put(name, event);
    }
}
