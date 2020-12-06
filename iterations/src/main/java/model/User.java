package model;

public class User {

    private int id;
    private String name;
    private String password;
    private String salt;
    private String email;
    private String affil;
    private String title;
    private String description;
    private String pic;

   public User(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public User(String name, String password, String salt) {
        this.name = name;
        this.password = password;
        this.salt = salt;
        this.email = "";
        this.affil = "";
        this.title = "";
        this.description = "";
        this.pic = "";
    }

    public User(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAffil() {
        return affil;
    }

    public void setAffil(String affil) {
        this.affil = affil;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getSalt() {
        return salt;
    }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public String getPic() { return pic; }

    public void setPic(String pic) { this.pic = pic; }
}
