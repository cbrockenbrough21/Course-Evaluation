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

    public void setActiveUser(User activeUser) { this.activeUser = activeUser; }

    public void handleCreateAccountButton(){
        var loginService = new LoginService();
        String enteredUsername = username.getText();
        String enteredPassword = password.getText();

        if(username == null || password == null){
            handleInvalidEntryError();
            return;
        }
        else if(enteredUsername.equals("") || enteredPassword.equals("")){
            handleInvalidEntryError();
            return;
        }

        boolean successfulAdd = loginService.addIfNotExists(enteredUsername, enteredPassword);
        System.out.println("got to this line");

        if(successfulAdd){
            handleAccountCreated();
            return;
        }
        else{
            System.out.println("got to this line");
            handleUserExistsError();
            return;
        }

    }

    public void handleUserExistsError(){
        createAccountLabel.setStyle("-fx-text-fill: red;");
        createAccountLabel.setText("Username already exists. Please try a new username or login to existing account.");
    }

    public void handleInvalidEntryError(){
        createAccountLabel.setStyle("-fx-text-fill: red;");
        createAccountLabel.setText("Invalid Username or Password. Please try again.");
    }

    public void handleAccountCreated(){
        createAccountLabel.setText("Account successfully created! Please go back to login.");
    }

    public void handleBackButton(){
        try {
            var loginService = new LoginService();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            var controller = (LoginController) fxmlLoader.getController();
            controller.setActiveUser(activeUser);
            controller.setPrimaryStage(primaryStage);
            primaryStage.setTitle("Course Search");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
