package persistence;

import exception.DaoException;
import model.User;

import java.util.List;

public interface UserDao {
    int add(User user) throws DaoException;
    List<User> listAll() throws DaoException;
    boolean delete(User user) throws DaoException;
}