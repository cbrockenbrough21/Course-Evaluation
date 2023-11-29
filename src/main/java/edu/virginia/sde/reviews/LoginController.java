package edu.virginia.sde.reviews;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML
    public Label loginError;
    @FXML
    public TextField username;
    @FXML
    public TextField password;

    private User activeUser;
    LoginService loginService;

    private Stage primaryStage;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void displayLogin(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        //stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
        loginService = new LoginService(); //fix this
    }

    public void handleLoginButton() {
        String enteredUsername = username.getText();
        String enteredPassword = username.getText();

        if(enteredPassword.equals("") || enteredUsername.equals("")){
            handleCreateAccountError();
            return;
        }
        else{
            boolean matches = loginService.UsernamePasswordMatches(enteredUsername, enteredPassword);
            if(matches){
                //log them in, so scene switch to the course review page
            }
            else{
                handleLoginError();
                return;
            }
        }

        // if user does not exist
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("course-search.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            var controller = (CourseSearchController) fxmlLoader.getController();
            controller.setPrimaryStage(primaryStage);
            primaryStage.setTitle("Course Search");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void handleLoginError() {
        loginError.setStyle("-fx-text-fill: red;");
        loginError.setText("Incorrect Username or Password. Please try again.");
    }

    //create a function for handling the login button being pressed
        //see if that pair exists in the database, if not prompt an error
        //this function will be in our LoginService file, we will just call it here and use the result to change the display

    public void handleCreateAccountButton(){
        //do this in the service file
            //check if username already exists in database
            //add username and password to database

        //are they logged in now or is the next step for them to log in? show message about this to clarify?
        handleCreateAccountError();
    }

    public void handleCreateAccountError(){
        loginError.setStyle("-fx-text-fill: red;");
        loginError.setText("Invalid Username or Password. Please try again.");
    }

}
