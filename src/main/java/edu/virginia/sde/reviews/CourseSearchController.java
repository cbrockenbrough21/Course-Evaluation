package edu.virginia.sde.reviews;

import javafx.collections.FXCollections;
import javafx.scene.control.TextField;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CourseSearchController {
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
        tableView.setRowFactory(new Callback<TableView<Course>, TableRow<Course>>() {
//            subjectColumn.setCellValueFactory(new PropertyValueFactory<>("subject"));
//            numberColumn.setCellValueFactory(new PropertyValueFactory<>("courseNumber"));
//            titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
//            ratingColumn.setCellValueFactory(new PropertyValueFactory<>("rating"));
            @Override
            public TableRow<Course> call(TableView<Course> tableView) {
                final TableRow<Course> row = new TableRow<>();
                row.setOnMouseClicked(event -> {
                    if (!row.isEmpty() && event.getClickCount() == 1) {
                        Course rowData = row.getItem();
                        // Handle the row click event here
                        System.out.println("Clicked on row with title: " + rowData.getTitle());
                    }
                });
                return row;
            }
        });
        //updateTable();
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

    //Makes courses pop up given any combo of title, subject, and number
    //Display the list of courses given the current subject, number, title in the text boxes
    public void handleSearchButton(){
        String subject = subjectTextField.getText();
        Integer number = Integer.parseInt(numberTextField.getText());
        String title = titleTextField.getText();

        CourseSearchService courseSearchService = new CourseSearchService();
        List<Course> searchResults = courseSearchService.findAllCourses(subject, number, title);

        ObservableList<Course> obsList = FXCollections.observableList(searchResults);
        tableView.getItems().clear();
        tableView.getItems().addAll(obsList);
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

    public void handleCourseClicked() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("course-review.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            var controller = (CourseReviewsController) fxmlLoader.getController();
            controller.setPrimaryStage(primaryStage);
            primaryStage.setTitle("Course Reviews");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

   // private void updateTable(){
      //  List<Course> courseList = new ArrayList<>();
       // Course myCourse = new Course("CS", 2100, "DSA1");
       // courseList.add(myCourse);

       // ObservableList<Course> obsList = FXCollections.observableList(courseList);
       // tableView.getItems().clear();
        //tableView.getItems().addAll(obsList);
   // }
}
