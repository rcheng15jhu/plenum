package persistence;

import exception.DaoException;
import model.Connection;

import java.util.List;

public interface ConnectionDao {
    int add(Connection conn) throws DaoException;
    List<Connection> listAll() throws DaoException;
    boolean delete(Connection conn) throws DaoException;
}
