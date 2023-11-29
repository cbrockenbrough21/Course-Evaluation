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

    public void setPrimaryStage(Stage primaryStage) { this.primaryStage = primaryStage; }

    public void handleCreateAccountButton(){
        var loginService = new LoginService();
        String enteredUsername = username.getText();
        String enteredPassword = password.getText();

        if(username == null || password == null){
            handleInvalidEntryError();
            return;
        }

        boolean successfulAdd = loginService.addIfNotExists(enteredUsername, enteredPassword);

        if(successfulAdd){
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

    }

}
