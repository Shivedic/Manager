package com.digitalhomeland.storemanager;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.digitalhomeland.storemanager.models.Employee;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class RemoveEmployeeActivity extends AppCompatActivity {

    public static ArrayList<Employee> empList = new ArrayList<Employee>();
    public static Context mContext;
    private static Volley_Request postRequest;
    static DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_employee);

        getWindow().setBackgroundDrawableResource(R.drawable.btr);
        db = new DatabaseHandler(this);
        empList = db.getAllEmployees();
        mContext = this;
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df2 = new SimpleDateFormat("dd/MM/yyyy");
        String date = df2.format(c.getTime());
        ListView listView = (ListView)findViewById(R.id.today_attendance_list_view);
        removeEmpListAdapter adapter = new removeEmpListAdapter(getApplicationContext(), empList, date, this);
        listView.setAdapter(adapter);
    }
}
