package edu.virginia.sde.reviews;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class ReviewsController {
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

    @FXML
    private Button deleteButton;

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
        var courseReviewsService = new ReviewsService();
        List<Review> reviewList = courseReviewsService.getReviewList(activeCourse);
        ObservableList<Review> obsList = FXCollections.observableList(reviewList);
        tableView.getItems().clear();
        tableView.getItems().addAll(obsList);
    }

    public void setActiveCourseLabel() {
        setAvgRating();
        activeCourseLabel.setStyle("-fx-text-fill: navy;");
        activeCourseLabel.setText(activeCourse.toString() + " - " + "Average Rating: " + activeCourse.getRating());
    }

    public void setAvgRating() {
        var courseReviewsService = new ReviewsService();
        double cum_rating = 0.0;
        int rev_count = 0;
        List<Review> courseReviews = courseReviewsService.getReviewList(activeCourse);
        for (Review review : courseReviews) {
            rev_count++;
            cum_rating = cum_rating + review.getRating();
        }
        String avg = String.format("%.2f", (cum_rating / rev_count));
        if (courseReviews.isEmpty()) {
            avg = "";
        }
        activeCourse.setRating(avg);
    }

    public void setUserReview(){
        ReviewsService reviewsService = new ReviewsService();
        activeReview = reviewsService.getUserReview(activeUser.getId(), activeCourse.getCourseId());
        if (activeReview != null){
            submitButton.setText("Save");
            deleteButton.setVisible(true);
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
        else {
            submitButton.setText("Submit");
            comment.setText("");
            deleteButton.setVisible(false);
            buttonGroup.selectToggle(null);
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
        ReviewsService reviewsService = new ReviewsService();
        var toggle = (RadioButton) buttonGroup.getSelectedToggle();
        if (toggle == null){
            submitLabel.setText("Did not choose rating");
            submitLabel.setVisible(true);
        }
        else {
            String choice = toggle.getId();
            String commentString = comment.getText();
            if (activeReview == null){
                reviewsService.addReview(activeUser.getId(), activeCourse.getCourseId(), choice, commentString);
                setAvgRating();
                setActiveCourseLabel();
                submitLabel.setText("Successfully submitted review!");
                submitLabel.setVisible(true);
                submitButton.setText("Save");
                activeReview = reviewsService.getUserReview(activeUser.getId(), activeCourse.getCourseId());
            }
            else {
                reviewsService.updateReview(activeReview.getReviewID(), choice, commentString);
                setAvgRating();
                setActiveCourseLabel();
                submitLabel.setText("Successfully updated review!");
                submitLabel.setVisible(true);
            }
            deleteButton.setVisible(true);
            updateTable();


        }
    }

    public void handleDeleteReviewButton(){
        if (activeReview != null){
            ReviewsService reviewsService = new ReviewsService();
            reviewsService.deleteReview(activeReview.getReviewID());
            setAvgRating();
            setActiveCourseLabel();
            activeReview = null;
            updateTable();
            setUserReview();
            submitLabel.setText("Successfully deleted review!");
            submitLabel.setVisible(true);
            deleteButton.setVisible(false);
        }
    }
}