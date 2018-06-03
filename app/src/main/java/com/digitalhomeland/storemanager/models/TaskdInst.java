package com.digitalhomeland.storemanager.models;

/**
 * Created by Asus on 2/10/2018.
 */

public class TaskdInst {
    String title;
    String subject;
    String empId;
    String hours;
    String minutes;
    String seqId;
    String date;
    String acceptedAt;
    String completedAt;
    String _id;

    public TaskdInst(String title, String subject, String empId, String hours, String minutes, String date, String acceptedAt, String completedAt, String _id){
        this.title = title;
        this.subject = subject;
        this.empId = empId;
        this.hours = hours;
        this.minutes = minutes;
        this.date = date;
        this.acceptedAt = acceptedAt;
        this.completedAt = completedAt;
        this._id = _id;
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

    public String getSeqId() {
        return seqId;
    }

    public String getDate() {
        return date;
    }

    public String getAcceptedAt() {
        return acceptedAt;
    }

    public String getCompletedAt() {
        return completedAt;
    }

    public String get_id() {
        return _id;
    }
}
