package persistence;

import exception.DaoException;
import model.Book;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;
<<<<<<< HEAD

import java.net.URISyntaxException;
import java.sql.SQLException;
=======
>>>>>>> iteration3
import java.util.List;

public class Sql2oBookDao implements BookDao {
    private final Sql2o sql2o;

    public Sql2oBookDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public int add(Book book) throws DaoException {
        try (Connection con = sql2o.open()) {
<<<<<<< HEAD
            String query = "INSERT INTO Books (title, isbn, publisher, year, authorId)" +
                    "VALUES (:title, :isbn, :publisher, :year, :authorId)";
=======
            String query = "INSERT INTO Books (id, title, isbn, publisher, year, authorId)" +
                    "VALUES (NULL, :title, :isbn, :publisher, :year, :authorId)";
>>>>>>> iteration3
            int id = (int) con.createQuery(query, true)
                    .bind(book)
                    .executeUpdate().getKey();
            book.setId(id);
            return id;
        }
<<<<<<< HEAD
        catch (Sql2oException e) {
            e.printStackTrace();
            throw new DaoException();
        }
=======
>>>>>>> iteration3
    }

    @Override
    public List<Book> listAll() throws DaoException {
        String sql = "SELECT * FROM Books";
        try (Connection con = sql2o.open()) {
            return con.createQuery(sql).executeAndFetch(Book.class);
        }
<<<<<<< HEAD
        catch (Sql2oException e) {
            e.printStackTrace();
=======
        catch (Sql2oException ex) {
>>>>>>> iteration3
            throw new DaoException();
        }
    }

    @Override
    public boolean update(Book book) throws DaoException {
        String sql = "Update Books SET title = :title, authorId = :authorId, " +
                "publisher = :publisher, year = :year WHERE isbn = :isbn";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql).bind(book)
                    .executeUpdate();
            return true;
        }
<<<<<<< HEAD
        catch (DaoException e) {
            e.printStackTrace();
=======
        catch (Sql2oException ex) {
>>>>>>> iteration3
            throw new DaoException();
        }
    }

    @Override
    public boolean delete(Book author) throws DaoException {
        String sql = "DELETE FROM Books WHERE isbn =:isbn";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql).bind(author)
                    .executeUpdate();
            return true;
        }
<<<<<<< HEAD
        catch (DaoException e) {
            e.printStackTrace();
=======
        catch (Sql2oException ex) {
>>>>>>> iteration3
            throw new DaoException();
        }
    }
}
