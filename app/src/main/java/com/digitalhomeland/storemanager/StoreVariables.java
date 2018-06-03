package com.digitalhomeland.storemanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;

public class StoreVariables extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_variables);

        final DatabaseHandler db =  new DatabaseHandler(this);

        final TableRow textRow = (TableRow) findViewById(R.id.dow_text_row);
        final TableRow spinnerRow = (TableRow) findViewById(R.id.dow_spinner_row);
        final TextView dowText = (TextView) findViewById(R.id.dow_text);
        final Spinner dowSpinner = (Spinner) findViewById(R.id.dow_spinner);
        final Button editBtn = (Button) findViewById(R.id.edit_btn);

        editBtn.setText("Edit");
        ArrayAdapter<CharSequence> dowAdapter = ArrayAdapter.createFromResource(this, R.array.days_of_week, R.layout.spinner_second_page);
        dowAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dowSpinner.setAdapter(dowAdapter);

        spinnerRow.setVisibility(View.GONE);

        if(db.getStore().getClosingDay().equals("")){
            dowText.setText("Please Set");
        } else {
            dowText.setText(db.getStore().getClosingDay());
        }

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editBtn.getText().equals("Edit")){
                    textRow.setVisibility(View.GONE);
                    spinnerRow.setVisibility(View.VISIBLE);
                    editBtn.setText("Save");
                }else if(editBtn.getText().equals("Save")){
                    db.updateStoreClosing(db.getStore().getStoreId(),dowSpinner.getSelectedItem().toString());
                    spinnerRow.setVisibility(View.GONE);
                    textRow.setVisibility(View.VISIBLE);
                    dowText.setText(dowSpinner.getSelectedItem().toString());
                    editBtn.setText("Edit");
                }
            }
        });
    }
}
