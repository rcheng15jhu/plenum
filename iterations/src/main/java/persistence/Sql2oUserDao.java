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
            String query = "INSERT INTO Users (name)" +
                    "VALUES (:name)";
            int id = (int) con.createQuery(query, true)
                    .bind(user)
                    .executeUpdate().getKey();
            user.setId(id);
            return id;
        }
        catch (Sql2oException ex) {
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
