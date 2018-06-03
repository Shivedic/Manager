package com.digitalhomeland.storemanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.digitalhomeland.storemanager.models.Employee;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class ViewDailyAttendance extends AppCompatActivity {

    DatabaseHandler db;
    public static ArrayList<String> dateList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_daily_attendance);
        getWindow().setBackgroundDrawableResource(R.drawable.btr);
        db = new DatabaseHandler(this);
        Bundle bundle = getIntent().getExtras();
        final String empId = bundle.getString("empId");

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df2 = new SimpleDateFormat("dd/MM/yyyy");
        String currMonth = df2.format(c.getTime()).substring(3,5);

        while(Integer.parseInt(df2.format(c.getTime()).substring(3,5)) == Integer.parseInt(currMonth) ){
            dateList.add(df2.format(c.getTime()));
            c.add(Calendar.DAY_OF_WEEK, -1);
        }

        ListView listView = (ListView) findViewById(R.id.emp_attendance_list_view);
        AttDailyListAdapter adapter = new AttDailyListAdapter(getApplicationContext(), dateList, empId, this);
        listView.setAdapter(adapter);
    }
}
