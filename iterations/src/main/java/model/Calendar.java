package model;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class Calendar {
    private int id;
    private String name;
    private int userId;
    private int eventId;
    private String blob;

    @Override
    public String toString() {
        return "Calendar{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", userId=" + userId +
                ", eventId=" + eventId +
                ", blob='" + blob + '\'' +
                '}';
    }

    public Calendar(int id, String name, int userId, int eventId, String blob) {
        this.id = id;
        this.name = name;
        this.userId = userId;
        this.eventId = eventId;
        this.blob = blob;
    }

    public Calendar(String name, int userId, int eventId) {
        this.name = name;
        this.userId = userId;
        this.eventId = eventId;
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

    public void setBlob(String blob) {
        this.blob = blob;
    }

    public String getBlob() {
        return this.blob;
    }
}
