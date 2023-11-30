package edu.virginia.sde.reviews;

import javafx.collections.FXCollections;
import javafx.scene.control.TextField;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.util.Callback;
import java.lang.reflect.InvocationTargetException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseSearchController {
    DatabaseConnection databaseConnection;
    @FXML
    private TableView<Course> tableView;

    @FXML
    private TextField subjectTextField;

    @FXML
    private TextField numberTextField;

    @FXML
    private TextField titleTextField;

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
        try {
            databaseConnection = new DatabaseConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
            controller.setActiveCourse(selectedCourse);
            controller.setActiveUser(activeUser);
            controller.setPrimaryStage(primaryStage);
            primaryStage.setTitle("Course Review");
            primaryStage.setScene(scene);
            controller.setActiveCourseLabel();
            primaryStage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //Makes courses pop up given any combo of title, subject, and number
    //Display the list of courses given the current subject, number, title in the text boxes
    public void handleSearchButton(){
        try{
            String subject = subjectTextField.getText();
            Integer number = numberTextField.getText().isEmpty() ? null : Integer.parseInt(numberTextField.getText());
            String title = titleTextField.getText();

            CourseSearchService courseSearchService = new CourseSearchService();
            List<Course> searchResults = courseSearchService.findAllCourses(subject, number, title);

            ObservableList<Course> obsList = FXCollections.observableList(searchResults);
            tableView.getItems().clear();
            tableView.getItems().addAll(obsList);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        List<Course> courseList = null;
        try {
            courseList = databaseConnection.getAllCourses();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ObservableList<Course> obsList = FXCollections.observableList(courseList);
        tableView.getItems().clear();
        tableView.getItems().addAll(obsList);
    }
}
