package com.digitalhomeland.storemanager.models;

/**
 * Created by Asus on 2/6/2018.
 */

public class Tasko {
    String id;
    String title;
    String subject;
    String empId;
    String hours;
    String minutes;
    String dateToSet;
    String acceptedAt;

    public Tasko(String title, String subject, String empId, String hours, String minutes, String dateToSet){
        this.title = title;
        this.subject = subject;
        this.empId = empId;
        this.hours = hours;
        this.minutes = minutes;
        this.dateToSet = dateToSet;
        this.acceptedAt = "";
    }

    public Tasko(String id,String title, String subject, String empId, String hours, String minutes, String dateToSet){
        this.id = id;
        this.title = title;
        this.subject = subject;
        this.empId = empId;
        this.hours = hours;
        this.minutes = minutes;
        this.dateToSet = dateToSet;
        this.acceptedAt = "";
    }

    public String getTitle() {
        return title;
    }

    public String getId() {
        return id;
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

    public String getAcceptedAt() {
        return acceptedAt;
    }

    public String getDateToSet() {
        return dateToSet;
    }
}
