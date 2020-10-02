package simple2meet;

import java.util.Collections;
import java.util.List;

public class Event {
  private UserCred host;
  private String name;
  private int uniqueId;
  private Range validTimeRange;
  private List<Calendar> participantCalendars;

  public void addCalendar(Calendar calendar) {
    participantCalendars.add(calendar);
  }

  /*public List<Calendar> getCalendars() {
    return Collections.unmodifiableList(participantCalendars);
  }*/

  /*public AggregateCalendar getAggregateCalendar(UserCred user) {
    //Make a aggregateCalendar with everyone except the user passed in
    return null;
  }*/
}

