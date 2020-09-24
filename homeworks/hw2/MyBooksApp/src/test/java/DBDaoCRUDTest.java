import model.Author;
import model.Book;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import persistence.Sql2oAuthorDao;
import persistence.Sql2oBookDao;

import org.junit.Before;
import org.junit.BeforeClass;

import java.sql.SQLException;


public class DBDaoCRUDTest {

    private static Sql2o sql2o;
    private static Sql2oAuthorDao authorDao;
    private static Sql2oBookDao bookDao;
    private static Author a1;
    private static Author a2;
    private static Book b1;
    private static Book b2;

    @BeforeClass
    public static void beforeClassTests() throws SQLException {
        String URI = "jdbc:sqlite:./MyBooksApp.db";
        Sql2o sql2o = new Sql2o(URI, "sa", "");
        try (Connection conn = sql2o.open()) {
          String sq1 = "DROP TABLE IF EXISTS Authors";
          conn.createQuery(sq1).executeUpdate();
          String sq2 = "DROP TABLE IF EXISTS Books";
          conn.createQuery(sq2).executeUpdate();
        }
        authorDao = new Sql2oAuthorDao(sql2o);
        bookDao = new Sql2oBookDao(sql2o, authorDao);

    }

    @Before
    public void beforeEachTest() {

        a1 = new Author("Emily St. John Mandel", 5, "Canadian");
        a2 = new Author("Emily St. John Mandel", 7, "American");

        try (Connection conn = sql2o.open()) {
          String sq1 = "CREATE TABLE IF NOT EXISTS Authors (" +
                  " id            INTEGER PRIMARY KEY," +
                  " name          VARCHAR(100) NOT NULL UNIQUE," +
                  " numOfBooks    INTEGER," +
                  " nationality   VARCHAR(30)" +
                  ");";
          conn.createQuery(sq1).executeUpdate();
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
          conn.createQuery(sq2).executeUpdate();
        }

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
