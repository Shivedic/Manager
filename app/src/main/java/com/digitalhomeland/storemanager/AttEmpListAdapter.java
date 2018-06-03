package com.digitalhomeland.storemanager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.digitalhomeland.storemanager.models.EmpRoster;
import com.digitalhomeland.storemanager.models.Employee;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Asus on 3/14/2018.
 */

public class AttEmpListAdapter extends BaseAdapter implements ListAdapter {

    static ArrayList<Employee> allEmp = new ArrayList<Employee>();
    private Context mContext;
    public static Activity mActivity;
    DatabaseHandler db;
    public static String month, date;
    public static Calendar cInst;

    public AttEmpListAdapter(Context mContext, ArrayList<Employee> empList, String month, Calendar c, String date, Activity mActivity) {
        this.mContext = mContext;
        this.allEmp  = empList;
        this.month = month;
        this.mActivity = mActivity;
        this.date = date;
        this.cInst = c;
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

         ArrayList<EmpRoster> emrList = new ArrayList<>();
        ArrayList<String> dateList = new ArrayList<>();
        SimpleDateFormat df1 = new SimpleDateFormat("MM");
        SimpleDateFormat df2 = new SimpleDateFormat("dd/MM/yyyy");
        String currMonth = df1.format(cInst.getTime());
        while(df1.format(cInst.getTime()).equals(currMonth)){
            dateList.add(df2.format(cInst.getTime()));
            cInst.add(Calendar.DAY_OF_MONTH, -1);
        }
        //load prev vals
        for (String date:dateList) {
            if(db.getEmpRosterByDate(date, allEmp.get(position).getEmployeeId()) != null) {
                EmpRoster rosterItem = db.getEmpRosterByDate(date, allEmp.get(position).getEmployeeId());
                emrList.add(rosterItem);
            }
        }

        // Calc summary
        Integer days = 0;
        Integer onDuty = 0;
        Integer present = 0;
        Integer plannedL = 0;
        Integer offD = 0;
        Integer weeklyO = 0;
        String summary;
        for(int i=0; i<emrList.size(); i++){
            days+=1;
            EmpRoster eRoster = emrList.get(i);
            if (eRoster.getEmpStatus().equals("On Duty")) {
                onDuty++;
                if (!eRoster.getCheckIn().equals("") && !eRoster.getCheckOut().equals("")) {
                    present++;
                }
            } else if(eRoster.getEmpStatus().equals("Planned Leave")) {
                plannedL++;
            }
            else if (eRoster.getEmpStatus().equals("Off Duty")){
                offD++;
            }
            else if (eRoster.getEmpStatus().equals("Weekly Off")){
                weeklyO++;
            }
        }

        summary = "Days:" + onDuty + ", Present:" + present + ", PL:" + plannedL + ", UPL:" + (onDuty - present);

        //Handle TextView and display string from your list
        TextView empName = (TextView) view.findViewById(R.id.emp_name);
        empName.setText(allEmp.get(position).getFirstName() + " - " + allEmp.get(position).getEmployeeId());

        final TextView summaryView = (TextView) view.findViewById(R.id.attendance_summary);
        summaryView.setText(summary);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mActivity, UpdateEmpRoster.class);
                i.putExtra("empId", allEmp.get(position).getEmployeeId());
                mActivity.startActivity(i);
            }
        });

        return view;
    }
}