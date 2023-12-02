package edu.virginia.sde.reviews;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableRow;
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

    private User activeUser;

    public void setActiveUser(User activeUser) { this.activeUser = activeUser; }
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
    public void initialize(){
        tableView.setRowFactory(tableView -> {
            TableRow<Review> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getClickCount() == 1) {
                    Review rowData = row.getItem();
                    handleRowClick(rowData);
                }
            });
            return row;
        });
    }

    private void handleRowClick(Review selectedReview){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("course-review.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            var controller = (CourseReviewsController) fxmlLoader.getController();
            controller.setPrimaryStage(primaryStage);
            //controller.setActiveCourse();
            primaryStage.setTitle("Course Review");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //System.out.println("Clicked on row with title: " + selectedReview.getSubject());
    }
    public void handleBackButton() {
        //scene switch back to course search from My Reviews
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

    //public String getActiveUsername(User activeUser) {return activeUser.getUsername(); }

    public void updateTable() {
        MyReviewsService myReviewsService = new MyReviewsService();
        List<Review> myReviewList = myReviewsService.getMyReviews(activeUser.getId());
        ObservableList<Review> obsList = FXCollections.observableList(myReviewList);
        tableView.getItems().clear();
        tableView.getItems().addAll(obsList);

    }
}
