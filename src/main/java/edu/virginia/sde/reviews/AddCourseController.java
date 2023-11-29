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


    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
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

    public void handleAddCourse(){
        //say course successfully added
        String subjectString = subject.getText();
        String numberString = courseNumber.getText();
        String titleString = title.getText();

        boolean successful = true;
        //addCoursetoDatabase(subject, courseNumber, title);

        if (successful){
            subject.clear();
            courseNumber.clear();
            title.clear();
            courseAddLabel.setText("Successfully added course!");
            courseAddLabel.setVisible(true);
        }
        else {
            courseAddLabel.setText("Unable to add the course because invalid input. Please try again. ");
            courseAddLabel.setVisible(true);
        }
    }
}