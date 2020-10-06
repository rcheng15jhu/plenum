package persistence;

import exception.DaoException;
import model.Event;

import java.util.List;

public class Sql2oEventDao implements EventDao{
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
