package persistence;

import exception.DaoException;
import model.Calendar;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

public class Sql2oCalendarDao implements CalendarDao {
    private final Sql2o sql2o;

    public Sql2oCalendarDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public int add(Calendar cal) throws DaoException {
        try (Connection con = sql2o.open()) {
            String query = "INSERT INTO Calendars (title, userId)" +
                    "VALUES (:title, :userId)";
            int id = (int) con.createQuery(query, true)
                    .bind(cal)
                    .executeUpdate().getKey();
            cal.setId(id);
            return id;
        }
        catch (Sql2oException ex) {
            ex.printStackTrace();
            throw new DaoException();
        }
    }

    @Override
    public List<Calendar> listAll() throws DaoException {
        String sql = "SELECT * FROM Calendars";
        try (Connection con = sql2o.open()) {
            return con.createQuery(sql).executeAndFetch(Calendar.class);
        }
        catch (Sql2oException ex) {
            ex.printStackTrace();
            throw new DaoException();
        }
    }

    public List<Calendar> listOne(int userID) throws DaoException {

        try (Connection con = sql2o.open()) {
            String sql = "SELECT * FROM Calendars WHERE userId =: userID";
            con.createQuery(sql)
                    .bind(userID)
                    .executeUpdate();
            return con.createQuery(sql).executeAndFetch(Calendar.class);
        }
        catch (Sql2oException ex) {
            ex.printStackTrace();
            throw new DaoException();
        }
    }

    @Override
    public Calendar getCal(Calendar cal) throws DaoException {
        try (Connection con = sql2o.open()) {
            String sql = "SELECT * FROM Calendars"+
                    "WHERE id = :id";
            return con.createQuery(sql)
                    .bind(cal)
                    .executeAndFetch(Calendar.class).get(0);
        }
        catch (Sql2oException e) {
            throw new DaoException();
        }

    }

    @Override
    public Calendar getCal(int id) throws DaoException {
        try (Connection con = sql2o.open()) {
            String sql = "SELECT * FROM Calendars WHERE id = :id";
            return con.createQuery(sql)
                    .addParameter("id", id)
                    .executeAndFetch(Calendar.class).get(0);
        }
        catch (Sql2oException e) {
            throw new DaoException();
        }
    }

    @Override
    public boolean delete(Calendar cal) throws DaoException {
        try (Connection con = sql2o.open()) {
            String preQ = "PRAGMA foreign_keys = ON;";
            con.createQuery(preQ).executeUpdate();

            String query = "DELETE FROM Calendars WHERE id = :id";
            con.createQuery(query)
                    .bind(cal)
                    .executeUpdate();
            return true;
        }
        catch (Sql2oException ex) {
            throw new DaoException();
        }
    }
}
