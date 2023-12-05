package edu.virginia.sde.reviews;

import javafx.collections.FXCollections;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;;
import java.sql.SQLException;
import java.util.List;


public class CourseSearchController {
    DatabaseConnection databaseConnection;
    @FXML
    private Label courseSearchError;

    @FXML
    private TableView<Course> tableView;

    @FXML
    private TextField subjectTextField;

    @FXML
    private TextField numberTextField;

    @FXML
    private TextField titleTextField;
    private Course activeCourse;
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
            var controller = (ReviewsController) fxmlLoader.getController();
            controller.setActiveCourse(selectedCourse);
            controller.setActiveCourseLabel();
            controller.setActiveUser(activeUser);
            controller.updateTable();
            controller.setUserReview();
            controller.setPrimaryStage(primaryStage);
            primaryStage.setTitle("Course Review");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //Makes courses pop up given any combo of title, subject, and number
    //Display the list of courses given the current subject, number, title in the text boxes
    public void handleSearchButton(){
        try{
            courseSearchError.setText("");
            String subject = subjectTextField.getText();
            Integer number = null;

            if (subject.length() == 1 || subject.length() > 4) {
                handleInvalidMnemonicError();
                return;
            }

            if (!numberTextField.getText().isEmpty()) {
                try {
                    number = Integer.parseInt(numberTextField.getText());
                } catch (NumberFormatException ex) {
                    handleInvalidInputError();
                    return;
                }
            }

            String title = titleTextField.getText();

            var courseSearchService = new CourseSearchService();
            var courseReviewService = new ReviewsService();
            List<Course> searchResults = courseSearchService.findAllCourses(subject, number, title);
            courseReviewService.displayAverageRating(courseReviewService, searchResults);

            ObservableList<Course> obsList = FXCollections.observableList(searchResults);
            tableView.getItems().clear();
            tableView.getItems().addAll(obsList);

            if (searchResults.isEmpty()) {
                handleCourseSearchError();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleInvalidInputError() {
        courseSearchError.setStyle("-fx-text-fill: red;");
        courseSearchError.setText("Invalid input for the course number. Please enter a valid integer.");
    }

    private void handleInvalidMnemonicError() {
        courseSearchError.setStyle("-fx-text-fill: red;");
        courseSearchError.setText("Invalid input for the course mnemonic. Please enter a mnemonic between 2-4 characters.");
    }

    public void handleCourseSearchError() {
        courseSearchError.setStyle("-fx-text-fill: red;");
        courseSearchError.setText("No course meets search criteria. Please add course through 'Add Course Window'.");
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
            controller.updateTable();
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
            controller.setActiveUser(activeUser);
            primaryStage.setTitle("Add Course");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateTable(){
        var courseSearchService = new CourseSearchService();
        var courseReviewService = new ReviewsService();
        List<Course> courseList = courseSearchService.getCourses();
        courseReviewService.displayAverageRating(courseReviewService, courseList);
        ObservableList<Course> obsList = FXCollections.observableList(courseList);
        tableView.getItems().clear();
        tableView.getItems().addAll(obsList);
    }
}
