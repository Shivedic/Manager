package com.digitalhomeland.storemanager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.digitalhomeland.storemanager.models.TaskInst;
import com.digitalhomeland.storemanager.models.TaskdInst;
import com.digitalhomeland.storemanager.models.Taskw;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TaskView extends AppCompatActivity {

    static DatabaseHandler db;
    private static ArrayList<TaskInst> taskList = new ArrayList<TaskInst>();
    private static Volley_Request postRequest;
    static Context mContext;
    static String calDate = "";
    static Button getStatus;
    public static Activity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_view);
        getWindow().setBackgroundDrawableResource(R.drawable.btr);
        db = new DatabaseHandler(this);
        mContext = this;
        mActivity = TaskView.this;
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df2 = new SimpleDateFormat("dd/MM/yyyy");
        calDate = df2.format(c.getTime());
        if(db.getTaskdByDate(calDate) != null) {
            taskList = db.getTaskdByDate(calDate);
        }
        if(db.getTaskwByDate(calDate) != null){
        taskList.addAll(db.getTaskwByDate(calDate));}
        if(db.getTaskoByDate(calDate) != null){
        taskList.addAll(db.getTaskoByDate(calDate));}
        getStatus = (Button) findViewById(R.id.get_status);
        getStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String request = "{\"reciever\":\"getTaskStatus\", \"storeId\" : \"" + db.getUser().getStoreId() + "\",\"date\":\"" + calDate + "\"}";
                postRequest = new Volley_Request();
                postRequest.createRequest(mContext, mContext.getResources().getString(R.string.mJSONURL_taskd), "POST", "checkTaskdStatus", request);
            }
        });
        setListView(db);
    }

    public static void taskdStatusResponse(String response){
        try{
            JSONObject respObj = new JSONObject(response);
            JSONArray responseArr = respObj.getJSONArray("tasks");
            for(int i=0; i<responseArr.length(); i++) {
                JSONObject responseObj = responseArr.getJSONObject(i);
                if (responseObj.has("acceptedAt")) {
                    db.updateTaskdInstState(responseObj.get("_id").toString(), responseObj.getString("acceptedAt"));
                }
            }
            String req = "{\"reciever\":\"getTaskStatus\", \"storeId\" : \"" + db.getUser().getStoreId() + "\",\"date\":\"" + calDate + "\"}";
            postRequest = new Volley_Request();
            postRequest.createRequest(mContext, mContext.getResources().getString(R.string.mJSONURL_taskw), "POST", "checkTaskwStatus", req);
        }catch(JSONException e){
            Log.d("myTag", "json error " ,e);
        }
    }

    public static void taskwStatusResponse(String response){
        try{
            JSONObject respObj = new JSONObject(response);
            JSONArray responseArr = respObj.getJSONArray("tasks");
            for(int i=0; i<responseArr.length(); i++) {
                JSONObject responseObj = responseArr.getJSONObject(i);
                if (responseObj.has("acceptedAt")) {
                    db.updateTaskwInstState(responseObj.get("_id").toString(), responseObj.getString("acceptedAt"));
                }
            }
            String req = "{\"reciever\":\"getTaskStatus\", \"storeId\" : \"" + db.getUser().getStoreId() + "\",\"date\":\"" + calDate + "\"}";
            postRequest = new Volley_Request();
            postRequest.createRequest(mContext, mContext.getResources().getString(R.string.mJSONURL_tasko), "POST", "checkTaskoStatus", req);
        }catch(JSONException e){
            Log.d("myTag", "json error " ,e);
        }
    }

    public static void taskoStatusResponse(String response){
        try{
            JSONObject respObj = new JSONObject(response);
            JSONArray responseArr = respObj.getJSONArray("tasks");
            for(int i=0; i<responseArr.length(); i++) {
                JSONObject responseObj = responseArr.getJSONObject(i);
                if (responseObj.has("acceptedAt")) {
                    db.updateTaskoInstState(responseObj.get("_id").toString(), responseObj.getString("acceptedAt"));
                }
            }
            Intent in =  new Intent(mActivity,  TaskView.class);
            mActivity.startActivity(in);
        }catch(JSONException e){
            Log.d("myTag", "json error " ,e);
        }
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

    public int setListView(DatabaseHandler db){
        ListView listView = (ListView)findViewById(R.id.taskd_list_view);
        TaskListAdapter adapter = new TaskListAdapter(getApplicationContext(), taskList);
        listView.setAdapter(adapter);
        return taskList.size();
    }
}
