package edu.virginia.sde.reviews;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LoginController {

    private User activeUser;

    public void displayLogin(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        //stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
    }

    //create a function for handling the login button being pressed
        //see if that pair exists in the database, if not prompt an error
}
