import com.google.gson.Gson;
import exception.DaoException;
import model.Author;
import model.Book;
import org.sql2o.Sql2o;
import org.sqlite.SQLiteConfig;
import persistence.Sql2oAuthorDao;
import persistence.Sql2oBookDao;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;

public class Server {

    private static Sql2o getSql2o() throws URISyntaxException {
        String[] dbUrl = getDbUrl(System.getenv("DATABASE_URL"));
        createTables();
        Sql2o sql2o = new Sql2o(dbUrl[0], dbUrl[1], dbUrl[2]);
        return sql2o;
    }

    private static void createTables() {
        try (Connection conn = getConnection()) {
            String sq1 = "";
            String sq2 = "";

            if ("SQLite".equalsIgnoreCase(conn.getMetaData().getDatabaseProductName())) { // running locally
                // set on foreign keys
                SQLiteConfig config = new SQLiteConfig();
                config.enforceForeignKeys(true);
                config.setPragma(SQLiteConfig.Pragma.FOREIGN_KEYS, "ON");
                sq1 = "CREATE TABLE IF NOT EXISTS Authors (" +
                        " id            INTEGER PRIMARY KEY," +
                        " name          VARCHAR(100) NOT NULL UNIQUE," +
                        " numOfBooks    INTEGER," +
                        " nationality   VARCHAR(30)" +
                        ");";
                sq2 = "CREATE TABLE IF NOT EXISTS Books (" +
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
            }
            else {
                sq1 = "CREATE TABLE IF NOT EXISTS Authors (" +
                        " id            serial PRIMARY KEY," +
                        " name          VARCHAR(100) NOT NULL UNIQUE," +
                        " numOfBooks    INTEGER," +
                        " nationality   VARCHAR(30)" +
                        ");";
                sq2 = "CREATE TABLE IF NOT EXISTS Books (" +
                        " id        serial PRIMARY KEY," +
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
            }

            Statement st = conn.createStatement();
            st.executeUpdate(sq1);
            st.executeUpdate(sq2);

        } catch (URISyntaxException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws URISyntaxException, SQLException {
        String databaseUrl = System.getenv("DATABASE_URL");
        if (databaseUrl == null) {
            // Not on Heroku, so use SQLite
            return DriverManager.getConnection("jdbc:sqlite:./MyBooksApp.db");
        }

        String[] dbUri = getDbUrl(databaseUrl);

        String username = dbUri[1];
        String password = dbUri[2];
        String dbUrl = dbUri[0];

        return DriverManager.getConnection(dbUrl, username, password);
    }

    private static String[] getDbUrl(String databaseUrl) throws URISyntaxException {
        if (databaseUrl == null) {
            return new String[]{"jdbc:sqlite:./MyBooksApp.db", "", ""};
        }

        URI dbUri = new URI(databaseUrl);

        String username = dbUri.getUserInfo().split(":")[0];
        String password = dbUri.getUserInfo().split(":")[1];
        String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':'
                + dbUri.getPort() + dbUri.getPath() + "?sslmode=require";
        return new String[]{dbUrl, username, password};
    }

    static final int PORT = 7000;  
    private static int getHerokuAssignedPort() {
      String herokuPort = System.getenv("PORT");
      if (herokuPort != null) {
        return Integer.parseInt(herokuPort);
      }
      return PORT;
    }

    public static void main(String[] args) {
        // set port number
        port(getHerokuAssignedPort());

        Sql2o attemptSql2o = null;
        try {
            attemptSql2o = getSql2o();
        } catch(URISyntaxException e) {
            System.out.println("Server unable to start; invalid URI");
            e.printStackTrace();
            return;
        }
        Sql2o sql2o = attemptSql2o;

        staticFiles.location("/public");

        post("/", (req, res) -> {
            String username = req.queryParams("username");
            res.cookie("username", username);
            res.redirect("/");
            return null;
        });

        get("/", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            if (req.cookie("username") != null)
                model.put("username", req.cookie("username"));
            res.status(200);
            res.type("text/html");
            return new ModelAndView(model, "public/templates/index.vm");
        }, new VelocityTemplateEngine());

        get("/authors", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("authors", new Sql2oAuthorDao(sql2o).listAll());
            res.status(200);
            res.type("text/html");
            return new ModelAndView(model, "public/templates/authors.vm");
        }, new VelocityTemplateEngine());

        get("/addauthor", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            res.status(200);
            res.type("text/html");
            return new ModelAndView(model, "public/templates/addauthor.vm");
        }, new VelocityTemplateEngine());

