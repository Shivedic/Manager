package com.digitalhomeland.storemanager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.digitalhomeland.storemanager.models.EmpRoster;
import com.digitalhomeland.storemanager.models.Employee;

import java.util.ArrayList;

/**
 * Created by Asus on 3/26/2018.
 */

public class AttTodayListAdapter extends BaseAdapter implements ListAdapter {

    static ArrayList<Employee> allEmp = new ArrayList<Employee>();
    private Context mContext;
    public static Activity mActivity;
    DatabaseHandler db;
    public static String month, date;

    public AttTodayListAdapter(Context mContext, ArrayList<Employee> empList,String date, Activity mActivity) {
        this.mContext = mContext;
        this.allEmp  = empList;
        this.mActivity = mActivity;
        this.date = date;
        db = new DatabaseHandler(mContext);
    }

    @Override
    public int getCount() {
        return allEmp.size();
    }

    @Override
    public Object getItem(int position) {
        return allEmp.get(position);
    }

    @Override
    public long getItemId(int position) {
        return allEmp.get(position).hashCode(); //fix it later
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        final DatabaseHandler db = new DatabaseHandler(mActivity);
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (view == null) {
            view = inflater.inflate(R.layout.emp_attendance_list_item, null);
        }

        //load prev vals
        EmpRoster emr = db.getEmpRosterByDate(date, allEmp.get(position).getEmployeeId());
        //EmpRoster emr = db.getEmpRosterByDate(date, "pathak");

        String checkIn = "checkIn : ";
        if(emr.getCheckIn() == null){
            checkIn += "None";
        } else {
            checkIn += emr.getCheckIn();
        }

        String checkOut = "checkOut : ";
        if(emr.getCheckOut() == null){
            checkOut += "None";
        } else {
            checkOut += emr.getCheckOut();
        }

        String summary = checkIn + " | " + checkOut;
        //Handle TextView and display string from your list
        TextView empName = (TextView) view.findViewById(R.id.emp_name);
        empName.setText(allEmp.get(position).getFirstName() + " - " + allEmp.get(position).getEmployeeId());

        TextView summaryView = (TextView) view.findViewById(R.id.attendance_summary);
        summaryView.setText(summary);

        return view;
    }
}