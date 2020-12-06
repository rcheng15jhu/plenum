package persistence;

import java.util.function.Function;

import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import exception.DaoException;

public class Transaction {

    public static <T> T execute(Sql2o sql2o, Function<Connection, T> query) {
        try (Connection con = sql2o.open()) {
            return query.apply(con);
        } catch (Sql2oException ex) {
            ex.printStackTrace();
            throw new DaoException();
        }
    }

}
