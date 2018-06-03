package com.digitalhomeland.storemanager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.digitalhomeland.storemanager.models.Application;
import com.digitalhomeland.storemanager.models.Employee;

import java.util.ArrayList;

/**
 * Created by Asus on 2/24/2018.
 */

public class RooasterWeeklyListAdapter extends BaseAdapter implements ListAdapter {
    static String[] dateList = new String[7];
    static String[] colorList = new String[7];
    static String[] dayofW = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
    private Context mContext;
    public static Activity mActivity;
    DatabaseHandler db;

    public RooasterWeeklyListAdapter(Context mContext, String[] dateList, String[] colorList, Activity mActivity) {
        this.mContext = mContext;
        this.dateList = dateList;
        this.colorList = colorList;
        this.mActivity = mActivity;
        db = new DatabaseHandler(mContext);
    }

    @Override
    public int getCount() {
        return dateList.length;
    }

    @Override
    public Object getItem(int position) {
        return dateList[position];
    }

    @Override
    public long getItemId(int position) {
        return dateList[position].hashCode(); //fix it later
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (view == null) {
            view = inflater.inflate(R.layout.weekly_list_item, null);

        }
        //Handle TextView and display string from your list

        RelativeLayout listItem = (RelativeLayout) view.findViewById(R.id.list_item);
        TextView dateText = (TextView) view.findViewById(R.id.list_date);
        dateText.setText(dateList[position] + " | " + dayofW[position]);

            dateText.setTextColor(Color.GREEN);
            view.setBackgroundResource(R.drawable.backwithbordergreen);

        view.findViewById(R.id.store_roster_btn).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mActivity, StoreRoster.class);
                i.putExtra("date", dateList[position]);
                i.putExtra("dayOfW", dayofW[position]);
                mActivity.startActivity(i);
            }
        });

        view.findViewById(R.id.emp_roster_btn).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mActivity, UpdateEmpRoster.class);
                i.putExtra("date", dateList[position]);
                i.putExtra("dayOfW", dayofW[position]);
                mActivity.startActivity(i);
            }
        });

        return view;
    }
}