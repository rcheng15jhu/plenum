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
            String query = "INSERT INTO Calendars (name, userId, blob)" +
                    "VALUES (:name, :userId, :blob)";
            int id = (int) con.createQuery(query, true)
                    .addParameter("name", cal.getName())
                    .addParameter("userId", cal.getUserId())
                    .executeUpdate().getKey();
            cal.setId(id);
            return id;
        }
        catch (Sql2oException ex) {
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
            throw new DaoException();
        }
    }

    @Override
    public Calendar getCal(int id) throws DaoException {
        String sql = "SELECT * FROM Calendars"+
                     "WHERE id = :id";
        try (Connection con = sql2o.open()) {
            return con.createQuery(sql).executeAndFetch(Calendar.class).get(0);
        }
        catch (Sql2oException ex) {
            throw new DaoException();
        }
    }

    @Override
    public boolean delete(Calendar cal) throws DaoException {
        try (Connection con = sql2o.open()) {
            String preQ = "PRAGMA foreign_keys = ON;";
            con.createQuery(preQ).executeUpdate();

            String query = "DELETE FROM Calendar WHERE id = :id";
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
