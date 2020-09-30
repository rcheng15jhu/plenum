package simple2meet;

import java.util.Map;

public class User {

  public class UserCred {
    private String name;
    private int uniqueId;
  }
  private Map<String, Object> connectedAccountAuthentications;
  private Map<String, Calendar> storedCalendars;
  private Map<String, Event> events;

  public void storeCalender(String name, Calendar calendar) {
    storedCalendars.put(name, calendar);
  }

  public void addEvent(String name, Event event) {
    events.put(name, event);
  }
}
