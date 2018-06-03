package com.digitalhomeland.storemanager;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.digitalhomeland.storemanager.models.Application;
import com.digitalhomeland.storemanager.models.Employee;
import com.digitalhomeland.storemanager.models.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;
import spencerstudios.com.bungeelib.Bungee;

import static com.digitalhomeland.storemanager.LandingActivity.db;

public class DashboardActivity extends AppCompatActivity implements LocationListener{

    LocationManager locationManager;
    private static Volley_Request getRequest, postRequest;
    static boolean flag = false;
    static Activity mActivity;
    static NotificationManager notificationManager;
    String buttonAction;
    static Context mContext;
    static String[] daysOfW = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        mActivity = DashboardActivity.this;
        db = new DatabaseHandler(this);
        //db.viewAllTaskw();
        //db.deleteEmp();
        db.deleteRoster();
        getWindow().setBackgroundDrawableResource(R.drawable.btr);
        Intent intent = getIntent();
        mContext = DashboardActivity.this;
        Calendar c = Calendar.getInstance();
        Log.d("myTag", "day of w : " + c.DAY_OF_WEEK );
        String req = "{\"reciever\":\"empGetManager\",\"params\":{\"storeId\":\"" + db.getStore().getStoreId() + "\",\"managerId\":\"" + db.getUser().getEmployeeId() + "\"}}";
        postRequest = new Volley_Request();
        postRequest.createRequest(DashboardActivity.this, getResources().getString(R.string.mJSONURL_employee), "POST", "getEmployees", req);

        String move = intent.getStringExtra("move");
        if(move != null && move.equals("front")) {
            String req1 = "{\"reciever\":\"empGetManager\",\"params\":{\"storeId\":\"" + db.getStore().getStoreId() + "\",\"managerId\":\"" + db.getUser().getEmployeeId() + "\"}}";
            postRequest = new Volley_Request();
            postRequest.createRequest(DashboardActivity.this, getResources().getString(R.string.mJSONURL_employee), "POST", "getEmployees", req1);
        }

