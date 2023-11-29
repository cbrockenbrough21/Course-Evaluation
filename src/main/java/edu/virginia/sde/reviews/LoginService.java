package edu.virginia.sde.reviews;

import java.sql.SQLException;

public class LoginService {
    public void save(){
        DatabaseConnection databaseConnection = null;
        try {
            databaseConnection = new DatabaseConnection();
            databaseConnection.addUser("owarren", "blahblah");
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
    /*
    //function that will get all the users and return true or false if the user/password combo is present
    public boolean UsernamePasswordMatches(String username, String password) {
        DatabaseConnection databaseConnection = null;
        try {
            databaseConnection = new DatabaseConnection();
            /*String databasePassword = DatabaseConnection.getPasswordByUsername(username);
            if(databasePassword.equals(null)){
                return false; //or could make this more specific to mean they aren't in the database at all
            }
            else if(databasePassword.equals(password)){
                return true;
            }
            return true;
        } catch (SQLException e) {
            //rollback changes?
            throw new RuntimeException();
        } finally{
            try{
                if(databaseConnection != null){
                    databaseConnection.disconnect();
                }
            } catch(SQLException e){
                throw new RuntimeException();
            }
        }
    }*/
}
