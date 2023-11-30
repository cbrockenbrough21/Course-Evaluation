package edu.virginia.sde.reviews;


import javax.security.auth.Subject;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import java.util.ArrayList;
import java.util.List;

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
                                Subject TEXT NOT NULL,
                                Number INTEGER NOT NULL,
                                Title TEXT UNIQUE NOT NULL
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
            addStatement.executeUpdate();
            addStatement.close();
            connection.commit();
        } catch(SQLException e){
            connection.rollback();
            throw new SQLIntegrityConstraintViolationException();
        }
    }

    public void addCourse(String subject, int course_number, String title) throws SQLException {
        try {
            var statement = connection.prepareStatement(
                    """
                            INSERT INTO COURSES(subject, number, title)
                            VALUES(?, ?, ?)
                        """
            );
            statement.setString(1, subject);
            statement.setInt(2, course_number);
            statement.setString(3, title);
            int rows = statement.executeUpdate();
            statement.close();
            connection.commit();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            connection.rollback();
            throw new SQLIntegrityConstraintViolationException();
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

    public int getUserIDByUsername(String givenUsername) throws SQLException{
        try{
            var statement = connection.prepareStatement(
                    """
                            SELECT UserID FROM Users WHERE Username = ?
                            """);
            statement.setString(1, givenUsername);
            ResultSet rs = statement.executeQuery();
            int userID = -1;
            while(rs.next()){
                userID = rs.getInt("UserID");
            }
            return userID;
        } catch(SQLException e){
            connection.rollback();
            throw(e);
        }
    }

    public int getCourseID(String subject, int number, String title) throws SQLException{
        try{
            var statement = connection.prepareStatement(
                    """
                            SELECT CourseID FROM Courses WHERE Subject = ? AND Number = ? AND Title = ?
                            """);
            statement.setString(1, subject);
            statement.setInt(2, number);
            statement.setString(3, title);
            ResultSet rs = statement.executeQuery();
            int courseID = -1;
            while(rs.next()){
                courseID = rs.getInt("CourseID");
            }
            return courseID;
        } catch(SQLException e){
            connection.rollback();
            throw(e);
        }
    }

    //Search classes by different combinations of subject, number, and title
    public List<Course> searchCourses(String subject, Integer number, String title) throws SQLException {
        StringBuilder sql = new StringBuilder("SELECT * FROM Courses WHERE 1=1");

        if (subject != null && !subject.isEmpty()) {
            sql.append(" AND Subject = ?");
        }

        if (number != null) {
            sql.append(" AND Number = ?");
        }

        if (title != null && !title.isEmpty()) {
            sql.append(" AND Title LIKE ?");
        }

        try (PreparedStatement stmt = connection.prepareStatement(sql.toString())) {
            int paramIndex = 1;

            if (subject != null && !subject.isEmpty()) {
                stmt.setString(paramIndex++, subject);
            }

            if (number != null) {
                stmt.setInt(paramIndex++, number);
            }

            if (title != null && !title.isEmpty()) {
                stmt.setString(paramIndex++, "%" + title + "%");
            }

            ResultSet rs = stmt.executeQuery();

            List<Course> courses = new ArrayList<>();

            while (rs.next()) {
                String retrievedSubject = rs.getString("Subject");
                int retrievedNumber = rs.getInt("Number");
                String retrievedTitle = rs.getString("Title");

                Course course = new Course(retrievedSubject, retrievedNumber, retrievedTitle);
                courses.add(course);
            }

            return courses;
        } catch (SQLException e) {
            connection.rollback();
            throw (e);
        }
    }

    public List<Course> getAllCourses() throws SQLException{
        List<Course> allCourses = new ArrayList<>();
        try{
            var statement = connection.prepareStatement(
                    """
                            SELECT * FROM Courses
                            """);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                String subject = rs.getString("subject");
                int number = rs.getInt("number");
                String title = rs.getString("title");
                allCourses.add(new Course(subject, number, title));
            }
            return allCourses;
        } catch(SQLException e){
            connection.rollback();
            throw(e);
        }
    }

    public List<Review> getReviews(int courseId) throws SQLException {
        List<Review> courseReviews = new ArrayList<>();
        //int courseId = course.getCourseId();
        try{
            var statement = connection.prepareStatement(
                    """
                            SELECT * FROM Reviews WHERE Reviews.COURSEID= ?
                            """);
            statement.setInt(1, courseId);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                String subject = rs.getString("subject");
                int number = rs.getInt("number");
                String title = rs.getString("title");
                //courseReviews.add(new Review());
            }
            return courseReviews;
        } catch(SQLException e){
            connection.rollback();
            throw(e);
        }
    }

    public void disconnect() throws SQLException {
        connection.close();
    }
}