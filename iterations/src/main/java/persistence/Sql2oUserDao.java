package persistence;

import exception.DaoException;
import model.Event;
import model.User;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;
import security.Encryption;

import java.util.List;

public class Sql2oUserDao implements UserDao{
    private final Sql2o sql2o;

    public Sql2oUserDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public int add(User user) throws DaoException {
        try (Connection con = sql2o.open()) {
            System.out.println("Inside add!");
            String query = "INSERT INTO Users (name, password, salt, email, affil, title, description, pic)" +
                    "VALUES (:name, :password, :salt, '', '', '', '', '')";
            int id = (int) con.createQuery(query, true)
                    .bind(user)
                    .executeUpdate().getKey();
            user.setId(id);
            System.out.println(id);
            return id;
        }
        catch (Sql2oException ex) {
            ex.printStackTrace();
            throw new DaoException();
        }
    }

    @Override
    public List<User> listAll() throws DaoException {
        String sql = "SELECT * FROM Users";
        try (Connection con = sql2o.open()) {
            return con.createQuery(sql).executeAndFetch(User.class);
        }
        catch (Sql2oException ex) {
            throw new DaoException();
        }
    }

    public boolean checkCred(String name, String pass) throws DaoException {
        String sql = "SELECT * FROM Users WHERE name = :name";
        try (Connection con = sql2o.open()) {

            List<User> user = con.createQuery(sql)
                    .addParameter("name", name)
                    .executeAndFetch(User.class);

            if (user.size() == 0) {
                return false;
            }

            String pword = user.get(0).getPassword();
            String salt = user.get(0).getSalt();

            if (pword.equals(Encryption.sha2_hash(pass, salt))) {
                return true;
            }
            else {
                return false;
            }
        }
        catch (Sql2oException ex) {
            throw new DaoException();
        }
    }

    public User getUserFromName(String name) throws DaoException {
        String sql = "SELECT * FROM Users WHERE name = :name";
        try (Connection con = sql2o.open()) {
            return con.createQuery(sql)
                    .addParameter("name", name)
                    .executeAndFetch(User.class).get(0);
        }
        catch (Sql2oException ex) {
            ex.printStackTrace();
            throw new DaoException();
        }
    }

    public User getUserFromId(int userId) throws DaoException {
        String sql = "SELECT * FROM Users WHERE id = :id";
        try (Connection con = sql2o.open()) {
            return con.createQuery(sql)
                    .addParameter("id", userId)
                    .executeAndFetch(User.class).get(0);
        }
        catch (Sql2oException ex) {
            ex.printStackTrace();
            throw new DaoException();
        }
    }

    @Override
    public boolean delete(User user) throws DaoException {
        try (Connection con = sql2o.open()) {
            String query = "DELETE FROM Users WHERE id = :id";
            con.createQuery(query)
                    .bind(user)
                    .executeUpdate();
            return true;
        }
        catch (Sql2oException ex) {
            ex.printStackTrace();
            throw new DaoException();
        }
    }

    public boolean passwordcheck(String name, String pword, String newpword) throws DaoException {
        String sql = "SELECT * FROM Users WHERE name = :name";
        String sql2 = "UPDATE Users SET password = :newpword WHERE name = :name";
        try (Connection con = sql2o.open()) {
            User u = con.createQuery(sql)
                    .addParameter("name", name)
                    .executeAndFetch(User.class).get(0);
            if (Encryption.sha2_hash(pword, u.getSalt()).equals(u.getPassword())) {
                con.createQuery(sql2)
                        .addParameter("newpword", Encryption.sha2_hash(newpword, u.getSalt()))
                        .addParameter("name", name)
                        .executeUpdate();
                return true;
            }
            else
                return false;
        }
        catch (Sql2oException ex) {
            ex.printStackTrace();
            throw new DaoException();
        }
    }

    public boolean setemail(User u, String email) {
        try (Connection con = sql2o.open()) {
            String query = "UPDATE Users SET email = :email WHERE name = :name";
            con.createQuery(query)
                    .addParameter("email", email)
                    .addParameter("name", u.getName())
                    .executeUpdate();
            return true;
        }
        catch (Sql2oException ex) {
            ex.printStackTrace();
            throw new DaoException();
        }
    }

    public boolean setaffil(User u, String affil) {
        try (Connection con = sql2o.open()) {
            String query = "UPDATE Users SET affil = :affil WHERE name = :name";
            con.createQuery(query)
                    .addParameter("affil", affil)
                    .addParameter("name", u.getName())
                    .executeUpdate();
            return true;
        }
        catch (Sql2oException ex) {
            ex.printStackTrace();
            throw new DaoException();
        }
    }

    public boolean settitle(User u, String title) {
        try (Connection con = sql2o.open()) {
            String query = "UPDATE Users SET title = :title WHERE name = :name";
            con.createQuery(query)
                    .addParameter("title", title)
                    .addParameter("name", u.getName())
                    .executeUpdate();
            return true;
        }
        catch (Sql2oException ex) {
            ex.printStackTrace();
            throw new DaoException();
        }
    }

    public boolean setdescription(User u, String description) {
        try (Connection con = sql2o.open()) {
            String query = "UPDATE Users SET description = :description WHERE name = :name";
            con.createQuery(query)
                    .addParameter("description", description)
                    .addParameter("name", u.getName())
                    .executeUpdate();
            return true;
        }
        catch (Sql2oException ex) {
            ex.printStackTrace();
            throw new DaoException();
        }
    }
}
