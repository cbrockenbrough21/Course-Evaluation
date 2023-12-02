package edu.virginia.sde.reviews;

public class Course {
    private String subject;
    private int courseNumber;
    private String title;

    private String rating;

    private int courseId;

    public Course(String subject, int courseNumber, String title, int courseId) {
        this.subject = subject;
        this.courseNumber = courseNumber;
        this.title = title;
        this.courseId = courseId;
    }

    public Course(String subject, int courseNumber, String title, String rating) {
        this.subject = subject;
        this.courseNumber = courseNumber;
        this.title = title;
        this.rating = rating;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getCourseNumber() {
        return courseNumber;
    }

    public void setCourseNumber(int courseNumber) {
        this.courseNumber = courseNumber;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    @Override
    public String toString() {
        return subject + " " + courseNumber + ": " + title;
    }
}
