package com.digitalhomeland.storemanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.digitalhomeland.storemanager.models.Employee;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class ViewAttendance extends AppCompatActivity {

    DatabaseHandler db;
    public static ArrayList<Employee> empList = new ArrayList<Employee>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_attendance);

        getWindow().setBackgroundDrawableResource(R.drawable.btr);
        db = new DatabaseHandler(this);
        empList = db.getAllEmployees();

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df2 = new SimpleDateFormat("dd/MM/yyyy");
        String currMonth = df2.format(c.getTime()).substring(3,5);
        String date = df2.format(c.getTime());
        ListView listView = (ListView)findViewById(R.id.emp_attendance_list_view);
        AttEmpListAdapter adapter = new AttEmpListAdapter(getApplicationContext(), empList, currMonth,c,date, this);
        listView.setAdapter(adapter);
    }
}
