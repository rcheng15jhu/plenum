import com.google.gson.Gson;
import model.Author;
import model.Book;
import okhttp3.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import java.io.IOException;
import java.sql.*;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

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
        }

        //adding author again would cause server error
        try(Response response2 = client.newCall(request).execute()) {
            assertEquals(500, response2.code());
        }
    }

    @Test
    public void testListAuthors() throws IOException {
        //adds a few authors to Authors table (assumes /addauthor works)
        List<Author> authorsToAdd = new ArrayList<>();
        authorsToAdd.add(new Author("Sadegh Hedayat", 26, "Iranian"));
        authorsToAdd.add(new Author("Ernest Hemingway", 12, "American"));
        int id = 1;
        for(Author a : authorsToAdd) {
            a.setId(id++);
            RequestBody postBody = getAuthorRequestBody(a);
            client.newCall(new Request.Builder()
                    .url("http://localhost:7000/addauthor")
                    .post(postBody)
                    .build()).execute().close();
        }

        Request request = new Request.Builder()
                .url("http://localhost:7000/authors")
                .build();
        try(Response response = client.newCall(request).execute()) {
            assertEquals(200, response.code());

            assertEquals(new Gson().toJson(authorsToAdd), response.body().string());
        }
    }

    public RequestBody getBookRequestBody(Book b) {
        return new FormBody.Builder()
                .add("title", b.getTitle())
                .add("isbn", b.getIsbn())
                .add("publisher", b.getPublisher())
                .add("year", Integer.toString(b.getYear()))
                .add("authorID", Integer.toString(b.getAuthorId()))
                .build();
    }

    @Test
    public void testAddBook() throws IOException {
        //Add book before Author exists
        Book b1 = new Book("Old Man and the Sea", "0684801221", "Scribner; Later Printing Edition"
                , 1995, 1);
        RequestBody postBody1 = getBookRequestBody(b1);
        Request request1 = new Request.Builder()
                .url("http://localhost:7000/addbook")
                .post(postBody1)
                .build();
        try(Response response1 = client.newCall(request1).execute()) {
            assertEquals(500, response1.code());
        }

        //Add author to Authors table (assumes /addauthor works)
        Author a = new Author("Ernest Hemingway", 12, "American");
        RequestBody postBody2 = getAuthorRequestBody(a);
        Request request2 = new Request.Builder()
                .url("http://localhost:7000/addauthor")
                .post(postBody2)
                .build();
        client.newCall(request2).execute().close();

        //Test that addbook works
        try(Response response3 = client.newCall(request1).execute()) {
            assertEquals(201, response3.code());
            assertEquals(new Gson().toJson(b1.toString()), response3.body().string());
        }

        //Adding existing book would cause server error
        try(Response response4 = client.newCall(request1).execute()) {
            assertEquals(500, response4.code());
        }

        //Adding book with invalid authorId would cause server error
        Book b2 = new Book("The Hobbit", "9780547928227", "George Allen and Unwin", 1937, 2);
        RequestBody postBody3 = getBookRequestBody(b2);
        Request request3 = new Request.Builder()
                .url("http://localhost:7000/addbook")
                .post(postBody3)
                .build();
        try(Response response5 = client.newCall(request3).execute()) {
            assertEquals(500, response5.code());
        }
    }

    @Test
    public void testListBooks() throws IOException {
        //adds authors and books to respective tables (assumes /addauthor and /addbook work)
        List<Author> authorsToAdd = new ArrayList<>();
        authorsToAdd.add(new Author("J. R. R. Tolkien", 23, "American"));
        authorsToAdd.add(new Author("Ernest Hemingway", 12, "American"));
        int id = 1;
        for(Author a : authorsToAdd) {
            a.setId(id++);
            RequestBody postBody = getAuthorRequestBody(a);
            client.newCall(new Request.Builder()
                    .url("http://localhost:7000/addauthor")
                    .post(postBody)
                    .build()).execute().close();
        }

        List<Book> booksToAdd = new ArrayList<>();
        booksToAdd.add(new Book("Old Man and the Sea", "0684801221", "Scribner; Later Printing Edition"
                , 1995, 2));
        booksToAdd.add(new Book("The Hobbit", "9780547928227", "George Allen and Unwin", 1937, 1));
        id = 1;
        for(Book b : booksToAdd) {
            b.setId(id++);
            RequestBody postBody = getBookRequestBody(b);
            client.newCall(new Request.Builder()
                    .url("http://localhost:7000/addbook")
                    .post(postBody)
                    .build()).execute().close();
        }

        Request request = new Request.Builder()
                .url("http://localhost:7000/books")
                .build();
        try(Response response = client.newCall(request).execute()) {
            assertEquals(200, response.code());
            assertEquals(new Gson().toJson(booksToAdd), response.body().string());
        }
    }

    @Test
    public void testDeleteAuthor() throws IOException {
        //add author to Authors table (assumes /addauthor works)
        Author a = new Author("Ernest Hemingway", 12, "American");
        RequestBody postBody1 = getAuthorRequestBody(a);
        Request request1 = new Request.Builder()
                .url("http://localhost:7000/addauthor")
                .post(postBody1)
                .build();
        client.newCall(request1).execute().close();

        //add book written by author
        Book b = new Book("Old Man and the Sea", "0684801221", "Scribner; Later Printing Edition"
                , 1995, 1);
        RequestBody postBody2 = getBookRequestBody(b);
        Request request2 = new Request.Builder()
                .url("http://localhost:7000/addbook")
                .post(postBody2)
                .build();
        client.newCall(request2).execute().close();

        //delete author just added
        RequestBody postBody3 = new FormBody.Builder()
                .add("name", "Ernest Hemingway")
                .build();
        Request request3 = new Request.Builder()
                .url("http://localhost:7000/delauthor")
                .post(postBody3)
                .build();
        try(Response response3 = client.newCall(request3).execute()) {
            assertEquals(204, response3.code());
        }

        //associated book should have been deleted
        List<Book> emptyBookList = new ArrayList<>();
        Request request4 = new Request.Builder()
                .url("http://localhost:7000/books")
                .build();
        try(Response response4 = client.newCall(request4).execute()) {
            assertEquals(200, response4.code());
            assertEquals(new Gson().toJson(emptyBookList), response4.body().string());
        }

        //should not have server error (500) if author successfully deleted
        try(Response response5 = client.newCall(request1).execute()) {
            assertEquals(201, response5.code());
        }
    }

    @Test
    public void testDeleteBook() throws IOException {
        //add author and book (assumes /addauthor and /addbook work)
        //add author to Authors table
        Author a = new Author("Ernest Hemingway", 12, "American");
        RequestBody postBody1 = getAuthorRequestBody(a);
        Request request1 = new Request.Builder()
                .url("http://localhost:7000/addauthor")
                .post(postBody1)
                .build();
        client.newCall(request1).execute().close();

        Book b = new Book("Old Man and the Sea", "0684801221", "Scribner; Later Printing Edition"
                , 1995, 1);
        RequestBody postBody2 = getBookRequestBody(b);
        Request request2 = new Request.Builder()
                .url("http://localhost:7000/addbook")
                .post(postBody2)
                .build();
        client.newCall(request2).execute().close();

        //delete author just added
        RequestBody postBody3 = new FormBody.Builder()
                .add("isbn", "0684801221")
                .build();
        Request request3 = new Request.Builder()
                .url("http://localhost:7000/delbook")
                .post(postBody3)
                .build();
        try(Response response3 = client.newCall(request3).execute()) {
            assertEquals(204, response3.code());
        }

        //should not have server error (500) if book successfully deleted
        try(Response response4 = client.newCall(request2).execute()) {
            assertEquals(201, response4.code());
        }
    }
}