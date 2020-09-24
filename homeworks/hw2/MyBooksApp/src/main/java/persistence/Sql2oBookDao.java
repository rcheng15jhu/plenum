package persistence;

import exception.DaoException;
import model.Book;
import model.Author;

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

            String preQ = "PRAGMA foreign_keys = ON;";
            con.createQuery(preQ).executeUpdate();

            //check if author id exists
            String sql = "SELECT id FROM Authors " +
                    "WHERE id = :authorId";
            List<Integer> result = con.createQuery(sql)
                    .bind(book)
                    .executeAndFetch(Integer.class);
            if (result == null || result.size() == 0) {
                throw new DaoException();
            }

            //add book to table if author id exists
            String query2 = "INSERT INTO Books (title, isbn, publisher, year, authorId)" +
                    "VALUES (:title, :isbn, :publisher, :year, :authorId)";
            int id = (int) con.createQuery(query2, true)
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
        try (Connection con = sql2o.open()) {
            String query = "DELETE FROM Books WHERE isbn = :isbn";
            con.createQuery(query)
                    .bind(book)
                    .executeUpdate();
            return true;
        }
    }

    @Override
    public boolean update(Book book) throws DaoException {
        try (Connection con = sql2o.open()) {

            String preQ = "PRAGMA foreign_keys = ON;";
            con.createQuery(preQ).executeUpdate();

            String sql = "SELECT id FROM Authors " +
                    "WHERE id = :authorId";
            List<Integer> result = con.createQuery(sql)
                    .bind(book)
                    .executeAndFetch(Integer.class);
            if (result == null || result.size() == 0) {
                throw new DaoException();
            }

            String query = "UPDATE Books " +
                    "SET title = :title, " +
                    "publisher = :publisher, " +
                    "year = :year, " +
                    "authorId = :authorId " +
                    "WHERE isbn = :isbn";
            con.createQuery(query)
                    .bind(book)
                    .executeUpdate();
            return true;
        }
    }
}

