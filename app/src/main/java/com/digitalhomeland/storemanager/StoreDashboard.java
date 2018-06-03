package com.digitalhomeland.storemanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StoreDashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_dashboard);
        getWindow().setBackgroundDrawableResource(R.drawable.btr);

        Button removeEmpButton = (Button) findViewById(R.id.add_employee);
        removeEmpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //intent for Create Notification Page
                Intent i = new Intent(StoreDashboard.this, AddEmployee.class);
                startActivity(i);
            }
        });
    }
}
