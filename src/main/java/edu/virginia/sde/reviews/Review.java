package edu.virginia.sde.reviews;


public class Review {
    private String subject;
    private int courseNumber;
    private String title;

    private double rating;
    private String timestamp;

    //Constructor for my review screen, can add another if class is used for writing reviews
    public Review (String subject, int courseNumber, double rating, String timestamp) {
        this.subject = subject;
        this.courseNumber = courseNumber;
        this.rating = rating;
        this.timestamp = timestamp;
    }

    public String getSubject() {return subject; }

    public void setSubject(String subject) {this.subject = subject; }

    public int getCourseNumber() {return courseNumber; }

    public void setCourseNumber(int courseNumber) {this.courseNumber = courseNumber; }

    public double getRating() {return rating; }

    public void setRating(double rating) {this.rating = rating; }

    //public Timestamp getTimeStamp() {return timestamp;}

}
