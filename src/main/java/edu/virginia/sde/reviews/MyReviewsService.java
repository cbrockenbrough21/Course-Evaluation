package edu.virginia.sde.reviews;

import java.sql.SQLException;
import java.util.List;

public class MyReviewsService {

    public List<Review> getMyReviews(int userID){
        DatabaseConnection databaseConnection = null;
        try {
            databaseConnection = new DatabaseConnection();
            return databaseConnection.getReviewsByUserID(userID);
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

    public Course getCoursebyCourseId(int courseID){
        DatabaseConnection databaseConnection = null;
        try {
            databaseConnection = new DatabaseConnection();
            return databaseConnection.getCourseByCourseID(courseID);
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
}
