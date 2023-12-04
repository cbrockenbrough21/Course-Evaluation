package edu.virginia.sde.reviews;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class Review {
    private int reviewID;
    private int userID;
    private int courseID;
    private int rating;
    private String comment;
    private Timestamp timestamp;
    private Course course;
    private String subject;
    private int courseNumber;

    //Constructor for my review screen, can add another if class is used for writing reviews
    public Review (int reviewID, int userID, int courseID, int rating, String comment, Timestamp timestamp, Course course) {
        this.reviewID = reviewID;
        this.userID = userID;
        this.courseID = courseID;
        this.rating = rating;
        this.comment = comment;
        this.timestamp = convertToLocalTime(timestamp);
        this.course = course;
        subject = course.getSubject();
        courseNumber = course.getCourseNumber();
    }

    private Timestamp convertToLocalTime(Timestamp timestamp) {
        if (timestamp != null) {
            TimeZone timeZone = TimeZone.getTimeZone("America/New_York");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            sdf.setTimeZone(timeZone);
            String formattedTime = sdf.format(timestamp);
            try {
                return new Timestamp(sdf.parse(formattedTime).getTime());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
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

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public void setSubject(String subject) { this.course.setSubject(subject); }

    public String getSubject() { return course.getSubject(); }

    public void setCourseNumber(int courseNumber) { this.course.setCourseNumber(courseNumber); }

    public int getCourseNumber() { return course.getCourseNumber(); }

    public Course getCourse() { return course; }

    public void setCourse(Course course) { this.course = course; }
}
