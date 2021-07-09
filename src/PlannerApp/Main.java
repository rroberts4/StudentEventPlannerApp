///////////////////////////////////////////////////////////////////////////////
//
// Title:           PlannerApp
// Main Class File: PlannerApp.Main.java
// File:            Main.java
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
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import static javafx.fxml.FXMLLoader.load;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = load(getClass().getResource("LoginScene.fxml"));
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();

    }



    public static void main(String[] args) {
        launch(args);
    }
}
