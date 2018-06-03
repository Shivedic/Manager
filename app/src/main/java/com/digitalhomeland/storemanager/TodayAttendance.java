package com.digitalhomeland.storemanager;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.digitalhomeland.storemanager.models.EmpRoster;
import com.digitalhomeland.storemanager.models.Employee;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class TodayAttendance extends AppCompatActivity {
    public static ArrayList<Employee> empList = new ArrayList<Employee>();
    public static Context mContext;
    private static Volley_Request postRequest;
    static DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today_attendance);
        getWindow().setBackgroundDrawableResource(R.drawable.btr);
        db = new DatabaseHandler(this);
        empList = db.getAllEmployees();
        mContext = this;
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df2 = new SimpleDateFormat("dd/MM/yyyy");
        String date = df2.format(c.getTime());
        ListView listView = (ListView)findViewById(R.id.today_attendance_list_view);
        AttTodayListAdapter adapter = new AttTodayListAdapter(getApplicationContext(), empList, date, this);
        listView.setAdapter(adapter);

        Button getStatus = (Button) findViewById(R.id.get_status);
        getStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String request = "{\"reciever\":\"getAttStatus\", \"storeId\" : \"" + db.getUser().getStoreId() + "\",\"date\":\"" + Utils.getCurrentDate() + "\",\"employees\":[";
                for (Employee emp:
                     db.getAllEmployees()) {
                    request += "\"" + emp.getEmployeeId() + "\",";
                }
                request = request.substring(0, request.length() - 1);
                request += "]}";
                //+ db.getEmployeeCount() + "\"}";
                postRequest = new Volley_Request();
                postRequest.createRequest(mContext, mContext.getResources().getString(R.string.mJSONURL_emproster), "POST", "checkAttStatus", request);
            }
        });
    }

    public static void attStatusResponse(String response){
        try{
            JSONObject respObj = new JSONObject(response);
            JSONArray responseArr = respObj.getJSONArray("empr");
            for(int i=0; i<responseArr.length(); i++) {
                JSONObject responseObj = responseArr.getJSONObject(i);
                if (responseObj.has("checkIn")) {
                    db.updateCheckIn(responseObj.get("empId").toString(),responseObj.get("date").toString(), responseObj.getString("checkIn"));
                db.getAllEmpRoster();
                }
                if(responseObj.has("checkOut")){
                    db.updateCheckOut(responseObj.get("empId").toString(),responseObj.get("date").toString(), responseObj.getString("checkOut"));
                }
                EmpRoster emr = db.getEmpRosterByDate(responseObj.get("date").toString(),responseObj.get("empId").toString());
                Log.d("myTag", "checkIn : " + emr.getCheckIn() + " : " + emr.getDate());
            }
        }catch(JSONException e){
            Log.d("myTag", "json error " ,e);
        }
    }
}
