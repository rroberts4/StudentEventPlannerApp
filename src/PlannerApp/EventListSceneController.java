///////////////////////////////////////////////////////////////////////////////
//
// Title:           PlannerApp
// Main Class File: PlannerApp.Main.java
// File:            EventListSceneController.java
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
        import javafx.fxml.FXML;
        import javafx.fxml.FXMLLoader;
        import javafx.fxml.Initializable;
        import javafx.scene.Parent;
        import javafx.scene.Scene;
        import javafx.scene.control.*;
        import javafx.scene.control.cell.PropertyValueFactory;
        import javafx.stage.Stage;


        import java.net.URL;
        import java.sql.Connection;
        import java.sql.ResultSet;
        import java.text.SimpleDateFormat;
        import java.util.Date;
        import java.util.ResourceBundle;
        import java.util.logging.Level;
        import java.util.logging.Logger;


public class EventListSceneController implements Initializable {

    @FXML
    private TableView<Event> eventTable;
    @FXML
    private TableColumn<Event,String> columnName;
    @FXML
    private TableColumn<Event,String>  columnDate;
    @FXML
    private TableColumn<Event,String>  columnType;
    @FXML
    private Label exitLabel;
    @FXML
    private Button returnButton;
    String todaysDate;

    ObservableList<Event> list = FXCollections.observableArrayList();








    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setCurrentDate();

        try {
            displayStudentsListOfEvents();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void displayStudentsListOfEvents()throws Exception{
        try {

            DatabaseConnection connectNow = new DatabaseConnection();
            Connection con = connectNow.getConnection();

            //Query returns rows of Events already created by User with due dates on or past the current date

            ResultSet result = con.createStatement().executeQuery("SELECT activity_name,activity_type,due_date" +
                    " FROM student_activity WHERE student_id = '" +
                    LoginSceneController.getStudentId() + "'AND due_date >= '"+
                    todaysDate + "' ORDER BY due_date");

            while(result.next()){
                list.add(new Event(result.getString("activity_name"),
                        result.getString("activity_type"), result.getString("due_date")));
            }
        } catch(Exception e) {
            e.printStackTrace();
            e.getCause();
            Logger.getLogger(EventListSceneController.class.getName()).log(Level.SEVERE,null,e);

        }


        columnName.setCellValueFactory(new PropertyValueFactory<>("eventName"));
        columnType.setCellValueFactory(new PropertyValueFactory<>("eventType"));
        columnDate.setCellValueFactory(new PropertyValueFactory<>("eventDate"));


        eventTable.setItems(list);





    }
    public void setCurrentDate(){

        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        todaysDate = dateFormat.format(currentDate);
    }

    public void exitLabelClicked() {
        Stage stage = (Stage) exitLabel.getScene().getWindow();
        stage.close();

    }
    public void returnButtonAction()throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("PlannerHomeScene.fxml"));
        Stage window =(Stage) returnButton.getScene().getWindow();
        window.setScene(new Scene(root, 800,600));
    }
}



