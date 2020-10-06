package persistence;

import exception.DaoException;
import model.Range;

import java.util.List;

public interface RangeDao {
    int add(Range range) throws DaoException;
    List<Range> listAll() throws DaoException;
    boolean delete(Range range) throws DaoException;
}
