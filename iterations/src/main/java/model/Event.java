package model;

import java.util.List;

public class Event {
    private int id;
    private String title;
    private int startTime;
    private int endTime;
//    private Range validTimeRange;

    public Event(int id) {
        this.id = id;
        title = "";
    }

    public Event(String title, int id) {
        this.title = title;
        this.id = id;
    }

    public Event(String title, int startTime, int endTime) {
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
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

//    public Range getValidTimeRange() {
//        return validTimeRange;
//    }
//
//    public void setValidTimeRange(Range validTimeRange) {
//        this.validTimeRange = validTimeRange;
//    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}