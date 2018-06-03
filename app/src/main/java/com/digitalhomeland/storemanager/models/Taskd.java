package com.digitalhomeland.storemanager.models;

/**
 * Created by Asus on 2/6/2018.
 */

public class Taskd {
    String title;
    String subject;
    String empId;
    String hours;
    String minutes;
    String seqId;

    public Taskd(String title, String subject, String empId, String hours, String minutes){
        this.title = title;
        this.subject = subject;
        this.empId = empId;
        this.hours = hours;
        this.minutes = minutes;
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
}
