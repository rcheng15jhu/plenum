import com.google.gson.Gson;
import model.User;
import okhttp3.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.io.IOException;
import java.sql.*;

import static org.junit.Assert.*;

public class RESTAPITest {

    final static String URI = "jdbc:sqlite:./Plenum.db";
    static OkHttpClient client;
    static Gson gson;
    private static Connection conn;
    private static Statement st;

    private String user1 = "12345678";
    private String user2 = "abcdefgh";

    @BeforeClass
    public static void beforeClassTests() throws SQLException {

//        Sql2o sql2o = new Sql2o(URI, "", "");
//        String sqlDropUsers = "DROP TABLE IF EXISTS Users";
//        String sqlDropEvents = "DROP TABLE IF EXISTS Events";
//        String sqlDropCalendars = "DROP TABLE IF EXISTS Calendars";
//        String sqlDropConnections = "DROP TABLE IF EXISTS Connections";
//        String sqlDropAvailabilities = "DROP TABLE IF EXISTS Availabilities";
//        try (Connection con = sql2o.open()) {
//            con.createQuery(sqlDropUsers).executeUpdate();
//            con.createQuery(sqlDropEvents).executeUpdate();
//            con.createQuery(sqlDropCalendars).executeUpdate();
//            con.createQuery(sqlDropConnections).executeUpdate();
//            con.createQuery(sqlDropAvailabilities).executeUpdate();
//        }
        //Server.main(null);

        //System.out.println("oh no");

        client = new OkHttpClient();
        gson = new Gson();
        //conn = DriverManager.getConnection(URI);
        //st = conn.createStatement();
    }

//    @Before
//    public void beforeEachTest() throws SQLException {
//        //Create new tables for each test
//        String sql = "DROP TABLE IF EXISTS User";
//        st.execute(sql);
//        String sq2 = "DROP TABLE IF EXISTS Events";
//        st.execute(sq2);
//        String sq3 = "DROP TABLE IF EXISTS Availabilities";
//        st.execute(sq3);
//        String sq4 = "DROP TABLE IF EXISTS Calendars";
//        st.execute(sq4);
//
//
//    }

    @Before
    public void beforeEachTest() throws IOException {
        delUser(user1, user1);
        delUser(user2, user2);
    }

    public int addUser(String username, String password) throws IOException {
        RequestBody postBody = new FormBody.Builder().build();
        HttpUrl url = new HttpUrl.Builder()
                .scheme("http")
                .host("localhost")
                .port(7000)
                .addPathSegment("api")
                .addPathSegment("adduser")
                .addQueryParameter("username", username)
                .addQueryParameter("password", password)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(postBody)
                .build();
        return client.newCall(request).execute().code();
    }

    public int delUser(String username, String password) throws IOException {
        RequestBody postBody = new FormBody.Builder().build();
        HttpUrl url = new HttpUrl.Builder()
                .scheme("http")
                .host("localhost")
                .port(7000)
                .addPathSegment("api")
                .addPathSegment("deluser")
                .addQueryParameter("username", username)
                .addQueryParameter("password", password)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(postBody)
                .build();
        return client.newCall(request).execute().code();
    }

    public int login(String username, String password) throws IOException {
        RequestBody postBody = new FormBody.Builder().build();
        HttpUrl url = new HttpUrl.Builder()
                .scheme("http")
                .host("localhost")
                .port(7000)
                .addPathSegment("api")
                .addPathSegment("login")
                .addQueryParameter("username", username)
                .addQueryParameter("password", password)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(postBody)
                .build();
        return client.newCall(request).execute().code();
    }

    public User getProfile(String username) throws IOException {
        RequestBody postBody = new FormBody.Builder().build();
        HttpUrl url = new HttpUrl.Builder()
                .scheme("http")
                .host("localhost")
                .port(7000)
                .addPathSegment("api")
                .addPathSegment("getprofile")
                .build();
        Request request = new Request.Builder()
                .addHeader("username", username)
                .url(url)
                .post(postBody)
                .build();
        String responseString = client.newCall(request).execute().body().string();
        System.out.println(responseString);
        return new Gson().fromJson(responseString, User.class);
    }

    @Test
    public void testAddUser() throws IOException {
        int responseCode = addUser(user1, user1);
        assertEquals(200, responseCode);
    }

    @Test
    public void testDelUser() throws IOException {
        addUser(user1, user1);
        int responseCode = delUser(user1, user1);
        assertEquals(204, responseCode);
    }

    @Test
    public void testLogin() throws IOException {
        String username = "123455678";
        int responseCode = login(user1, user1);
        assertEquals(401, responseCode);
        addUser(user1, user1);

        responseCode = login(user1, user1);
        assertEquals(200, responseCode);

        responseCode = login(user2, user1);
        assertEquals(401, responseCode);

        responseCode = login(user1, user2);
        assertEquals(401, responseCode);
    }


    @Test
    public void testViewProfile() throws IOException {
        addUser(user1, user1);
        User user = getProfile(user1);
        System.out.println(user);
        assertFalse(user == null);
    }
}
