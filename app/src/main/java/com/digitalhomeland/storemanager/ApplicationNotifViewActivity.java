package com.digitalhomeland.storemanager;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.digitalhomeland.storemanager.models.Application;
import com.digitalhomeland.storemanager.models.Employee;

public class ApplicationNotifViewActivity extends AppCompatActivity {

    TextView dateView, titleView, subjectView, acceptStatusTv, empVal;
    TableRow acceptRow;
    static String applicationId;
    String responseString, acceptStatus;
    Application application = null;
    static Activity mActivity;
    String mJSONURLString = "https://agile-wildwood-34684.herokuapp.com/api/applications";
    private static Volley_Request getRequest, postRequest;
    SharedPreferences pref;
    static DatabaseHandler db = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onNewIntent(getIntent());
        setContentView(R.layout.activity_application_notif_view);
        //set bkg
        getWindow().setBackgroundDrawableResource(R.drawable.btr);
        mActivity = ApplicationNotifViewActivity.this;
        dateView = (TextView) findViewById(R.id.notif_date_textview);
        titleView = (TextView) findViewById(R.id.notif_title_textview);
        subjectView = (TextView) findViewById(R.id.notif_subject_textview);
        acceptStatusTv = (TextView) findViewById(R.id.accept_status_textview);
        empVal = (TextView) findViewById(R.id.emp_textview);
        acceptRow = (TableRow) findViewById(R.id.accept_row);
        Button approveApplicationButton = (Button) findViewById(R.id.approve_application_button);
        Button rejectApplicationButton = (Button) findViewById(R.id.reject_application_button);
        db = new DatabaseHandler(this);
        Bundle bundle = getIntent().getExtras();
        applicationId = bundle.getString("applicationid");
        application = db.getApplicationById(applicationId);
        Log.d("myTag", "found appliid : " + applicationId);
     //   if (bundle.containsKey("acceptstatus"))
       //     acceptStatus = bundle.getString("acceptstatus");
        acceptStatus = application.getAcceptStatus();
        Log.d("myTag", "appnotifview:oncreate : acceptsatus " + application.getAcceptStatus());
        // Toast.makeText(getApplicationContext(), application.getAcceptStatus() , Toast.LENGTH_SHORT).show();


        if (acceptStatus.equals("false")) {
            Toast.makeText(getApplicationContext(), "Blank detected", Toast.LENGTH_SHORT);
            acceptRow.setVisibility(View.VISIBLE);
            acceptRow.setBackgroundColor(Color.parseColor("#c34242"));
            acceptStatusTv.setVisibility(View.VISIBLE);
            acceptStatusTv.setText("Rejected");
            approveApplicationButton.setVisibility(View.GONE);
            rejectApplicationButton.setVisibility(View.GONE);
        } else if(acceptStatus.equals("true")) {
            //Toast.makeText(getApplicationContext(), "Blank not detected", Toast.LENGTH_SHORT);
            acceptRow.setVisibility(View.VISIBLE);
            acceptRow.setBackgroundColor(Color.parseColor("#708238"));
            acceptStatusTv.setVisibility(View.VISIBLE);
            acceptStatusTv.setText("Accepted");
            approveApplicationButton.setVisibility(View.GONE);
            rejectApplicationButton.setVisibility(View.GONE);
        } else if(acceptStatus.equals("")){
            acceptRow.setVisibility(View.GONE);
            acceptStatusTv.setVisibility(View.GONE);
            approveApplicationButton.setVisibility(View.VISIBLE);
            rejectApplicationButton.setVisibility(View.VISIBLE);
        }


        if (getIntent().hasExtra("date") && getIntent().hasExtra("title") && getIntent().hasExtra("subject") && getIntent().hasExtra("acceptstatus") && getIntent().hasExtra("empId")) {
            dateView.setText(getIntent().getStringExtra("date"));
            titleView.setText(bundle.getString("title"));
            subjectView.setText(bundle.getString("subject"));
            Employee emp = db.getEmployeeById(bundle.getString("empId"));
            empVal.setText(emp.getFirstName() + " - " + emp.getEmployeeId());
        }

        approveApplicationButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                responseString = "{\"reciever\":\"SMResponse\",\"applicationid" + "\":\"" + applicationId + "\",\"accepted\":\"true\"}";
                Log.d("myTag", "appliTeacherResponseRequest : " + responseString);
                postRequest = new Volley_Request();
                postRequest.createRequest(ApplicationNotifViewActivity.this, getResources().getString(R.string.mJSONURL_appli), "POST", "ResponseApproveSM", responseString);
            }
        });

        rejectApplicationButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                responseString = "{\"reciever\":\"SMResponse\",\"applicationid\":\"" + applicationId + "\",\"accepted\":\"false\"}";
                Log.d("myTag", "appliTeacherResponseRequest : " + responseString);
                postRequest = new Volley_Request();
                postRequest.createRequest(ApplicationNotifViewActivity.this, getResources().getString(R.string.mJSONURL_appli), "POST", "ResponseRejectSM", responseString);
            }
        });
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

    public static void responseAcceptString(String responseString) {
        db.updateApplicationAcceptStatus(applicationId, "true");
        //send Response
        Intent i = new Intent(mActivity, DashboardActivity.class);
        mActivity.startActivity(i);
    }

    public static void responseRejectString(String responseString) {
        db.updateApplicationAcceptStatus(applicationId, "false");
        //send Response
        Intent i = new Intent(mActivity, DashboardActivity.class);
        mActivity.startActivity(i);
    }

    @Override
    public void onNewIntent(Intent intent){
        Bundle extras = intent.getExtras();
        if(extras != null){
            if(extras.containsKey("title"))
            {
                Log.d("myTag", "title obtained : " + extras.get("title"));
                //setContentView(R.layout.viewmain);
                // extract the extra-data in the Notification
                //String msg = extras.getString("NotificationMessage");
                //txtView = (TextView) findViewById(R.id.txtMessage);
                //txtView.setText(msg);
            }
        }
    }

}
