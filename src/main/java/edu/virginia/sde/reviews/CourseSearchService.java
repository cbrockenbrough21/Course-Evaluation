package edu.virginia.sde.reviews;

import java.sql.SQLException;
import java.util.List;

public class CourseSearchService {

    //Function that returns all of the courses given any combination of title, subject, and number
    public List<Course> findAllCourses(String subject, Integer number, String title) {
        DatabaseConnection databaseConnection = null;
        try {
            databaseConnection = new DatabaseConnection();
            List<Course> courses = databaseConnection.searchCourses(subject, number, title);
            return courses;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error while searching for courses", e);
        } finally {
            try {
                if (databaseConnection != null) {
                    databaseConnection.disconnect();
                }
            } catch (SQLException e) {
                throw new RuntimeException();
            }
        }
    }

    public List<Course> getCourses(){
        DatabaseConnection databaseConnection = null;
        try{
            databaseConnection = new DatabaseConnection();
            return databaseConnection.getAllCourses();
        } catch(SQLException e){
            throw new RuntimeException();
        } finally {
            try {
                if (databaseConnection != null) {
                    databaseConnection.disconnect();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
