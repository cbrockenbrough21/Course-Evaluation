package edu.virginia.sde.reviews;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class CourseSearchController {
    @FXML
    private TableView<Course> tableView;

    public void initialize(){
        updateTable();
    }
    public void handleSearchButton(){
        //logic for search for anything in all three of the fields
        //updateTable();
    }

    private void updateTable(){
        List<Course> courseList = new ArrayList<>();
        Course myCourse = new Course("CS", 2100, "DSA1");
        courseList.add(myCourse);
        ObservableList<Course> obsList = FXCollections.observableList(courseList);
        tableView.getItems().clear();
        tableView.getItems().addAll(obsList);
    }
}
