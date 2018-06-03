package com.digitalhomeland.storemanager;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.digitalhomeland.storemanager.models.TaskInst;
import com.digitalhomeland.storemanager.models.Taskd;
import com.digitalhomeland.storemanager.models.TaskdInst;
import com.digitalhomeland.storemanager.models.Tasko;
import com.digitalhomeland.storemanager.models.Taskw;
import com.digitalhomeland.storemanager.models.TaskwInst;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TaskPageViewActivity extends AppCompatActivity {
    TextView dateView, titleView, subjectView, acceptStatusTv;
    TableRow acceptStatusRow;
    static String taskId, type;
    String responseString, acceptStatus;
    static TaskdInst taskd = null;
    static TaskwInst taskw = null;
    static Tasko tasko = null;
    static TaskInst tsk = null;
    static String status = "";
    static Activity mActivity;
    String mJSONURLString = "https://agile-wildwood-34684.herokuapp.com/api/taskd";
    private static Volley_Request getRequest, postRequest;
    SharedPreferences pref;
    static DatabaseHandler db = null;
    static String compareTime = "";
    static String currentTime = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onNewIntent(getIntent());
        setContentView(R.layout.activity_task_page_view);
        //set bkg
        getWindow().setBackgroundDrawableResource(R.drawable.btr);
        mActivity = TaskPageViewActivity.this;
        acceptStatusRow = (TableRow) findViewById(R.id.accecpt_statustv_row);
        acceptStatusTv = (TextView) findViewById(R.id.accept_status_textview);
        dateView = (TextView) findViewById(R.id.notif_date_textview);
        titleView = (TextView) findViewById(R.id.notif_title_textview);
        subjectView = (TextView) findViewById(R.id.notif_subject_textview);
        Button approveApplicationButton = (Button) findViewById(R.id.approve_application_button);
        db = new DatabaseHandler(this);

        Bundle bundle = getIntent().getExtras();
        taskId = bundle.getString("taskdId");
        type = bundle.getString("type");
        if(type.equals("taskd")){
            taskd = db.getTaskdInstById(taskId);
            tsk = new TaskInst(taskd);}
        else if (type.equals("taskw")){
            taskw = db.getTaskwInstById(taskId);
            tsk = new TaskInst(taskd);
        }
        else if (type.equals("tasko")) {
            tasko = db.getTaskoById(taskId);
            tsk = new TaskInst(taskd);
        }

        String taskTime = tsk.getHours() + ":" + tsk.getMinutes();

        Calendar c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());
        SimpleDateFormat df = new SimpleDateFormat("hh:mm");
        compareTime = df.format(c.getTime());

        if(checktimings(taskTime, compareTime)){
            approveApplicationButton.setVisibility(View.INVISIBLE);
        } else {
            approveApplicationButton.setVisibility(View.VISIBLE);
        }

        Log.d("myTag", "found appliid : " + taskId);
        acceptStatus = tsk.getAcceptedAt();
        Log.d("myTag", "appnotifview:oncreate : acceptsatus " + acceptStatus);

        // Toast.makeText(getApplicationContext(), application.getAcceptStatus() , Toast.LENGTH_SHORT).show();
        if(acceptStatus.equals(""))
            acceptStatusTv.setText("Todo");
        else
            acceptStatusTv.setText("Done");

        dateView.setText(tsk.getHours() + ":" + tsk.getMinutes());
        titleView.setText(tsk.getTitle());
        subjectView.setText(tsk.getSubject());

        /*
        approveApplicationButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                System.out.println("Current time => " + c.getTime());
                SimpleDateFormat df = new SimpleDateFormat("hh:mm");
                currentTime = df.format(c.getTime());

                if(type.equals("taskd")){
                    String responseString = "{\"reciever\":\"TDAccept\",\"" + "taskId" + "\":\"" + taskd.get_id() + "\",\"acceptedAt" + "\":\"" + currentTime + "\"}";
                    Log.d("myTag", "appliTeacherResponseRequest : " + responseString);
                    postRequest = new Volley_Request();
                    postRequest.createRequest(getApplicationContext(), getApplicationContext().getResources().getString(R.string.mJSONURL_taskd), "POST", "TDAccept", responseString);
                    finish();
                }
                else if(type.equals("taskw")){
                    String responseString = "{\"reciever\":\"TWAccept\",\"" + "taskId" + "\":\"" + taskw.getId() + "\",\"acceptedAt" + "\":\"" + currentTime + "\"}";
                    Log.d("myTag", "appliTeacherResponseRequest : " + responseString);
                    postRequest = new Volley_Request();
                    postRequest.createRequest(getApplicationContext(), getApplicationContext().getResources().getString(R.string.mJSONURL_taskw), "POST", "TWAccept", responseString);
                    finish();
                }
                else if(type.equals("tasko")) {
                    String responseString = "{\"reciever\":\"TOAccept\",\"" + "taskId" + "\":\"" + tasko.getId()  + "\",\"acceptedAt" + "\":\"" + currentTime + "\"}";
                    Log.d("myTag", "appliTeacherResponseRequest : " + responseString);
                    postRequest = new Volley_Request();
                    postRequest.createRequest(getApplicationContext(), getApplicationContext().getResources().getString(R.string.mJSONURL_tasko), "POST", "TOAccept", responseString);
                    finish();
                }
            }
        });*/
    }

    private boolean checktimings(String timeNow, String time) {

        String pattern = "HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);

        try {
            Date date1 = sdf.parse(timeNow);
            Date date2 = sdf.parse(time);

            if(date1.before(date2)) {
                return true;
            } else {

                return false;
            }
        } catch (ParseException e){
            e.printStackTrace();
        }
        return false;
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
/*

    public static void TDAcceptString(String responseString) {
        if(db.updateTaskDAcceptStatus(taskId, currentTime)) {
            //send Response
            Intent i = new Intent(mActivity, DashboardActivity.class);
            mActivity.startActivity(i);
        }
    }

    public static void TWAcceptString(String responseString) {
        if(db.updateTaskWAcceptStatus(taskId, currentTime)) {
            //send Response
            Intent i = new Intent(mActivity, DashboardActivity.class);
            mActivity.startActivity(i);
        }
    }

    public static void TOAcceptString(String responseString) {
        if(db.updateTaskOAcceptStatus(taskId, currentTime)) {
            //send Response
            Intent i = new Intent(mActivity, DashboardActivity.class);
            mActivity.startActivity(i);
        }
    }
*/
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
