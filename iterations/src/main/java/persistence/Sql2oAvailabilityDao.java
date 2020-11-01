package persistence;

import exception.DaoException;
import model.Availability;
import model.Calendar;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

public class Sql2oAvailabilityDao implements AvailabilityDao {
    private final Sql2o sql2o;

    public Sql2oAvailabilityDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public int add(Availability a) throws DaoException {
        try (Connection con = sql2o.open()) {
            String query = "INSERT INTO Availabilities (calendarId, date, qHour)" +
                    "VALUES (:calendarId, :date, :qHour)";
            int id = (int) con.createQuery(query, true)
                    .bind(a)
                    .executeUpdate().getKey();
            a.setId(id);
            return id;
        }
        catch (Sql2oException ex) {
            ex.printStackTrace();
            throw new DaoException();
        }
    }

    @Override
    public List<Availability> listAll() throws DaoException {
        String sql = "SELECT * FROM Availabilities";
        try (Connection con = sql2o.open()) {
            return con.createQuery(sql).executeAndFetch(Availability.class);
        }
        catch (Sql2oException ex) {
            ex.printStackTrace();
            throw new DaoException();
        }
    }

    @Override
    public List<Availability> listAllInCal(Calendar cal) throws DaoException {
        String sql = "SELECT * FROM Availabilities WHERE calendarId = :id";
        try (Connection con = sql2o.open()) {
            return con.createQuery(sql).bind(cal).executeAndFetch(Availability.class);
        }
        catch (Sql2oException ex) {
            System.err.println(ex);
            throw new DaoException();
        }
    }

    @Override
    public boolean delete(Availability a) throws DaoException {
        try (Connection con = sql2o.open()) {
            String preQ = "PRAGMA foreign_keys = ON;";
            con.createQuery(preQ).executeUpdate();

            String query = "DELETE FROM Availabilities WHERE id = :id";
            con.createQuery(query)
                    .bind(a)
                    .executeUpdate();
            return true;
        }
        catch (Sql2oException ex) {
            throw new DaoException();
        }
    }

    public List<Boolean> updatecheck(Availability a) throws DaoException {
        try (Connection con = sql2o.open()) {
            String query = "SELECT * FROM Availabilities " +
                    "WHERE date =: date, " +
                    "AND qHour =: qHour)" +
                    "AND calenderID =: calenderID";
            return con.createQuery(query).bind(a).executeAndFetch(Boolean.class);
        }
        catch (Sql2oException ex) {
            throw new DaoException();
        }
    }

    public int updateadd(Availability a) throws DaoException {
        try (Connection con = sql2o.open()) {
            String query = "SELECT * FROM Availabilities " +
                    "WHERE date =: date, " +
                    "AND qHour =: qHour)" +
                    "AND calenderID =: calenderID";
            int id = (int) con.createQuery(query, true)
                    .bind(a)
                    .executeUpdate().getKey();
            a.setId(id);
            return id;
        }
        catch (Sql2oException ex) {
            throw new DaoException();
        }
    }

    public int updateremove(Availability a) throws DaoException {
        try (Connection con = sql2o.open()) {
            String query = "UPDATE Availabilities " +
                    "SET date =: date, " +
                    "qHour =: qHour)" +
                    "WHERE calenderID =: calenderID";
            int id = (int) con.createQuery(query, true)
                    .bind(a)
                    .executeUpdate().getKey();
            a.setId(id);
            return id;
        }
        catch (Sql2oException ex) {
            throw new DaoException();
        }
    }
}
