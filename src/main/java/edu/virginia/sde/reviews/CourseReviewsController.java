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

    @FXML
    private Button submitButton;

    private User activeUser;

    private Course activeCourse;

    private Stage primaryStage;
    private ToggleGroup buttonGroup;

    private Review activeReview;

    public void setActiveUser(User activeUser) { this.activeUser = activeUser; }

    public void setActiveCourse(Course activeCourse){this.activeCourse = activeCourse;}

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void initialize(){
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

    public void setUserReview(){
        CourseReviewsService courseReviewsService = new CourseReviewsService();
        activeReview = courseReviewsService.getUserReview(activeUser.getId(), activeCourse.getCourseId());
        if (activeReview != null){
            submitButton.setText("Resubmit");
            comment.setText(activeReview.getComment());
            switch(activeReview.getRating()) {
                case 1: {
                    buttonGroup.selectToggle(rating1);
                    break;
                }
                case 2: {
                    buttonGroup.selectToggle(rating2);
                    break;
                }
                case 3: {
                    buttonGroup.selectToggle(rating3);
                    break;
                }
                case 4: {
                    buttonGroup.selectToggle(rating4);
                    break;
                }
                case 5: {
                    buttonGroup.selectToggle(rating5);
                    break;
                }
            }
        }
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
        int courseId = activeCourse.getCourseId();
        int userId = activeUser.getId();
        var toggle = (RadioButton) buttonGroup.getSelectedToggle();
        if (toggle == null){
            submitLabel.setText("Did not choose rating");
            submitLabel.setVisible(true);
        }
        else {
            String choice = toggle.getText();
            String commentString = comment.getText();
            if (activeReview == null){
                courseReviewsService.addReview(activeUser.getId(), activeCourse.getCourseId(), choice, commentString);
                submitLabel.setText("Successfully submitted review!");
                submitLabel.setVisible(true);
            }
            else {
                courseReviewsService.updateReview(activeReview.getReviewID(), choice, commentString);
                submitLabel.setText("Successfully updated review!");
                submitLabel.setVisible(true);
            }
            updateTable();

        }
    }

    public void handleDeleteReviewButton(){
        submitLabel.setText("Haven't done this button yet");
        submitLabel.setVisible(true);
    }
}