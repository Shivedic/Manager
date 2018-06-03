package com.digitalhomeland.storemanager;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import static com.digitalhomeland.storemanager.LandingActivity.db;

public class AddEmployee extends AppCompatActivity {

    EditText joinKey;
    Button activation;
    Volley_Request postRequest;
    static DatabaseHandler db;
    public static Activity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);
        getWindow().setBackgroundDrawableResource(R.drawable.btr);
        joinKey = (EditText) findViewById(R.id.join_key);
        activation = (Button) findViewById(R.id.activate_key);
        mActivity = AddEmployee.this;

        db = new DatabaseHandler(this);

        if(db.getStore().getKeyState().equals("INACTIVE")){
            joinKey.setEnabled(true);
            activation.setText("Activate Key");
        } else if (db.getStore().getKeyState().equals("ACTIVE")){
            joinKey.setEnabled(false);
            activation.setText("Deactivate Key");
        }

        activation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Application sync
                //db.removeAllApplications(); //not part of live server
                if(db.getStore().getKeyState().equals("INACTIVE")) {
                    String keyRequest = "{\"reciever\":\"activateKey\",\"params\":{\"storeId\":\"" + db.getStore().getStoreId() + "\",\"joinKey\":\"" + joinKey.getText().toString() + "\"}}";
                    Log.d("myTag", "applicationRequest : " + keyRequest);
                    postRequest = new Volley_Request();
                    postRequest.createRequest(AddEmployee.this, getResources().getString(R.string.mJSONURL_store), "POST", "activateKey", keyRequest);
                }else if (db.getStore().getKeyState().equals("ACTIVE")){
                    String keyRequest = "{\"reciever\":\"deactivateKey\",\"params\":{\"storeId\":\"" + db.getStore().getStoreId() + "\",\"joinKey\":\"" + joinKey.getText().toString() + "\"}}";
                    Log.d("myTag", "applicationRequest : " + keyRequest);
                    postRequest = new Volley_Request();
                    postRequest.createRequest(AddEmployee.this, getResources().getString(R.string.mJSONURL_store), "POST", "activateKey", keyRequest);
                }
            }
        });
    }

    public static void activateKeyResponse(String response) {
        db.updateActivationKeyStatus("ACTIVE", db.getStore().getStoreId());
        Intent i = new Intent(mActivity, DashboardActivity.class);
        i.putExtra("action", "FrontBack");
        mActivity.startActivity(i);
    }

    public static void deactivateKeyResponse(String response) {
        db.updateActivationKeyStatus("INACTIVE", db.getStore().getStoreId());
        Intent i = new Intent(mActivity, DashboardActivity.class);
        i.putExtra("action", "FrontBack");
        mActivity.startActivity(i);
    }
}
