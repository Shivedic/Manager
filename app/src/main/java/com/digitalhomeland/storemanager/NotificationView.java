package com.digitalhomeland.storemanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class NotificationView extends AppCompatActivity {

    TextView dateView, titleView, subjectView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_view);
        //set bkg
        getWindow().setBackgroundDrawableResource(R.drawable.btr);
        dateView = (TextView)findViewById(R.id.notif_date_textview);
        titleView = (TextView)findViewById(R.id.notif_title_textview);
        subjectView = (TextView) findViewById(R.id.notif_subject_textview);
        if (getIntent().hasExtra("date") && getIntent().hasExtra("title") && getIntent().hasExtra("subject")){
            dateView.setText(getIntent().getStringExtra("date"));
            titleView.setText(getIntent().getStringExtra("title"));
            subjectView.setText(getIntent().getStringExtra("subject"));
        }
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

}
