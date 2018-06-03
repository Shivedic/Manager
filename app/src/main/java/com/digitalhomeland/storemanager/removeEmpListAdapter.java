package com.digitalhomeland.storemanager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.digitalhomeland.storemanager.models.EmpRoster;
import com.digitalhomeland.storemanager.models.Employee;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;
import spencerstudios.com.bungeelib.Bungee;

import static com.digitalhomeland.storemanager.LandingActivity.db;

/**
 * Created by Asus on 4/6/2018.
 */

public class removeEmpListAdapter extends BaseAdapter implements ListAdapter {

    static ArrayList<Employee> allEmp = new ArrayList<Employee>();
    private static Context mContext;
    public static Activity mActivity;
    DatabaseHandler db;
    public static String month, date;
    private static Volley_Request getRequest, postRequest;

    public removeEmpListAdapter(Context mContext, ArrayList<Employee> empList,String date, Activity mActivity) {
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
            view = inflater.inflate(R.layout.emp_remove_list_item, null);
        }

        TextView empName = (TextView) view.findViewById(R.id.emp_name);
        empName.setText(allEmp.get(position).getFirstName() + " - " + allEmp.get(position).getEmployeeId());

        Button removeEmp = (Button) view.findViewById(R.id.remove_emp);
        removeEmp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SweetAlertDialog(mContext, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Are you sure?")
                        .setContentText("This will remove " + allEmp.get(position).getEmployeeId() + " from store")
                        .setConfirmText("Yes do it!")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                String req = "{\"reciever\":\"remove\", \"employee\" : {\"storeId\":\"" + db.getStore().getStoreId() + "\",\"empId\":\"" + allEmp.get(position).getEmployeeId() + "\"}}";
                                postRequest = new Volley_Request();
                                postRequest.createRequest(mContext, mContext.getResources().getString(R.string.mJSONURL_employee), "POST", "removeEmployee", req);
                                sDialog.dismissWithAnimation();
                                Intent i = new Intent(mContext, UpdateRoster.class);
                                mContext.startActivity(i);
                                Bungee.zoom(mContext);
                            }
                        })
                        .show();
            }
        });
        return view;
    }

    public static void removeEmpResponse(String responseObj){
        /*
        db.removeEmp(allEmp.get(position).getEmployeeId());
        new SweetAlertDialog(mContext, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Are you sure?")
                .setContentText("This will remove " + allEmp.get(position).getEmployeeId() + " from store")
                .setConfirmText("Yes do it!")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        String req = "{\"reciever\":\"remove\", \"employee\" : {\"storeId\":\"" + db.getStore().getStoreId() + "\",\"empId\":\"" + allEmp.get(position).getEmployeeId() + "\"}}";
                        postRequest = new Volley_Request();
                        postRequest.createRequest(mContext, mContext.getResources().getString(R.string.mJSONURL_employee), "POST", "removeEmployee", req);
                        sDialog.dismissWithAnimation();
                        Intent i = new Intent(mContext, UpdateRoster.class);
                        mContext.startActivity(i);
                        Bungee.zoom(mContext);
                    }
                })
                .show();
                */
    }
}