package simple2meet;

import java.util.Date;
import java.util.Map;

public class Calendar {
  private UserCred user;
  private int startOffset;
  private Map<Date, boolean[]> dateRanges;

  public boolean[] getAvailabilityOn(Date date) {
    return null;
  }

  public boolean editAvailability(String passwd, Date date, boolean[] times) {
    return false;
  }
}
