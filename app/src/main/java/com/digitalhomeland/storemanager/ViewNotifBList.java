package com.digitalhomeland.storemanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.digitalhomeland.storemanager.models.Application;
import com.digitalhomeland.storemanager.models.Notif;

import java.util.ArrayList;

public class ViewNotifBList extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_notif_blist);
        getWindow().setBackgroundDrawableResource(R.drawable.btr);
        final DatabaseHandler db = new DatabaseHandler(this);
        setListView(db);
    }

    public int setListView(DatabaseHandler db){
        ArrayList<Notif> notifbList = db.getAllNotifb();
        if(notifbList.size() != 0) {
            ListView listView = (ListView) findViewById(R.id.notif_list_view);
            NotifListAdapter adapter = new NotifListAdapter(getApplicationContext(), notifbList, this);
            listView.setAdapter(adapter);
        }
        else {

        }
        return notifbList.size();
    }
}
