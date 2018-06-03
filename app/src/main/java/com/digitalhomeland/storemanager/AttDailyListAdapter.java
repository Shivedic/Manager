package com.digitalhomeland.storemanager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.digitalhomeland.storemanager.models.EmpRoster;
import com.digitalhomeland.storemanager.models.Employee;

import java.util.ArrayList;

/**
 * Created by Asus on 3/14/2018.
 */

public class AttDailyListAdapter extends BaseAdapter implements ListAdapter {

    static ArrayList<String> dateList = new ArrayList<String>();
    private Context mContext;
    public static Activity mActivity;
    DatabaseHandler db;
    public static String empId;

    public AttDailyListAdapter(Context mContext, ArrayList<String> dateList, String empId, Activity mActivity) {
        this.mContext = mContext;
        this.dateList = dateList;
        this.mActivity = mActivity;
        this.empId = empId;
        db = new DatabaseHandler(mContext);
    }

    @Override
    public int getCount() {
        return dateList.size();
    }

    @Override
    public Object getItem(int position) {
        return dateList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return dateList.get(position).hashCode(); //fix it later
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        final DatabaseHandler db = new DatabaseHandler(mActivity);
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (view == null) {
            view = inflater.inflate(R.layout.emp_daily_att_list_item, null);
        }

        //load prev vals
        final EmpRoster emr = db.getEmpRosterByDate(dateList.get(position), empId);

        //Handle TextView and display string from your list
        TextView listDate = (TextView) view.findViewById(R.id.list_date);
        listDate.setText(dateList.get(position));

        final LinearLayout statusLayout = (LinearLayout) view.findViewById(R.id.emp_status_layout);
        final LinearLayout inOutLayout = (LinearLayout) view.findViewById(R.id.in_out_layout);
        final TextView checkIn = (TextView) view.findViewById(R.id.check_in);
        final TextView checkOut = (TextView) view.findViewById(R.id.check_out);
        final TextView statusVal = (TextView) view.findViewById(R.id.emp_status);

        if (emr.getEmpStatus().equals("On Duty")) {
            statusLayout.setVisibility(View.GONE);
            inOutLayout.setVisibility(View.VISIBLE);
            checkIn.setText(emr.getCheckIn());
            checkOut.setText(emr.getCheckOut());
        } else {
            inOutLayout.setVisibility(View.GONE);
            statusLayout.setVisibility(View.VISIBLE);
            statusVal.setText(emr.getEmpStatus());
        }

        return view;
    }
}
