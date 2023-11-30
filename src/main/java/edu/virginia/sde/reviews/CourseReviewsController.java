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

    @FXML
    private RadioButton rating1;

    @FXML
    private RadioButton rating2;

    @FXML
    private RadioButton rating3;

    @FXML
    private RadioButton rating4;

    @FXML
    private RadioButton rating5;

    @FXML
    private TextField comment;

    @FXML
    private Label submitLabel;

    private User activeUser;

    private Course activeCourse;

    private Stage primaryStage;
    private ToggleGroup buttonGroup;

    public void setActiveUser(User activeUser) { this.activeUser = activeUser; }

    public void setActiveCourse(Course activeCourse){this.activeCourse = activeCourse;}

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void initialize(){
        var courseReviewsService = new CourseReviewsService();
        courseReviewsService.initialize();

        buttonGroup = new ToggleGroup();
        rating1.setToggleGroup(buttonGroup);
        rating2.setToggleGroup(buttonGroup);
        rating3.setToggleGroup(buttonGroup);
        rating4.setToggleGroup(buttonGroup);
        rating5.setToggleGroup(buttonGroup);
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
        CourseReviewsService courseReviewsService = new CourseReviewsService();
        System.out.println(activeUser);
        var toggle = (RadioButton) buttonGroup.getSelectedToggle();
        if (toggle == null){
            submitLabel.setText("Did not choose rating");
            submitLabel.setVisible(true);
        }
        else {
            String choice = toggle.getText();
            String commentString = comment.getText();
            courseReviewsService.addReview(activeUser.getId(), activeCourse.getCourseId(), choice, commentString);
        }
    }



}