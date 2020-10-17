package model;

import java.util.List;

public class Event {
    private int id;
    private String title;
    private Range validTimeRange;

    public Event(int id) {
        this.id = id;
        title = "";
    }

    public Event(String title, int id) {
        this.title = title;
        this.id = id;
    }

    public Event(String title, Range validTimeRange) {
        this.title = title;
        this.validTimeRange = validTimeRange;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Range getValidTimeRange() {
        return validTimeRange;
    }

    public void setValidTimeRange(Range validTimeRange) {
        this.validTimeRange = validTimeRange;
    }

    @Override
    public String toString() {
        return "Event{" +
                "title='" + title + '\'' +
                ", id=" + id +
                '}';
    }
}