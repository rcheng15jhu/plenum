package persistence;

import exception.DaoException;
import model.Book;
import model.Author;

import java.util.List;
import org.sql2o.*;

public class Sql2oBookDao implements BookDao {

    private final Sql2o sql2o;
    private Sql2oAuthorDao authorDao;

    public Sql2oBookDao(Sql2o sql2o, Sql2oAuthorDao authorDao) {
        this.sql2o = sql2o;
        this.authorDao = authorDao;
    }

    @Override
    public int add(Book book) throws DaoException {
        try (Connection con = sql2o.open()) {
            Author au = book.getAuthor();

            //check if author id exists
            String sql = "SELECT id FROM Authors " +
                    "WHERE name = :name";
            List<Integer> result = con.createQuery(sql)
                    .bind(au)
                    .executeAndFetch(Integer.class);
            if (result == null || result.size() == 0) {
                throw new DaoException();
            }

            //add book to table if author id exists
            String query2 = "INSERT INTO Books (title, isbn, publisher, year, authorId)" +
                    "VALUES (:title, :isbn, :publisher, :year, :authorId)";
            int id = (int) con.createQuery(query2, true)
                    .bind(book)
                    .addParameter("authorId", result.get(0))
                    .executeUpdate().getKey();
            book.setId(id);

            //update author's numOfBooks
            au.setNumOfBooks(au.getNumOfBooks() + 1);
            authorDao.update(au);

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

            String sql = "SELECT id FROM Authors " +
                    "WHERE name = :name";
            List<Integer> result = con.createQuery(sql)
                    .bind(book.getAuthor())
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
                    .addParameter("authorId", result.get(0))
                    .executeUpdate();
            return true;
        }
    }
}