        post("/addauthor", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            String name = req.queryParams("name");
            int numOfBooks = Integer.parseInt(req.queryParams("numOfBooks"));
            String nationality = req.queryParams("nationality");
            Author author = new Author(name, numOfBooks, nationality);
            try {
                int id = new Sql2oAuthorDao(sql2o).add(author);
                if (id > 0) {
                    model.put("added", "true");
                }
                else {
                    model.put("failedAdd", "true");
                }
            }
            catch (DaoException ex) {
                model.put("failedAdd", "true");
            }
            res.status(201);
            res.type("text/html");
            ModelAndView mdl = new ModelAndView(model, "public/templates/addauthor.vm");
            return new VelocityTemplateEngine().render(mdl);
        });

        post("/delauthor", (req, res) -> {
            String name = req.queryParams("name");
            Author a = new Author(name, 0, "");
            new Sql2oAuthorDao(getSql2o()).delete(a);
            res.status(200);
            res.type("application/json");
            return new Gson().toJson(a.toString());
        });

        post("/delbook", (req, res) -> {
            String isbn = req.queryParams("isbn");
            Book b = new Book("", isbn, "", 0, 0);
            new Sql2oBookDao(getSql2o()).delete(b);
            res.status(200);
            res.type("application/json");
            return new Gson().toJson(b.toString());
        });

        get("/books", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            if (req.cookie("username") == null) {
                res.redirect("/");
                return null;
            }
            model.put("books", new Sql2oBookDao(sql2o).listAll());
            res.status(200);
            res.type("text/html");
            return new ModelAndView(model, "public/templates/books.vm");
        }, new VelocityTemplateEngine());

        get("/addbook", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            if (req.cookie("username") == null) {
                res.redirect("/");
                return null;
            }
            res.status(200);
            res.type("text/html");
            return new ModelAndView(model, "public/templates/addbook.vm");
        }, new VelocityTemplateEngine());

        post("/addbook", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            Sql2oAuthorDao sql2oAuthorDao = new Sql2oAuthorDao(sql2o);
            String name = req.queryParams("name");
            int authorId = sql2oAuthorDao.queryId(name);
            if (authorId == -1) {
                int numOfBooks = Integer.parseInt(req.queryParams("numOfBooks"));
                String nationality = req.queryParams("nationality");
                Author author = new Author(name, numOfBooks, nationality);
                try {
                    authorId = sql2oAuthorDao.add(author);
                    if (authorId > 0) {
                        model.put("added", "true");
                    }
                    else {
                        model.put("failedAdd", "true");
                    }
                }
                catch (DaoException ex) {
                    model.put("failedAdd", "true");
                }
            }
            if (authorId > 0) {
                String title = req.queryParams("title");
                String isbn = req.queryParams("isbn");
                String publisher = req.queryParams("publisher");
                int year = Integer.parseInt(req.queryParams("year"));
                Book book = new Book(title, isbn, publisher, year, authorId);
                try {
                    int id = new Sql2oBookDao(sql2o).add(book);
                    if (id > 0) {
                        model.put("added", "true");
                    }
                    else {
                        model.put("failedAdd", "true");
                        model.remove("added");
                    }
                }
                catch (DaoException ex) {
                    model.put("failedAdd", "true");
                    model.remove("added");
                }
            }
            ModelAndView mdl = new ModelAndView(model, "public/templates/books.vm");
            return new VelocityTemplateEngine().render(mdl);
        });
    }
}
