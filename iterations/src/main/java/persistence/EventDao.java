package persistence;

import exception.DaoException;
import model.Event;

import java.util.List;

public interface EventDao {
    int add(Event event) throws DaoException;
    List<Event> listAll() throws DaoException;
    boolean delete(Event cal) throws DaoException;
}
