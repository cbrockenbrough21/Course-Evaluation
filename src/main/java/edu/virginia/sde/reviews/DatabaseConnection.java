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

    public void addUser(String username, String password) throws SQLException{
        try{
            var addStatement = connection.prepareStatement(
                    """
                            INSERT INTO Users(Username, Password)
                            VALUES(?, ?)""");
            addStatement.setString(1, username);
            addStatement.setString(2, password);

            int rowsAdded = addStatement.executeUpdate();
            addStatement.close();
            connection.commit();
        } catch(SQLException e){
            connection.rollback();
            throw(e);
        }
    }

    public void addCourse(String subject, int course_number, String title) throws SQLException {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    """
                            INSERT INTO COURSES(subject, course_number, title)
                            VALUES(?, ?, ?)
                        """
            );
            statement.setString(1, subject);
            statement.setInt(2, course_number);
            statement.setString(3, title);
            statement.executeUpdate();
            statement.close();
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        }
    }

    public String getPasswordByUsername(String givenUsername) throws SQLException{
        try{
            var statement = connection.prepareStatement(
                    """
                            SELECT * FROM Users WHERE Username = ?
                            """);
            statement.setString(1, givenUsername);
            ResultSet rs = statement.executeQuery();
            String password = null;
            while(rs.next()){
                password = rs.getString("Password");
            }
            return password;
        } catch(SQLException e){
            connection.rollback();
            throw(e);
        }
    }

    public void disconnect() throws SQLException {
        connection.close();
    }
}