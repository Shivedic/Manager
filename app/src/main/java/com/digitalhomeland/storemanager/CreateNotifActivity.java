package com.digitalhomeland.storemanager;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.digitalhomeland.storemanager.models.Application;
import com.digitalhomeland.storemanager.models.Employee;
import com.digitalhomeland.storemanager.models.Notif;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import spencerstudios.com.bungeelib.Bungee;

import static com.digitalhomeland.storemanager.LandingActivity.db;

public class CreateNotifActivity extends AppCompatActivity {

    Spinner typeSpinner,empSpinner =null;
    TableRow empRow = null;
    Button sendNotifButton;
    private static Volley_Request postRequest;
    ArrayList<Employee> empList = new ArrayList<>();
    ArrayList<String> empName = new ArrayList<>();
    static Activity mActivity;
    static TextView titleText,subjectContent;
    static DatabaseHandler dbObj = null;
    static String currentDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_notif);
        getWindow().setBackgroundDrawableResource(R.drawable.btr);
        final DatabaseHandler db = new DatabaseHandler(this);
        dbObj = db;
        empList = db.getAllEmployees();
        titleText = (TextView) findViewById(R.id.cr_no_title_text);
        subjectContent = (TextView) findViewById(R.id.cr_no_subject_text);
        mActivity = CreateNotifActivity.this;
        empRow = (TableRow) findViewById(R.id.cr_no_emp_row);
        empSpinner = (Spinner) findViewById(R.id.cr_no_emp);
        sendNotifButton = (Button) findViewById(R.id.cr_no_send_button);

        currentDate = new SimpleDateFormat("yyyy-mm-dd").format(new Date());

        ArrayAdapter<String> dataAdapterEmp = new ArrayAdapter<String>(getApplicationContext() ,R.layout.spinner_second_page, getEmpNameList());
        dataAdapterEmp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        empSpinner.setAdapter(dataAdapterEmp);

        typeSpinner = (Spinner) findViewById(R.id.cr_no_type_spinner);

        ArrayAdapter<CharSequence> typeAdapter = ArrayAdapter.createFromResource(this, R.array.notif_type_array, R.layout.spinner_second_page);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(typeAdapter);
        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("myTag", "selected one at : "  + position +"  position  : " + typeSpinner.getItemAtPosition(position).toString());
                String selectionText = typeSpinner.getItemAtPosition(position).toString();
                // typeSpinner = selectionText;
                if(position == 1){
                    empRow.setVisibility(View.GONE);
                } else if(position == 2){
                    empRow.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sendNotifButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                String notifReqStr = "";
                String arg0Val,arg1Val,arg2Val = "";
                arg0Val = typeSpinner.getSelectedItem().toString();
                arg1Val = db.getUser().getStoreId();
                if(typeSpinner.getSelectedItemPosition() == 1 ){
                }
                else if(typeSpinner.getSelectedItemPosition() == 2)
                {
                    arg2Val = empList.get(empSpinner.getSelectedItemPosition()).getEmployeeId();
                }
                String title = titleText.getText().toString();
                String subject = subjectContent.getText().toString();

                // Send args to httpRequest
                // {"reciever":"class", "params":{"class":"II", "section":"B", "city":"Bareilly", "schoolName":"Uttam Public", "studentcount":"6", "notification":{"title":"Served Notif", "subject":"Hello There!"}}}
                if(arg0Val.equals("Broadcast")) {
                    notifReqStr = "{\"reciever\":\"Broadcast\",\"params\":{\"storeId\":\"" + arg1Val + "\",\"empcount\":\"" + db.getEmployeeCount() + "\",\"notifb\":{\"title\":\"" + title + "\",\"subject\":\"" + subject + "\",\"date\":\"" + currentDate + "\",\"storeId\":\"" + arg1Val + "\"}}}";
                    postRequest = new Volley_Request();
                    Log.d("myTag","notif req : " + notifReqStr);
                    postRequest.createRequest(mActivity, getResources().getString(R.string.mJSONURL_notifb) , "POST", "CreateNotificationActivity",notifReqStr);

                }
                // {"reciever":"student", "params":{"class":"II", "section":"B", "city":"Bareilly", "schoolName":"St. stephens", "name":"Shibu","rollno":"121", "notification":{"title":"Sample Notif for student 2", "subject":"Hello There! 2"}}}
                else if(arg0Val.equals("Personal")) {
                    notifReqStr = "{\"reciever\":\"Employee\",\"params\":{ \"notife\":{\"title\":\"" + title + "\",\"subject\":\"" + subject + "\",\"storeId\":\"" + arg1Val + "\",\"date\":\"" + currentDate + "\",\"empId\":\"" + arg2Val + "\"}}}";
                    postRequest = new Volley_Request();
                    Log.d("myTag","notif req : " + notifReqStr);
                    postRequest.createRequest(mActivity, getResources().getString(R.string.mJSONURL_notife) , "POST", "CreateNotificationActivity",notifReqStr);

                }
            }});
    }

    public ArrayList<String> getEmpNameList(){
        empName.clear();
        for(int i=0; i<empList.size(); i++){
            empName.add(empList.get(i).getEmployeeId() + "-" + empList.get(i).getFirstName() + " " + empList.get(i).getLastName());
        }
        return empName;
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


    public static void notifCreatedResponse(String responseString){
        Log.d("myTag", "added notif : " + responseString);
        try{
            JSONObject responseObj = new JSONObject(responseString);
            if(responseObj.has("empId")){
                Notif notif = new Notif(responseObj.getString("_id"),responseObj.getString("title"),responseObj.getString("subject"),responseObj.getString("date"),responseObj.getString("empId"),Integer.parseInt(responseObj.getString("seqId")));
                db.addNotif(notif);
            }
            else
            {
                Notif notif = new Notif(responseObj.getString("_id"),responseObj.getString("title"),responseObj.getString("subject"),responseObj.getString("date"),"-",Integer.parseInt(responseObj.getString("seqId")));
                db.addNotif(notif);
            }

        }catch(JSONException e){
            Log.d("myTag", "json error " ,e);
        }
        Intent i = new Intent(mActivity, DashboardActivity.class);
        i.putExtra("action", "back");
        mActivity.startActivity(i);
        Bungee.slideLeft(mActivity);
    }
}
