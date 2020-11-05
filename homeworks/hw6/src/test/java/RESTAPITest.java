import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.Author;
import model.Book;
import okhttp3.*;
import org.junit.BeforeClass;
import org.junit.Test;
<<<<<<< HEAD
import org.sql2o.Connection;
import org.sql2o.Sql2o;

=======
>>>>>>> iteration3
import java.io.IOException;
import java.sql.*;
import java.util.List;

import static org.junit.Assert.*;

public class RESTAPITest {

<<<<<<< HEAD
    final static String URI = "jdbc:sqlite:./MyBooksApp.db";
=======
>>>>>>> iteration3
    static OkHttpClient client;
    static Gson gson;
    @BeforeClass
    public static void beforeClassTests() throws SQLException {
<<<<<<< HEAD

        Sql2o sql2o = new Sql2o(URI,"","");
        String sqlDropAuthors = "DROP TABLE IF EXISTS Authors";
        String sqlDropBooks = "DROP TABLE IF EXISTS Books";
        try (Connection con = sql2o.open()) {
            con.createQuery(sqlDropBooks).executeUpdate();
            con.createQuery(sqlDropAuthors).executeUpdate();
        }
        Server.main(null);

=======
>>>>>>> iteration3
        client = new OkHttpClient();
        gson = new Gson();
    }

<<<<<<< HEAD
//    @Test
//    public void testListAuthors() throws IOException {
//        Request request = new Request.Builder()
//                .url("http://localhost:7000/authors")
//                .build();
//        Response response = client.newCall(request).execute();
//        String resBody = response.body().string();
//        System.out.println(resBody);
//        Author[] authors = gson.fromJson(resBody, Author[].class);
//        // loop through authors and do extra assertions
//        assertEquals(200, response.code());
//    }
=======
    @Test
    public void testListAuthors() throws IOException {
        Request request = new Request.Builder()
                .url("http://localhost:7000/authors")
                .build();
        Response response = client.newCall(request).execute();
        String resBody = response.body().string();
        Author[] authors = gson.fromJson(resBody, Author[].class);
        // loop through authors and do extra assertions
        assertEquals(200, response.code());
    }
>>>>>>> iteration3

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


}