package com.digitalhomeland.storemanager;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.digitalhomeland.storemanager.models.EmpRoster;
import com.digitalhomeland.storemanager.models.Employee;
import com.digitalhomeland.storemanager.models.StRoster;
import com.digitalhomeland.storemanager.models.Taskd;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import cn.pedant.SweetAlert.SweetAlertDialog;
import spencerstudios.com.bungeelib.Bungee;

import static com.digitalhomeland.storemanager.LandingActivity.db;

public class RosterDasboard extends AppCompatActivity {

    static ArrayList<StRoster> strList = new ArrayList<>();
    static ArrayList<EmpRoster> emprList = new ArrayList<>();
    private static Volley_Request getRequest, postRequest;
    public static Context mContext;
    public static int j;
    static String currentDate;
    static boolean breakVal = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roster_dasboard);
        getWindow().setBackgroundDrawableResource(R.drawable.btr);
        mContext = this;
        final Calendar c = Calendar.getInstance();
        final Calendar cfut = Calendar.getInstance();
        final Calendar cpast = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());
        int dayOfW = c.DAY_OF_WEEK;
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        SimpleDateFormat df2 = new SimpleDateFormat("dd/MM/yyyy");
        String currentDateTime = df.format(c.getTime());
        currentDate = df2.format(c.getTime());

        Button updateRosterButton = (Button) findViewById(R.id.update_roster);
        updateRosterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //intent for Create Notification Page
                Intent i = new Intent(RosterDasboard.this, UpdateRoster.class);
                startActivity(i);
            }
        });

        Button generateRosterButton = (Button) findViewById(R.id.generate_roster);
        generateRosterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    generateWeeklyRoster(cfut, c , cpast);
            }
        });

        Button pushRosterButton = (Button) findViewById(R.id.push_roster);
        pushRosterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //intent for Create Notification Page
                final DatabaseHandler db = new DatabaseHandler(RosterDasboard.this);
                strList = db.getPushableSTR();
                emprList = db.getPushableER();
                breakVal = false;
                for (EmpRoster emr:
                     emprList) {
                    if(emr.getEmpStatus().equals("")){
                        new SweetAlertDialog(mContext, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Employee Roster Error")
                                .setContentText("EmpId : " + emr.getEmpId() + " for " + emr.getDate() +  " roster not set")
                                .setConfirmText("Let,s fix it!")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        sDialog.dismissWithAnimation();
                                        Intent i = new Intent(RosterDasboard.this, UpdateRoster.class);
                                        startActivity(i);
                                        Bungee.zoom(RosterDasboard.this);
                                    }
                                })
                                .show();
                        breakVal = true;
                       break;
                    }
                }
                if(!breakVal) {
                    String req = "{\"reciever\":\"add\", \"params\" : {\"SRList\":[";
                    for (StRoster sr : strList
                            ) {
                        // "SRList":[{"dayOfW":"Hindi","date":"Hin001","storeId":"pihu007","storeStatus":"I","events":"A","eventTitle":"pihu007","eventSubject":""
                        req += "{\"dayOfW\":\"" + sr.getDayOfw() + "\",\"date\":\"" + sr.getDate() + "\",\"storeId\":\"" + db.getUser().getStoreId() + "\",\"storeStatus\":\"" + sr.getStoreStatus() + "\",\"events\":\"" + sr.getEvents() + "\",\"eventTitle\":\"" + sr.getEventTitle() + "\",\"eventSubject\":\"" + sr.getEventSub() + "\"},";
                    }
                    req = req.substring(0, req.length() - 1);
                    req += "]}}";
                    postRequest = new Volley_Request();
                    postRequest.createRequest(mContext, mContext.getResources().getString(R.string.mJSONURL_stroster), "POST", "pushStRoster", req);
                }
            }
        });
    }

    public static void generateWeeklyRoster(Calendar cfut, Calendar cpres, Calendar cpast){
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


    public static void pushStrResponse(String response){
        try {
            JSONObject rosterResponse = new JSONObject(response);
            String success = rosterResponse.getString("success");
            if(success.equals("true")){
                for (StRoster str:strList
                     ) {
                    db.updateStRosterPushed(str);
                }
            }
            pushEmpRoster();
        }catch(Exception e){
            Log.d("myTag", "error : " + e, e);
            e.printStackTrace  ();
        }
        }

        public static void pushEmpRoster() {
                String req = "{\"reciever\":\"add\", \"params\" : {\"ERList\":[";
               for (EmpRoster er:emprList
                    ) {
                    // "SRList":[{"dayOfW":"Hindi","date":"Hin001","storeId":"pihu007","storeStatus":"I","events":"A","eventTitle":"pihu007","eventSubject":""
                    req += "{\"dayOfW\":\"" + er.getDayOfw() + "\",\"date\":\"" + er.getDate() + "\",\"storeId\":\"" + db.getUser().getStoreId() + "\",\"empId\":\"" + er.getEmpId() + "\",\"status\":\"" + er.getEmpStatus() + "\",\"shift\":\"" + er.getShift() + "\"},";
                }
                req = req.substring(0, req.length() - 1);
                req += "]}}";
                postRequest = new Volley_Request();
                postRequest.createRequest(mContext, mContext.getResources().getString(R.string.mJSONURL_emproster), "POST", "pushEmpRoster", req);

        }

    public static void pushEmrResponse(String response){
        try {
            JSONObject rosterResponse = new JSONObject(response);
            String success = rosterResponse.getString("success");
            if(success.equals("true")){
                for (EmpRoster etr:emprList
                     ) {
                    db.updateEmpRosterPushed(etr);
                }
            }
        }catch(Exception e){
            Log.d("myTag", "error : " + e, e);
            e.printStackTrace();
        }
    }
}
