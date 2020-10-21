import com.google.gson.Gson;
import exception.DaoException;
import model.*;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sqlite.SQLiteConfig;
import org.sqlite.SQLiteDataSource;
import persistence.Sql2oAvailabilityDao;
import persistence.Sql2oCalendarDao;
import persistence.Sql2oEventDao;
import persistence.Sql2oUserDao;
import static spark.Spark.*;
import spark.ModelAndView;
import spark.Spark;
import spark.template.velocity.VelocityTemplateEngine;
import spark.utils.IOUtils;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

public class Server {

    private static Sql2o sql2o;

    private static Sql2o getSql2o() {
        if(sql2o == null) {
            System.out.println("was null!");
            // set on foreign keys
            SQLiteConfig config = new SQLiteConfig();
            config.enforceForeignKeys(true);
            config.setPragma(SQLiteConfig.Pragma.FOREIGN_KEYS, "ON");

            // create data source
            SQLiteDataSource ds = new SQLiteDataSource(config);
            ds.setUrl("jdbc:sqlite:Quorum.db");

            sql2o = new Sql2o(ds);
            try (Connection conn = sql2o.open()) {
                String sq1 = "CREATE TABLE IF NOT EXISTS Users (" +
                        " id            INTEGER PRIMARY KEY," +
                        " name          VARCHAR(100) NOT NULL UNIQUE," +
                        " password      VARCHAR(100) NOT NULL" +
                        ");";
                conn.createQuery(sq1).executeUpdate();
                String sq2 = "CREATE TABLE IF NOT EXISTS Events (" +
                        " id            INTEGER PRIMARY KEY," +
                        " title         VARCHAR(100) NOT NULL" +
                        ");";
                conn.createQuery(sq2).executeUpdate();
                String sq3 = "CREATE TABLE IF NOT EXISTS Calendars (" +
                        " id            INTEGER PRIMARY KEY," +
                        " name          VARCHAR(100) NOT NULL," +
                        " userId        INTEGER NOT NULL," +
                        " FOREIGN KEY(userId)" +
                        " REFERENCES Users (id)" +
                        "   ON UPDATE CASCADE" +
                        "   ON DELETE CASCADE" +
                        ");";
                conn.createQuery(sq3).executeUpdate();
                String sq4 = "CREATE TABLE IF NOT EXISTS Connections (" +
                        " id            INTEGER PRIMARY KEY," +
                        " eventId       INTEGER NOT NULL," +
                        " calendarId    INTEGER NOT NULL," +
                        " userId        INTEGER NOT NULL," +
                        " FOREIGN KEY(eventId)" +
                        " REFERENCES Events (id)" +
                        "   ON UPDATE CASCADE" +
                        "   ON DELETE CASCADE," +
                        " FOREIGN KEY(calendarId)" +
                        " REFERENCES Calendars (id)" +
                        "   ON UPDATE CASCADE" +
                        "   ON DELETE CASCADE," +
                        " FOREIGN KEY(userId)" +
                        " REFERENCES Users (id)" +
                        "   ON UPDATE CASCADE" +
                        "   ON DELETE CASCADE" +
                        ");";
                conn.createQuery(sq4).executeUpdate();
                String sq5 = "CREATE TABLE IF NOT EXISTS Availabilities (" +
                        " id            INTEGER PRIMARY KEY," +
                        " calendarId    INTEGER NOT NULL," +
                        " date          DATE," +
                        " qAvail        INTEGER NOT NULL," +
                        " FOREIGN KEY(calendarId)" +
                        " REFERENCES Calendars (id)" +
                        "   ON UPDATE CASCADE" +
                        "   ON DELETE CASCADE" +
                        ");";
                conn.createQuery(sq5).executeUpdate();
            }
        }
        return sql2o;
    }

    final static int PORT_NUM = 7000;
    private static int getHerokuAssignedPort() {
        String herokuPort = System.getenv("PORT");
        if (herokuPort != null) {
          return Integer.parseInt(herokuPort);
        }
        return PORT_NUM;
    }

    public static void main(String[] args)  {
        // set port number
        port(getHerokuAssignedPort());

        staticFiles.location("/public");

        // root route; show a simple message!

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

        post("/signup", (req, res) -> {
            System.out.println("request received!");
            String username = req.queryParams("username");
            String password = req.queryParams("password");
            res.cookie("username", username);
            User u = new User(username, password);
            new Sql2oUserDao(getSql2o()).add(u);
            res.redirect("/");
            return null;
        });

        get("/signup", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            res.status(200);
            res.type("text/html");
            return new ModelAndView(model, "public/templates/signup.vm");
        }, new VelocityTemplateEngine());


