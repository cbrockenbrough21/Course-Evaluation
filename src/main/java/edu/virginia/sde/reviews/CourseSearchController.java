package edu.virginia.sde.reviews;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
public class CourseSearchController {
    @FXML
    private TableView<Course> tableView;

    public void handleSearchButton(){
        //logic for search for anything in all three of the fields
    }

    private void updateTable(){
        tableView.getItems().clear();
        tableView.getItems().addAll(obsList);
    }
}
