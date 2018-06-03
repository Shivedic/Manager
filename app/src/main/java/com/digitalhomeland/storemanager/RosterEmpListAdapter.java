package com.digitalhomeland.storemanager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.digitalhomeland.storemanager.models.EmpRoster;
import com.digitalhomeland.storemanager.models.Employee;

import java.util.ArrayList;

/**
 * Created by Asus on 2/26/2018.
 */

public class RosterEmpListAdapter extends BaseAdapter implements ListAdapter {

    static ArrayList<Employee> allEmp = new ArrayList<Employee>();
    private Context mContext;
    public static Activity mActivity;
    DatabaseHandler db;
    public static String date;
    public static String[] status = {"None","On Duty", "Weekly Off", "Planned Leave", "Unplanned Leave"};
    public static String[] shift = {"None","Morning", "Evening"};

    public RosterEmpListAdapter(Context mContext, ArrayList<Employee> empList,String date, Activity mActivity) {
        this.mContext = mContext;
        this.allEmp  = empList;
        this.date = date;
        this.mActivity = mActivity;
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
            view = inflater.inflate(R.layout.emp_roster_list_item, null);
        }

        //load prev vals
        final EmpRoster emr = db.getEmpRosterByDate(date, allEmp.get(position).getEmployeeId());

        //Handle TextView and display string from your list
        TextView empName = (TextView) view.findViewById(R.id.emp_name);
        empName.setText(allEmp.get(position).getFirstName() + " - " + allEmp.get(position).getEmployeeId());

        final TextView statusVal = (TextView) view.findViewById(R.id.cr_no_status_val);
        final TextView shiftVal = (TextView) view.findViewById(R.id.cr_no_shift_value);

        final Spinner statusSpinner = (Spinner) view.findViewById(R.id.status_spinner);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (mActivity, android.R.layout.simple_spinner_item,
                        status); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        statusSpinner.setAdapter(spinnerArrayAdapter);
        statusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                statusVal.setText(statusSpinner.getSelectedItem().toString());
                emr.setEmpStatus(statusSpinner.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        final Spinner shiftSpinner = (Spinner) view.findViewById(R.id.shift_spinner);
        ArrayAdapter<String> shiftArrayAdapter = new ArrayAdapter<String>
                (mActivity, android.R.layout.simple_spinner_item,
                        shift); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        shiftSpinner.setAdapter(shiftArrayAdapter);
        shiftSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                shiftVal.setText(shiftSpinner.getSelectedItem().toString());
                emr.setShift(shiftSpinner.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Log.d("myTag", "emp status : " + date + " : " + emr.getEmpStatus());
        if(emr != null){
            if(!(emr.getEmpStatus().equals(""))) {
                if(emr.getEmpStatus().equals("Planned Leave")) {
                    statusVal.setText(emr.getEmpStatus());
                    shiftVal.setText("-");
                } else {
                    statusVal.setText(emr.getEmpStatus());
                    shiftVal.setText(emr.getShift());
                }
            } else if (!(emr.getShift().equals(""))) {
                statusVal.setText(statusSpinner.getSelectedItem().toString());
                shiftVal.setText(emr.getShift());
            } else {
                statusVal.setText(statusSpinner.getSelectedItem().toString());
                shiftVal.setText(shiftSpinner.getSelectedItem().toString());
            }
        } else {
            statusVal.setText(statusSpinner.getSelectedItem().toString());
            shiftVal.setText(shiftSpinner.getSelectedItem().toString());
        }
        statusSpinner.setVisibility(View.GONE);
        shiftSpinner.setVisibility(View.GONE);

        final Button editBtn = (Button) view.findViewById(R.id.edit_btn);
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editBtn.getText().equals("Edit")){
                        statusVal.setVisibility(View.GONE);
                        shiftVal.setVisibility(View.GONE);
                        statusSpinner.setVisibility(View.VISIBLE);
                        shiftSpinner.setVisibility(View.VISIBLE);
                        editBtn.setText("Save");
                } else if (editBtn.getText().equals("Save")){
                    statusVal.setVisibility(View.VISIBLE);
                    shiftVal.setVisibility(View.VISIBLE);
                    statusSpinner.setVisibility(View.GONE);
                    shiftSpinner.setVisibility(View.GONE);
                     db.updateEmpRoster(emr);
                    statusVal.setText(statusSpinner.getSelectedItem().toString());
                    shiftVal.setText(shiftSpinner.getSelectedItem().toString());
                    editBtn.setText("Edit");
                }

            }
        });
        return view;
    }
}