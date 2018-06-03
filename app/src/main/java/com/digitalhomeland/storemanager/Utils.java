package com.digitalhomeland.storemanager;

import android.util.Log;

import com.digitalhomeland.storemanager.models.EmpRoster;
import com.digitalhomeland.storemanager.models.Employee;
import com.digitalhomeland.storemanager.models.StRoster;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import static com.digitalhomeland.storemanager.LandingActivity.db;

/**
 * Created by Asus on 3/19/2018.
 */

public class Utils {

    public static String getCurrentDate(){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df2 = new SimpleDateFormat("dd/MM/yyyy");
        String currentDate = df2.format(c.getTime());
        return  currentDate;
    }

    public static String getCurrentTime(){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        String currentTime = df.format(c.getTime());
        return  currentTime;
    }

    public static void generateWeeklyRoster(){
        final Calendar cpres = Calendar.getInstance();
        final Calendar cfut = Calendar.getInstance();
        final Calendar cpast = Calendar.getInstance();
        cpast.add(Calendar.DAY_OF_WEEK, -7);
        db.deleteRoster();    // coo=mment out later
        if(db.getStRosterCount() == 0){
            cpres.add(Calendar.DAY_OF_WEEK, -1);
            for(int i = 0; i<7; i++){
                cpres.add(Calendar.DAY_OF_WEEK, 1);
                SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                String presDate = df.format(cpres.getTime());
                //StRoster rosterPast = db.getStoreRosterByDate(pastDate);
                StRoster rosterNew = new StRoster(Integer.toString(cpres.DAY_OF_WEEK), presDate);
                Log.d("myTag", "generating new for day : " + presDate);
                db.generateSTRoster(presDate, rosterNew);
                ArrayList<Employee> eList= db.getAllEmployees();
                for (Employee emp:eList
                        ) {
                    EmpRoster empRoster = new EmpRoster(Integer.toString(cpres.DAY_OF_WEEK), presDate, emp.getEmployeeId());
                    if(db.getApprovedAppli(presDate, emp.getEmployeeId()))
                    {
                        empRoster.setEmpStatus("Planned Leave");
                        db.generateEmpRoster(presDate, empRoster);
                    } else {
                        db.generateEmpRoster(presDate, empRoster);
                    }

                }

            /*
            for (Employee emp:
                 empList) {
                EmpRoster empRoster = new EmpRoster(Integer.toString(cpres.DAY_OF_WEEK), presDate, emp.getEmployeeId());
                db.generateEmpRoster(presDate, empRoster);
            }
            */
            }}
        else{
            for(int i = 0; i<7; i++){
                cpast.add(Calendar.DAY_OF_WEEK, i);
                SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                String pastDate = df.format(cpast.getTime());
                StRoster rosterPast = db.getSTRosterByDate(pastDate);

                cfut.add(Calendar.DAY_OF_WEEK, i);
                String futDate = df.format(cfut.getTime());
                Log.d("myTag", "generating for day : " + futDate);
                db.generateSTRoster(futDate, rosterPast);

                for (Employee emp:
                        db.getAllEmployees()) {
                    EmpRoster empRosterPast = db.getEmpRosterByDate(pastDate, emp.getEmployeeId());
                    db.generateEmpRoster(futDate, empRosterPast);
                }
            }
        }
    }
}
