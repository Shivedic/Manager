package com.digitalhomeland.storemanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AttendanceDashboard extends AppCompatActivity {

    String buttonAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_dashboard);
        getWindow().setBackgroundDrawableResource(R.drawable.btr);

        Button checkIn = (Button) findViewById(R.id.check_in);
        checkIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonAction = "1";
                Intent i = new Intent(AttendanceDashboard.this, AttendanceActivity.class);
                i.putExtra("action", "CheckIn");
                startActivity(i);
                // getLocation();
            }
        });

        Button checkOut = (Button) findViewById(R.id.check_out);
        checkOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonAction = "2";
                Intent i = new Intent(AttendanceDashboard.this, AttendanceActivity.class);
                i.putExtra("action", "CheckOut");
                startActivity(i);
                // getLocation();
            }
        });

        Button viewAttendance = (Button) findViewById(R.id.view_attendance);
        viewAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AttendanceDashboard.this, ViewAttendance.class);
                startActivity(i);
            }
        });

        Button todayAttendance = (Button) findViewById(R.id.todays_att);
        todayAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AttendanceDashboard.this, TodayAttendance.class);
                startActivity(i);
            }
        });
    }
}
