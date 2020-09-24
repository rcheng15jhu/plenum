import exception.DaoException;
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
          String sq3 = "PRAGMA foreign_keys = ON";
          conn.createQuery(sq3).executeUpdate();
        }
        authorDao = new Sql2oAuthorDao(sql2o);
        bookDao = new Sql2oBookDao(sql2o);

    }

    @Before
    public void beforeEachTest() {

        a1 = new Author("Emily St. John Mandel", 5, "Canadian");
        a2 = new Author("Emily St. John Mandel", 7, "American");
        a3 = new Author("J. R. R. Tolkien", 23, "American");
        a4 = new Author("George. R. R. Martin", 5, "American");

        b1 = new Book("The Hobbit", "9780547928227", "George Allen and Unwin", 1937, 1);
        b2 = new Book("Song of Ice and Fire", "9780547928228", "Bantam Books", 1996, 1);

        try (Connection conn = sql2o.open()) {
            String sq1 = "DROP TABLE IF EXISTS Authors";
            conn.createQuery(sq1).executeUpdate();
            String sq2 = "DROP TABLE IF EXISTS Books";
            conn.createQuery(sq2).executeUpdate();
            sq1 = "CREATE TABLE IF NOT EXISTS Authors (" +
                  " id            INTEGER PRIMARY KEY," +
                  " name          VARCHAR(100) NOT NULL UNIQUE," +
                  " numOfBooks    INTEGER," +
                  " nationality   VARCHAR(30)" +
                  ");";
            conn.createQuery(sq1).executeUpdate();
            sq2 = "CREATE TABLE IF NOT EXISTS Books (" +
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
        b1.setAuthorId(a3.getId());
        bookDao.add(b1);

        List<Book> result;
        List<Author> authors;
        try (Connection conn = sql2o.open()) {
            String sq1 = "Select * FROM Books WHERE isbn = :isbn";
            result = conn.createQuery(sq1)
                    .bind(b1)
                    .executeAndFetch(Book.class);

            String sq2 = "Select * FROM Authors WHERE id = :authorId";
            authors = conn.createQuery(sq2)
                    .bind(b1)
                    .executeAndFetch(Author.class);
        }

        //Check book is added
        assertEquals(1, result.size());
        assertEquals(b1, result.get(0));

        //Author has to exist for the book to be added
        assertEquals(1, authors.size());
    }

    @Test (expected = DaoException.class)
    public void testExceptionOnNoAuthorBook() {
      bookDao.add(b1);
    }

    @Test (expected = org.sql2o.Sql2oException.class)
    public void testExceptionOnDuplicateAuthors() {
        authorDao.add(a1);
        authorDao.add(a1);
    }

    @Test (expected = org.sql2o.Sql2oException.class)
    public void testExceptionOnDuplicateBooks() {
        authorDao.add(a3);
        bookDao.add(b1);
        bookDao.add(b1);
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
        b1.setAuthorId(a3.getId());
        bookDao.add(b1);
        b1.setAuthorId(a4.getId());
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

    @Test
    public void testDeleteAuthor()  {
        authorDao.add(a3);
        authorDao.add(a4);
        b1.setAuthorId(a3.getId());
        bookDao.add(b1);
        b2.setAuthorId(a4.getId());
        bookDao.add(b2);

        try(Connection conn = sql2o.open()) {
            System.out.println(conn.createQuery("Select * FROM Authors").executeAndFetch(Author.class));
            System.out.println(conn.createQuery("Select * FROM Books").executeAndFetch(Book.class));
        }

        assertTrue(authorDao.delete(a3));

        List<Author> list;
        List<Book> books;

        try(Connection conn = sql2o.open()) {
            System.out.println(conn.createQuery("Select * FROM Authors").executeAndFetch(Author.class));
            System.out.println(conn.createQuery("Select * FROM Books").executeAndFetch(Book.class));
            String sq1 = "Select * FROM Authors WHERE name = :name";
            list = conn.createQuery(sq1)
                    .bind(a3)
                    .executeAndFetch(Author.class);

            String sq2 = "Select * FROM Books WHERE isbn = :isbn";
            books = conn.createQuery(sq2)
                    .bind(b1)
                    .executeAndFetch(Book.class);
        }

        //Check if author is successfully deleted
        assertEquals(0, list.size());

        System.out.println(books);
        //Deleting an author should delete all books written by the author
        assertEquals(0, books.size());
    }

    @Test
    public void testDeleteBook() {
        authorDao.add(a3);
        bookDao.add(b1);
        assertTrue(bookDao.delete(b1));
        List<Book> list;
        try(Connection conn = sql2o.open()) {
            String sql = "Select * FROM Books WHERE isbn = 9780547928227";
            list = conn.createQuery(sql).executeAndFetch(Book.class);
        }
        assertEquals(0, list.size());
    }
}


