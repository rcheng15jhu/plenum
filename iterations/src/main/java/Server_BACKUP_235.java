import com.google.gson.Gson;
import exception.DaoException;
import model.*;
import org.sql2o.Sql2o;
import org.sqlite.SQLiteConfig;
import persistence.Sql2oAvailabilityDao;
import persistence.Sql2oCalendarDao;
import persistence.Sql2oEventDao;
import persistence.Sql2oUserDao;
import static spark.Spark.*;

import spark.ModelAndView;
import spark.Spark;
import spark.template.velocity.VelocityTemplateEngine;
import spark.utils.IOUtils;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Server {

    private static Sql2o sql2o;

    private static Sql2o getSql2o() throws URISyntaxException {
        if(sql2o == null) {
            System.out.println("was null!");
            // set on foreign keys
            SQLiteConfig config = new SQLiteConfig();
            config.enforceForeignKeys(true);
            config.setPragma(SQLiteConfig.Pragma.FOREIGN_KEYS, "ON");

            // create data source - update to use postgresql
            String[] dbUrl = getDbUrl(System.getenv("DATABASE_URL"));
            sql2o = new Sql2o(dbUrl[0], dbUrl[1], dbUrl[2]);

            try (Connection conn = getConnection()) {
                String sq1 = "CREATE TABLE IF NOT EXISTS Users (" +
                        " id            serial PRIMARY KEY," +
                        " name          VARCHAR(100) NOT NULL UNIQUE," +
                        " password      VARCHAR(100) NOT NULL" +
                        ");";
                String sq2 = "CREATE TABLE IF NOT EXISTS Events (" +
                        " id            serial PRIMARY KEY," +
                        " title         VARCHAR(100) NOT NULL," +
                        " startTime     INTEGER," +
                        " endTime       INTEGER" +
                        ");";
                String sq3 = "CREATE TABLE IF NOT EXISTS Calendars (" +
                        " id            serial PRIMARY KEY," +
                        " name          VARCHAR(100) NOT NULL," +
                        " userId        INTEGER NOT NULL," +
                        " FOREIGN KEY(userId)" +
                        " REFERENCES Users (id)" +
                        "   ON UPDATE CASCADE" +
                        "   ON DELETE CASCADE" +
                        ");";
                String sq4 = "CREATE TABLE IF NOT EXISTS Connections (" +
                        " id            serial PRIMARY KEY," +
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
                String sq5 = "CREATE TABLE IF NOT EXISTS Availabilities (" +
                        " id            serial PRIMARY KEY," +
                        " calendarId    INTEGER NOT NULL," +
<<<<<<< HEAD
                        " date          DATE," +
                        " qAvail        INTEGER NOT NULL," + 
=======
                        " date          INTEGER NOT NULL," +
                        " qAvail        INTEGER NOT NULL," +
>>>>>>> 08edc7b1efb34475e1282364e1cc74816f3218ec
                        " FOREIGN KEY(calendarId)" +
                        " REFERENCES Calendars (id)" +
                        "   ON UPDATE CASCADE" +
                        "   ON DELETE CASCADE" +
                        ");";
                Statement st = conn.createStatement();
                st.executeUpdate(sq1);
                st.executeUpdate(sq2);
                st.executeUpdate(sq3);
                st.executeUpdate(sq4);
                st.executeUpdate(sq5);
            } catch (URISyntaxException | SQLException e) {
                e.printStackTrace();
            }
        }
        return sql2o;
    }

    public static Connection getConnection() throws URISyntaxException, SQLException {
        String databaseUrl = System.getenv("DATABASE_URL");
        if (databaseUrl == null) {
            // Not on Heroku
            throw new SQLException();
        }

        String[] dbUri = getDbUrl(databaseUrl);

        String username = dbUri[1];
        String password = dbUri[2];
        String dbUrl = dbUri[0];

        return DriverManager.getConnection(dbUrl, username, password);
    }

    private static String[] getDbUrl(String databaseUrl) throws URISyntaxException {
        if (databaseUrl == null) {
            throw new URISyntaxException("error", "Incorrect database URL");
        }

        URI dbUri = new URI(databaseUrl);

        String username = dbUri.getUserInfo().split(":")[0];
        String password = dbUri.getUserInfo().split(":")[1];
        String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':'
                + dbUri.getPort() + dbUri.getPath() + "?sslmode=require";
        return new String[]{dbUrl, username, password};
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

        /* after((Filter) (request, response) -> {
            response.header("Access-Control-Allow-Origin", "*");
            response.header("Access-Control-Allow-Methods", "GET, POST");
        }); */

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

        //adduser route; allows a new user to be added
        post("/adduser", (req, res) -> {
            System.out.println("request received!");
            String username = req.queryParams("username");
            String password = req.queryParams("password");
            res.cookie("username", username);
            User u = new User(username, password);
            new Sql2oUserDao(getSql2o()).add(u);
            res.redirect("/");
            return new Gson().toJson(u.toString());
        });

        //displays sign-up view
        get("/adduser", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            res.status(200);
            res.type("text/html");
            return new ModelAndView(model, "public/templates/signup.vm");
        }, new VelocityTemplateEngine());


        // calendars route; return list of calendars as JSON
        get("/calendars", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
//            if (req.cookie("username") == null) {
//                res.redirect("/");
//                return null;
//            }
            Sql2oCalendarDao sql2oCalendar = new Sql2oCalendarDao(getSql2o());
            model.put("calendars", sql2oCalendar.listAll());
            res.type("text/html");
            res.status(200);
            return new ModelAndView(model, "public/templates/calendars.vm");
        }, new VelocityTemplateEngine());

        //calendar route; returns availabilities associated with the calendar id
        get("/calendar", (req, res) -> {
            String results;
            String idParam = req.queryParams("id");
            if(idParam != null) {
                int id = Integer.parseInt(idParam);
                Calendar c = new Calendar(id);
                List<Availability> availabilities = new Sql2oAvailabilityDao(getSql2o()).listAllInCal(c);
                results = new Gson().toJson(AvailableDates.createFromAvailability(availabilities));
            } else {
                results = new Gson().toJson(new Sql2oCalendarDao(getSql2o()).listAll());
                System.out.println(results);
            }
//            Sql2oCalendarDao sql2oCalendarDao = new Sql2oCalendarDao(getSql2o());
//            int id = Integer.parseInt(req.queryParams("id"));
//            Calendar c = sql2oCalendarDao.getCal(id);


            res.type("application/json");
            res.status(200);
            return results;
        });

        //addcalendar route; add a new calendar
        post("/addcalendar", (req, res) -> {
            String name = req.queryParams("title");
            String username = req.cookie("username");
            int userId = new Sql2oUserDao(getSql2o()).getId(username);
            Calendar c = new Calendar(name, userId);
            System.out.println(c);
            new Sql2oCalendarDao(getSql2o()).add(c);
            String blob = req.body();
            System.out.println(blob);
            AvailableDates a = new Gson().fromJson(blob, AvailableDates.class);
            Sql2oAvailabilityDao availabilityDao = new Sql2oAvailabilityDao(getSql2o());
            a.getAvailabilityStream(c.getId())
                    .forEachOrdered(availabilityDao::add);
            res.status(201);
            res.type("application/json");
            return new Gson().toJson(c.toString());
        });

        //delcalendar route; delete calendar
        post("/delcalendar", (req, res) -> {
            int id = Integer.parseInt(req.queryParams("id"));
            Calendar c = new Calendar(id);
            try {
                c = new Sql2oCalendarDao(getSql2o()).getCal(id);
                System.out.println(c);
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
            Map<String, Object> model = new HashMap<>();
//        if (req.cookie("username") == null) {
//            res.redirect("/");
//            return null;
//        }
            Sql2oEventDao sql2oEventDao = new Sql2oEventDao(getSql2o());
            model.put("events", sql2oEventDao.listAll());
            res.type("text/html");
            res.status(200);
            return new ModelAndView(model, "public/templates/events.vm");
        }, new VelocityTemplateEngine());

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
            int startTime = Integer.parseInt(req.queryParams("startTime"));
            int endTime = Integer.parseInt(req.queryParams("endTime"));
            Event e = new Event(title, startTime, endTime);
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
            int date = Integer.parseInt(req.queryParams("date"));
            System.out.println(date);
            int qAvail = Integer.parseInt(req.queryParams("qAvail"));
            Availability a = new Availability(calendarId, date, qAvail);
            new Sql2oAvailabilityDao(getSql2o()).add(a);
            res.status(201);
            res.type("application/json");
            return new Gson().toJson(a.toString());
        });

        //updateavailability route; updates availability
        post("/updateavailability", (req, res) -> {
            int calendarId = Integer.parseInt(req.queryParams("calendarId"));
            int date = Integer.parseInt(req.queryParams("date"));
            System.out.println(date);
            int qAvail = Integer.parseInt(req.queryParams("qAvail"));
            int availstate = Integer.parseInt(req.queryParams("state"));
            Availability a = new Availability(calendarId, date, qAvail);
            if (availstate == 1) {
                new Sql2oAvailabilityDao(getSql2o()).add(a);
                }
            else {
                new Sql2oAvailabilityDao(getSql2o()).delete(a);
            }

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

        get("/viewstaticcalendar", (req, res) -> {
            res.status(200);
            res.type("text/html");
            return IOUtils.toString(Spark.class.getResourceAsStream("/public/templates/index1.html"));
        });

        get("/viewevent", (req, res) -> {
            res.status(200);
            res.type("text/html");
            return IOUtils.toString(Spark.class.getResourceAsStream("/public/templates/index2.html"));
        });

        get("/makecalendarnew", (req, res) -> {
            res.status(200);
            res.type("text/html");
            return IOUtils.toString(Spark.class.getResourceAsStream("/public/templates/index3.html"));
        });

        get("/viewcalendar", (req, res) -> {
            res.status(200);
            res.type("text/html");
            return IOUtils.toString(Spark.class.getResourceAsStream("/public/templates/index4.html"));
        });


    }
}
