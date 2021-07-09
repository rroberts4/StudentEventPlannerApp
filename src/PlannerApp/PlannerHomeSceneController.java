///////////////////////////////////////////////////////////////////////////////
//
// Title:           PlannerApp
// Main Class File: PlannerApp.Main.java
// File:            PlannerHomeSceneController.java
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

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PlannerHomeSceneController implements Initializable {

    @FXML
    private Label eventOneLabel, eventTwoLabel, eventThreeLabel, eventFourLabel;

    @FXML
    private Label exitLabel, greetingLabel,dateLabel;
    @FXML
    private ImageView eventListImage, addEventImage;

    String todaysDate;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        greetingLabel.setText("Welcome, " + LoginSceneController.getStudentName());

        displayTodaysDate();

        try {
            displayUsersListOfUpcomingEvents();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void exitLabelClicked(){
        Stage stage = (Stage) exitLabel.getScene().getWindow();
        stage.close();
    }
    public void eventListClicked() throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("EventListScene.fxml"));
        Stage window = (Stage) eventListImage.getScene().getWindow();
        window.setScene(new Scene(root, 800, 600));

    }
    public void addEventClicked() throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("CreateEventScene.fxml"));
        Stage window = (Stage) addEventImage.getScene().getWindow();
        window.setScene(new Scene(root, 800, 600));

    }

    public void displayTodaysDate(){

        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM/dd/yyyy");
        todaysDate = dateFormat.format(currentDate);
        dateLabel.setText(todaysDate); // display the date on Home Scene

        //set today's date in SQL format for future query reference
        dateFormat =  new SimpleDateFormat("yyyy-MM-dd");
        todaysDate  = dateFormat.format(currentDate);

    }
    public void displayUsersListOfUpcomingEvents() throws Exception{

        /*There is a possibility events (2-4) where never created by user
        so we set them blank from the start eventOne will always be set with a
        message informin the user of no upcoming Events */

        String eventOne;
        String eventTwo = " ";
        String eventThree = " ";
        String eventFour = " ";

        Date eventDateFormat;
        String eventDate;
        SimpleDateFormat shortDateFormat = new SimpleDateFormat("MM/dd/yyyy");
        int eventNumberCounter = 1;


        try {



            DatabaseConnection connectNow = new DatabaseConnection();
            Connection con = connectNow.getConnection();
            String eventQuery = "SELECT due_date,concat(activity_type,': ',activity_name) FROM student_activity \n" +
                    "WHERE student_id = '" + LoginSceneController.getStudentId() + "'AND due_date >= '" +
                    todaysDate +"' ORDER BY due_date Limit 4;";

            ResultSet result = con.createStatement().executeQuery(eventQuery);

            eventOne = "No upcoming events at this time."; //default message if no events have been scheduled

            while (result.next()) {
                if (eventNumberCounter == 1) {
                    eventDateFormat = result.getDate("due_date");
                    eventDate = shortDateFormat.format(eventDateFormat);
                    eventOne = eventDate + "   "  + result.getString("concat(activity_type,': ',activity_name)");
                }
                if (eventNumberCounter == 2) {
                    eventDateFormat = result.getDate("due_date");
                    eventDate = shortDateFormat.format(eventDateFormat);
                    eventTwo = eventDate + "   "  + result.getString("concat(activity_type,': ',activity_name)");
                }
                if (eventNumberCounter == 3) {
                    eventDateFormat = result.getDate("due_date");
                    eventDate = shortDateFormat.format(eventDateFormat);
                    eventThree = eventDate + "   "  + result.getString("concat(activity_type,': ',activity_name)");
                }
                if (eventNumberCounter == 4) {
                    eventDateFormat = result.getDate("due_date");
                    eventDate = shortDateFormat.format(eventDateFormat);
                    eventFour = eventDate + "   "  + result.getString("concat(activity_type,': ',activity_name)");
                }
                eventNumberCounter++;
            }

            eventOneLabel.setText(eventOne);
            eventTwoLabel.setText(eventTwo);
            eventThreeLabel.setText(eventThree);
            eventFourLabel.setText(eventFour);

        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
            Logger.getLogger(PlannerHomeSceneController.class.getName()).log(Level.SEVERE, null, e);
        }

    }




}
