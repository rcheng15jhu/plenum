package model;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class Calendar {
    private int id;
    private String name;
    private int userId;

    @Override
    public String toString() {
        return "Calendar{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", userId=" + userId +
                '}';
    }

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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
