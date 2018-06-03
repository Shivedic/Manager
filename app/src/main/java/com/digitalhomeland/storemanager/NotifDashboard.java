package com.digitalhomeland.storemanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import spencerstudios.com.bungeelib.Bungee;

public class NotifDashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notif_dashboard);
        getWindow().setBackgroundDrawableResource(R.drawable.btr);

        Button sendNotificationButton = (Button) findViewById(R.id.send_notif);
        sendNotificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //intent for Create Notification Page
                Intent i = new Intent(NotifDashboard.this, CreateNotifActivity.class);
                startActivity(i);
                Bungee.zoom(NotifDashboard.this);
            }
        });

        Button viewNotifBroadcastButton = (Button) findViewById(R.id.view_notif);
        viewNotifBroadcastButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //intent for Create Notification Page
                Intent i = new Intent(NotifDashboard.this, ViewNotifBList.class);
                startActivity(i);
                Bungee.zoom(NotifDashboard.this);
            }
        });

    }
}
