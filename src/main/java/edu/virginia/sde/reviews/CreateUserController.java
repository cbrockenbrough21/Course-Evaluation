package edu.virginia.sde.reviews;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class CreateUserController {
    private Stage primaryStage;
    @FXML
    public TextField username;
    @FXML
    public TextField password;
    @FXML
    public Label createAccountLabel;
    private User activeUser;

    public void setPrimaryStage(Stage primaryStage) { this.primaryStage = primaryStage; }

    public void handleCreateAccountButton(){
        var loginService = new LoginService();
        String enteredUsername = username.getText();
        String enteredPassword = password.getText();

        if(username == null || password == null){
            handleInvalidEntryError();
            return;
        }

        enteredUsername = enteredUsername.trim();
        enteredPassword = enteredPassword.trim();

        if (enteredUsername.isEmpty()) {
            handleInvalidEntryError();
            return;
        }

        if (enteredPassword.length() < 8) {
            handleWrongPasswordLength();
            return;
        }

        boolean successfulAdd = loginService.addIfNotExists(enteredUsername, enteredPassword);

        if(successfulAdd){
            int userID = loginService.getUserID(enteredUsername);
            activeUser = new User(enteredUsername, enteredPassword, userID);
            handleAccountCreated();
            return;
        }
        else{
            handleUserExistsError();
            return;
        }

    }

    public void handleUserExistsError(){
        createAccountLabel.setStyle("-fx-text-fill: red;");
        createAccountLabel.setText("Username already exists (note that adding trailing/leading spaces to an existing username won't make it different).");
    }

    public void handleInvalidEntryError(){
        createAccountLabel.setStyle("-fx-text-fill: red;");
        createAccountLabel.setText("Invalid Username or Password. Username must be at least 1 character (excluding trailing/leading spaces). Please try again.");
    }

    public void handleWrongPasswordLength(){
        createAccountLabel.setStyle("-fx-text-fill: red;");
        createAccountLabel.setText("Password must be at least 8 characters (excluding leading/trailing spaces).");
    }

    public void handleAccountCreated(){
        createAccountLabel.setStyle("-fx-text-fill: black;");
        createAccountLabel.setText("Account successfully created! Return to login.");
    }

    public void handleBackButton(){
        try {
            var loginService = new LoginService();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            var controller = (LoginController) fxmlLoader.getController();
            controller.setActiveUser(activeUser);
            controller.setPrimaryStage(primaryStage);
            primaryStage.setTitle("Login");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
