package persistence;

import exception.DaoException;
import model.Event;
import org.sql2o.Sql2o;

import java.util.List;

public class Sql2oEventDao implements EventDao{
    private final Sql2o sql2o;

    public Sql2oEventDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public int add(Event event) throws DaoException {
        return 0;
    }

    @Override
    public List<Event> listAll() throws DaoException {
        return null;
    }

    @Override
    public boolean delete(Event cal) throws DaoException {
        return false;
    }
}