        String action = intent.getStringExtra("action");
        if(action != null && !action.equals("back")) {
            //if (db.getEmployeeCount() != ((Integer.parseInt(db.getStore().getEmpCount()) - 1))) {
                String req2 = "{\"reciever\":\"empGetManager\",\"params\":{\"storeId\":\"" + db.getStore().getStoreId() + "\",\"managerId\":\"" + db.getUser().getEmployeeId() + "\"}}";
                postRequest = new Volley_Request();
                postRequest.createRequest(DashboardActivity.this, getResources().getString(R.string.mJSONURL_employee), "POST", "getEmployees", req2);
            //}
        }

        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 101);

        }

        if(db.getSTRosterByDate(Utils.getCurrentDate()) != null)
        {
            if(!db.getSTRosterByDate(Utils.getCurrentDate()).getStoreStatus().equals("Closed") && db.getEmpRosterByDate(Utils.getCurrentDate(), db.getUser().getEmployeeId()) != null && db.getStore().getRosterGenDay().equals(daysOfW[c.DAY_OF_WEEK-1])){
                new SweetAlertDialog(mContext, SweetAlertDialog.NORMAL_TYPE)
                        .setTitleText("Roster Generation Time")
                        .setContentText("Please update and push roster before checkout")
                        .setConfirmText("Let,s do it!")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                Utils.generateWeeklyRoster();
                                sDialog.dismissWithAnimation();
                            }
                        })
                        .show();
            }
        }

        Button syncButton = (Button) findViewById(R.id.sync_button);
        syncButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Application sync
                //db.removeAllApplications(); //not part of live server
                String applicationRequest = "{\"reciever\":\"SMsync\",\"params\":{\"storeId\":\"" + db.getStore().getStoreId() +"\"}}";
                Log.d("myTag", "applicationRequest : " + applicationRequest);
                postRequest = new Volley_Request();
                postRequest.createRequest(DashboardActivity.this, getResources().getString(R.string.mJSONURL_appli), "POST", "SyncActivitySM",applicationRequest);
            }
        });

        // parent appli button
        Button ApplicationButton = (Button) findViewById(R.id.appli_dashb);
        ApplicationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //intent for Take Attendance Page
                Intent i = new Intent(DashboardActivity.this, AppliDashboard.class);
                startActivity(i);
                Bungee.zoom(DashboardActivity.this);
            }
        });

        Button attendance = (Button) findViewById(R.id.attendance_dashb);
        attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DashboardActivity.this, AttendanceDashboard.class);
                startActivity(i);
                Bungee.zoom(DashboardActivity.this);
            }
        });

        Button Task = (Button) findViewById(R.id.task_dashb);
        Task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DashboardActivity.this, TaskDashboard.class);
                startActivity(i);
                Bungee.zoom(DashboardActivity.this);
            }
        });

        // parent  notif button
        Button NotificationButton = (Button) findViewById(R.id.notif_dashb);
        NotificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //intent for Create Notification Page
                Intent i = new Intent(DashboardActivity.this, NotifDashboard.class);
                startActivity(i);
                Bungee.zoom(DashboardActivity.this);
            }
        });

        Button RosterButton = (Button) findViewById(R.id.roster_dashboard);
        RosterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //intent for Create Notification Page
                Intent i = new Intent(DashboardActivity.this, RosterDasboard.class);
                startActivity(i);
                Bungee.zoom(DashboardActivity.this);
            }
        });

        Button storeButton = (Button) findViewById(R.id.store_dashb);
        storeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);

                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"jha.akash10@gmail.com"});
                intent.putExtra(Intent.EXTRA_SUBJECT, "Sample subject");
                intent.putExtra(Intent.EXTRA_TEXT, "What the hell");

                intent.setType("message/rfc822");

                startActivity(Intent.createChooser(intent, "Select Email Sending App :"));

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

        try {
            if(!flag) {
                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                Log.d("myTag", "Latitude: " + location.getLatitude() + "\n Longitude: " + location.getLongitude() + "\n" + addresses.get(0).getAddressLine(0) + " : " +
                        addresses.get(0).getAddressLine(1) + " : " + addresses.get(0).getAddressLine(2));
                String req = "{\"reciever\":\"updateLoc\", \"params\" : {\"storeId\":\"" + db.getStore().getStoreId() + "\",\"lat\":\"" + location.getLatitude() + "\",\"longi\":\"" + location.getLongitude() + "\",\"address\":\"" + addresses.get(0).getAddressLine(0) + "\"}}";
                postRequest = new Volley_Request();
                postRequest.createRequest(DashboardActivity.this, getResources().getString(R.string.mJSONURL_store), "POST", "updateLocation", req);
                //    locationText.setText(locationText.getText() + "\n"+addresses.get(0).getAddressLine(0)+", "+
                //    addresses.get(0).getAddressLine(1)+", "+addresses.get(0).getAddressLine(2));
            flag = true;
            }
        }catch(Exception e)
        {

        }
    }

    public static void syncSMResponse(String responseString){
        try{
            JSONObject responseObj = new JSONObject(responseString);
            JSONArray responseArr = responseObj.getJSONArray("applications");
            for(int i=0; i<responseArr.length(); i++){
                JSONObject applicationObject = responseArr.getJSONObject(i);
                Application application = new Application(applicationObject.getString("id"),applicationObject.getString("title"),applicationObject.getString("subject"),applicationObject.getString("date"),applicationObject.getString("empId"),applicationObject.getString("seqId"));
                Log.d("myTag", "sync:addingApplication: " + application.getTitle() + " : " + application.getSubject());
                db.addApplications(application);
                showApplicationNotification(applicationObject.getString("date"),applicationObject.getString("title"),applicationObject.getString("subject"), applicationObject.getString("id"),applicationObject.getString("empId"),application.getAcceptStatus() ,(i+1)*(i+1)*7);
            }
        }catch(JSONException e){
            Log.d("myTag", "json error " ,e);
        }
    }

    public static void showApplicationNotif(Application application ,int id){
        Intent i = new Intent(mActivity, ApplicationNotifViewActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Log.d("myTag", "application to disp : " + application.getAcceptStatus());
        i.putExtra("date", application.getDate());
        i.putExtra("title", application.getTitle());
        i.putExtra("subject", application.getSubject());
        i.putExtra("applicationid", application.getId());
        i.putExtra("acceptstatus", application.getAcceptStatus());
        PendingIntent pi = PendingIntent.getActivity(mActivity, 0, i, 0);
        //Resources r = getResources();
        Notification notification = new NotificationCompat.Builder(mActivity)
                .setTicker(application.getAcceptStatus())
                .setSmallIcon(android.R.drawable.ic_menu_report_image)
                .setContentTitle(application.getTitle())
                .setContentText(application.getSubject())
                .setContentIntent(pi)
                .setAutoCancel(true)
                .build();

        NotificationManager notificationManager = (NotificationManager) mActivity.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(id, notification);
    }


    public static void getEmpResponse(String input){
        try {
            db.deleteEmp();
            // Add man to emp
            //{
            User manager = db.getUser();
            Employee manEmp = new Employee(manager);
            db.addEmployees(manEmp);
            //}
            JSONObject empO = new JSONObject(input);
            JSONArray empArr = empO.getJSONArray("employees");
            ArrayList<Employee> empList = db.getAllEmployees();
            ArrayList<String> empIdList = new ArrayList<>();
            for(int i=0; i< empList.size(); i++){
                Log.d("myTag", "emp Id : " + empList.get(i).getEmployeeId());
                empIdList.add(empList.get(i).getEmployeeId());
            }
            for(int i=0; i<empArr.length(); i++){
                JSONObject tempEmp = (JSONObject) empArr.get(i);
                if(!empIdList.contains(tempEmp.get("employeeId"))) {
                    Employee employee = new Employee(tempEmp.getString("_id"),tempEmp.getString("firstName"),tempEmp.getString("middleName"),tempEmp.getString("lastName"),tempEmp.getString("phone"),tempEmp.getString("email"),tempEmp.getString("aadharId"),tempEmp.getString("employeeId"),tempEmp.getString("isManager"),tempEmp.getString("teamName"),tempEmp.getString("designation"),tempEmp.getString("managerId"));
                    db.addEmployees(employee);
                }
                Log.d("myTag", "empcount : " + db.getEmployeeCount());
            }
        }catch(JSONException e){
            Log.d("myTag", "error : " + e, e);
            e.printStackTrace();}
    }

    public static void showApplicationNotification(String date, String title, String subject, String applicationId, String empId, String applicationStatus ,int id){
        Log.d("myTag","inside shownotif");
        Intent i = new Intent(mActivity, ApplicationNotifViewActivity.class);
        //.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.putExtra("acceptstatus", applicationStatus);
        i.putExtra("date", date);
        i.putExtra("title", title);
        i.putExtra("subject", subject);
        i.putExtra("empId", empId);
        Log.d("myTag", "adding appli : " + applicationId + " : " + applicationStatus);
        i.putExtra("applicationid", applicationId);
        PendingIntent pi = PendingIntent.getActivity(mActivity, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
        Resources r = (Resources) mActivity.getResources();
        Notification notification = new NotificationCompat.Builder(mActivity)
                .setTicker(r.getString(R.string.notification_title))
                .setSmallIcon(android.R.drawable.ic_menu_report_image)
                .setContentTitle(title)
                .setContentText(subject)
                .setContentIntent(pi)
                .setAutoCancel(true)
                .build();

        notificationManager = (NotificationManager) mActivity.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(id, notification);
    }


    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(DashboardActivity.this, "Please Enable Internet and Location", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // action with ID action_refresh was selected
            case R.id.action_refresh:
                Toast.makeText(this, "Refresh selected", Toast.LENGTH_SHORT)
                        .show();
                break;
            // action with ID action_settings was selected
            case R.id.action_settings:
                Toast.makeText(this, "Settings selected", Toast.LENGTH_SHORT)
                        .show();
                break;
            default:
                break;
        }

        return true;
    }

    @Override
    public void onBackPressed() {
        // Do Here what ever you want do on back press;
    }
}
