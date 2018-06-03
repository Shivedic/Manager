package com.digitalhomeland.storemanager.models;

/**
 * Created by Asus on 2/3/2018.
 */

public class Employee {
    String _id;
    String firstName;
    String middleName;
    String lastName;
    String phone;
    String email;
    String aadharId;
    String employeeId;
    String isManager;
    String teamName;
    String designation;
    String managerId;


    public Employee(String _id, String firstName, String middleName, String lastName, String phone, String email,String aadharId, String employeeId, String isManger, String teamName, String designation, String managerId)
    {
        this._id = _id;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.aadharId = aadharId;
        this.employeeId = employeeId;
        this.isManager = isManger;
        this.teamName = teamName;
        this.designation = designation;
        this.managerId = managerId;
    }

    public Employee(User user){
        this._id = user.get_id();
        this.firstName = user.firstName;
        this.middleName = user.middleName;
        this.lastName = user.getLastName();
        this.phone = user.phone;
        this.email = user.email;
        this.aadharId = user.aadharId;
        this.employeeId = user.employeeId;
        this.isManager = "true";
        this.teamName = user.teamName;
        this.designation = user.designation;
        this.managerId = user.getEmployeeId();
    }

    public String get_id(){return _id;}
    public String getFirstName(){return  firstName;}
    public String getMiddleName() {return middleName;}
    public String getLastName() {return lastName;}
    public String getPhone() {return phone;}
    public String getEmail() {return email;}
    public String getAadharId() {return aadharId;}
    public String getEmployeeId() { return employeeId;}

    public String getIsManager() {
        return isManager;
    }

    public String getTeamName() {
        return teamName;
    }

    public String getDesignation() {
        return designation;
    }

    public String getManagerId() {
        return managerId;
    }
}
