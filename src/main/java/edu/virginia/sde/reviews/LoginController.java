package edu.virginia.sde.reviews;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    public Label loginError;
    private User activeUser;

    public void displayLogin(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        //stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
    }

    public void handleLoginButton() {
        //Some type of select queries and logic to see
        //if the user exists and log them in if so

        // if user does not exist
        handleLoginError();
    }

    public void handleLoginError() {
        loginError.setStyle("-fx-text-fill: red;");
        loginError.setText("Incorrect Username or Password. Please try again.");
    }

    //create a function for handling the login button being pressed
        //see if that pair exists in the database, if not prompt an error

    public void handleCreateAccountButton(){
        //check if username already exists in database
        //add username and password to database

        //are they logged in now or is the next step for them to log in? show message about this to clarify?
    }

}
