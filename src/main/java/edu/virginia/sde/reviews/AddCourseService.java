package edu.virginia.sde.reviews;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.regex.Pattern;

public class AddCourseService {

    public boolean isValidSubject(String subject) {
        var p = Pattern.compile("[A-Za-z]{2,4}");
        return p.matcher(subject).matches();
    }

    public boolean isValidCourseNumber(String course_number) {
        return String.valueOf(course_number).matches("\\d{4}");
    }

    public boolean isValidTitle(String title) {
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
        DatabaseConnection databaseConnection = null;
        try {
            databaseConnection = new DatabaseConnection();
            List<Course> allCourses = databaseConnection.getAllCourses();
            if (!isCourseInDatabase(allCourses, subject, course_number, title)) {
                databaseConnection.addCourse(subject, course_number, title);
                return true;
            }
                return false;

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
            if (!(course.getSubject().equals(subject) && !(course.getTitle().equals(title)))) {
                return true;
            }
        }
        return false;
    }
}
