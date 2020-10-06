import com.google.gson.Gson;
import exception.DaoException;
import model.AggregateCalendar;
import model.Calendar;
import model.Event;
import model.Range;
import model.User;
import model.UserCred;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sqlite.SQLiteConfig;
import org.sqlite.SQLiteDataSource;
import persistence.Sql2oCalendarDao;
import persistence.Sql2oEventDao;
import persistence.Sql2oUserDao;

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
            ds.setUrl("jdbc:sqlite:Quorum.db");

            sql2o = new Sql2o(ds);
            // Need to change this to fit our database
            try (Connection conn = sql2o.open()) {
                String sq1 = "CREATE TABLE IF NOT EXISTS Users (" +
                        " id            INTEGER PRIMARY KEY," +
                        " name          VARCHAR(100) NOT NULL UNIQUE," +
                        ");";
                conn.createQuery(sq1).executeUpdate();
                String sq2 = "CREATE TABLE IF NOT EXISTS Event (" +
                        " id            INTEGER PRIMARY KEY," +
                        " title         VARCHAR(100) NOT NULL," +
                        " calId  INTEGER NOT NULL," +
                        ");";
                conn.createQuery(sq2 ).executeUpdate();
                String sq3 = "CREATE TABLE IF NOT EXISTS Calendars (" +
                        " id            INTEGER PRIMARY KEY," +
                        " title         VARCHAR(100) NOT NULL," +
                        " userId  INTEGER NOT NULL," +
                        " eventId  INTEGER NOT NULL," +
                        " FOREIGN KEY(userId)" +
                        " REFERENCES Users (id)" +
                        "   ON UPDATE CASCADE" +
                        "   ON DELETE CASCADE" +
                        " FOREIGN KEY(eventId)" +
                        " REFERENCES Events (id)" +
                        "   ON UPDATE CASCADE" +
                        "   ON DELETE CASCADE" +
                        ");";
                conn.createQuery(sq3).executeUpdate();
            }


        }
        return sql2o;
    }

    public static void main(String[] args)  {
        // set port number
        final int PORT_NUM = 7000;
        port(PORT_NUM);

        // root route; show a simple message!
        get("/", (req, res) -> "Welcome to Quorum");

        // authors route; return list of authors as JSON
        get("/calendarsAll", (req, res) -> {
            Sql2oCalendarDao sql2oCalendar = new Sql2oCalendarDao(getSql2o());
            String results = new Gson().toJson(sql2oCalendar.listAll());
            res.type("application/json");
            res.status(200);
            return results;
        });

        get("/calendar", (req, res) -> {
            int id = Integer.parseInt(req.queryParams("calendar id"));
            Sql2oCalendarDao sql2oCalendar = new Sql2oCalendarDao(getSql2o());
            String result = new Gson().toJson(sql2oCalendar.getCal(id));
            res.type("application/json");
            res.status(200);
            return result;
        });

        //addauthor route; add a new author
        post("/addcalendar", (req, res) -> {
/*            String name = req.queryParams("name");
            int numOfBooks = Integer.parseInt(req.queryParams("numOfBooks"));
            String nationality = req.queryParams("nationality");
            Author a = new Author(name, numOfBooks, nationality);
 */
            Calendar c = new Calendar();
            new Sql2oCalendarDao(getSql2o()).add(c);
            res.status(201);
            res.type("application/json");
            return new Gson().toJson(c.toString());
        });

        //delauthor route; delete author
        post("/delcalendar", (req, res) -> {
 //           String name = req.queryParams("name");
 //           Author a = new Author(name, 0, null);
            Calendar c = new Calendar();
            try {
                new Sql2oCalendarDao(getSql2o()).delete(c);
                res.status(204);
            } catch (DaoException ex) {
                res.status(404);
            }
            res.type("application/json");
            return new Gson().toJson(c.toString());
        });
    }
}
