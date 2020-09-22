package persistence;

import exception.DaoException;
import model.Author;
import model.Book;

import java.util.List;

public interface BookDao {
    int add(Book book) throws DaoException;
    List<Book> listAll();
    boolean delete(Book book);
    boolean update(Book book);
}