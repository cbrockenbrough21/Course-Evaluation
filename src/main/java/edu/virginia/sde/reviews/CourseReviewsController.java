package edu.virginia.sde.reviews;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class CourseReviewsController {
    @FXML
    public Label activeCourseLabel;

    @FXML
    private TableView<Review> tableView;

    private User activeUser;

    private Course activeCourse;

    private Stage primaryStage;

    public void setActiveUser(User activeUser) { this.activeUser = activeUser; }

    public void setActiveCourse(Course activeCourse){this.activeCourse = activeCourse;}

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void initialize(){
        var courseReviewsService = new CourseReviewsService();
        courseReviewsService.initialize();
    }

    public void updateTable(){
        var courseReviewsService = new CourseReviewsService();
        List<Review> reviewList = courseReviewsService.getReviewList(activeCourse);

        ObservableList<Review> obsList = FXCollections.observableList(reviewList);
        tableView.getItems().clear();
        tableView.getItems().addAll(obsList);
    }

    public void setActiveCourseLabel(){
        activeCourseLabel.setStyle("-fx-text-fill: navy;");
        activeCourseLabel.setText(activeCourse.toString());
    }

    public void handleBackButton(){
        try {
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

    public void handleSubmitReviewButton(){
        addCourseReview();
    }



}