package persistence;


import exception.DaoException;
import model.UserCred;

import java.util.List;

public interface UserCredDao {
    int add(UserCred uc) throws DaoException;
    List<UserCred> listAll() throws DaoException;
    boolean delete(UserCred uc) throws DaoException;
}
