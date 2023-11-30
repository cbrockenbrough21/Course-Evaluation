package edu.virginia.sde.reviews;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

public class LoginService {
    public void save() {
        DatabaseConnection databaseConnection = null;
        try {
            databaseConnection = new DatabaseConnection();
            databaseConnection.addUser("riley", "blahblah");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (databaseConnection != null) {
                    databaseConnection.disconnect();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    //function that will get all the users and return true or false if the user/password combo is present
    public boolean UsernamePasswordMatches(String username, String password) {
        DatabaseConnection databaseConnection = null;
        try {
            databaseConnection = new DatabaseConnection();
            String databasePassword = databaseConnection.getPasswordByUsername(username);
            if (databasePassword == null) {
                return false;
            } else if (databasePassword.equals(password)) {
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

    //basically just checking for that specific exception to be thrown if I try to add I think?
    //return true if successful add, return false if username was already in database
    public boolean addIfNotExists(String username, String password) {
        DatabaseConnection databaseConnection = null;
        try {
            databaseConnection = new DatabaseConnection();
            try {
                databaseConnection.addUser(username, password);
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