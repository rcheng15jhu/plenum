import com.google.gson.Gson;
import model.Author;
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

    public RequestBody getAuthorRequestBody(Author a) {
        return new FormBody.Builder()
                .add("name", a.getName())
                .add("numOfBooks", Integer.toString(a.getNumOfBooks()))
                .add("nationality", a.getNationality())
                .build();
    }

    @Test
    public void testAddAuthor() throws IOException {
        Author a = new Author("Sadegh Hedayat", 26, "Iranian");
        a.setId(1);
        RequestBody postBody = getAuthorRequestBody(a);
        Request request = new Request.Builder()
                .url("http://localhost:7000/addauthor")
                .post(postBody)
                .build();

        try(Response response = client.newCall(request).execute()) {
            assertEquals(201, response.code());

            assertEquals(new Gson().toJson(a.toString()), response.body().string());

//            String expected = new Gson().toJson(a.toString());
//            String expected1 = expected.substring(0, expected.indexOf(',')-1);
//            String expected2 = expected.substring(expected.indexOf(','));
//
//            String actual = response.body().string();
//            String actual1 = actual.substring(0, actual.indexOf(',')-1);
//            String actual2 = actual.substring(actual.indexOf(','));
//
//            assertEquals(expected1, actual1);
//            assertEquals(expected2, actual2);
        }

        //adding author again would cause server error
        try(Response response2 = client.newCall(request).execute()) {
            assertEquals(500, response2.code());
        }
    }

    @Test
    public void testListAuthors() throws IOException { // TODO: should probably check that authors added show up in json...
        Request request = new Request.Builder()
                .url("http://localhost:7000/authors")
                .build();
        Response response = client.newCall(request).execute();
        assertEquals(200, response.code());
    }

    @Test
    public void testAddBook() throws IOException {
        //Add book before Author exists
        RequestBody postBody = new FormBody.Builder()
                .add("title", "Old Man and the Sea")
                .add("isbn", "0684801221")
                .add("publisher", "Scribner; Later Printing Edition")
                .add("year", "1995")
                .add("authorID", "1")
                .build();
        Request request1 = new Request.Builder()
                .url("http://localhost:7000/addbook")
                .post(postBody)
                .build();
        Response response1 = client.newCall(request1).execute();
        assertEquals(500, response1.code());

        //Add author to Authors table (assumes /addauthor works)
        RequestBody postBody2 = new FormBody.Builder()
                .add("name", "Ernest Hemingway")
                .add("numOfBooks", "12")
                .add("nationality", "American")
                .build();
        Request request2 = new Request.Builder()
                .url("http://localhost:7000/addauthor")
                .post(postBody2)
                .build();
        client.newCall(request2).execute();

        //Test that addbook works
        Response response3 = client.newCall(request1).execute();
        assertEquals(201, response3.code());

        //Adding existing book would cause server error
        Response response4 = client.newCall(request1).execute();
        assertEquals(500, response4.code());
    }

    @Test
    public void testListBooks() throws IOException { // TODO: should probably check that books added show up in json...
        Request request = new Request.Builder()
                .url("http://localhost:7000/books")
                .build();
        Response response = client.newCall(request).execute();
        assertEquals(200, response.code());
    }

    @Test
    public void testDeleteAuthor() throws IOException {
        //add author to authors table
        RequestBody postBody1 = new FormBody.Builder()
                .add("name", "Ernest Hemingway")
                .add("numOfBooks", "12")
                .add("nationality", "American")
                .build();
        Request request1 = new Request.Builder()
                .url("http://localhost:7000/addauthor")
                .post(postBody1)
                .build();
        client.newCall(request1).execute();

        //delete author just added
        RequestBody postBody2 = new FormBody.Builder()
                .add("name", "Ernest Hemingway")
                .build();
        Request request2 = new Request.Builder()
                .url("http://localhost:7000/delauthor")
                .post(postBody2)
                .build();
        Response response2 = client.newCall(request2).execute();
        assertEquals(204, response2.code());

        //should not have server error (500) if author successfully deleted
        Response response3 = client.newCall(request1).execute();
        assertEquals(201, response3.code());
    }

    @Test
    public void testDeleteBook() throws IOException {
        //add author and book(assumes /addauthor and /addbook works)
        //add author to authors table
        RequestBody postBody0 = new FormBody.Builder()
                .add("name", "Ernest Hemingway")
                .add("numOfBooks", "12")
                .add("nationality", "American")
                .build();
        Request request0 = new Request.Builder()
                .url("http://localhost:7000/addauthor")
                .post(postBody0)
                .build();
        client.newCall(request0).execute();

        RequestBody postBody = new FormBody.Builder()
                .add("title", "Old Man and the Sea")
                .add("isbn", "0684801221")
                .add("publisher", "Scribner; Later Printing Edition")
                .add("year", "1995")
                .add("authorID", "1")
                .build();
        Request request1 = new Request.Builder()
                .url("http://localhost:7000/addbook")
                .post(postBody)
                .build();
        client.newCall(request1).execute();

        //delete author just added
        RequestBody postBody2 = new FormBody.Builder()
                .add("isbn", "0684801221")
                .build();
        Request request2 = new Request.Builder()
                .url("http://localhost:7000/delbook")
                .post(postBody2)
                .build();
        Response response2 = client.newCall(request2).execute();
        assertEquals(204, response2.code());

        //should not have server error (500) if book successfully deleted
        Response response3 = client.newCall(request1).execute();
        assertEquals(201, response3.code());
    }
}