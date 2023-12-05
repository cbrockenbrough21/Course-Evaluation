package edu.virginia.sde.reviews;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.regex.Pattern;


public class AddCourseService {

    public boolean isValidSubject(String subject1) {
        String subject = subject1.trim();
        var p = Pattern.compile("[A-Za-z]{2,4}");
        return p.matcher(subject).matches();
    }

    public boolean isValidCourseNumber(String course_number) {
        return String.valueOf(course_number).matches("\\d{4}");
    }

    public boolean isValidTitle(String title1) {
        String title = title1.trim();
        return !title.isEmpty() && title.length() <= 50;
    }

    public int getCourseID(String subject, String number, String title){
        int numberInt = Integer.parseInt(number);
        DatabaseConnection databaseConnection = null;
        try {
            databaseConnection = new DatabaseConnection();
            return databaseConnection.getCourseID(subject, numberInt, title);
        } catch (SQLException e) {
            throw new RuntimeException();
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

    public boolean addIfNotExists(String subject, int course_number, String title) {
        subject = subject.trim();
        title = title.trim();
        if (subject.isEmpty() || title.isEmpty()) {
            return false;
        }
        DatabaseConnection databaseConnection = null;
        try {
            databaseConnection = new DatabaseConnection();
            List<Course> allCourses = databaseConnection.getAllCourses();
            if (isCourseInDatabase(allCourses, subject, course_number, title)) {
                return false;
            }
            else {
                databaseConnection.addCourse(subject, course_number, title);
                return true;
            }

        } catch (SQLException e) {
            throw new RuntimeException();
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

    public boolean isCourseInDatabase(List<Course> allCourses, String subject, int course_number, String title) {
        for (Course course : allCourses) {
            if ((course.getSubject().equalsIgnoreCase(subject)) && (course.getCourseNumber() == course_number) && (course.getTitle().equalsIgnoreCase(title))) {
                return true;
            }
        }
        return false;
    }
}
