package persistence;

import exception.DaoException;
import model.Event;
import model.User;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

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
            String query = "INSERT INTO Users (name, password)" +
                    "VALUES (:name, :password)";
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
            String pword = con.createQuery(sql)
                    .addParameter("name", name)
                    .executeAndFetch(User.class).get(0).getPassword();
            if (pword.equals(pass)) {
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

    public int getId(String name) throws DaoException {
        String sql = "SELECT id FROM Users WHERE name = :name";
        try (Connection con = sql2o.open()) {
            return con.createQuery(sql)
                    .addParameter("name", name)
                    .executeAndFetch(User.class).get(0).getId();
        }
        catch (Sql2oException ex) {
            throw new DaoException();
        }
    }

    @Override
    public boolean delete(User user) throws DaoException {
        try (Connection con = sql2o.open()) {
            String preQ = "PRAGMA foreign_keys = ON;";
            con.createQuery(preQ).executeUpdate();

            String query = "DELETE FROM Users WHERE id = :id";
            con.createQuery(query)
                    .bind(user)
                    .executeUpdate();
            return true;
        }
        catch (Sql2oException ex) {
            throw new DaoException();
        }
    }
}
