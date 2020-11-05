package persistence;

import exception.DaoException;
import model.Calendar;
import model.Event;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

public class Sql2oEventDao implements EventDao{
    private final Sql2o sql2o;

    public Sql2oEventDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public int add(Event event) throws DaoException {
        try (Connection con = sql2o.open()) {
            System.out.println(event.toString());
            String query = "INSERT INTO Events (title, startTime, endTime)" +
                            "VALUES (:title, :startTime, :endTime)";
            int id = (int) con.createQuery(query, true)
                    .bind(event)
                    .executeUpdate().getKey();
            event.setId(id);
            return id;
        }
        catch (Sql2oException ex) {
            ex.printStackTrace();
            throw new DaoException();
        }
    }

    @Override
    public List<Event> listAll() throws DaoException {
        String sql = "SELECT * FROM Events";
        try (Connection con = sql2o.open()) {
            return con.createQuery(sql).executeAndFetch(Event.class);
        }
        catch (Sql2oException ex) {
            throw new DaoException();
        }
    }

    @Override
    public boolean delete(Event event) throws DaoException {
        try (Connection con = sql2o.open()) {
            String preQ = "PRAGMA foreign_keys = ON;";
            con.createQuery(preQ).executeUpdate();

            String query = "DELETE FROM Events WHERE id = :id";
            con.createQuery(query)
                    .bind(event)
                    .executeUpdate();
            return true;
        }
        catch (Sql2oException ex) {
            throw new DaoException();
        }
    }
}
