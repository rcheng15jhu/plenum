import model.Author;
import persistence.Sql2oAuthorDao;
import persistence.Sql2oBookDao;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.*;

import static org.junit.Assert.*;


public class DBDaoCRUDTest {

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

        a1 = new Author("Emily St. John Mandel", 5, "Canadian");
        a2 = new Author("Emily St. John Mandel", 7, "American");

        String sql = "CREATE TABLE IF NOT EXISTS Authors (" +
                " id            INTEGER PRIMARY KEY," +
                " name          VARCHAR(100) NOT NULL UNIQUE," +
                " numOfBooks    INTEGER," +
                " nationality   VARCHAR(30));";
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
                "   ON DELETE CASCADE);";
        st.execute(sq2);

//        (title, isbn, publisher, year, author)" +
    }

/*    @Test
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
    }*/
}
