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

    public void addReview(String choice, String comment){
        int rating = getRating(choice);
        DatabaseConnection databaseConnection = null;
        try {
            databaseConnection = new DatabaseConnection();
            databaseConnection.addReview();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public int getRating(String choice){
        int rating = -1;
        switch(choice){
            case "1": {
                rating = 1;
                break;
            }
            case "2": {
                rating = 2;
                break;
            }
            case "3": {
                rating = 3;
                break;
            }
            case "4": {
                rating = 4;
                break;
            }
            case "5": {
                rating = 5;
                break;
            }
        }
        return rating;
    }

}
