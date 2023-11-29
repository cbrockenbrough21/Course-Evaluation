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

    private Stage primaryStage;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setActiveUser(User activeUser){ this.activeUser = activeUser; }

    public void displayLogin(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        //stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
        var loginService = new LoginService(); //fix this
    }

    public void handleLoginButton() {
        var loginService = new LoginService();
        String enteredUsername = username.getText();
        String enteredPassword = password.getText();

        //System.out.println(enteredUsername);
        //System.out.println(enteredPassword);

        if(enteredPassword.equals("") || enteredUsername.equals("")){
            handleLoginError();
            return;
        }
        else{
            boolean matches = loginService.UsernamePasswordMatches(enteredUsername, enteredPassword);
            if(matches){
                //log them in, so scene switch to the course review page
                try {
                    loginService = new LoginService();
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("course-search.fxml"));
                    Scene scene = new Scene(fxmlLoader.load());
                    var controller = (CourseSearchController) fxmlLoader.getController();
                    controller.setActiveUser(activeUser);
                    controller.setPrimaryStage(primaryStage);
                    primaryStage.setTitle("Course Search");
                    primaryStage.setScene(scene);
                    primaryStage.show();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            else{
                handleLoginError();
                return;
            }
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
        //leads to a popup window where they enter password/username
        var loginService = new LoginService();
        String enteredUsername = username.getText();
        String enteredPassword = password.getText();
        boolean successfulAdd = loginService.addIfNotExists(enteredUsername, enteredPassword);

        if(successfulAdd){
            //log them in?
        }

        //are they logged in now or is the next step for them to log in? show message about this to clarify?
        handleCreateAccountError();
    }

    public void handleCreateAccountError(){
        loginError.setStyle("-fx-text-fill: red;");
        loginError.setText("Invalid Username or Password. Please try again.");
    }

}
