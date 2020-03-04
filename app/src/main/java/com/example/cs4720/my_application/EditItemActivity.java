package com.example.cs4720.my_application;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

public class EditItemActivity extends AppCompatActivity {

    private ArrayList<BucketItem> bucketList;
    private Toolbar toolbar;
    private EditText editName;
    private EditText editDescription;
    private EditText editLatitude;
    private EditText editLongitude;
    private CalendarView calendar;
    private Date date;
    private Button saveNewItem;
    private boolean isChecked;
    protected String TAG = "EditItemAct";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate");

        setContentView(R.layout.activity_edit_item);

        toolbar = (Toolbar)findViewById(R.id.edit_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Edit");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        editName = (EditText) findViewById(R.id.edit_edit_name);
        editDescription = (EditText) findViewById(R.id.edit_edit_description);
        editLatitude = (EditText) findViewById(R.id.edit_edit_latitude);
        editLongitude = (EditText) findViewById(R.id.edit_edit_longitude);
        calendar = (CalendarView) findViewById(R.id.edit_edit_calendarView);
        saveNewItem = (Button) findViewById(R.id.edit_save_changes);


        //TODO: prepopulate info from BucketListActivity

        Intent data = getIntent();

        Bundle extraData = data.getExtras();
        if (extraData != null) {
            BucketItem bucketItem = (BucketItem) extraData.getSerializable("EditItem");
            isChecked = bucketItem.isChecked();
            date = bucketItem.getDate();
            long d = date.getTime();
            editName.setText(bucketItem.getTitle());
            editDescription.setText(bucketItem.getDescription());
            editLatitude.setText(Double.toString(bucketItem.getLatitude()));
            editLongitude.setText(Double.toString(bucketItem.getLongitude()));
            calendar.setDate(d);
        }

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                int actualYear = year - 1900;
                date = new Date(actualYear, month, dayOfMonth); // calendar defaults to YEAR = 1970, MONTH = JANUARY, DAY_OF_MONTH = 1
            }
        });

        Bundle exData = data.getExtras();
        if (extraData != null) {
            this.bucketList = (ArrayList<BucketItem>) exData.getSerializable("bucket_list");
        }
    }

    protected void onResume() {
        super.onResume();
        saveNewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = editName.getText().toString();
                String description = editDescription.getText().toString();
                String sLatitude = editLatitude.getText().toString();
                String sLongtitude = editLongitude.getText().toString();

                if (title.length() == 0 || description.length() == 0 || sLatitude.length() == 0 || sLongtitude.length() == 0) {
                    Toast.makeText(EditItemActivity.this, "You are missing fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                Double latitude = Double.parseDouble(editLatitude.getText().toString());
                Double longitude = Double.parseDouble(editLongitude.getText().toString());

                final BucketItem bucketItem = new BucketItem(title, isChecked, description, date, latitude, longitude);
                Intent saveNewItemIntent = new Intent(EditItemActivity.this, BucketListActivity.class);
                saveNewItemIntent.putExtra("item", bucketItem);
                setResult(RESULT_OK, saveNewItemIntent);
                finish();
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onBackPressed();
        return true;
    }

}
