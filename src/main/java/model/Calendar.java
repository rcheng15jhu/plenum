package model;

public class Calendar {
    private int id;
    private String title;
    private int userId;

    @Override
    public String toString() {
        return "Calendar{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", userId=" + userId +
                '}';
    }

    public Calendar(int id, String title, int userId) {
        this.id = id;
        this.title = title;
        this.userId = userId;
    }

    public Calendar(String title, int userId) {
        this.title = title;
        this.userId = userId;
    }

    public Calendar(int id) {
        this.id = id;
        title = "";
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
