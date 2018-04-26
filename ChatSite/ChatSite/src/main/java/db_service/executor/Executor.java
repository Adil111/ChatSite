package db_service.executor;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Executor<T> {
    private Connection connection;
    public Executor(Connection connection) {
        this.connection = connection;
    }

    public int execUpdate(String query) throws SQLException {
        Statement statement = connection.createStatement();
        int count = statement.executeUpdate(query);
        statement.close();
        return count;
    }

    public T execQuery(String query, ResultHandler<T> resultHandler) throws SQLException {

            Statement statement = connection.createStatement();
        System.out.println(1);
            T value = resultHandler.handle(statement.executeQuery(query));
        System.out.println(2);
            statement.close();
        System.out.println(3);
            return value;

    }
}
