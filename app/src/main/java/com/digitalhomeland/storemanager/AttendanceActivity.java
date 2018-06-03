package com.digitalhomeland.storemanager;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.digitalhomeland.storemanager.models.EmpRoster;
import com.digitalhomeland.storemanager.models.Employee;
import com.digitalhomeland.storemanager.models.StRoster;
import com.digitalhomeland.storemanager.models.Taskd;
import com.digitalhomeland.storemanager.models.TaskdInst;
import com.digitalhomeland.storemanager.models.Tasko;
import com.digitalhomeland.storemanager.models.Taskw;
import com.digitalhomeland.storemanager.models.TaskwInst;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AttendanceActivity extends AppCompatActivity implements LocationListener {

    private int progressStatus = 0;
    private Handler handler = new Handler();
    LocationManager locationManager;
    static Location testLoc;
    static Address testAddress;
    static String testAddressString="";
    static DatabaseHandler db;
    private static Volley_Request postRequest;
    static Context mContext;
    String action;
    static String currentDate;
    static ArrayList<Employee> empList = new ArrayList<>();
    String req = "{\"reciever\":\"add\",\"params\":{\"taskds\":[";

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
        db = new DatabaseHandler(this);
        mContext = this;
        Bundle bundle = getIntent().getExtras();
        action = bundle.getString("action");
        final LinearLayout rl = (LinearLayout) findViewById(R.id.r1);
        final Button btn = (Button) findViewById(R.id.btn);
        final TextView tv = (TextView) findViewById(R.id.tv);
        final ProgressBar pb = (ProgressBar) findViewById(R.id.pb);

        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 101);

        }

        empList = db.getAllEmployees();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Set the progress status zero on each button click
                progressStatus = 0;
                getLocation();
                // Start the lengthy operation in a background thread
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while(progressStatus < 100){
                            // Update the progress status
                            progressStatus +=1;

                            // Try to sleep the thread for 20 milliseconds
                            try{
                                Thread.sleep(30);
                            }catch(InterruptedException e){
                                e.printStackTrace();
                            }

                            // Update the progress bar
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    pb.setProgress(progressStatus);
                                    // Show the progress on TextView
                                    tv.setText(progressStatus+"");
                                    // If task execution completed
                                    if(progressStatus == 100 && !(testAddressString.equals(""))){
                                        // Set a message of completion
                                        tv.setText(testAddressString);
                                        Log.d("myTag","matching addr : " + testAddressString + " : " + db.getStore().getAddress());
                                        /*
                                        if(db.getStore().getAddress().equals(testAddressString)){
                                         String req = "{\"reciever\":\"add\", \"params\" : {\"taskds\":[";
                                            ArrayList<Taskd> tl = db.getAllTaskd(0,0);
                                            for (Taskd tsk:tl
                                                 ) {
                                                req += "{\"title\":\"" + tsk.getTitle() + "\",\"subject\":\""+ tsk.getSubject() +"\",\"empId\":\""+ tsk.getEmpId() +"\",\"storeId\":\"" + db.getStore().getStoreId() + "\",\"hours\":\""+ tsk.getHours() +"\",\"minutes\":\"" + tsk.getMinutes() + "\"},";
                                            }
                                            req = req.substring(0, req.length()-1);
                                            req += "]}}";
                                            postRequest = new Volley_Request();
                                            postRequest.createRequest(AttendanceActivity.this, getResources().getString(R.string.mJSONURL_taskd), "POST", "addTaskDaily", req);
                                        }
                                        */
                                    }
                                }
                            });
                        }
                    }
                }).start(); // Start the operation
            }
        });
    }

    void getLocation() {
        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 5, (LocationListener) this);
        }
        catch(SecurityException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        //  locationText.setText("Latitude: " + location.getLatitude() + "\n Longitude: " + location.getLongitude());
        testLoc = location;
        try {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
      //      Log.d("myTag","Latitude: " + location.getLatitude() + "\n Longitude: " + location.getLongitude() + "\n" + addresses.get(0).getAddressLine(0)+" : "+
        //            addresses.get(0).getAddressLine(1)+" : "+addresses.get(0).getAddressLine(2) );
            testAddress = addresses.get(0);
            testAddressString = addresses.get(0).getAddressLine(0);
    //        Log.d("myTag", "matching : " + Double.parseDouble(db.getStore().getLat()) * 1.00 + " : " + location.getLatitude()*1.00);
            if((((location.getLatitude()*1.00)-1 <= (Double.parseDouble(db.getStore().getLat()) * 1.00)) || ((location.getLatitude()*1.00)-1 >= (Double.parseDouble(db.getStore().getLat()) * 1.00))) && (((location.getLongitude()*1.00)-1 <= (Double.parseDouble(db.getStore().getLongi()) * 1.00)) || ((location.getLongitude()*1.00)-1 >= (Double.parseDouble(db.getStore().getLongi()) * 1.00)))) {
                Calendar c = Calendar.getInstance();
                Calendar cfut = Calendar.getInstance();
                Calendar cpast = Calendar.getInstance();
                System.out.println("Current time => " + c.getTime());
                int dayOfW = c.DAY_OF_WEEK;
                SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy hh:mm");
                SimpleDateFormat df2 = new SimpleDateFormat("dd/MM/yyyy");
                String currentDateTime = df.format(c.getTime());
                currentDate = df2.format(c.getTime());
                String req = "";
                if(action.equals("CheckIn")){
                    req = "{\"reciever\":\"CheckIn\",\"params\":{\"storeId\":\"" + db.getStore().getStoreId() + "\",\"empId\":\"" + db.getUser().getEmployeeId() + "\",\"date\":\"" + currentDate +"\",\"checkIn\":\"" + currentDateTime + "\"}}";
                    postRequest = new Volley_Request();
                    if (req != "") {
                        postRequest.createRequest(mContext, getResources().getString(R.string.mJSONURL_emproster), "POST", "CheckIn", req);
                    }
                }
                else if(action.equals("CheckOut")) {
                    req = "{\"reciever\":\"CheckOut\",\"params\":{\"storeId\":\"" + db.getStore().getStoreId() + "\",\"empId\":\"" + db.getUser().getEmployeeId() + "\",\"date\":\"" + currentDate + "\",\"checkOut\":\"" + currentDateTime + "\"}}";
                    postRequest = new Volley_Request();
                    if (req != "") {
                        postRequest.createRequest(mContext, getResources().getString(R.string.mJSONURL_emproster), "POST", "CheckOut", req);
                    }
                }
                }
            locationManager.removeUpdates((LocationListener) this);
            locationManager = null;
        }catch(Exception e){
                Log.d("myTag", "err : ", e);

        }
    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(AttendanceActivity.this, "Please Enable GPS and Internet", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    public static void checkInResponse(String response){
        String req = "{\"reciever\":\"add\", \"params\" : {\"taskds\":[";
        ArrayList<Taskd> tl = db.getAllTaskd(0,0);
        for (Taskd tsk:tl
                ) {
            req += "{\"title\":\"" + tsk.getTitle() + "\",\"subject\":\""+ tsk.getSubject() +"\",\"empId\":\""+ tsk.getEmpId() +"\",\"storeId\":\"" + db.getStore().getStoreId() + "\",\"date\":\""+ currentDate + "\",\"hours\":\""+ tsk.getHours() +"\",\"minutes\":\"" + tsk.getMinutes() + "\"},";
        }
        req = req.substring(0, req.length()-1);
        req += "]}}";
        postRequest = new Volley_Request();
        postRequest.createRequest(mContext, mContext.getResources().getString(R.string.mJSONURL_taskd), "POST", "addTaskDaily", req);
       }

    public static void checkOutResponse(String response){
        String req = "{\"reciever\":\"getPunchStatus\", \"params\" :{\"storeId\":\"" + db.getUser().getStoreId() + "\", \"date\":\"" + currentDate + "\"}}";
        postRequest = new Volley_Request();
        postRequest.createRequest(mContext, mContext.getResources().getString(R.string.mJSONURL_emproster), "POST", "getEMRPunchStatus", req);
    }

    public static void addTaskdResponse(String response){
        try{
            Log.d("myTag", "task added");
            Calendar c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("dd//MM//yyyy");
            String currentDate = df.format(c.getTime());
            JSONObject respObj = new JSONObject(response);
            JSONArray responseArr = respObj.getJSONArray("taskds");
            for(int i=0; i<responseArr.length(); i++) {
                JSONObject responseObj = responseArr.getJSONObject(i);
                TaskdInst taskInst = new TaskdInst(responseObj.getString("title"), responseObj.getString("subject"), responseObj.getString("empId"), responseObj.getString("hours"), responseObj.getString("minutes"), responseObj.getString("date"), "", "", responseObj.getString("id"));
                db.addTaskdInst(taskInst);
            }
            //new req taskw
            String req = "{\"reciever\":\"add\", \"params\" : {\"taskws\":[";
            String[] daysList = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
            ArrayList<Taskw> tl = db.getAllTaskw(daysList[1]);
            for (Taskw tsk:tl
                    ) {
                req += "{\"title\":\"" + tsk.getTitle() + "\",\"subject\":\""+ tsk.getSubject() +"\",\"empId\":\""+ tsk.getEmpId() +"\",\"storeId\":\"" + db.getStore().getStoreId() + "\",\"date\":\""+ currentDate + "\",\"hours\":\""+ tsk.getHours() +"\",\"minutes\":\"" + tsk.getMinutes() +"\",\"dayOfW\":\"" + tsk.getDayOfWeek() + "\"},";
            }
            req = req.substring(0, req.length()-1);
            req += "]}}";
            postRequest = new Volley_Request();
            postRequest.createRequest(mContext, mContext.getResources().getString(R.string.mJSONURL_taskw), "POST", "addTaskWeekly", req);
        }catch(JSONException e){
            Log.d("myTag", "json error " ,e);
        }
    }

    public static void addTaskwResponse(String response){
        try{
            Log.d("myTag", "task added");
            Calendar c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            String currentDate = df.format(c.getTime());
            JSONObject respObj = new JSONObject(response);
            JSONArray responseArr = respObj.getJSONArray("taskw");
            for(int i=0; i<responseArr.length(); i++) {
                JSONObject responseObj = responseArr.getJSONObject(i);
                TaskwInst taskInst = new TaskwInst(responseObj.getString("id"), responseObj.getString("title"), responseObj.getString("subject"), responseObj.getString("empId"), responseObj.getString("hours"), responseObj.getString("minutes"), responseObj.getString("dayOfW"), responseObj.getString("date"), "");
                db.addTaskwInst(taskInst);
            }

            String req = "{\"reciever\":\"add\", \"params\" : {\"taskos\":[";
            String[] days = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};

            //test date
            ArrayList<Tasko> tl = db.getAllTasko(currentDate);
            for (Tasko tsk:tl
                    ) {
                req += "{\"title\":\"" + tsk.getTitle() + "\",\"subject\":\""+ tsk.getSubject() +"\",\"empId\":\""+ tsk.getEmpId() +"\",\"storeId\":\"" + db.getStore().getStoreId()  + "\",\"hours\":\""+ tsk.getHours() +"\",\"minutes\":\"" + tsk.getMinutes() +"\",\"dateToSet\":\"" + tsk.getDateToSet() + "\"},";
            }
            req = req.substring(0, req.length()-1);
            req += "]}}";
            postRequest = new Volley_Request();
            postRequest.createRequest(mContext, mContext.getResources().getString(R.string.mJSONURL_tasko), "POST", "addTaskOnce", req);
        }catch(JSONException e){
            Log.d("myTag", "json error " ,e);
        }
    }

    public static void addTaskoResponse(String response){
        try{
            JSONObject respObj = new JSONObject(response);
            JSONArray responseArr = respObj.getJSONArray("taskos");
            for(int i=0; i<responseArr.length(); i++) {
                JSONObject responseObj = responseArr.getJSONObject(i);
                Tasko taskInst = new Tasko(responseObj.getString("id"), responseObj.getString("title"), responseObj.getString("subject"), responseObj.getString("empId"), responseObj.getString("hours"), responseObj.getString("minutes"), responseObj.getString("dateToSet"));
                db.updateTasko(taskInst);

            }
            Log.d("myTag", "Transaction Complete");
        }catch(JSONException e){
            Log.d("myTag", "json error " ,e);
        }
    }
}