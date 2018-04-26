package db_service;

import db_service.dao.UsersDAO;
import db_service.data_set.UsersDataSet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBService {
    private Connection connection;
    private final static String URL = "jdbc:mysql://localhost:3306/dbtest";
    private final static String PARAM = "?useUnicode=true&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC"; //ssl=true caused WARN
    private final static String USERNAME = "root";
    private final static String PASSWORD = "root";

    public DBService() {
        this.connection = getMySQLConnection();
    }

    public void printInfo() {
        try {
            System.out.println("DB name: " + connection.getMetaData().getDatabaseProductName());
            System.out.println("DB version: " + connection.getMetaData().getDatabaseProductVersion());
            System.out.println("Driver: " + connection.getMetaData().getDriverName());
            System.out.println("Autocommit: " + connection.getAutoCommit());
        } catch (SQLException e){}
    }

    public void cleanUp() throws DBException {
        try {
            UsersDAO dao = new UsersDAO(connection);
            dao.dropTheTable();
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    public UsersDataSet getUser(long id) throws DBException {
        try {
            UsersDAO dao = new UsersDAO(connection);
            UsersDataSet dataSet = dao.getUser(id);
            return dataSet;
        }catch (SQLException e){
            throw new DBException(e);
        }
    }

    public UsersDataSet getUser(String login) throws DBException {
        try {
            UsersDAO dao = new UsersDAO(connection);
            UsersDataSet dataSet = dao.getUser(login);
            return dataSet;
        }catch (SQLException e){
            throw new DBException(e);
        }
    }

    public long AddUser(String login, String password, String email) throws DBException {
        long id;
        try{
            UsersDAO dao = new UsersDAO(connection);
            id = dao.AddUser(login,password,email);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException(e);
        }
        return id;
    }

   /* public void deleteUser(String login, String password, String email){
        UsersDAO dao = new UsersDAO(connection);
        dao.deleteUser(login,password,email);
    }

    public void updateUser(String login, String password, String email){
        UsersDAO dao = new UsersDAO(connection);
        dao.updateUser(login,password,email);
    } */

    //TODO: change DriverManager on DataSource http://zetcode.com/tutorials/jeetutorials/datasource/
    public Connection getMySQLConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL+PARAM, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}


