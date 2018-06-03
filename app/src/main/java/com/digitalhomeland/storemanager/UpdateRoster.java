package com.digitalhomeland.storemanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import spencerstudios.com.bungeelib.Bungee;

public class UpdateRoster extends AppCompatActivity {

    Button nextNotification;
    Button prevNotification;
    DatabaseHandler db;
    static String[] dateList = new String[7];
    static String[] colorList = new String[7];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application_view);
        //set bkg
        getWindow().setBackgroundDrawableResource(R.drawable.btr);
        nextNotification = (Button) findViewById(R.id.next_notification_button);
        prevNotification = (Button) findViewById(R.id.prev_notification_button);
        db = new DatabaseHandler(this);
        // setListView(db);
        ListView listView = (ListView)findViewById(R.id.application_list_view);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df2 = new SimpleDateFormat("dd/MM/yyyy");
        String currentDate = df2.format(c.getTime());
        for (int i = 0; i < 7; i++) {
            dateList[i] = df2.format(c.getTime());
            c.add(Calendar.DAY_OF_WEEK, 1);
        }
        RooasterWeeklyListAdapter adapter = new RooasterWeeklyListAdapter(getApplicationContext(), dateList,colorList, UpdateRoster.this);
        listView.setAdapter(adapter);

        prevNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //intent for Create Notification Page

            }
        });

        nextNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //intent for Create Notification Page

            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(UpdateRoster.this, RosterDasboard.class);
        startActivity(i);
        Bungee.zoom(UpdateRoster.this);
    }

    public static void setWeek(Calendar c, SimpleDateFormat sdf){
        int dayOfWeek  = (c.get(Calendar.DAY_OF_WEEK));
        //dayOfWeek -= 1;
        Calendar tempC = c;
            for (int i = 1; i < 8; i++) {
                if (dayOfWeek == i ) {
                    dateList[i-1] = sdf.format(tempC.getTime());
                    colorList[i-1] = "GREEN";
                }
                if (dayOfWeek < i) {
                    Log.d("myTag", "date fut : " + tempC.getTime());
                    tempC.add(Calendar.DAY_OF_MONTH, i - dayOfWeek);
                    Log.d("myTag", "date fut : " + tempC.getTime());
                    dateList[i-1] = sdf.format(tempC.getTime());
                    colorList[i-1] = "YELLOW";
                    tempC.add(Calendar.DAY_OF_MONTH, -(i - dayOfWeek));
                    Log.d("myTag", "date fut reset : " + tempC.getTime());
                }
                if (dayOfWeek > i) {
                    tempC.add(Calendar.DAY_OF_MONTH, (i - dayOfWeek));
                    Log.d("myTag", "date past : " + tempC.getTime());
                    dateList[i-1] = sdf.format(tempC.getTime());
                    colorList[i-1] = "RED";
                    tempC.add(Calendar.DAY_OF_MONTH, -(i- dayOfWeek));
                    Log.d("myTag", "date past reset : " + tempC.getTime());
                }
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


/*
    public int setListView(DatabaseHandler db){
        ArrayList<Application> applicationList = db.getAllApplications(LIMIT , OFFSET);
        for(int i=0;i<applicationList.size();i++) {
            Log.d("myTag", "applicationslistAdapter: setlistview : " + applicationList.get(i).getEmployeeId() + " : approved - " + applicationList.get(i).getAcceptStatus() + " limit : " + LIMIT + " offeset : " + OFFSET);
            if(applicationList.get(i).getEmployeeId().equals("")){Log.d("myTag", "matched appli");
                applicationList.remove(i);
                i--;
                continue;
            }
        }
        ListView listView = (ListView)findViewById(R.id.application_list_view);
        ApplicationListAdapter adapter = new ApplicationListAdapter(getApplicationContext(), applicationList, this);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent,
                                    View view, int position, long id) {
                Application clickedObj = (Application) parent.getItemAtPosition(position);
                Intent i = new Intent(getApplicationContext(),
                        ApplicationNotifViewActivity.class);
                i.putExtra("date", clickedObj.getDate());
                i.putExtra("title", clickedObj.getTitle());
                i.putExtra("subject", clickedObj.getSubject());
                i.putExtra("studentid", clickedObj.getEmployeeId());
                i.putExtra("acceptstaus", clickedObj.getAcceptStatus());
                startActivity(i);

            }});
        return applicationList.size();
    }*/
}


