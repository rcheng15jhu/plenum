package persistence;

import exception.DaoException;
import model.Availability;

import java.util.List;

public interface AvailabilityDao {
    int add(Availability a) throws DaoException;
    List<Availability> listAll() throws DaoException;
    List<Availability> listAllInCal(int id) throws DaoException;
    boolean delete(Availability a) throws DaoException;
}
