package com.digitalhomeland.storemanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.digitalhomeland.storemanager.models.Employee;

import java.util.ArrayList;

public class UpdateEmpRoster extends AppCompatActivity {

    DatabaseHandler db;
    public static ArrayList<Employee> empList = new ArrayList<Employee>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_emp_roster);
        getWindow().setBackgroundDrawableResource(R.drawable.btr);
        db = new DatabaseHandler(this);
        empList = db.getAllEmployees();
        Bundle bundle = getIntent().getExtras();
        final String date = bundle.getString("date");
        final String dayOfW = bundle.getString("dayOfW");

        ListView listView = (ListView)findViewById(R.id.emp_roster_list_view);
        RosterEmpListAdapter adapter = new RosterEmpListAdapter(getApplicationContext(), empList,date, this);
        listView.setAdapter(adapter);
    }
}
