package persistence;

import exception.DaoException;
import model.Author;

import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.List;
import org.sql2o.*;

public class Sql2oAuthorDao implements AuthorDao {

    private final Sql2o sql2o;

    public Sql2oAuthorDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }


    @Override
    public int add(Author author) throws DaoException {
        try (Connection con = sql2o.open()) {
            String query;
            query = "INSERT INTO Authors (name, numOfBooks, nationality)" +
                        "VALUES (:name, :numOfBooks, :nationality)";
            int id = (int) con.createQuery(query, true)
                    .bind(author)
                    .executeUpdate().getKey();
            author.setId(id);
            return id;
        }
        catch (URISyntaxException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Author> listAll() throws DaoException {
        String sql = "SELECT * FROM Authors";
        try (Connection con = sql2o.open()) {
            return con.createQuery(sql).executeAndFetch(Author.class);
        }
        catch (URISyntaxException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean update(Author author) throws DaoException {
        String sql = "Update Authors SET name = :name, numOfBooks = :numOfBooks, " +
                     "nationality = :nationality WHERE name = :name";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql).bind(author)
                    .executeUpdate();
            return true;
        }
        catch (URISyntaxException | SQLException e) {
            e.printStackTrace();
        }
    }

    public int queryId(String name) throws DaoException {
        String query = "Select * FROM Authors WHERE name = :name";
        try (Connection con = sql2o.open()) {
            List<Author> authors = con.createQuery(query)
                    .addParameter("name", name)
                    .executeAndFetch(Author.class);
            if(authors.size() == 1) {
                return authors.get(0).getId();
            } else {
                return -1;
            }
        }
        catch (URISyntaxException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean delete(Author author) throws DaoException {
        String sql = "DELETE FROM Authors WHERE name =:name";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql).bind(author)
                    .executeUpdate();
            return true;
        }
        catch (URISyntaxException | SQLException e) {
            e.printStackTrace();
        }
    }

}