        // calendars route; return list of calendars as JSON
        get("/calendars", (req, res) -> {
            Sql2oCalendarDao sql2oCalendar = new Sql2oCalendarDao(getSql2o());
            String results = new Gson().toJson(sql2oCalendar.listAll());
            res.type("application/json");
            res.status(200);
            return results;
        });

        //calendar route; returns a single calendar or list of calendars, depending on presence of query param
        get("/calendar", (req, res) -> {
            Sql2oAvailabilityDao sql2oAvailability = new Sql2oAvailabilityDao(getSql2o());
            String results;
            String idParam = req.queryParams("id");
            if(idParam != null) {
                int id = Integer.parseInt(idParam);
                System.out.println(new Gson().toJson(sql2oAvailability.listAllInCal(id)));
                results = new Gson().toJson(sql2oAvailability.listAllInCal(id));
            } else {
                results = new Gson().toJson(sql2oAvailability.listAll());
            }
            res.type("application/json");
            res.status(200);
            return results;
        });

        //addcalendar route; add a new calendar
        post("/addcalendar", (req, res) -> {
            String name = req.queryParams("name");
            int userId = Integer.parseInt(req.queryParams("userId"));
            Calendar c = new Calendar(name, userId);
            System.out.println(c);
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

        //events route; list all events
        get("/events", (req, res) -> {
            Sql2oEventDao sql2oEventDao = new Sql2oEventDao(getSql2o());
            String results = new Gson().toJson(sql2oEventDao.listAll());
            res.type("application/json");
            res.status(200);
            return results;
        });

        //delevent route; deletes event
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

        //addevent route; inserts a new event
        post("/addevent", (req, res) -> {
            String title = req.queryParams("title");
            Range validTimeRange = Range.parseRange(req.queryParams("range"));
            Event e = new Event(title, validTimeRange);
            new Sql2oEventDao(getSql2o()).add(e);
            res.status(201);
            res.type("application/json");
            return new Gson().toJson(e.toString());
        });

        //users route; lists all users
        get("/users", (req, res) -> {
            Sql2oUserDao sql2oUserDao = new Sql2oUserDao(getSql2o());
            String results = new Gson().toJson(sql2oUserDao.listAll());
            res.type("application/json");
            res.status(200);
            return results;
        });

        //deluser route; deletes users
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


        //adduser route; inserts a new user
        post("/adduser", (req, res) -> {
            String name = req.queryParams("name");
            String password = req.queryParams("password");
            User u = new User(name, password);
            new Sql2oUserDao(getSql2o()).add(u);
            res.status(201);
            res.type("application/json");
            return new Gson().toJson(u.toString());
        });

        //availabilities route; lists all availabilities
        get("/availabilities", (req, res) -> {
            Sql2oAvailabilityDao sql2oAvailabilityDao = new Sql2oAvailabilityDao(getSql2o());
            String results = new Gson().toJson(sql2oAvailabilityDao.listAll());
            res.type("application/json");
            res.status(200);
            return results;
        });

        //delavailability route; deletes availabilities
        post("/delavailability", (req, res) -> {
            int id = Integer.parseInt(req.queryParams("id"));
            Availability a = new Availability(id);
            try {
                new Sql2oAvailabilityDao(getSql2o()).delete(a);
                res.status(204);
            } catch (DaoException ex) {
                res.status(404);
            }
            res.type("application/json");
            return new Gson().toJson(a.toString());
        });

        //addavailability route; inserts a new availability
        post("/addavailability", (req, res) -> {
            int calendarId = Integer.parseInt(req.queryParams("calendarId"));
            Date date = Date.valueOf(req.queryParams("date"));
            int qAvail = Integer.parseInt(req.queryParams("qAvail"));
            Availability a = new Availability(calendarId, date, qAvail);
            new Sql2oAvailabilityDao(getSql2o()).add(a);
            res.status(201);
            res.type("application/json");
            return new Gson().toJson(a.toString());
        });

        //Gets page to make a calendar
        get("/makecalendar", (req, res) -> {
            //res.redirect("/templates/test_checkbox.html");
            res.status(200);
            res.type("text/html");
            return IOUtils.toString(Spark.class.getResourceAsStream("/public/templates/test_checkbox.html"));
        });

        get("/viewevent", (req, res) -> {
            res.status(200);
            res.type("text/html");
            return IOUtils.toString(Spark.class.getResourceAsStream("/public/templates/index1.html"));
        });
    }
}
