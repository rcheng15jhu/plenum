import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static model.SqlSchema.*;

import java.sql.*;

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

        st.executeUpdate(UsersSchema);
        st.executeUpdate(EventsSchema);
        st.executeUpdate(CalendarsSchema);
        st.executeUpdate(ConnectionsSchema);
        st.executeUpdate(AvailabilitiesSchema);
    }

    @Before
    public void beforeEachTest() throws SQLException {String sql = "DROP TABLE IF EXISTS Users";
        st.execute(sql);
        sql = "DROP TABLE IF EXISTS Events";
        st.execute(sql);
        sql = "DROP TABLE IF EXISTS Calendars";
        st.execute(sql);
        sql = "DROP TABLE IF EXISTS Connections";
        st.execute(sql);
        sql = "DROP TABLE IF EXISTS Availabilities";
        st.execute(sql);
        st.executeUpdate(UsersSchema);
        st.executeUpdate(EventsSchema);
        st.executeUpdate(CalendarsSchema);
        st.executeUpdate(ConnectionsSchema);
        st.executeUpdate(AvailabilitiesSchema);
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
