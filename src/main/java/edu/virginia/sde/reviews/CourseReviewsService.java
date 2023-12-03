package edu.virginia.sde.reviews;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;

public class CourseReviewsService {


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

    public Review getUserReview(int userId, int courseId){
        DatabaseConnection databaseConnection = null;
        try {
            databaseConnection = new DatabaseConnection();
            return databaseConnection.getUsersReviewsByCourseID(userId, courseId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
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

    public void addReview(int userId, int courseId, String choice, String comment){
        int rating = getRating(choice);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        DatabaseConnection databaseConnection = null;
        try {
            databaseConnection = new DatabaseConnection();
            databaseConnection.addReview(userId, courseId, rating, comment, timestamp);
        } catch (SQLException e) {
            throw new RuntimeException(e);
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

    public void updateReview(int reviewId, String choice, String comment){
        int rating = getRating(choice);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        DatabaseConnection databaseConnection = null;
        try {
            databaseConnection = new DatabaseConnection();
            databaseConnection.updateReview(reviewId, rating, comment, timestamp);
        } catch (SQLException e) {
            throw new RuntimeException(e);
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

    public void deleteReview(int reviewId){
        DatabaseConnection databaseConnection = null;
        try {
            databaseConnection = new DatabaseConnection();
            databaseConnection.deleteReview(reviewId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
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

    public void displayAverageRating(CourseReviewsService service, List<Course> courseList) {
        for (Course course : courseList) {
            double cum_rating = 0.0;
            int rev_count = 0;
            List<Review> courseReviews = service.getReviewList(course);
            for (Review review : courseReviews) {
                rev_count++;
                cum_rating = cum_rating + review.getRating();
            }
            String avg = String.format("%.2f", (cum_rating / rev_count));
            if (courseReviews.isEmpty()) {
                avg = "";
            }
            course.setRating(avg);
        }
    }

}
