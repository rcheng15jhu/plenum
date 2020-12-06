package persistence;

import exception.DaoException;
import model.Availability;
import model.Calendar;
import org.sql2o.Query;
import org.sql2o.Sql2o;
import java.util.List;
import java.util.stream.Stream;

public class Sql2oAvailabilityDao implements AvailabilityDao {
    private final Sql2o sql2o;

    public Sql2oAvailabilityDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public int add(Availability a) throws DaoException {
        return Transaction.execute(sql2o, (con) -> {
            String query = "INSERT INTO Availabilities (calendarId, date, qHour)"
                    + "VALUES (:calendarId, :date, :qHour)";
            int id = (int) con.createQuery(query, true).bind(a).executeUpdate().getKey();
            a.setId(id);
            return id;
        });
    }

    public List<Integer> addBatch(Stream<Availability> as) {
        return Transaction.execute(sql2o, (con) -> {
            String query = "INSERT INTO Availabilities (calendarId, date, qHour)"
                    + "VALUES (:calendarId, :date, :qHour)";

            Query q = con.createQuery(query, true);

            as.forEachOrdered(avail -> {
                q.bind(avail).addToBatch();
            });

            List<Integer> ids = q.executeBatch().getKeys(Integer.class);
            return ids;
        });
    }

    @Override
    public List<Availability> listAll() throws DaoException {
        return Transaction.execute(sql2o, (con) -> {
            String sql = "SELECT * FROM Availabilities";
            return con.createQuery(sql).executeAndFetch(Availability.class);
        });
    }

    @Override
    public List<Availability> listAllInCal(Calendar cal) throws DaoException {
        return Transaction.execute(sql2o, (con) -> {
            String sql = "SELECT * FROM Availabilities WHERE calendarId = :id";
            return con.createQuery(sql).bind(cal).executeAndFetch(Availability.class);
        });
    }

    @Override
    public boolean delete(Availability a) throws DaoException {
        return Transaction.execute(sql2o, (con) -> {
            int id = a.getId();
            if (id == 0) {
                String query = "DELETE FROM Availabilities WHERE qHour = :qHour AND date = :date AND calendarId =:calendarId";
                con.createQuery(query).bind(a).executeUpdate();
                return true;
            } else {
                String query = "DELETE FROM Availabilities WHERE id = :id";
                con.createQuery(query).bind(a).executeUpdate();
                return true;
            }
        });
    }

    public boolean updatecheck(Availability a) throws DaoException {
        return Transaction.execute(sql2o, (con) -> {
            String query = "SELECT * FROM Availabilities " + "WHERE date = :date " + "AND qHour = :qHour "
                    + "AND calendarId = :calendarId";
            List<Availability> list = con.createQuery(query).bind(a).executeAndFetch(Availability.class);
            if (list.size() == 1) {
                a.setId(list.get(0).getId());
                return true;
            } else {
                return false;
            }
        });
    }
}
