package edu.virginia.sde.reviews;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class AddCourseController{
    private Stage primaryStage;

    @FXML
    private TextField subject;

    @FXML
    private TextField courseNumber;

    @FXML
    private TextField title;

    @FXML
    private Label courseAddLabel;

    private Course activeCourse;

    private User activeUser;



    public void setActiveUser(User activeUser) { this.activeUser = activeUser; }


    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
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

    public void handleAddCourse(){
        var addCourseService = new AddCourseService();
        String subjectString = subject.getText();
        String numberString = courseNumber.getText();
        String titleString = title.getText();

        boolean validCourse = addCourseService.isValidSubject(subjectString) &&
                              addCourseService.isValidCourseNumber(numberString) &&
                              addCourseService.isValidTitle(titleString);

        if (!validCourse) {
            if (!addCourseService.isValidSubject(subjectString) && !subjectString.isEmpty()) {
                courseAddLabel.setStyle("-fx-text-fill: red;");
                courseAddLabel.setText("Unable to add - please try again with a 2-4 character subject.");
                courseAddLabel.setVisible(true);
            }
            else if (!addCourseService.isValidCourseNumber(numberString) && !numberString.isEmpty()) {
                courseAddLabel.setStyle("-fx-text-fill: red;");
                courseAddLabel.setText("Unable to add - please try again with a 4 integer number.");
                courseAddLabel.setVisible(true);
            }
            else if (subjectString.isEmpty() || numberString.isEmpty() || titleString.isEmpty()) {
                courseAddLabel.setStyle("-fx-text-fill: red;");
                courseAddLabel.setText("At least one input is missing. Please add a course by filling out all fields.");
                courseAddLabel.setVisible(true);
            }
        }

        if (validCourse) {
            int course_number = Integer.parseInt(numberString);
            boolean successfulAdd = addCourseService.addIfNotExists(subjectString, course_number, titleString);
            if (successfulAdd) {
                int courseID = addCourseService.getCourseID(subjectString, numberString, titleString);
                activeCourse = new Course(subjectString, Integer.parseInt(numberString), titleString, courseID);
                handleCourseAdded();
            }
            else {
                courseAddLabel.setStyle("-fx-text-fill: red;");
                courseAddLabel.setText("The course already exists. Please visit Course from Course Search.");
                courseAddLabel.setVisible(true);
            }
        }
    }
    public void handleCourseAdded() {
        subject.clear();
        courseNumber.clear();
        title.clear();
        courseAddLabel.setText("Successfully added course!");
        courseAddLabel.setVisible(true);
    }
}
