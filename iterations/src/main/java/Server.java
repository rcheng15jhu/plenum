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
                        " name          VARCHAR(100) NOT NULL UNIQUE" +
                        ");";
                conn.createQuery(sq1).executeUpdate();
                String sq2 = "CREATE TABLE IF NOT EXISTS Events (" +
                        " id            INTEGER PRIMARY KEY," +
                        " title         VARCHAR(100) NOT NULL" +
                        ");";
                conn.createQuery(sq2 ).executeUpdate();
                String sq3 = "CREATE TABLE IF NOT EXISTS Calendars (" +
                        " id            INTEGER PRIMARY KEY," +
                        " title         VARCHAR(100) NOT NULL," +
                        " userId        INTEGER NOT NULL," +
                        " eventId       INTEGER NOT NULL," +
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

        staticFiles.location("/public");

        // root route; show a simple message!
        get("/", (req, res) -> "Welcome to Quorum");

        // calendars route; return list of calendars as JSON
        get("/calendars", (req, res) -> {
            Sql2oCalendarDao sql2oCalendar = new Sql2oCalendarDao(getSql2o());
            String results = new Gson().toJson(sql2oCalendar.listAll());
            res.type("application/json");
            res.status(200);
            return results;
        });

        //calendar route; returns a single calendar
        get("/calendar", (req, res) -> {
            int id = Integer.parseInt(req.queryParams("id"));
            Sql2oCalendarDao sql2oCalendar = new Sql2oCalendarDao(getSql2o());
            String result = new Gson().toJson(sql2oCalendar.getCal(id));
            res.type("application/json");
            res.status(200);
            return result;
        });

        //addcalendar route; add a new calendar
        post("/addcalendar", (req, res) -> {
            String title = req.queryParams("title");
            int userId = Integer.parseInt(req.queryParams("userId"));
            int eventId = Integer.parseInt(req.queryParams("eventId"));
            Calendar c = new Calendar(title, userId);
            new Sql2oCalendarDao(getSql2o()).add(c);
            res.status(201);
            res.type("application/json");
            return new Gson().toJson(c.toString());
        });

        //delcalendar route; delete calendar
        post("/delcalendar", (req, res) -> {
            int id = Integer.parseInt(req.queryParams("id"));
            Calendar c = new Calendar(id);
            try {
                new Sql2oCalendarDao(getSql2o()).delete(c);
                res.status(204);
            } catch (DaoException ex) {
                res.status(404);
            }
            res.type("application/json");
            return new Gson().toJson(c.toString());
        });

        get("/events", (req, res) -> {
            Sql2oEventDao sql2oEventDao = new Sql2oEventDao(getSql2o());
            String results = new Gson().toJson(sql2oEventDao.listAll());
            res.type("application/json");
            res.status(200);
            return results;
        });

        post("/delevent", (req, res) -> {
            int id = Integer.parseInt(req.queryParams("id"));
            Event e = new Event(id);
            try {
                new Sql2oEventDao(getSql2o()).delete(e);
                res.status(204);
            } catch (DaoException ex) {
                res.status(404);
            }
            res.type("application/json");
            return new Gson().toJson(e.toString());
        });

        post("/addevent", (req, res) -> {
            String title = req.queryParams("title");
            Event e = new Event(title);
            new Sql2oEventDao(getSql2o()).add(e);
            res.status(201);
            res.type("application/json");
            return new Gson().toJson(e.toString());
        });

        get("/users", (req, res) -> {
            Sql2oUserDao sql2oUserDao = new Sql2oUserDao(getSql2o());
            String results = new Gson().toJson(sql2oUserDao.listAll());
            res.type("application/json");
            res.status(200);
            return results;
        });

        post("/deluser", (req, res) -> {
            int id = Integer.parseInt(req.queryParams("id"));
            User u = new User(id);
            try {
                new Sql2oUserDao(getSql2o()).delete(u);
                res.status(204);
            } catch (DaoException ex) {
                res.status(404);
            }
            res.type("application/json");
            return new Gson().toJson(u.toString());
        });

        post("/adduser", (req, res) -> {
            String name = req.queryParams("name");
            User u = new User(name);
            new Sql2oUserDao(getSql2o()).add(u);
            res.status(201);
            res.type("application/json");
            return new Gson().toJson(u.toString());
        });

        get("/maketemplate", (req, res) -> {
            res.redirect("/templates/test_checkbox.html");
            return null;
        });
    }
}
