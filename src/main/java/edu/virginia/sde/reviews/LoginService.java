package edu.virginia.sde.reviews;

import java.sql.SQLException;

public class LoginService {
    public void save(){
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
            if(databasePassword == null){
                return false;
            }
            else if(databasePassword.equals(password)){
                //System.out.println("got to this line");
                return true;
            }
            return false;
        } catch (SQLException e) {
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
    }
}
