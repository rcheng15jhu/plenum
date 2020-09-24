import model.Author;
import persistence.Sql2oAuthorDao;
import persistence.Sql2oBookDao;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.*;

import static org.junit.Assert.*;


public class DBPersistenceTest {

    private static String URI;
    private static Connection conn;
    private static Statement st;
    private static Sql2oAuthorDao authorDao;
    private static Sql2oBookDao bookDao;
    private static Author a1;
    private static Author a2;
    private static Book b1;
    private static Book b2;

    @BeforeClass
    public static void beforeClassTests() throws SQLException {
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

        bookDao = new Sql2oBookDao();
        authorDao = new Sql2oAuthorDao();

        String sql = "CREATE TABLE IF NOT EXISTS Authors (id INTEGER PRIMARY KEY, name VARCHAR(100) NOT NULL UNIQUE," +
                " numOfBooks INTEGER, nationality VARCHAR(30));";
        st.execute(sql);

        a1 = Author('Emily St. John Mandel', 5, 'Canadian');
        a2 = Author('Emily St. John Mandel', 7, 'American');
    }

    @Test
    public void testUpdateAuthor() throws SQLException {

        auth = Author('Emily St. John Mandel', 5, 'Canadian');
        add(auth);
        auth1 = Author('Emily St. John Mandel', 7, 'American');
        assertTrue(update(auth1));
    }

    public void testDeleteAuthor() throws SQLException {
        st.add(a1);

        try {
            authorDao.delete(a1);
            ResultSet rs = st.executeQuery(<Some SQL Statement>)
            assertFalse(rs.next())
        } catch(DaoException e) {
            System.out.println("Exception thrown for deleting author");
        }
    }
}
