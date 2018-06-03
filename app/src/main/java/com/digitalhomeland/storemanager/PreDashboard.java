package com.digitalhomeland.storemanager;

import android.content.Intent;
import android.util.Log;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.digitalhomeland.storemanager.models.Employee;
import com.digitalhomeland.storemanager.models.User;

import org.json.JSONException;
import org.json.JSONObject;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.digitalhomeland.storemanager.LandingActivity.db;

public class PreDashboard extends AppCompatActivity {

    private static Volley_Request postRequest;
    private static Context mContext;
    static DatabaseHandler db;
    private static Button checkApprovalButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_dashboard);
        getWindow().setBackgroundDrawableResource(R.drawable.btr);
        mContext = PreDashboard.this;
        db = new DatabaseHandler(this);

        checkApprovalButton = (Button) findViewById(R.id.check_approval);
        checkApprovalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //intent for Create Notification Page
                User user = db.getUser();
                String req = "{\"reciever\":\"checkApproval\", \"params\": { \"storeId\" :\"" + db.getStore().getStoreId() + "\",\"employeeId\":\"" + user.getEmployeeId() + "\"}}";
                postRequest = new Volley_Request();
                postRequest.createRequest(mContext, getResources().getString(R.string.mJSONURL_employee), "POST", "CheckApproval", req);

            }
        });
    }

    public static void checkApprovalResponse(String responseString){
        try{
            final JSONObject responseObj = new JSONObject(responseString);
            final String _id = responseObj.getString("employeeId");
            final String approved = responseObj.getString("approved");
            if(approved.equals("true")){
                new SweetAlertDialog(mContext, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("Approval Done")
                        .setContentText("Your sale has been confirmed")
                        .setConfirmText("Congratulations!")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                                checkApprovalButton.setVisibility(View.GONE);
                                String teamName = "";
                                String designation = "";
                                try {
                                    User manager = db.getUser();
                                    Employee manEmp = new Employee(manager);
                                    db.addEmployees(manEmp);
                                    teamName = responseObj.getString("teamName");
                                    designation = responseObj.getString("designation");
                                }catch(JSONException ex){
                                    Log.d("myTag", "Json exception", ex);
                                }
                                    db.updateTeam(_id,designation,teamName);
                                Log.d("myTag", "user assugned team : " + db.getUser());
                                Intent i = new Intent(mContext, DashboardActivity.class);
                                mContext.startActivity(i);
                            }
                        })
                        .show();
            } else if (approved.equals("false")){
                new SweetAlertDialog(mContext, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Approval Not Done")
                        .setContentText("Please check after a while")
                        .setConfirmText("Ok!")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                            }
                        })
                        .show();
            }
        }catch(JSONException e){
            Log.d("myTag", "json error " ,e);
        }
    }

    @Override
    public void onBackPressed() {
        // Do Here what ever you want do on back press;
    }
}
