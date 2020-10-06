package persistence;

import exception.DaoException;
import model.User;

import java.util.List;

public class Sql2oUserDao implements UserDao{
    @Override
    public int add(User user) throws DaoException {
        return 0;
    }

    @Override
    public List<User> listAll() throws DaoException {
        return null;
    }

    @Override
    public boolean delete(User user) throws DaoException {
        return false;
    }
}
