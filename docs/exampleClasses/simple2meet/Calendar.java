package simple2meet;

import java.util.Date;
import java.util.Map;

public class Calendar {
  private User.UserCred user;
  private int startOffset;
  private Map<Date, boolean[]> dateRanges;
}
