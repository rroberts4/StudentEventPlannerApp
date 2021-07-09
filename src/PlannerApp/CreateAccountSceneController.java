///////////////////////////////////////////////////////////////////////////////
//
// Title:           PlannerApp
// Main Class File: PlannerApp.Main.java
// File:            CreateAccountSceneController.java
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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class CreateAccountSceneController {
    @FXML
    private Button createAccountButton, returnButton;
    @FXML
    private Label messageLabel, exitLabel;

    @FXML
    private TextField usernameTextField, firstNameTextField, lastNameTextField;
    @FXML
    private PasswordField passwordTextField;


    public void createAccountButtonAction(javafx.event.ActionEvent event) {

        if(checkIfStudentsUserInputIsEmpty()){
          return;
      } else{
          checkIfStudentsUsernameIsUnique();
      }
    }
    public boolean checkIfStudentsUserInputIsEmpty(){

        if(usernameTextField.getText().isEmpty() || passwordTextField.getText().isEmpty()){
            messageLabel.setText("Please enter username and pasword!");
            return true;

        } else if(firstNameTextField.getText().isEmpty() || lastNameTextField.getText().isEmpty()){
            messageLabel.setText("Please enter your first and last name!");
            return true;

        } else{
            return false;
        }

    }

    public void checkIfStudentsUsernameIsUnique(){

        // method checks if the username entered is unique, if it is creates a new account for user

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection con = connectNow.getConnection();

        String verifyUsername = "SELECT count(1) FROM user_student WHERE username = '" + usernameTextField.getText() + "';";

        try{

            Statement statement = con.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyUsername);



            while(queryResult.next()){

                if(queryResult.getInt("count(1)") == 1){
                    messageLabel.setText("Username entered already exists. Please select another one!");
                    return;




                }else{
                    statement.close();

                    createNewStudentAccount();
                }

            }


        } catch(Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }
    public void exitLabelClicked() {
        Stage stage = (Stage) exitLabel.getScene().getWindow();
        stage.close();
    }

    public void createNewStudentAccount(){
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection con = connectNow.getConnection();

        String username = usernameTextField.getText();
        String name = firstNameTextField.getText() + " " + lastNameTextField.getText();
        String password = passwordTextField.getText();

        //Query uses all obtain info to create new Student User for application
        String insertField = "INSERT INTO user_student (student_name,username,password) Values('";

        String insertValues = name + "','" + username + "','" + password + "');";

        String studentInsertedValues = insertField + insertValues;

        try {
            Statement statement = con.createStatement();
            statement.executeUpdate(studentInsertedValues);

            //returns User to Login Page

            Parent root = FXMLLoader.load(getClass().getResource("LoginScene.fxml"));
            Stage window = (Stage) createAccountButton.getScene().getWindow();
            window.setScene(new Scene(root, 600, 400));


        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();

        }

    }
    public void returnButtonAction() throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("LoginScene.fxml"));
        Stage window = (Stage) returnButton.getScene().getWindow();
        window.setScene(new Scene(root, 600, 400));

    }



}
