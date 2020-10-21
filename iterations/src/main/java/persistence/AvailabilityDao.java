package persistence;

import exception.DaoException;
import model.Availability;
import model.Calendar;

import java.util.List;

public interface AvailabilityDao {
    int add(Availability a) throws DaoException;
    List<Availability> listAll() throws DaoException;
    List<Availability> listAllInCal(Calendar cal) throws DaoException;
    boolean delete(Availability a) throws DaoException;
}
