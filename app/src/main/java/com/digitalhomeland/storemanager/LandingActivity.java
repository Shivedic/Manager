package com.digitalhomeland.storemanager;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import com.digitalhomeland.storemanager.models.LocationObj;
import com.digitalhomeland.storemanager.models.Store;
import com.digitalhomeland.storemanager.Volley_Request;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Locale;

public class LandingActivity extends AppCompatActivity implements LocationListener{

    LocationManager locationManager;
    static Location testLoc;
    static String testAddress;
    private static Volley_Request postRequest;
    static DatabaseHandler db;
    String actionButton;
    static Context mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        mActivity = LandingActivity.this;
        db = new DatabaseHandler(this);
        //db.dropAllUserTables();
        //db.deleteEmp();
        //db.deleteStore();
        //db.deleteRoster();
        setNavigation();
        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
        }

        Button continueButton = (Button) findViewById(R.id.btn_do);
        continueButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Launch Time Picker Dialog
                actionButton = "1";
                getLocation();
            }

        });

        Button contiButton = (Button) findViewById(R.id.btn_conti);
        contiButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Launch Time Picker Dialog
                getLocation();
                actionButton = "2";
            }

        });

    }

    void getLocation() {
        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 5, (LocationListener) this);
        }
        catch(SecurityException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        //  locationText.setText("Latitude: " + location.getLatitude() + "\n Longitude: " + location.getLongitude());
        testLoc = location;
        try {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            Log.d("myTag","Latitude: " + location.getLatitude() + "\n Longitude: " + location.getLongitude() + "\n" + addresses.get(0).getAddressLine(0)+" : "+
                    addresses.get(0).getAddressLine(1)+" : "+addresses.get(0).getAddressLine(2) );
            testAddress = addresses.get(0).getAddressLine(0);
            if(actionButton == "1"){
                String req = "{\"reciever\":\"add\", \"params\" : {\"store\":{\"storeId\":\"store001\",\"empCount\":\"10\",\"name\":\"ABC corp\""+", \"address\":\"" + testAddress + "\"," + "\"lat\":\"" + location.getLatitude() + "\"," + "\"longi\":\"" + location.getLongitude() + "\"," + "\"city\":\"Bareilly\"}}}";
                postRequest = new Volley_Request();
                postRequest.createRequest(LandingActivity.this, getResources().getString(R.string.mJSONURL_store), "POST", "addStore", req);
            }
            else if(actionButton == "2"){
                String req = "{\"reciever\":\"empGet\",\"params\":{\"address\":\"" + testAddress + "\"}}";
                postRequest = new Volley_Request();
                postRequest.createRequest(LandingActivity.this, getResources().getString(R.string.mJSONURL_store), "POST", "loadStore", req);
            }
            }catch(Exception e)
        {
            e.printStackTrace();
            Log.d("myTag", "error : " , e);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(locationManager != null) {
            locationManager.removeUpdates(this);
        }
        Log.d("my/tag", "onPause, done");
    }

    public static void addStoreResponse(String responseString){
    }

    public static void getStoresResponse(String responseString){
        try{
            JSONObject responseObj = new JSONObject(responseString);

            //Store storeRes = new Store(responseObj.getString("_id"),responseObj.getString("storeId"),responseObj.getString("name"),responseObj.getString("city"),responseObj.getString("state"),responseObj.getString("lat"),responseObj.getString("longi"),responseObj.getString("address"),responseObj.getString("empCount"));
            Store storeRes = new Store(responseObj.getString("_id"),responseObj.getString("name"),responseObj.getString("storeId"),responseObj.getString("city"),responseObj.getString("state"),responseObj.getString("lat"),responseObj.getString("longi"),responseObj.getString("address"),responseObj.getString("empCount"),responseObj.getString("keyActive"),responseObj.getString("sellerId"),responseObj.getString("closingDay"),responseObj.getString("rosterGenDay"));
            Log.d("myTag", "storeaddr : " + storeRes.getAddress());
            db.addStores(storeRes);
            Log.d("myTag", "add store : " + db.getStore().getAddress());
            //List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            // Log.d("myTag", "sync:addingApplication: " + testAddress.getAddressLine(0).equals(locRes.getAddress()) + " : " + locRes.getLat().equals(testLoc.getLatitude()) + " : " + locRes.getLong().equals(testLoc.getLongitude()));
            Intent i = new Intent(mActivity, SignupActivity.class);
            mActivity.startActivity(i);
        }catch(JSONException e){
            Log.d("myTag", "json error " ,e);
        }
    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(LandingActivity.this, "Please Enable GPS and Internet", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onBackPressed() {
        // Do Here what ever you want do on back press;
    }

    @Override
    protected void onResume() {
        super.onResume();
        setNavigation();
    }


    public static void setNavigation(){
        if(db.getStore() != null && db.getUser() == null){
            //signup
            Intent i = new Intent(mActivity, SignupActivity.class);
            mActivity.startActivity(i);
        } else if (db.getStore() != null && db.getUser() != null && db.getUser().getTeamName().equals("")){
            //predashboard
            Intent i = new Intent(mActivity, PreDashboard.class);
            mActivity.startActivity(i);
        } else if(db.getStore() != null && db.getUser() != null && !db.getUser().getTeamName().equals("")){
            //dashboard
            Intent i = new Intent(mActivity, DashboardActivity.class);
            mActivity.startActivity(i);
        }
    }
}
