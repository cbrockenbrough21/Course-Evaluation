package edu.virginia.sde.reviews;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
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

    public boolean addIfNotExists(String subject, int course_number, String title) {
        DatabaseConnection databaseConnection = null;
        try {
            databaseConnection = new DatabaseConnection();
            try {
                databaseConnection.addCourse(subject, course_number, title);
                return true;
            } catch (SQLIntegrityConstraintViolationException e) {
                return false;
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
}
