import model.Author;
import model.Book;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import persistence.Sql2oAuthorDao;
import persistence.Sql2oBookDao;

import org.junit.Before;
import org.junit.BeforeClass;
import static org.junit.Assert.*;

import java.util.List;


public class DBDaoCRUDTest {

    private static Sql2o sql2o;
    private static Sql2oAuthorDao authorDao;
    private static Sql2oBookDao bookDao;
    private static Author a1;
    private static Author a2;
    private static Author a3;
    private static Author a4;
    private static Book b1;
    private static Book b2;

    @BeforeClass
    public static void beforeClassTests() {
        String URI = "jdbc:sqlite:./MyBooksApp.db";
        sql2o = new Sql2o(URI, "", "");
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
        a3 = new Author("J. R. R. Tolkien", 23, "American");
        a4 = new Author("George. R. R. Martin", 5, "American");

        b1 = new Book("The Hobbit", "9780547928227", "George Allen and Unwin", 1937, a3);
        b2 = new Book("Song of Ice and Fire", "9780547928227", "Bantam Books", 1996, a4);

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
    }

    @Test
    public void addAuthorTest() {
      authorDao.add(a1);
      List<Integer> result;
      try(Connection conn = sql2o.open()) {
        String sq1 = "Select * FROM Authors WHERE name = " + a1.getName();
        result = conn.createQuery(sq1).executeAndFetch(Integer.class);
      }
      assertEquals(1, result.size());
      assertEquals(a1.getNumOfBooks(), (int) result.get(0));
    }

/*    @Test
    public void testUpdateAuthor() throws SQLException {
        conn.add(a1);
        assertTrue(update(a2));
        String sql = "Select * FROM Authors WHERE name = Emily St. John Mandel";
        List<Author> list = conn.createQuery(sql).executeAndFetch(Author.class);
        assertTrue(list.get(0).getNumOfBooks == 7);
        assertTrue(list.get(0).getNationality.equals("American");
    }

    @Test
    public void testUpdateBook() throws SQLException {
        conn.add(b1);
        assertTrue(update(b2));
        String sql = "Select * FROM Books WHERE isbn = 9780547928227";
        List<Book> list = conn.createQuery(sql).executeAndFetch(Book.class);
        assertTrue(list.get(0).getTitle.equals("Song of Ice and Fire");
        assertTrue(list.get(0).getYear == 1996);
        assertTrue(list.get(0).getAuthor.equals(a4);
        assertTrue(list.get(0).getPublisher.equals("Bantam Books");
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
