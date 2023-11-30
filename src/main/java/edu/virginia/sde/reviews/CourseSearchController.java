package edu.virginia.sde.reviews;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CourseSearchController {
    @FXML
    private TableView<Course> tableView;

//    @FXML
//    private TableColumn<Course, String> subjectColumn;
//
//    @FXML
//    private TableColumn<Course, Integer> numberColumn;
//
//    @FXML
//    private TableColumn<Course, String> titleColumn;
//
//    @FXML
//    private TableColumn<Course, Double> ratingColumn;
    private Stage primaryStage;
    private User activeUser;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setActiveUser(User activeUser) { this.activeUser = activeUser; }

    public void initialize(){
        tableView.setRowFactory(tableView -> {
            TableRow<Course> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getClickCount() == 1) {
                    Course rowData = row.getItem();
                    handleRowClick(rowData);
                }
            });
            return row;
        });
        updateTable();
    }

    private void handleRowClick(Course selectedCourse){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("course-review.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            var controller = (CourseReviewsController) fxmlLoader.getController();
            controller.setPrimaryStage(primaryStage);
            primaryStage.setTitle("Course Review");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Clicked on row with title: " + selectedCourse.getTitle());
    }

    public void handleSearchButton(){
        //logic for search for anything in all three of the fields
        updateTable();
    }

    public void handleLogOutButton(){
        //scene switch to log in page
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            var controller = (LoginController) fxmlLoader.getController();
            controller.setPrimaryStage(primaryStage);
            primaryStage.setTitle("Login");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void handleMyReviewsButton(){
        //scene switch to MyReviews page
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("my-reviews.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            var controller = (MyReviewsController) fxmlLoader.getController();
            controller.setActiveUser(activeUser);
            controller.setPrimaryStage(primaryStage);
            primaryStage.setTitle("My Reviews");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void handleAddCourseButton(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("add-course.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            var controller = (AddCourseController) fxmlLoader.getController();
            controller.setPrimaryStage(primaryStage);
            primaryStage.setTitle("Add Course");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void updateTable(){
        List<Course> courseList = new ArrayList<>();
        Course myCourse = new Course("CS", 2100, "DSA1", 2.1);
        courseList.add(myCourse);

        ObservableList<Course> obsList = FXCollections.observableList(courseList);
        tableView.getItems().clear();
        tableView.getItems().addAll(obsList);
    }
}
