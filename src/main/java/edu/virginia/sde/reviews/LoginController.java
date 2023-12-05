package edu.virginia.sde.reviews;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

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
        stage.setScene(scene);
        stage.show();
    }

    public void handleLoginButton() {
        var loginService = new LoginService();
        String enteredUsername = username.getText();
        String enteredPassword = password.getText();

        if(enteredPassword.isEmpty() || enteredUsername.isEmpty()){
            handleLoginError();
        }

        else{
            loginError.setText("");
            boolean matches = loginService.UsernamePasswordMatches(enteredUsername, enteredPassword);
            if(matches){
                User newUser = new User(enteredUsername, enteredPassword, loginService.getUserID(enteredUsername));
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("course-search.fxml"));
                    Scene scene = new Scene(fxmlLoader.load());
                    var controller = (CourseSearchController) fxmlLoader.getController();
                    controller.setActiveUser(newUser);
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
            }
        }
    }

    public void handleLoginError() {
        loginError.setStyle("-fx-text-fill: red;");
        loginError.setText("Incorrect Username or Password. Please try again.");
    }

    public void handleCreateAccountButton(){
        //leads to a popup window where they enter password/username
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("create-user.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            var controller = (CreateUserController) fxmlLoader.getController();
            controller.setPrimaryStage(primaryStage);
            primaryStage.setTitle("Course Search");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void handleCloseButton() {
        System.exit(0);
    }

}
