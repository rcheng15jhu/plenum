import model.Availability;
import model.AvailableDate;
import model.AvailableDates;
import model.Calendar;
import model.Connections;
import model.Event;
import model.Range;
import model.User;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.*;

import static org.junit.Assert.*;

public class DBPersistenceTest {

    private static String URI;
    private static Connection conn;
    private static Statement st;

    @BeforeClass
    //I made the URI Plenum.db but I'm not sure which it's supposed to be
    public static void beforeClassTests() throws SQLException {
        URI = "jdbc:sqlite:./Plenum.db";
        conn = DriverManager.getConnection(URI);
        st = conn.createStatement();

        String sql = "DROP TABLE IF EXISTS Users";
        st.execute(sql);
        sql = "DROP TABLE IF EXISTS Events";
        st.execute(sql);
        sql = "DROP TABLE IF EXISTS Calendars";
        st.execute(sql);
        sql = "DROP TABLE IF EXISTS Connections";
        st.execute(sql);
        sql = "DROP TABLE IF EXISTS Availabilities";
        st.execute(sql);
    }

    @Before
    public void beforeEachTest() throws SQLException {
        String sq1 = "CREATE TABLE IF NOT EXISTS Users (" +
                " id            serial PRIMARY KEY," +
                " name          VARCHAR(100) NOT NULL UNIQUE," +
                " password      VARCHAR(100) NOT NULL" +
                ");";
        String sq2 = "CREATE TABLE IF NOT EXISTS Events (" +
                " id            serial PRIMARY KEY," +
                " title         VARCHAR(100) NOT NULL," +
                " startTime     INTEGER," +
                " endTime       INTEGER" +
                ");";
        String sq3 = "CREATE TABLE IF NOT EXISTS Calendars (" +
                " id            serial PRIMARY KEY," +
                " name          VARCHAR(100) NOT NULL," +
                " userId        INTEGER NOT NULL," +
                " FOREIGN KEY(userId)" +
                " REFERENCES Users (id)" +
                "   ON UPDATE CASCADE" +
                "   ON DELETE CASCADE" +
                ");";
        String sq4 = "CREATE TABLE IF NOT EXISTS Connections (" +
                " id            serial PRIMARY KEY," +
                " eventId       INTEGER NOT NULL," +
                " calendarId    INTEGER NOT NULL," +
                " userId        INTEGER NOT NULL," +
                " FOREIGN KEY(eventId)" +
                " REFERENCES Events (id)" +
                "   ON UPDATE CASCADE" +
                "   ON DELETE CASCADE," +
                " FOREIGN KEY(calendarId)" +
                " REFERENCES Calendars (id)" +
                "   ON UPDATE CASCADE" +
                "   ON DELETE CASCADE," +
                " FOREIGN KEY(userId)" +
                " REFERENCES Users (id)" +
                "   ON UPDATE CASCADE" +
                "   ON DELETE CASCADE" +
                ");";
        String sq5 = "CREATE TABLE IF NOT EXISTS Availabilities (" +
                " id            serial PRIMARY KEY," +
                " calendarId    INTEGER NOT NULL," +
                " date          INTEGER NOT NULL," +
                " qAvail        INTEGER NOT NULL," +
                " FOREIGN KEY(calendarId)" +
                " REFERENCES Calendars (id)" +
                "   ON UPDATE CASCADE" +
                "   ON DELETE CASCADE" +
                ");";
        st.executeUpdate(sq1);
        st.executeUpdate(sq2);
        st.executeUpdate(sq3);
        st.executeUpdate(sq4);
        st.executeUpdate(sq5);
    }

    @Test
    public void UserTest1() throws SQLException {

    }

    @Test
    public void CalendarTest1() throws SQLException {

    }

    @Test
    public void ConnectionsTest1() throws SQLException {

    }

    @Test
    public void EventTest1() throws SQLException {

    }

    @Test
    public void AvailabilitiesTest1() throws SQLException {

    }

    @AfterClass
    public static void closeStatement() throws SQLException {
        conn.close();
    }
}
