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
      List<Author> result;
      try(Connection conn = sql2o.open()) {
        String sq1 = "Select * FROM Authors WHERE name = :name";
        result = conn.createQuery(sq1)
                .bind(a1)
                //.addParameter("name", a1.getName())
                .executeAndFetch(Author.class);
      }
      assertEquals(1, result.size());
      assertEquals(a1, result.get(0));
    }

    @Test
    public void addBookTest() {
      authorDao.add(a3);
      bookDao.add(b1);
      List<Book> result;
      try (Connection conn = sql2o.open()) {
        String sq1 = "Select * FROM Books WHERE isbn = :isbn";
        result = conn.createQuery(sq1)
                .bind(b1)
                .executeAndFetch(Book.class);
      }
      assertEquals(1, result.size());
      assertEquals(b1, result.get(0));
    }

    @Test
    public void testUpdateAuthor() {
        authorDao.add(a1);
        a1.setNumOfBooks(7);
        a1.setNationality("Hi");
        assertTrue(authorDao.update(a1));
        List<Author> list;
        try(Connection conn = sql2o.open()) {
            String sql = "Select * FROM Authors WHERE name = :name";
            list = conn.createQuery(sql)
                    .bind(a1)
                    .executeAndFetch(Author.class);
        }
        assertEquals(a1, list.get(0));
    }

    @Test
    public void testUpdateBook() {
        authorDao.add(a3);
        authorDao.add(a4);
        bookDao.add(b1);
        b1.setAuthor(a4);
        b1.setYear(1996);
        b1.setTitle("Song of Ice and Fire");
        b1.setPublisher("Bantam Books");
        assertTrue(bookDao.update(b1));
        List<Book> list;
        try(Connection conn = sql2o.open()) {
            String sql = "Select * FROM Books WHERE isbn = 9780547928227";
            list = conn.createQuery(sql).executeAndFetch(Book.class);
        }
        assertEquals(b1, list.get(0));
    }

}
