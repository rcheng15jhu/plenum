package persistence;

import exception.DaoException;
import model.Calendar;
import model.Connections;

import java.util.List;

public interface ConnectionsDao {
    int add(Connections conn) throws DaoException;
    List<Connections> listAll() throws DaoException;
    boolean delete(Connections conn) throws DaoException;
}
