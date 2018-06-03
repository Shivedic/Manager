package com.digitalhomeland.storemanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AppliDashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appli_dashboard);
        getWindow().setBackgroundDrawableResource(R.drawable.btr);

        Button viewApplicationButton = (Button) findViewById(R.id.view_application_button);
        viewApplicationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //intent for Take Attendance Page
                Intent i = new Intent(AppliDashboard.this, ApplicationViewActivity.class);
                startActivity(i);
            }
        });
    }
}
