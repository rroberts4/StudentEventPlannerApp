///////////////////////////////////////////////////////////////////////////////
//
// Title:           PlannerApp
// Main Class File: PlannerApp.Main.java
// File:            Event.java
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

public class Event {


    String eventName;
    String eventType;
    String eventDate;







    public Event(String name, String type, String date) {
        this.eventName = name;
        this.eventType = type;
        this.eventDate = date;


    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }
}

