import okhttp3.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import java.io.IOException;
import java.sql.*;
import java.sql.Connection;

import static org.junit.Assert.*;

public class RESTAPITest {

    static OkHttpClient client;
    private static String URI;
    private static Connection conn;
    private static Statement st;

    @BeforeClass
    public static void beforeClassTests() throws SQLException {
        client = new OkHttpClient();
        URI = "jdbc:sqlite:./MyBooksApp.db";
        conn = DriverManager.getConnection(URI);
        st = conn.createStatement();

        String sql = "DROP TABLE IF EXISTS Authors";
        st.execute(sql);
        String sq2 = "DROP TABLE IF EXISTS Books";
        st.execute(sq2);
    }

    @Before
    public void beforeEachTest() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS Authors (id INTEGER PRIMARY KEY, name VARCHAR(100) NOT NULL UNIQUE," +
                " numOfBooks INTEGER, nationality VARCHAR(30));";
        st.execute(sql);

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
        st.execute(sq2);
    }

    @Test
    public void testListAuthors() throws IOException {
        Request request = new Request.Builder()
                .url("http://localhost:7000/authors")
                .build();
        Response response = client.newCall(request).execute();
        assertEquals(200, response.code());
    }

    @Test
    public void testAddAuthor() throws IOException {
        RequestBody postBody = new FormBody.Builder()
                .add("name", "Sadegh Hedayat")
                .add("numOfBooks", "26")
                .add("nationality", "Iranian")
                .build();
        Request request = new Request.Builder()
                .url("http://localhost:7000/addauthor")
                .post(postBody)
                .build();
        Response response = client.newCall(request).execute();
        assertEquals(201, response.code());
    }

    @Test
    public void testListBooks() throws IOException {
        Request request = new Request.Builder()
                .url("http://localhost:7000/books")
                .build();
        Response response = client.newCall(request).execute();
        assertEquals(200, response.code());
    }
}