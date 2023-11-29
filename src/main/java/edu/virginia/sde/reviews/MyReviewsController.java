package edu.virginia.sde.reviews;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.scene.Scene;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class MyReviewsController {

    private Stage primaryStage;

    @FXML
    private TableView<Review> tableView;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
    public void initialize(){
        updateTable();
    }
    public void handleBackButton() {
        //scene switch back to course search from My Reviews
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("course-search.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            var controller = (CourseSearchController) fxmlLoader.getController();
            controller.setPrimaryStage(primaryStage);
            primaryStage.setTitle("Course Search");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void updateTable() {
        //eventually this will be querying a database but for now hard-coded
        List<Review> myReviewList = new ArrayList<>();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Review myReview = new Review("CS", 2100, 3.0, timestamp.toString());
        myReviewList.add(myReview);
        ObservableList<Review> obsList = FXCollections.observableList(myReviewList);
        tableView.getItems().clear();
        tableView.getItems().addAll(obsList);

    }
}
