package com.digitalhomeland.storemanager.models;

/**
 * Created by Asus on 3/5/2018.
 */

public class EmpRoster {
    String id;
    String dayOfw;
    String date;
    String empId;
    String empStatus;
    String shift;
    String checkIn;
    String checkOut;
    String pushed;

    public EmpRoster(String dayOfw, String date, String empId){
        this.dayOfw = dayOfw;
        this.date = date;
        this.empStatus = "";
        this.shift = "";
        this.empId = empId;
        this.checkIn = "";
        this.checkOut = "";
        this.pushed = "";
    }

    public EmpRoster(String id, String dayOfw, String date, String empId, String empStatus, String shift, String pushed){
        this.id =id;
        this.dayOfw = dayOfw;
        this.date = date;
        this.empId = empId;
        this.empStatus = empStatus;
        this.shift = shift;
        this.checkIn = "";
        this.checkOut = "";
        this.pushed = pushed;
    }

    public EmpRoster(String id, String dayOfw, String date, String empId, String empStatus, String shift, String pushed, String checkIn, String checkOut){
        this.id =id;
        this.dayOfw = dayOfw;
        this.date = date;
        this.empId = empId;
        this.empStatus = empStatus;
        this.shift = shift;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.pushed = pushed;
    }

    public String getCheckIn() {
        return checkIn;
    }

    public String getCheckOut() {
        return checkOut;
    }

    public void setCheckIn(String checkIn) {
        this.checkIn = checkIn;
    }

    public void setCheckOut(String checkOut) {
        this.checkOut = checkOut;
    }

    public String getDayOfw() {
        return dayOfw;
    }

    public String getEmpStatus() {
        return empStatus;
    }

    public String getShift() {
        return shift;
    }

    public String getEmpId() {
        return empId;
    }

    public String getDate() {
        return date;
    }

    public String getPushed() {
        return pushed;
    }

    public void setEmpStatus(String empStatus) {
        this.empStatus = empStatus;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    public void setPushed(String pushed) {
        this.pushed = pushed;
    }
}
