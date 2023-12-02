package edu.virginia.sde.reviews;
import java.security.Timestamp;

public class Review {
    private int reviewID;
    private int userID;
    private int courseID;
    private int rating;
    private String comment;
    private String timestamp;

    //Constructor for my review screen, can add another if class is used for writing reviews
    public Review (int reviewID, int userID, int courseID, int rating, String comment, Timestamp timestamp) {
        this.reviewID = reviewID;
        this.userID = userID;
        this.courseID = courseID;
        this.rating = rating;
        this.comment = comment;
        this.timestamp = String.valueOf(timestamp);
    }

    public Review (int reviewID, int userID, int courseID, int rating, String comment, String timestamp) {
        this.reviewID = reviewID;
        this.userID = userID;
        this.courseID = courseID;
        this.rating = rating;
        this.comment = comment;
        this.timestamp = timestamp;
    }

    public int getReviewID() {
        return reviewID;
    }

    public void setReviewID(int reviewID) {
        this.reviewID = reviewID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
