import com.google.gson.Gson;
import exception.DaoException;
import model.Author;
import model.Book;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sqlite.SQLiteConfig;
import org.sqlite.SQLiteDataSource;
import persistence.Sql2oAuthorDao;
import persistence.Sql2oBookDao;

import java.util.List;

import static spark.Spark.*;

public class Server {

    private static Sql2o sql2o;

    private static Sql2o getSql2o() {
        if(sql2o == null) {
            // set on foreign keys
            SQLiteConfig config = new SQLiteConfig();
            config.enforceForeignKeys(true);
            config.setPragma(SQLiteConfig.Pragma.FOREIGN_KEYS, "ON");

            // create data source
            SQLiteDataSource ds = new SQLiteDataSource(config);
            ds.setUrl("jdbc:sqlite:MyBooksApp.db");

            sql2o = new Sql2o(ds);
            try (Connection conn = sql2o.open()) {
                String sq1 = "CREATE TABLE IF NOT EXISTS Authors (" +
                        " id            INTEGER PRIMARY KEY," +
                        " name          VARCHAR(100) NOT NULL UNIQUE," +
                        " numOfBooks    INTEGER," +
                        " nationality   VARCHAR(30)" +
                        ");";
                conn.createQuery(sq1).executeUpdate();
                String sq2 = "CREATE TABLE IF NOT EXISTS Books (" +
                        " id        INTEGER PRIMARY KEY," +
                        " title     VARCHAR(100) NOT NULL," +
                        " isbn      VARCHAR(100) NOT NULL UNIQUE," +
                        " publisher VARCHAR(100)," +
                        " year      INTEGER," +
                        " authorId  INTEGER NOT NULL," +
                        " FOREIGN KEY(authorId)" +
                        " REFERENCES Authors (id)" +
                        "   ON UPDATE CASCADE" +
                        "   ON DELETE CASCADE" +
                        ");";
                conn.createQuery(sq2).executeUpdate();
            }
        }
        return sql2o;
    }

    public static void main(String[] args)  {
        // set port number
        final int PORT_NUM = 7000;
        port(PORT_NUM);

        // root route; show a simple message!
        get("/", (req, res) -> "Welcome to MyBooksApp");

        // authors route; return list of authors as JSON
        get("/authors", (req, res) -> {
            Sql2oAuthorDao sql2oAuthor = new Sql2oAuthorDao(getSql2o());
            String results = new Gson().toJson(sql2oAuthor.listAll());
            res.type("application/json");
            res.status(200);
            return results;
        });

        //addauthor route; add a new author
        post("/addauthor", (req, res) -> {
            String name = req.queryParams("name");
            int numOfBooks = Integer.parseInt(req.queryParams("numOfBooks"));
            String nationality = req.queryParams("nationality");
            Author a = new Author(name, numOfBooks, nationality);
            new Sql2oAuthorDao(getSql2o()).add(a);
            res.status(201);
            res.type("application/json");
            return new Gson().toJson(a.toString());
        });

        post("/addbook", (req, res) -> {
            String title = req.queryParams("title");
            String isbn = req.queryParams("isbn");
            String publisher = req.queryParams("publisher");
            int year = Integer.parseInt(req.queryParams("year"));
            int authorID = Integer.parseInt(req.queryParams("authorID"));
            Book b = new Book(title, isbn, publisher, year, authorID);
            new Sql2oBookDao(getSql2o()).add(b);
            res.status(201);
            res.type("application/json");
            return new Gson().toJson(b.toString());
        });

        //bookss route; return list of book as JSON
        get("/books", (req, res) -> {
            Sql2oBookDao sql2oBook = new Sql2oBookDao(getSql2o());
            String results = new Gson().toJson(sql2oBook.listAll());
            res.type("application/json");
            res.status(200);
            return results;
        });

        //delauthor route; delete author
        post("/delauthor", (req, res) -> {
            String name = req.queryParams("name");
            Author a = new Author(name, 0, null);
            try {
                new Sql2oAuthorDao(getSql2o()).delete(a);
                res.status(204);
            } catch (DaoException ex) {
                res.status(404);
            }
            res.type("application/json");
            return new Gson().toJson(a.toString());
        });

        //delbook route; delete book
        post("/delbook", (req, res) -> {
            String isbn = req.queryParams("isbn");
            Book b = new Book(null, isbn, null, 0, 0);
            try {
                new Sql2oBookDao(getSql2o()).delete(b);
                res.status(204);
            } catch (DaoException ex) {
                res.status(404);
            }
            res.type("application/json");
            return new Gson().toJson(b.toString());
        });
    }
}
