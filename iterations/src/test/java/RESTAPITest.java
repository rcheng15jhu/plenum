import com.google.gson.Gson;
import okhttp3.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.io.IOException;
import java.sql.*;

import static org.junit.Assert.*;

public class RESTAPITest {

    final static String URI = "jdbc:sqlite:./Plenum.db";
    static OkHttpClient client;
    static Gson gson;
    private static Statement st;

    @BeforeClass
    public static void beforeClassTests() throws SQLException {

        Sql2o sql2o = new Sql2o(URI, "", "");
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
        Server.main(null);

        System.out.println("oh no");

        client = new OkHttpClient();
        gson = new Gson();
    }

    @Before
    public void beforeEachTest() throws SQLException {
        //Create new tables for each test
        String sql = "DROP TABLE IF EXISTS Authors";
        st.execute(sql);
        String sq2 = "DROP TABLE IF EXISTS Books";
        st.execute(sq2);

        String sql3 = "CREATE TABLE IF NOT EXISTS Authors (id INTEGER PRIMARY KEY, name VARCHAR(100) NOT NULL UNIQUE," +
                " numOfBooks INTEGER, nationality VARCHAR(30));";
        st.execute(sql3);

        String sq4 = "CREATE TABLE IF NOT EXISTS Books (" +
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
        st.execute(sq4);
    }

    @Test
    public void testAddUser() throws IOException {
        RequestBody postBody = new FormBody.Builder()
                .add("name", "username123")
                .add("password", "123")
                .build();
        Request request = new Request.Builder()
                 .url("http://localhost:7000/api/adduser")
                 .post(postBody)
                 .build();
         Response response = client.newCall(request).execute();
         assertEquals("", response.code());
    }

    @Test
    public void testLogin() throws IOException {
        RequestBody postBodyAdd = new FormBody.Builder()
                .add("name", "username123")
                .add("password", "123")
                .build();
        Request requestAdd = new Request.Builder()
                .url("http://localhost:7000/api/adduser")
                .post(postBodyAdd)
                .build();
        client.newCall(requestAdd).execute();

        RequestBody postBody = new FormBody.Builder()
                .add("name", "username123")
                .add("password", "123")
                .build();
        Request request = new Request.Builder()
                .url("http://localhost:7000/api/login")
                .post(postBody)
                .build();
        Response response = client.newCall(request).execute();
        assertEquals(201, response.code());

        RequestBody postBody1 = new FormBody.Builder()
                .add("name", "username12")
                .add("password", "123")
                .build();
        Request request1 = new Request.Builder()
                .url("http://localhost:7000/api/login")
                .post(postBody1)
                .build();
        Response response1 = client.newCall(request1).execute();
        assertEquals(401, response1.code());

        RequestBody postBody2 = new FormBody.Builder()
                .add("name", "username123")
                .add("password", "12345")
                .build();
        Request request2 = new Request.Builder()
                .url("http://localhost:7000/api/login")
                .post(postBody2)
                .build();
        Response response2 = client.newCall(request2).execute();
        assertEquals(401, response2.code());
    }
}
