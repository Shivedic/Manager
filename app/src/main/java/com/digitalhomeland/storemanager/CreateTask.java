package com.digitalhomeland.storemanager;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.DialogFragment;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.digitalhomeland.storemanager.models.Employee;
import com.digitalhomeland.storemanager.models.Taskd;
import com.digitalhomeland.storemanager.models.Tasko;
import com.digitalhomeland.storemanager.models.Taskw;

import static com.digitalhomeland.storemanager.LandingActivity.db;

public class CreateTask extends AppCompatActivity {

Activity mActivity;
    private int mYear, mMonth, mDay, mHour, mMinute;
    Spinner typeSpinner, empSpinner, dayOfWSpinner = null;
    ArrayList<Employee> empList = new ArrayList<>();
    ArrayList<String> empName = new ArrayList<>();
    TextView titleText, subjectContent,dateToSet;
    TableRow dateRow, dowRow, timeRow;
    private static Volley_Request getRequest, postRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);
        //set bkg
        getWindow().setBackgroundDrawableResource(R.drawable.btr);
        empList = db.getAllEmployees();
        mActivity = CreateTask.this;
        titleText = (TextView) findViewById(R.id.cr_no_title_text);
        subjectContent = (TextView) findViewById(R.id.cr_no_subject_text);
        dateToSet = (TextView) findViewById(R.id.date_to_set);
        dateRow = (TableRow) findViewById(R.id.row_date);
        timeRow = (TableRow) findViewById(R.id.row_time);
        dowRow = (TableRow) findViewById(R.id.dow_row);
        dateRow.setVisibility(View.GONE);
        dowRow.setVisibility(View.GONE);
        dateToSet.setVisibility(View.GONE);
        typeSpinner = (Spinner) findViewById(R.id.ct_type_spinner);

        ArrayAdapter<CharSequence> typeAdapter = ArrayAdapter.createFromResource(this, R.array.type_array, R.layout.spinner_second_page);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(typeAdapter);
        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("myTag", "selected one at : "  + position +"  position  : " + typeSpinner.getItemAtPosition(position).toString());
                    String selectionText = typeSpinner.getItemAtPosition(position).toString();
                   // typeSpinner = selectionText;
                    if(position == 1){
                        dateRow.setVisibility(View.GONE);
                        timeRow.setVisibility(View.VISIBLE);
                        dowRow.setVisibility(View.GONE);
                    } else if(position == 2){
                        dateRow.setVisibility(View.GONE);
                        dowRow.setVisibility(View.VISIBLE);
                        dowRow.invalidate();
                    }
                    else if(position == 3){
                        dateRow.setVisibility(View.VISIBLE);
                        dateRow.invalidate();
                        dowRow.setVisibility(View.GONE);
                    }
                }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        empSpinner = (Spinner) findViewById(R.id.ct_emp);
        ArrayAdapter<String> dataAdapterCity = new ArrayAdapter<String>(getApplicationContext() ,R.layout.spinner_second_page, getEmpNameList());
        dataAdapterCity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        empSpinner.setAdapter(dataAdapterCity);

        dayOfWSpinner = (Spinner) findViewById(R.id.ct_dow);

        ArrayAdapter<CharSequence> dowAdapter = ArrayAdapter.createFromResource(this, R.array.days_of_week, R.layout.spinner_second_page);
        dowAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dayOfWSpinner.setAdapter(dowAdapter);



        Button setDateButton = (Button) findViewById(R.id.ct_date);
        setDateButton.setOnClickListener(new View.OnClickListener() {

                                             @Override
                                             public void onClick(View v) {
                                                 DialogFragment newFragment = new DatePickerFragment();
                                                 newFragment.show(getFragmentManager(), "datePicker");
                                             }
                                         });

        Button setTimeButton = (Button) findViewById(R.id.ct_time);
        setTimeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(mActivity,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                Log.d("myTag", hourOfDay + " : " + minute);
                                mHour = hourOfDay;
                                mMinute = minute;
                            }
                        }, mHour, mMinute, true);
                timePickerDialog.show();
            }

        });


        Button sendNotifButton = (Button) findViewById(R.id.cr_no_send_button);
        sendNotifButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
            String req = "{\"reciever\":\"add\",\"params\":{";
                    //}}";
                if(typeSpinner.getSelectedItemPosition() == 1){
                    Log.d("myTag", "setting task : " +  titleText.getText().toString() + " " + subjectContent.getText().toString() + " " + empList.get(empSpinner.getSelectedItemPosition() - 1).getEmployeeId()  + " " + String.valueOf(mHour) + " " + String.valueOf(mMinute));
                    Taskd tsk = new Taskd(titleText.getText().toString(),subjectContent.getText().toString(), empList.get(empSpinner.getSelectedItemPosition() - 1).getEmployeeId(),String.valueOf(mHour), String.valueOf(mMinute));
                    db.addTaskd(tsk);
                    //req += "\"storeId\":\"" + db.getStore().getStoreId() + "\",\"empId\":\"" + empList.get(empSpinner.getSelectedItemPosition()-1).getEmployeeId() + "\",\"title\":\"" + titleText.getText() + "\",\"subject\":\"" + subjectContent.getText() + "\",\"hours\":\"" + mHour + "\",\"minutes\":\"" + mMinute + "\"}}";
                    //postRequest = new Volley_Request();
                    //postRequest.createRequest(CreateTask.this, getResources().getString(R.string.mJSONURL_taskd), "POST", "addTaskDaily", req);
                }else if (typeSpinner.getSelectedItemPosition() == 2){
                    Taskw tsk = new Taskw(subjectContent.getText().toString(),titleText.getText().toString(), empList.get(empSpinner.getSelectedItemPosition()).getEmployeeId(),String.valueOf(mHour), String.valueOf(mMinute), dayOfWSpinner.getSelectedItem().toString());
                    db.addTaskw(tsk);

                    //req += "\"storeId\":\"" + db.getStore().getStoreId() + "\",\"empId\":\"" + empList.get(empSpinner.getSelectedItemPosition()-1).getEmployeeId() + "\",\"title\":\"" + titleText.getText() + "\",\"subject\":\"" + subjectContent.getText() + "\",\"hours\":\"" + mHour + "\",\"minutes\":\"" + mMinute + "\",\"dayOfW\":\"" + dayOfWSpinner.getSelectedItem() + "\"}}";
                    //postRequest = new Volley_Request();
                    //postRequest.createRequest(CreateTask.this, getResources().getString(R.string.mJSONURL_taskw), "POST", "addTaskWeekly", req);
            }else if (typeSpinner.getSelectedItemPosition() == 3){
                    Tasko tsk = new Tasko(subjectContent.getText().toString(),titleText.getText().toString(), empList.get(empSpinner.getSelectedItemPosition()).getEmployeeId(),String.valueOf(mHour), String.valueOf(mMinute), dateToSet.getText().toString());
                    db.addTasko(tsk);
                    //req += "\"storeId\":\"" + db.getStore().getStoreId() + "\",\"empId\":\"" + empList.get(empSpinner.getSelectedItemPosition()-1).getEmployeeId() + "\",\"title\":\"" + titleText.getText() + "\",\"subject\":\"" + subjectContent.getText() + "\",\"hours\":\"" + mHour + "\",\"minutes\":\"" + mMinute + "\",\"dateToSet\":\"" + dateToSet + "\"}}";
                    //postRequest = new Volley_Request();
                    //postRequest.createRequest(CreateTask.this, getResources().getString(R.string.mJSONURL_tasko), "POST", "addTaskOnce", req);
            }
                Intent i = new Intent(CreateTask.this, DashboardActivity.class);
                i.putExtra("move", "back");
                startActivity(i);
            }
    });
    }

    public ArrayList<String> getEmpNameList(){
        empName.clear();
        empName.add("Select Employee");
        for(int i=0; i<empList.size(); i++){
            empName.add(empList.get(i).getEmployeeId() + "-" + empList.get(i).getFirstName() + " " + empList.get(i).getLastName());
       }
       return empName;
    }
    }
