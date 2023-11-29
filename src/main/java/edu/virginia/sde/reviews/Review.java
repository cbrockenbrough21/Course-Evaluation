package edu.virginia.sde.reviews;


import java.sql.Timestamp;

public class Review {
    private String mnemonic;
    private int courseNumber;
    private String title;

    private double rating;
    private String timestamp;

    //Constructor for my review screen, can add another if class is used for writing reviews
    public Review (String mnemonic, int courseNumber, double rating, String timestamp) {
        this.mnemonic = mnemonic;
        this.courseNumber = courseNumber;
        this.rating = rating;
        this.timestamp = timestamp;
    }

    public String getMnemonic() {return mnemonic; }

    public void setMnemonic(String mnemonic) {this.mnemonic = mnemonic; }

    public int getCourseNumber() {return courseNumber; }

    public void setCourseNumber(int courseNumber) {this.courseNumber = courseNumber; }

    public double getRating() {return rating; }

    public void setRating(double rating) {this.rating = rating; }

    //public Timestamp getTimeStamp() {return timestamp;}

}
