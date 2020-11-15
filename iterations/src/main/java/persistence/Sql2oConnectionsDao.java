package persistence;

import exception.DaoException;
import model.Availability;
import model.Calendar;
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

    public List<Connections> listOne(int userId) throws DaoException {
        try (Connection con = sql2o.open()) {
            String sql = "SELECT * FROM Connections WHERE userId = :userId";
            return con.createQuery(sql)
                    .addParameter("userId", userId)
                    .executeAndFetch(Connections.class);
        }
        catch (Sql2oException ex) {
            ex.printStackTrace();
            throw new DaoException();
        }
    }

    @Override
    public boolean delete(Connections conn) throws DaoException{
        try (Connection con = sql2o.open()) {
            String query = "DELETE FROM Connections WHERE id = :id";
            con.createQuery(query)
                    .bind(conn)
                    .executeUpdate();
            return true;
        }
        catch (Sql2oException ex) {
            ex.printStackTrace();
            throw new DaoException();
        }
    }

    public List<Connections> updateconnectionscheck(int eventId, int calendarId, int userId) throws DaoException {
        try (Connection con = sql2o.open()) {
            String query = "SELECT * FROM Connections " +
                    "WHERE eventId = :eventId "+
                    "AND userId = :userId ";
            List<Connections> list = con.createQuery(query)
                    .addParameter("eventId", eventId)
                    .addParameter("userId", userId)
                    .executeAndFetch(Connections.class);
            return list;
        }
        catch (Sql2oException ex) {
            ex.printStackTrace();
            throw new DaoException();
        }
    }
}
