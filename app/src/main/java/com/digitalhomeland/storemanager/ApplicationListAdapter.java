package com.digitalhomeland.storemanager;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.digitalhomeland.storemanager.models.Application;
import com.digitalhomeland.storemanager.models.Employee;

import java.util.ArrayList;

/**
 * Created by prthma on 14-06-2017.
 */

public class ApplicationListAdapter extends BaseAdapter implements ListAdapter {
    private ArrayList<Application> applicationList  = new ArrayList<>();
    private String parentId = "";
    private String request = "";
    private String mJSONURL = "https://fast-shelf-51581.herokuapp.com/api/parents";
    private Context mContext;
    private Context context;
    DatabaseHandler db;

    public ApplicationListAdapter(Context mContext , ArrayList<Application> applicationList, Context context) {
        this.mContext = mContext;
        this.applicationList = applicationList;
        Log.d("myTag", "applicationList : " + applicationList.toString());
        for(int i = 0; i < applicationList.size(); i++){
            Log.d("myTag", "appli list item : " + applicationList.get(i).getId() + " : " + applicationList.get(i).getEmployeeId());
        }
        this.context = context;
        db = new DatabaseHandler(mContext);
    }

    @Override
    public int getCount() {
        return applicationList.size();
    }

    @Override
    public Object getItem(int position) {
        return applicationList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return applicationList.get(position).hashCode(); //fix it later
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        LayoutInflater inflater =  (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(view == null) {
            view = inflater.inflate(R.layout.application_list_item, null);

        }
        //Handle TextView and display string from your list
        Employee emp = db.getEmployeeById(applicationList.get(position).getEmployeeId());
        TextView studentNameText = (TextView) view.findViewById(R.id.student_name_string);
        studentNameText.setText(emp.getFirstName() + " " + emp.getLastName());
        TextView studentRollNoText = (TextView) view.findViewById(R.id.student_rollno_string);
        studentRollNoText.setText(emp.getEmployeeId());
        TextView applicationDateText = (TextView) view.findViewById(R.id.application_date_view);
        applicationDateText.setText(applicationList.get(position).getDate());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent i = new Intent( context , ApplicationNotifViewActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("date", applicationList.get(position).getDate() );
                i.putExtra("title",applicationList.get(position).getTitle() );
                i.putExtra("subject",applicationList.get(position).getSubject() );
                i.putExtra("applicationid", applicationList.get(position).getId());
                i.putExtra("empId",applicationList.get(position).getEmployeeId());
                i.putExtra("acceptstatus",applicationList.get(position).getAcceptStatus());
                mContext.startActivity(i);
            }
        });


        return view;
    }
}
