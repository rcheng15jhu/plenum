package model;

public class SqlSchema {

  public static String UsersSchema = "CREATE TABLE IF NOT EXISTS Users (" +
          " id            serial PRIMARY KEY," +
          " name          VARCHAR(100) NOT NULL UNIQUE," +
          " password      VARCHAR(48) NOT NULL," +
          " salt          VARCHAR(36) NOT NULL," +
          " email          VARCHAR(100) NOT NULL," +
          " affil          VARCHAR(100) NOT NULL," +
          " title          VARCHAR(200) NOT NULL," +
          " description   VARCHAR(1000) NOT NULL," +
          " pic          VARCHAR(100) NOT NULL" +
          ");";

  public static String EventsSchema = "CREATE TABLE IF NOT EXISTS Events (" +
          " id            serial PRIMARY KEY," +
          " title         VARCHAR(100) NOT NULL," +
          " startTime     INTEGER NOT NULL," +
          " endTime       INTEGER NOT NULL" +
          ");";

  public static String CalendarsSchema = "CREATE TABLE IF NOT EXISTS Calendars (" +
          " id            serial PRIMARY KEY," +
          " title         VARCHAR(100) NOT NULL," +
          " userId        INTEGER NOT NULL," +
          " FOREIGN KEY(userId)" +
          " REFERENCES Users (id)" +
          "   ON UPDATE CASCADE" +
          "   ON DELETE CASCADE" +
          ");";

  public static String ConnectionsSchema = "CREATE TABLE IF NOT EXISTS Connections (" +
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

  public static String AvailabilitiesSchema = "CREATE TABLE IF NOT EXISTS Availabilities (" +
          " id            serial PRIMARY KEY," +
          " calendarId    INTEGER NOT NULL," +
          " date          INTEGER NOT NULL," +
          " qHour        INTEGER NOT NULL," +
          " FOREIGN KEY(calendarId)" +
          " REFERENCES Calendars (id)" +
          "   ON UPDATE CASCADE" +
          "   ON DELETE CASCADE" +
          ");";

}
