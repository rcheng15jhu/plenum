import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import exception.DaoException;
import model.*;
import org.sql2o.Sql2o;
import org.sqlite.SQLiteConfig;
import persistence.Sql2oAvailabilityDao;
import persistence.Sql2oConnectionsDao;
import persistence.Sql2oCalendarDao;
import persistence.Sql2oEventDao;
import persistence.Sql2oUserDao;
import security.Encryption;
import static spark.Spark.*;

import spark.Spark;
import spark.utils.IOUtils;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Stream;

import io.github.cdimascio.dotenv.Dotenv;

public class Server {

    private static Sql2o sql2o;

    private static Sql2o getSql2o() throws URISyntaxException {
        if(sql2o == null) {
            try (Connection conn = getConnection()) {
                String sq1 = "CREATE TABLE IF NOT EXISTS Users (" +
                        " id            serial PRIMARY KEY," +
                        " name          VARCHAR(100) NOT NULL UNIQUE," +
                        " password      VARCHAR(48) NOT NULL" +
                        " salt          VARCHAR(36) NOT NULL" +
                        ");";
                String sq2 = "CREATE TABLE IF NOT EXISTS Events (" +
                        " id            serial PRIMARY KEY," +
                        " title         VARCHAR(100) NOT NULL," +
                        " startTime     INTEGER NOT NULL," +
                        " endTime       INTEGER NOT NULL" +
                        ");";
                String sq3 = "CREATE TABLE IF NOT EXISTS Calendars (" +
                        " id            serial PRIMARY KEY," +
                        " title         VARCHAR(100) NOT NULL," +
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
                        " date          INTEGER NOT NULL," +
                        " qHour        INTEGER NOT NULL," +
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

        // create data source - update to use postgresql
        String[] dbUrl = getDbUrl(System.getenv("DATABASE_URL"));
        sql2o = new Sql2o(dbUrl[0], dbUrl[1], dbUrl[2]);

        return sql2o;
    }

    public static Connection getConnection() throws SQLException, URISyntaxException {
        String databaseUrl = System.getenv("DATABASE_URL");
        if (databaseUrl == null) { //running locally
            Dotenv dotenv = Dotenv.load();
            return DriverManager.getConnection(
                dotenv.get("DEV_DB_URL"),
                dotenv.get("DEV_DB_USER"),
                dotenv.get("DEV_DB_PWORD"));
        }
        String[] dbUri = getDbUrl(databaseUrl);

        String username = dbUri[1];
        String password = dbUri[2];
        String dbUrl = dbUri[0];

        return DriverManager.getConnection(dbUrl, username, password);
    }

    private static String[] getDbUrl(String databaseUrl) throws URISyntaxException {
        String username, password, dbUrl;
        if (databaseUrl == null) {
            Dotenv dotenv = Dotenv.load();
            username = dotenv.get("DEV_DB_USER");
            password = dotenv.get("DEV_DB_PWORD");
            dbUrl = dotenv.get("DEV_DB_URL");
        } else {
            URI dbUri = new URI(databaseUrl);

            username = dbUri.getUserInfo().split(":")[0];
            password = dbUri.getUserInfo().split(":")[1];
            dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':'
                    + dbUri.getPort() + dbUri.getPath() + "?sslmode=require";
        }

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

    public static void main(String[] args) throws URISyntaxException {
        // set port number
        port(getHerokuAssignedPort());

        staticFiles.location("/public");

        sql2o = getSql2o();

        /* after((Filter) (request, response) -> {
            response.header("Access-Control-Allow-Origin", "*");
            response.header("Access-Control-Allow-Methods", "GET, POST");
        }); */

        // root route; show a simple message!

        post("/api/login", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            String username = req.queryParams("username");
            String password = req.queryParams("password");
            boolean corr = new Sql2oUserDao(getSql2o()).checkCred(username, password);
            if (corr) {
                //res.cookie("username", username);
                res.status(200);
            }
            else {
                res.status(401);
            }
            return "";
        });

//        get("/", (req, res) -> {
//            Map<String, Object> model = new HashMap<>();
//            if (req.cookie("username") != null)
//                model.put("username", req.cookie("username"));
//            res.status(200);
//            res.type("text/html");
//            return IOUtils.toString(Spark.class.getResourceAsStream("/public/static/html/index.html"));
//        });

        //adduser route; allows a new user to be added

        post("/api/adduser", (req, res) -> {
            System.out.println("request received!");
            String username = req.queryParams("username");
            String password = req.queryParams("password");
            String salt = makeSalt();
            //res.cookie("username", username);
            User u = new User(username, sha2_hash(password, salt), salt);
            new Sql2oUserDao(getSql2o()).add(u);
            res.redirect("/");
            //don't return actual new user for security reasons
            //but return empty string nonetheless
            return "";
        });

//        //displays sign-up view
//        get("/adduser", (req, res) -> {
//            Map<String, Object> model = new HashMap<>();
//            res.status(200);
//            res.type("text/html");
//            return new ModelAndView(model, "public/templates/signup.vm");
//        }, new VelocityTemplateEngine());


        // calendars route; return list of calendars as JSON
        get("/api/calendars", (req, res) -> {
            Sql2o sql2o = getSql2o();
//            if (req.cookie("username") == null) {
//                res.redirect("/");
//                return null;
//            }
            String name = req.cookie("username");
            int userId = new Sql2oUserDao(sql2o).getUserFromName(name).getId();
            String results = new Gson().toJson(new Sql2oCalendarDao(sql2o).listOne(userId));
            res.type("application/json");
            res.status(200);
            return results;
        });
    
        //calendar route; returns availabilities associated with the calendar id
        get("/api/calendar", (req, res) -> {
            Sql2o sql2o = getSql2o();
            String username = req.cookie("username");
            String idParam = req.queryParams("id");
            String results;
            int userId = new Sql2oUserDao(sql2o).getUserFromName(username).getId();
            if(idParam != null) {
                int id = Integer.parseInt(idParam);
                Calendar c = new Sql2oCalendarDao(sql2o).getCal(id);
                User u = new Sql2oUserDao(sql2o).getUserFromId(c.getUserId());
                if (!username.equals(u.getName())) {
                    res.status(404);
                    return "";
                }
                List<Availability> availabilities = new Sql2oAvailabilityDao(sql2o).listAllInCal(c);
                results = new Gson().toJson(AvailableDates.createFromAvailability(u.getName(), c.getTitle(), availabilities));
            } else {
                String name = req.cookie("username");
                results = new Gson().toJson(new Sql2oCalendarDao(sql2o).listOne(userId));
                System.out.println(results);
            }
//            Sql2oCalendarDao sql2oCalendarDao = new Sql2oCalendarDao(getSql2o());
//            int id = Integer.parseInt(req.queryParams("id"));
//            Calendar c = sql2oCalendarDao.getCal(id);


            res.type("application/json");
            res.status(200);
            return results;
        });

        get("/api/aggregate", (req, res) -> {
            Sql2o sql2o = getSql2o();
            String results = "";
            //event id
            String idParam = req.queryParams("id");
            if(idParam != null) {
                List<Connections> conns = new Sql2oConnectionsDao(sql2o).listAll();
                List<AvailableDates> ads = new ArrayList<>();
                //get all available dates of all calendars associated with event id
                for(Connections co : conns) {
                    if((co.getEventId() + "").equals(idParam)) {
                        Calendar ca = new Sql2oCalendarDao(sql2o).getCal(co.getCalendarId());
                        User us = new Sql2oUserDao(sql2o).getUserFromId(ca.getUserId());
                        List<Availability> av = new Sql2oAvailabilityDao(sql2o).listAllInCal(ca);
                        AvailableDates ad = AvailableDates.createFromAvailability(us.getName(), ca.getTitle(), av);
                        ads.add(ad);
                    }
                }
                results = new Gson().toJson(ads);
            }
            res.type("application/json");
            res.status(200);
            return results;
        });

        //addcalendar route; add a new calendar
        post("/api/addcalendar", (req, res) -> {
            long startTime = System.nanoTime();
            String title = req.queryParams("title");
            String username = req.cookie("username");
            int userId = new Sql2oUserDao(sql2o).getUserFromName(username).getId();
            Calendar c = new Calendar(title, userId);
            System.out.println(c);
            new Sql2oCalendarDao(sql2o).add(c);
            String blob = req.body();
            System.out.println(blob);
            long stopTime = System.nanoTime();     
            AvailableDates a = new Gson().fromJson(blob, AvailableDates.class);
            Sql2oAvailabilityDao availabilityDao = new Sql2oAvailabilityDao(sql2o);
            a.getAvailabilityStream(c.getId())
                    .forEachOrdered(availabilityDao::add);
            res.status(201);
            res.type("application/json");
            System.out.println(stopTime - startTime);       
            return new Gson().toJson(c.toString());
        });

        //delcalendar route; delete calendar
        post("/api/delcalendar", (req, res) -> {
            Sql2o sql2o = getSql2o();
            int id = Integer.parseInt(req.queryParams("id"));
            Calendar c = new Calendar(id);
            try {
                c = new Sql2oCalendarDao(sql2o).getCal(id);
                System.out.println(c);
                new Sql2oCalendarDao(sql2o).delete(c);
                res.status(204);
            } catch (DaoException ex) {
                res.status(404);
            }
            res.type("application/json");
            return new Gson().toJson(c.toString());
        });

        //events route; list all events
        get("/api/events", (req, res) -> {
            List<Event> events = new Sql2oEventDao(getSql2o()).listAll();
            String results = new Gson().toJson(events);
            res.type("application/json");
            res.status(200);
            return results;
        });

        //delevent route; deletes event
        post("/api/delevent", (req, res) -> {
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
        post("/api/addevent", (req, res) -> {
            String title = req.queryParams("title");
            int startTime = Integer.parseInt(req.queryParams("startTime"));
            int endTime = Integer.parseInt(req.queryParams("endTime"));
            Event e = new Event(title, startTime, endTime);
            System.out.println(e.toString());
            new Sql2oEventDao(getSql2o()).add(e);
            res.status(201);
            res.type("application/json");
            return new Gson().toJson(e.toString());
        });

        //addconnection route; associates a calendar and an event together
        post("/api/addconnection", (req, res) -> {
            Sql2o sql2o = getSql2o();
            String username = req.cookie("username");
            int userId = new Sql2oUserDao(sql2o).getUserFromName(username).getId();
            int eventId = Integer.parseInt(req.queryParams("eventId"));
            int calendarId = Integer.parseInt(req.queryParams("calendarId"));
            Connections c = new Connections(eventId, calendarId, userId);
            System.out.println(c.toString());
            new Sql2oConnectionsDao(sql2o).add(c);
            res.status(201);
            res.type("application/json");
            return new Gson().toJson(c.toString());
        });

        //delonnection route; inserts a new event
        post("/api/delconnection", (req, res) -> {
            Sql2o sql2o = getSql2o();
            String username = req.cookie("username");
            int userId = new Sql2oUserDao(sql2o).getUserFromName(username).getId();
            int eventId = Integer.parseInt(req.queryParams("eventId"));
            int calendarId = Integer.parseInt(req.queryParams("calendarId"));
            Connections c = new Connections(eventId, calendarId, userId);
            System.out.println(c.toString());
            try {
                new Sql2oConnectionsDao(sql2o).delete(c);
                res.status(204);
            } catch (DaoException e) {
                res.status(404);
            }
            res.type("application/json");
            return new Gson().toJson(c.toString());
        });

        //connections route; lists all availabilities
        get("/api/connections", (req, res) -> {
            Sql2oConnectionsDao sql2oConnectionsDao = new Sql2oConnectionsDao(getSql2o());
            String results = new Gson().toJson(sql2oConnectionsDao.listAll());
            res.type("application/json");
            res.status(200);
            return results;
        });

        //changepassword route; allows user to change password
        post("/changepassword", (req, res) -> {
            if (req.cookie("username") == null)
                res.redirect("/");
            String username = req.cookie("username");
            String password = req.queryParams("password");
            String newpassword = req.queryParams("new password");
            boolean pcheck = new Sql2oUserDao(getSql2o()).passwordcheck(username, password, newpassword);
            if (pcheck) {
                res.status(200);
            }
            else {
                res.status(400);
            }
            res.type("application/json");
            //don't return actual new user for security reasons
            //but return empty string nonetheless
            return "";
        });

        //updateconnection route; edit the calendar associated with the event
        post("/api/updateconnection", (req, res) -> {
            Sql2o sql2o = getSql2o();
            String username = req.cookie("username");
            int userId = new Sql2oUserDao(sql2o).getUserFromName(username).getId();
            int eventId = Integer.parseInt(req.queryParams("eventId"));
            int calendarId = Integer.parseInt(req.queryParams("calendarId"));
            Connections c = new Connections(eventId, calendarId, userId);
            System.out.println(c.toString());
            List<Connections> updateconnections = new Sql2oConnectionsDao(sql2o).updateconnectionscheck(eventId, calendarId, userId);
            if (updateconnections.size() == 1) {
                new Sql2oConnectionsDao(sql2o).delete(updateconnections.get(0));
            }

            new Sql2oConnectionsDao(sql2o).add(c);
            res.status(201);
            res.type("application/json");
            return new Gson().toJson(c.toString());
        });

        //users route; lists all users
        get("/users", (req, res) -> {
            if (req.cookie("username") == null)
                res.redirect("/");
            Sql2oUserDao sql2oUserDao = new Sql2oUserDao(getSql2o());
            String results = new Gson().toJson(sql2oUserDao.listAll());
            res.type("application/json");
            res.status(200);
            return results;
        });

        //deluser route; deletes users
        post("/deluser", (req, res) -> {
            if (req.cookie("username") == null)
                res.redirect("/");
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
            if (req.cookie("username") == null)
                res.redirect("/");
            Sql2oAvailabilityDao sql2oAvailabilityDao = new Sql2oAvailabilityDao(getSql2o());
            String results = new Gson().toJson(sql2oAvailabilityDao.listAll());
            res.type("application/json");
            res.status(200);
            return results;
        });

        //delavailability route; deletes availabilities
        post("/delavailability", (req, res) -> {
            if (req.cookie("username") == null)
                res.redirect("/");
            int calendarId = Integer.parseInt(req.queryParams("calendarId"));
            int date = Integer.parseInt(req.queryParams("date"));
            System.out.println(date);
            int qHour = Integer.parseInt(req.queryParams("qHour"));
            Availability a = new Availability(calendarId, date, qHour);
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
            if (req.cookie("username") == null)
                res.redirect("/");
            int calendarId = Integer.parseInt(req.queryParams("calendarId"));
            int date = Integer.parseInt(req.queryParams("date"));
            System.out.println(date);
            int qHour = Integer.parseInt(req.queryParams("qHour"));
            Availability a = new Availability(calendarId, date, qHour);
            new Sql2oAvailabilityDao(getSql2o()).add(a);
            res.status(201);
            res.type("application/json");
            return new Gson().toJson(a.toString());
        });

        //updateavailability route; updates availability
        post("/api/updateavailability", (req, res) -> {
            Sql2o sql2o = getSql2o();
            int calendarId = Integer.parseInt(req.queryParams("calendarId"));
            int date = Integer.parseInt(req.queryParams("date"));
            System.out.println(date);
            int qHour = Integer.parseInt(req.queryParams("qHour"));
            int availstate = Integer.parseInt(req.queryParams("state"));
            Availability a = new Availability(calendarId, date, qHour);
            boolean curAvail = new Sql2oAvailabilityDao(sql2o).updatecheck(a);
            if (availstate == 1 && !curAvail) {
                new Sql2oAvailabilityDao(sql2o).add(a);
                res.status(201);
                }
            else if (availstate == 0 && curAvail) {
                new Sql2oAvailabilityDao(sql2o).delete(a);
                res.status(204);
            }


            res.type("application/json");
            return new Gson().toJson(a.toString());
        });



        makeStaticRoutes(
                Arrays.asList(
                          "/login", "/profile"
                        , "/create-calendar", "/view-calendar", "/list-calendar"
                        , "/create-event", "/list-events"
                ),
                Arrays.asList(
                        new StaticRoutes("/", "index").noRedirect(),
                        new StaticRoutes("", "index").noRedirect(),
                        new StaticRoutes("view-event").noRedirect()
                )
        );

    }

    private static class StaticRoutes {
        public String routeName;
        public String fileName;
        public boolean redirect;

        public StaticRoutes(String routeName) {
            this.routeName = routeName;
            this.fileName = routeName.substring(1);
            redirect = true;
        }

        public StaticRoutes(String routeName, String fileName) {
            this.routeName = routeName;
            this.fileName = fileName;
            redirect = true;
        }

        public StaticRoutes noRedirect() {
            redirect = false;
            return this;
        }
    }



    public static void makeStaticRoutes(List<String> simpleRoutes, List<StaticRoutes> otherRoutes) {
        Stream.concat(simpleRoutes.parallelStream().map(StaticRoutes::new).sequential(), otherRoutes.stream())
                .forEach(route -> get(route.routeName, (req, res) -> {
                    res.status(200);
                    res.type("text/html");
                    if (route.redirect && req.cookie("username") == null) {
                        res.redirect("/");
                        return "";
                    }

                    return IOUtils.toString(Spark.class.getResourceAsStream("/public/static/html/" + route.fileName + ".html"));
                }));
    }
}
