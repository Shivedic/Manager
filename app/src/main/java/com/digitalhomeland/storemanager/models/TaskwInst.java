package com.digitalhomeland.storemanager.models;

/**
 * Created by Asus on 3/10/2018.
 */

public class TaskwInst {
    String title;
    String subject;
    String empId;
    String hours;
    String minutes;
    String dayOfWeek;
    String date;
    String acceptedAt;
    String _id;

    public TaskwInst( String id, String title, String subject,String empId, String hours, String minutes, String dayOfWeek, String date, String acceptedAt){
        this._id = id;
        this.title = title;
        this.subject = subject;
        this.empId = empId;
        this.hours = hours;
        this.minutes = minutes;
        this.dayOfWeek = dayOfWeek;
        this.date = date;
        this.acceptedAt = acceptedAt;
    }

    public String getTitle() {
        return title;
    }
    public String getSubject() {
        return subject;
    }
    public String getEmpId(){
        return empId;
    }
    public String getHours(){
        return hours;
    }
    public String getMinutes(){
        return minutes;
    }
    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public String getDate() {
        return date;
    }

    public String get_id() {
        return _id;
    }

    public String getAcceptedAt() {
        return acceptedAt;
    }
}

