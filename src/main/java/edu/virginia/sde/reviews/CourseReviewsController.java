package edu.virginia.sde.reviews;

import javafx.stage.Stage;

public class CourseReviewsController {

    private User activeUser;

    private Stage primaryStage;
    public void setActiveUser(User activeUser) { this.activeUser = activeUser; }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
}
