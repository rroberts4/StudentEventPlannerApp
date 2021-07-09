///////////////////////////////////////////////////////////////////////////////
//
// Title:           PlannerApp
// Main Class File: PlannerApp.Main.java
// File:            LoginSceneController.java
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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class LoginSceneController {
    @FXML
    private Button loginButton, createAccountButton;
    @FXML
    private Label messageLabel, exitLabel;

    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordTextField;
    private static int studentId;
    private static String studentName;

    public void createAccountButtonAction(ActionEvent event)throws Exception{

        //sends user to create Account Scene

        Parent root = FXMLLoader.load(getClass().getResource("CreateAccountScene.fxml"));
        Stage window = (Stage) createAccountButton.getScene().getWindow();
        window.setScene(new Scene(root, 600, 400));


    }
    public void loginButtonAction(ActionEvent event)throws Exception{
       if(checkIfStudentUserInfoIsEmpty()){
           return;
       }
       checkIfUserLoginIsValid();

    }
    public boolean  checkIfStudentUserInfoIsEmpty(){
        if(!usernameTextField.getText().isEmpty() && !passwordTextField.getText().isEmpty()){
            return false;

        } else{
            messageLabel.setText("Please enter username and password!");
            return true;
        }

    }

    public void exitLabelClicked(){
        Stage stage = (Stage) exitLabel.getScene().getWindow();
        stage.close();
    }

    public void checkIfUserLoginIsValid() throws Exception{

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection con = connectNow.getConnection();

        String verifyLogin = "SELECT count(1), student_id, student_name FROM user_student WHERE username = '" + usernameTextField.getText() +"' AND password ='" + passwordTextField.getText()  +"'";

        try{

            Statement statement = con.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLogin);



            while(queryResult.next()){

                if(queryResult.getInt("count(1)") == 1){
                    // If Login is Valid store the user's name and student ID for future reference

                    setStudentId(queryResult.getInt("student_id"));
                    setStudentName(queryResult.getString("student_name"));




                }else{
                    //If Login not valid return with error Message

                    messageLabel.setText("Invalid Login. Please Try Again.");
                    return;
                }

            }


        } catch(Exception e){
            e.printStackTrace();
            e.getCause();
        }

        //Switch to Home Screen after sucessful Login

        Parent root = FXMLLoader.load(getClass().getResource("PlannerHomeScene.fxml"));
        Stage window = (Stage) loginButton.getScene().getWindow();
        window.setScene(new Scene(root, 800, 600));
    }



    public static int getStudentId() {
        return studentId;
    }

    public static void setStudentId(int studentId) {
        LoginSceneController.studentId = studentId;
    }

    public static String getStudentName() {
        return studentName;
    }

    public static void setStudentName(String studentName) {
        LoginSceneController.studentName = studentName;
    }
}
