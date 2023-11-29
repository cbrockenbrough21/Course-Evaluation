package edu.virginia.sde.reviews;

import java.sql.SQLException;

public class LoginService {

    private final DatabaseDriver databaseDriver;

    public LoginService(){
        databaseDriver = new DatabaseDriver();
        try{
            databaseDriver.connect();
        }
        catch(SQLException e){
        }
    }

    //function that will get all the users and return true or false if the user/password combo is present
    public boolean UsernamePasswordMatches(String username, String password){
        try{
            String databasePassword = databaseDriver.getPasswordByUsername(username);
            if(databasePassword.equals(null)){
                return false; //or could make this more specific to mean they aren't in the database at all
            }
            else if(databasePassword.equals(password)){
                return true;
            }
        }
        //not sure what to do if this is caught because this means there is more than one of that user in database
        catch (SQLException e){
            //rollback changes?
            throw
        }
    }

}
