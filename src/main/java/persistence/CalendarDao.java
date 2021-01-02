package persistence;

import exception.DaoException;
import model.Calendar;

import java.util.List;

public interface CalendarDao {
    int add(Calendar cal) throws DaoException;
    List<Calendar> listAll() throws DaoException;
    Calendar getCal(Calendar cal) throws DaoException;
    Calendar getCal(int id) throws DaoException;
    boolean delete(Calendar cal) throws DaoException;
}
