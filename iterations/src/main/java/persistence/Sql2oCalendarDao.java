package persistence;

import exception.DaoException;
import model.Calendar;
import org.sql2o.Sql2o;

import java.util.List;

public class Sql2oCalendarDao implements CalendarDao {
    private final Sql2o sql2o;

    public Sql2oCalendarDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public int add(Calendar cal) throws DaoException {
        return 0;
    }

    @Override
    public List<Calendar> listAll() throws DaoException {
        return null;
    }

    @Override
    public Calendar getCal(int id) throws DaoException {
        return null;
    }

    @Override
    public boolean delete(Calendar cal) throws DaoException {
        return false;
    }
}
