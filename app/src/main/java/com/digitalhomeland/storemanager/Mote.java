package com.digitalhomeland.storemanager;

/**
 * Created by Asus on 2/2/2018.
 */

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import java.util.Calendar;

public class Mote extends BroadcastReceiver{

    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub

        Toast.makeText(context, "Alarm worked.", Toast.LENGTH_LONG).show();
        final Calendar c = Calendar.getInstance();
        long mHour = c.get(Calendar.HOUR_OF_DAY);
        long mMinute = c.get(Calendar.MINUTE);
        Log.d("myTag", "Alarm executed at : " + mHour + ":" + mMinute);

    }



}