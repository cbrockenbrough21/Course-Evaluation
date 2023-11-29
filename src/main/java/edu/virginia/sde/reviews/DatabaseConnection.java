package edu.virginia.sde.reviews;


import java.sql.*;

public class DatabaseConnection {
    public static final String DATABASE_CONNECTION = "jdbc:sqlite:course_reviews.sqlite";

    private final Connection connection;

    public DatabaseConnection() throws SQLException {
        connection = DriverManager.getConnection(DATABASE_CONNECTION);
        connection.setAutoCommit(false);
        createUsersTables();
        createCoursesTables();
        createReviewsTables();

    }

    private void createUsersTables() throws SQLException {
        try {
            var statement = connection.prepareStatement(
                    """
                                CREATE TABLE IF NOT EXISTS USERS (
                                UserID INTEGER PRIMARY KEY,
                                Username TEXT UNIQUE NOT NULL,
                                Password TEXT NOT NULL
                            );
                            """);
            statement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        }
    }
    private void createCoursesTables() throws SQLException {
        try {
            var statement = connection.prepareStatement(
                    """
                                CREATE TABLE IF NOT EXISTS COURSES (
                                CourseID INTEGER PRIMARY KEY,
                                Subject TEXT CHECK(length(Subject) >= 2 AND length(Subject) <= 4),
                                Number INTEGER CHECK(length(number) == 4),
                                Title TEXT CHECK(length(Title) >= 1 AND length(Title) <= 50)
                            );
                            """);
            statement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        }
    }

    private void createReviewsTables() throws SQLException {
        try {
            var statement = connection.prepareStatement(
                    """
                                CREATE TABLE IF NOT EXISTS Reviews (
                                ReviewID INTEGER PRIMARY KEY,
                                UserID INTEGER NOT NULL,
                                CourseID INTEGER NOT NULL,
                                Rating INTEGER CHECK(Rating >=1 AND Rating<=5),
                                Comment TEXT,
                                Timestamp TIMESTAMP,
                                FOREIGN KEY (UserID) REFERENCES Users(UserID) ON DELETE CASCADE,
                                FOREIGN KEY (CourseID) REFERENCES Courses(CourseID) ON DELETE CASCADE
                            );
                            """);
            statement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        }
    }

    public void disconnect() throws SQLException {
        connection.close();
    }
}