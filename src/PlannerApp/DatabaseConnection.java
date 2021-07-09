///////////////////////////////////////////////////////////////////////////////
//
// Title:           PlannerApp
// Main Class File: PlannerApp.Main.java
// File:            DataBaseConnection.java
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

import java.sql.Connection;
import java.sql.DriverManager;


public class DatabaseConnection {

    public Connection databaseLink;
    public Connection getConnection(){
        String databaseName = "student_planner";
        String databaseUser = "root";
        String databasePassword = "batman24";
        String url = "jdbc:mysql://localhost/" + databaseName;

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            databaseLink = DriverManager.getConnection(url,databaseUser,databasePassword);
        } catch(Exception e) {
            e.printStackTrace();
            e.getCause();
        }
        return databaseLink;
    }
}
