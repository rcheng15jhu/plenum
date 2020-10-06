package model;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class Calendar {
    private int id;
    private String name;
    private int userId;
    private List<Event> participantEvents;
    private List<User> participantUsers;

    public Calendar(int id, String name, int userId) {
        this.id = id;
        this.name = name;
        this.userId = userId;
    }

    public Calendar(String name, int userId) {
        this.name = name;
        this.userId = userId;
    }

    public Calendar(int id) {
        this.id = id;
        name = "";
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
