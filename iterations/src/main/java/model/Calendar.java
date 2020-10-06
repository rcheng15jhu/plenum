package model;

import java.util.Date;
import java.util.Map;

public class Calendar {
    private int id;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

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
