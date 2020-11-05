package persistence;

import exception.DaoException;
import model.Connections;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

public class Sql2oConnectionsDao implements ConnectionsDao {
    private final Sql2o sql2o;

    public Sql2oConnectionsDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public int add(Connections conn) throws DaoException{
        try (Connection con = sql2o.open()) {
            String query = "INSERT INTO Connections (eventId, calendarId, userId)" +
                    "VALUES (:eventId, :calendarId, :userId)";
            int id = (int) con.createQuery(query, true)
                    .bind(conn)
                    .executeUpdate().getKey();
            conn.setId(id);
            return id;
        }
        catch (Sql2oException ex) {
            ex.printStackTrace();
            throw new DaoException();
        }
    }

    @Override
    public List<Connections> listAll() throws DaoException{
        String sql = "SELECT * FROM Connections";
        try (Connection con = sql2o.open()) {
            return con.createQuery(sql).executeAndFetch(Connections.class);
        }
        catch (Sql2oException ex) {
            ex.printStackTrace();
            throw new DaoException();
        }
    }

    @Override
    public boolean delete(Connections conn) throws DaoException{
        try (Connection con = sql2o.open()) {
            String preQ = "PRAGMA foreign_keys = ON;";
            con.createQuery(preQ).executeUpdate();

            String query = "DELETE FROM Calendars WHERE id = :id";
            con.createQuery(query)
                    .bind(conn)
                    .executeUpdate();
            return true;
        }
        catch (Sql2oException ex) {
            throw new DaoException();
        }
    }
}
