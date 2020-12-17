package persistence;

import exception.DaoException;
import model.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

public class Sql2oConnectionDao implements ConnectionDao {
    private final Sql2o sql2o;

    public Sql2oConnectionDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public int add(Connection conn) throws DaoException{
        try (org.sql2o.Connection con = sql2o.open()) {
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
    public List<Connection> listAll() throws DaoException{
        String sql = "SELECT * FROM Connections";
        try (org.sql2o.Connection con = sql2o.open()) {
            return con.createQuery(sql).executeAndFetch(Connection.class);
        }
        catch (Sql2oException ex) {
            ex.printStackTrace();
            throw new DaoException();
        }
    }

    public List<Connection> listOneUser(int userId) throws DaoException {
        try (org.sql2o.Connection con = sql2o.open()) {
            String sql = "SELECT * FROM Connections WHERE userId = :userId";
            return con.createQuery(sql)
                    .addParameter("userId", userId)
                    .executeAndFetch(Connection.class);
        }
        catch (Sql2oException ex) {
            ex.printStackTrace();
            throw new DaoException();
        }
    }

    public List<Connection> listOneEvent(int eventId) throws DaoException {
        try (org.sql2o.Connection con = sql2o.open()) {
            String sql = "SELECT * FROM Connections WHERE eventId = :eventId";
            return con.createQuery(sql)
                    .addParameter("eventId", eventId)
                    .executeAndFetch(Connection.class);
        }
        catch (Sql2oException ex) {
            ex.printStackTrace();
            throw new DaoException();
        }
    }

    @Override
    public boolean delete(Connection conn) throws DaoException{
        try (org.sql2o.Connection con = sql2o.open()) {
            String query = "DELETE FROM Connections WHERE eventId = :eventId AND calendarId = :calendarId";
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

    public List<Connection> updateconnectionscheck(int eventId, int calendarId, int userId) throws DaoException {
        try (org.sql2o.Connection con = sql2o.open()) {
            String query = "SELECT * FROM Connections " +
                    "WHERE eventId = :eventId "+
                    "AND userId = :userId ";
            List<Connection> list = con.createQuery(query)
                    .addParameter("eventId", eventId)
                    .addParameter("userId", userId)
                    .executeAndFetch(Connection.class);
            return list;
        }
        catch (Sql2oException ex) {
            ex.printStackTrace();
            throw new DaoException();
        }
    }
}
