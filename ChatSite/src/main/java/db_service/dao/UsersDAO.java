package db_service.dao;

import db_service.data_set.UsersDataSet;
import db_service.executor.Executor;
import db_service.executor.ResultHandler;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UsersDAO {
    private final Connection connection;

    public UsersDAO(Connection connection) {
        this.connection = connection;
    }

    public void dropTheTable() throws SQLException {
        Executor executor = new Executor(connection);
        executor.execUpdate("drop table users;");
    }

    public int AddUser(String login, String password, String email) throws SQLException {
        Executor executor = new Executor(connection);
        int count = executor.execUpdate("insert into users(login, password, email) values ('" +
                login + "', '" + password + "', '" + email + "');");
        return count;
    }

    public UsersDataSet getUser(long id) throws SQLException {
        String query = "select * from users where id=" + id+";";
        Executor executor = new Executor(connection);
        return (UsersDataSet) executor.execQuery(query, result -> {
            result.next();
            return new UsersDataSet(id, result.getString("login"), result.getString("password"), result.getString("email"));
        });
    }

    public UsersDataSet getUser(String login) throws SQLException {
        String query = "select * from users where login='" + login +"';";
        Executor executor = new Executor(connection);
        return (UsersDataSet) executor.execQuery(query, new ResultHandler() { //I know, it's not lambda, just for comparing
            public UsersDataSet handle(ResultSet result) throws SQLException {
                result.next();
                return new UsersDataSet(result.getLong(1), login, result.getString(3), result.getString(4));
            }
        });
    }
}
