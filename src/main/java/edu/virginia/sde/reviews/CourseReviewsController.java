package edu.virginia.sde.reviews;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class CourseReviewsController {

    @FXML
    public Label courses_reviews;

    private User activeUser;

    private Course activeCourse;

    private Stage primaryStage;
    public void setActiveUser(User activeUser) { this.activeUser = activeUser; }
    public void setActiveCourse(Course activeCourse){this.activeCourse = activeCourse;}

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void getCourseNameOfReviews() {
        courses_reviews.setStyle("-fx-text-fill: navy;");
        //store a course before scene switching
        //courses_reviews.setText("");
    }
}
