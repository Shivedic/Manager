package com.digitalhomeland.storemanager;

import android.content.Intent;
import android.graphics.Color;
import android.icu.util.Calendar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.digitalhomeland.storemanager.models.StRoster;

public class StoreRoster extends AppCompatActivity {

    public static ToggleButton tgBtn, eventTgBtn;
    static TableRow addEventRow;
    static LinearLayout addEventLayout;
    static DatabaseHandler db;
    static EditText titleText, subjectText;
    static Button editEventBtn;
    static StRoster st = null;
    static String[] daysOfW = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_roster);
        getWindow().setBackgroundDrawableResource(R.drawable.btr);
        tgBtn = (ToggleButton) findViewById(R.id.status_toggle_button);
        eventTgBtn = (ToggleButton) findViewById(R.id.event_toggle_button);
        addEventRow = (TableRow) findViewById(R.id.add_event_row);
        addEventLayout = (LinearLayout) findViewById(R.id.add_event_form);
        titleText = (EditText) findViewById(R.id.cr_no_title_text);
        subjectText = (EditText) findViewById(R.id.cr_no_subject_text);
        editEventBtn = (Button) findViewById(R.id.edit_event);

        Bundle bundle = getIntent().getExtras();
        final String date = bundle.getString("date");
        final String dayOfW = bundle.getString("dayOfW");

        db = new DatabaseHandler(this);
        loadStoreRoster(date, dayOfW);

        if (tgBtn.isChecked()) {
            tgBtn.setBackgroundColor(Color.GREEN);
        } else if(!(tgBtn.isChecked()))
        {
        tgBtn.setBackgroundColor(Color.RED);
        }

        if (eventTgBtn.isChecked()) {
            eventTgBtn.setBackgroundColor(Color.GREEN);
        } else if(!(eventTgBtn.isChecked()))
        {
            eventTgBtn.setBackgroundColor(Color.RED);
        }

        tgBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    // if toggle button is enabled/on
                    addEventRow.setVisibility(View.VISIBLE);
                    tgBtn.setBackgroundColor(Color.GREEN);
                    st.setStoreStatusToggle("Open");
                }
                else{
                    addEventRow.setVisibility(View.INVISIBLE);
                    addEventLayout.setVisibility(View.INVISIBLE);
                    tgBtn.setBackgroundColor(Color.RED);
                    st.setStoreStatusToggle("Closed");
                }
            }
        });

        findViewById(R.id.save_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!tgBtn.isChecked()){
                    addEventRow.setVisibility(View.INVISIBLE);
                    addEventLayout.setVisibility(View.INVISIBLE);
                }
                else if(tgBtn.isChecked() && !eventTgBtn.isChecked()){
                    addEventRow.setVisibility(View.VISIBLE);
                    st.setStoreStatusToggle("Open");
                }
                else if(tgBtn.isChecked() && eventTgBtn.isChecked()){
                    addEventRow.setVisibility(View.VISIBLE);
                    st.setStoreStatusToggle("Open");
                    st.setEvents("Yes");
                    st.setEventSub(((EditText) findViewById(R.id.cr_no_subject_text)).getText().toString());
                    st.setEventTitle(((EditText) findViewById(R.id.cr_no_title_text)).getText().toString());
                }
                db.updateStRoster(st);
                Intent i = new Intent(StoreRoster.this, UpdateRoster.class);
                i.putExtra("action", "FrontBack");
                startActivity(i);
            }
        });

        eventTgBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    // if toggle button is enabled/on
                    addEventLayout.setVisibility(View.VISIBLE);
                    eventTgBtn.setBackgroundColor(Color.GREEN);
                    if(st.getEventTitle().equals("") && st.getEventSub().equals("")) {
                        editEventBtn.setText("Add Event");
                    } else {
                        editEventBtn.setText("Edit Event Details");
                    }
                    st.setEvents("Yes");
                }
                else{
                    addEventLayout.setVisibility(View.INVISIBLE);
                    eventTgBtn.setBackgroundColor(Color.RED);
                    st.setEvents("None");
                }
            }
        });


        editEventBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editEventBtn.getText().equals("Edit Event Details")){
                    titleText.setEnabled(true);
                    subjectText.setEnabled(true);
                    editEventBtn.setText("Done");
                }
                else if(editEventBtn.getText().equals("Add Event")){
                    titleText.setEnabled(true);
                    subjectText.setEnabled(true);
                    editEventBtn.setText("Done");
                }
                else if(editEventBtn.getText().equals("Done")){
                    titleText.setEnabled(false);
                    subjectText.setEnabled(false);
                    editEventBtn.setText("Edit Event Details");
                }
            }
        });
    }

    public static void loadStoreRoster(String date, String dayOfW){
        db.getAllStRoster();
        st = db.getSTRosterByDate(date);
        if(st == null) {
            st = new StRoster(date, dayOfW);
            return;
        }
        Log.d("myTag", "got : " + st.getInString());
        if(st.getStoreStatus().equals("Open")){
            tgBtn.setChecked(true);
        } else if (st.getStoreStatus().equals("Closed")){
            tgBtn.setChecked(false);
        }
        if(st.getEvents().equals("Yes")){
            eventTgBtn.setChecked(true);
            titleText.setText(st.getEventTitle());
            titleText.setEnabled(false);
            subjectText.setText(st.getEventSub());
            subjectText.setEnabled(false);
            editEventBtn.setText("Edit Event Details");
        } else if(st.getEvents().equals("None")){
            eventTgBtn.setChecked(false);
            addEventLayout.setVisibility(View.INVISIBLE);
        }
    }

}
