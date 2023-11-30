package edu.virginia.sde.reviews;

import java.sql.SQLException;
import java.util.*;

public class CourseReviewsService {

    public void initialize(){
        DatabaseConnection databaseConnection = null;
        try {
            databaseConnection = new DatabaseConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Review> getReviewList(Course course){
        int courseId = course.getCourseId();
        System.out.println("this is the courseID: " + courseId);
        List<Review> reviewList = null;

        DatabaseConnection databaseConnection = null;
        try {
            databaseConnection = new DatabaseConnection();
            reviewList = databaseConnection.getReviewsByCourseID(courseId);
            return reviewList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
