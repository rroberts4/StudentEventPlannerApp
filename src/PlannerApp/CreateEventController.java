///////////////////////////////////////////////////////////////////////////////
//
// Title:           PlannerApp
// Main Class File: PlannerApp.Main.java
// File:            CreateEventController.java
// Date:            June 2021
//
// Author:          Ryan Jordan Roberts
/*
 * This Application simulates a student daily planner.
 * Allowing users to create an account and login.
 * Users are able to add and see upcoming events in their planner.
 * Login and event planner info are stored using a MYSQL Database.

 */
///////////////////////////////////////////////////////////////////////////////

package PlannerApp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.net.URL;
import java.sql.Connection;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class CreateEventController implements Initializable {

    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField eventNameTextField;
    @FXML
    private Button confirmButton, returnButton;
    @FXML
    private Label  errorLabel;
    @FXML
    private ComboBox<String> eventTypeComboBox;

    String eventDueDate;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        confirmButton.setVisible(false); //user must select a due date before confirm button is visible

        fillEventTypeComboBox();

        disablePastDatesFromUserDatePicker();

    }
    public void setEventDueDate(){
        LocalDate selectedDate = datePicker.getValue();
        eventDueDate = selectedDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        confirmButton.setVisible(true);
    }

    public void fillEventTypeComboBox(){
        ObservableList<String> eventList = FXCollections.observableArrayList("Classwork", "Homework", "School Event","Exam", "Midterm","Final","Quiz", "Test", "Meeting", "Other");
        eventTypeComboBox.setItems(eventList);

    }
    public void disablePastDatesFromUserDatePicker(){

        datePicker.setDayCellFactory(param -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.compareTo(LocalDate.now()) < 0);
            }
        });

    }
    public void returnButtonAction(ActionEvent event) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("PlannerHomeScene.fxml"));
        Stage window = (Stage) returnButton.getScene().getWindow();
        window.setScene(new Scene(root, 800, 600));
    }

    public void confirmButtonAction(ActionEvent event){
      if(checkIfEventInfoIsEmpty()){
          return;
      }

        createNewStudentEvent();


    }
    public boolean checkIfEventInfoIsEmpty(){
        if (eventTypeComboBox.getSelectionModel().isEmpty()){
            errorLabel.setText("Please make an Event type selection!");
            return true;
        }
        if(eventNameTextField.getText().isEmpty()){
            errorLabel.setText("Please enter the name of your Event!");
            return true;
        }
        return false;
    }
    public void createNewStudentEvent(){

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection con = connectNow.getConnection();


        String eventName = eventNameTextField.getText();
        String eventType = eventTypeComboBox.getSelectionModel().getSelectedItem();

        int studentId = LoginSceneController.getStudentId(); //getStudent Id from Login Page

        //Query uses all obtain Info from User to create a new Event
        String insertField = "INSERT INTO student_activity (student_id,activity_name,activity_type,due_date) Values('";

        String insertValues = studentId + "','" + eventName + "','" + eventType +"','" + eventDueDate + "');";

        String studentInsertedValues = insertField + insertValues;

        try {
            Statement statement = con.createStatement();
            statement.executeUpdate(studentInsertedValues);
            Parent root = FXMLLoader.load(getClass().getResource("PlannerHomeScene.fxml"));
            Stage window = (Stage) confirmButton.getScene().getWindow();
            window.setScene(new Scene(root, 800, 600));


        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();

        }

    }
}
