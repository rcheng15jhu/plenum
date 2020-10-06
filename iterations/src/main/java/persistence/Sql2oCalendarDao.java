package persistence;

import exception.DaoException;
import model.Calendar;

import java.util.List;

public class Sql2oCalendarDao implements CalendarDao {
    @Override
    public int add(Calendar cal) throws DaoException {
        return 0;
    }

    @Override
    public List<Calendar> listAll() throws DaoException {
        return null;
    }

    @Override
    public boolean delete(Calendar cal) throws DaoException {
        return false;
    }
}
