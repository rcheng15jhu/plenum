import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.Availability;
import model.AvailableDate;
import model.AvailableDates;
import model.Calendar;
import model.Connections;
import model.Event;
import model.Range;
import model.User;
import okhttp3.*;
import org.junit.BeforeClass;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.*;
import java.util.List;

import static org.junit.Assert.*;


public class RESTAPITest {

    final static String URI = "jdbc:sqlite:./Plenum.db";
    static OkHttpClient client;
    static Gson gson;

    @BeforeClass
    public static void beforeClassTests() throws SQLException {

        Sql2o sql2o = new Sql2o(URI,"","");
        String sqlDropUsers = "DROP TABLE IF EXISTS Users";
        String sqlDropEvents = "DROP TABLE IF EXISTS Events";
        String sqlDropCalendars = "DROP TABLE IF EXISTS Calendars";
        String sqlDropConnections = "DROP TABLE IF EXISTS Connections";
        String sqlDropAvailabilities = "DROP TABLE IF EXISTS Availabilities";
        try (Connection con = sql2o.open()) {
            con.createQuery(sqlDropUsers).executeUpdate();
            con.createQuery(sqlDropEvents).executeUpdate();
            con.createQuery(sqlDropCalendars).executeUpdate();
            con.createQuery(sqlDropConnections).executeUpdate();
            con.createQuery(sqlDropAvailabilities).executeUpdate();
        }
        try {
            Server.main(null);
        } catch (URISyntaxException e) {
            System.out.println("oh no");
        }

        client = new OkHttpClient();
        gson = new Gson();
    }

    @Test
    public void testAddUser() throws IOException {
        // RequestBody postBody = new FormBody.Builder()
        //         .add("name", "Sadegh Hedayat")
        //         .add("numOfBooks", "26")
        //         .add("nationality", "Iranian")
        //         .build();
        // Request request = new Request.Builder()
        //         .url("http://localhost:7000/addauthor")
        //         .post(postBody)
        //         .build();
        // Response response = client.newCall(request).execute();
        // assertEquals(201, response.code());
    }
}
