package com.digitalhomeland.storemanager.models;

/**
 * Created by Asus on 2/6/2018.
 */

public class Taskw {
    String title;
    String subject;
    String empId;
    String hours;
    String minutes;
    String dayOfWeek;

    public Taskw( String title, String subject,String empId, String hours, String minutes, String dayOfWeek){
        this.title = title;
        this.subject = subject;
        this.empId = empId;
        this.hours = hours;
        this.minutes = minutes;
        this.dayOfWeek = dayOfWeek;
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
}
