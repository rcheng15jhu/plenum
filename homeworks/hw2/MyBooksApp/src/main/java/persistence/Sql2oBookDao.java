package persistence;

import exception.DaoException;
import model.Book;
import java.util.List;
import org.sql2o.*;

public class Sql2oBookDao implements BookDao {

    private final Sql2o sql2o;

    public Sql2oBookDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public int add(Book book) throws DaoException {
        try (Connection con = sql2o.open()) {
            String query = "INSERT INTO Books (title, isbn, publisher, year, author)" +
                    "VALUES (:title, :isbn, :publisher, :year, :author)";
            int id = (int) con.createQuery(query, true)
                    .bind(book)
                    .executeUpdate().getKey();
            book.setId(id);
            return id;
        }
    }

    @Override
    public List<Book> listAll() {
        String sql = "SELECT * FROM Books";
        try (Connection con = sql2o.open()) {
            return con.createQuery(sql).executeAndFetch(Book.class);
        }
    }

    @Override
    public boolean delete(Book book) throws DaoException {
        try (Connection con = sql2o.beginTransaction()) {
            String query = "DELETE FROM Books WHERE isbn=" + book.getIsbn();
            con.createQuery(query).executeUpdate();
            con.commit();
            return true;
        }
    }

    @Override
    public boolean update(Book book) throws DaoException {
        try (Connection con = sql2o.beginTransaction()) {
            String query = "UPDATE FROM BOOKS" +
            "SET title=" + book.getTitle() + ", publisher=" + book.getPublisher() +
            ", year=" + book.getYear() + ", author=" + book.getAuthor() +
            "WHERE isbn=" + book.getIsbn();            con.createQuery(query).executeUpdate();
            con.commit();
            return true;
        }
    }
}

