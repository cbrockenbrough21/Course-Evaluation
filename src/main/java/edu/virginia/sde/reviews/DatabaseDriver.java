package edu.virginia.sde.reviews;

import java.sql.*;
import java.util.*;


public class DatabaseDriver {

    private static Connection connection;
    public static void main(String[] args){
        try{
            connect();
            Statement statement = connection.createStatement();
            createUsersTable(statement);
            createCoursesTable(statement);
            createReviewsTable(statement);
            statement.close();
            commit();

        } catch(SQLException e){
            e.printStackTrace();
        } finally {
            disconnect();
        }
    }

    public static void connect() throws SQLException{
        if(connection != null && !connection.isClosed()){
            throw new IllegalStateException("The connection is already open");
        }
        connection = DriverManager.getConnection("jdbc:sqlite:course_reviews.sqlite");
        connection.createStatement().execute("PRAGMA foreign_keys = ON");
        connection.setAutoCommit(false);
    }

    public static void disconnect(){
        try{
            if(connection != null && !connection.isClosed()){
                connection.close();
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    public static void commit() throws SQLException {
        connection.commit();
    }

    public static void rollback() throws SQLException {
        connection.rollback();
    }

    private static void createUsersTable(Statement statement) throws SQLException{
        String sql = "CREATE TABLE IF NOT EXISTS Users(" +
                "UserID INTEGER PRIMARY KEY," +
                "Username TEXT UNIQUE," +
                "Password TEXT)";
        statement.executeUpdate(sql);
    }

    //Asked ChatGDP how to add constraints to database
    private static void createCoursesTable(Statement statement) throws SQLException{
        String sql2 = "CREATE TABLE IF NOT EXISTS Courses(" +
                "CourseID INTEGER PRIMARY KEY," +
                "Subject TEXT CHECK(length(Subject) >= 2 AND length(Subject) <= 4)," +
                "Number INTEGER CHECK(length(Number) == 4)," +
                "Title TEXT CHECK(length(Title) >= 1 AND length(Title) <= 50))";
        statement.executeUpdate(sql2);
    }

    private static void createReviewsTable(Statement statement) throws SQLException{
        String sql3 = "CREATE TABLE IF NOT EXISTS Reviews(" +
                "ReviewID INTEGER PRIMARY KEY," +
                "UserID INTEGER NOT NULL," +
                "CourseID INTEGER NOT NULL," +
                "Rating INTEGER CHECK(Rating >= 1 AND Rating <= 5)," +
                "Comment TEXT," +
                "Timestamp TIMESTAMP," +
                "FOREIGN KEY (UserID) REFERENCES Users(UserID) ON DELETE CASCADE," +
                "FOREIGN KEY (CourseID) REFERENCES Courses(CourseID) ON DELETE CASCADE)";
        statement.executeUpdate(sql3);
    }

    private User getUser(ResultSet resultSet) throws SQLException{
        var id = resultSet.getInt("UserID");
        var username = resultSet.getString("Username");
        var password = resultSet.getString("Password");
        return new User(username, password, id);
    }

    public List<User> getAllUsers() throws SQLException{
        if(connection.isClosed()){
            throw new IllegalStateException("Connection to the database is not open");
        }
        PreparedStatement statement = connection.prepareStatement("SELECT * from Users");
        var users = new ArrayList<User>();
        ResultSet resultSet = statement.executeQuery();
        while(resultSet.next()){
            var user = getUser(resultSet);
            users.add(user);
        }
        statement.close();
        return users;
    }

    //returns a String of the password for that specific username if it is in the database
        //if it isn't in the database, it returns null
        //throws an exception if there is more than one of that username in the database
    public String getPasswordByUsername(String givenUsername) throws SQLException{
        if(connection.isClosed()){
            throw new IllegalStateException("Connection to the database is not open");
        }
        PreparedStatement statement = connection.prepareStatement(
                String.format("SELECT * from User WHERE Username = ?"));
        statement.setString(1, givenUsername);
        ResultSet resultSet = statement.executeQuery();

        int count = 0;
        String password = null;
        while(resultSet.next()){
            count ++;
            password = resultSet.getString("Password");
        }
        if(count > 1){
            throw new SQLException("More than one instance of this Username in the database")
        }
        return password;
    }
}

