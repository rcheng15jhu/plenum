package simple2meet;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class Calendar {
  private User.UserCred user;
  private Range validTimeRange;
  private Map<Date, List<Range>> days;
}
