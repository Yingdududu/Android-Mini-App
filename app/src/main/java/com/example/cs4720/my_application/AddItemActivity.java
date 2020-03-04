package com.example.cs4720.my_application;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;


public class AddItemActivity extends AppCompatActivity {


    private ArrayList<BucketItem> bucketList;
    private Toolbar toolbar;
    private EditText editName;
    private EditText editDescription;
    private EditText editLatitude;
    private EditText editLongitude;
    private CalendarView calendar;
    private Date date;
    private Button saveNewItem;
    protected String TAG = "AddItemAct";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        toolbar = (Toolbar) findViewById(R.id.addtoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Add");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editName = (EditText) findViewById(R.id.edit_name);
        editDescription = (EditText) findViewById(R.id.edit_description);
        editLatitude = (EditText) findViewById(R.id.edit_latitude);
        editLongitude = (EditText) findViewById(R.id.edit_longitude);
        calendar = (CalendarView) findViewById(R.id.edit_calendarView);
        saveNewItem = (Button) findViewById(R.id.save_new_item);

        date = new Date();

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                int actualYear = year - 1900;
                date = new Date(actualYear, month, dayOfMonth); // calendar defaults to YEAR = 1970, MONTH = JANUARY, DAY_OF_MONTH = 1
            }
        });

        Bundle extraData = getIntent().getExtras();
        if (extraData != null) {
            this.bucketList = (ArrayList<BucketItem>) extraData.getSerializable("bucket_list");
        }

    }
    protected void onStart() {
        super.onStart();
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
                    Toast.makeText(AddItemActivity.this, "You are missing fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                Double latitude = Double.parseDouble(editLatitude.getText().toString());
                Double longitude = Double.parseDouble(editLongitude.getText().toString());

                final BucketItem bucketItem = new BucketItem(title, false, description, date, latitude, longitude);

                Intent saveNewItemIntent = new Intent(AddItemActivity.this, BucketListActivity.class);
                saveNewItemIntent.putExtra("item", bucketItem);
                setResult(RESULT_OK, saveNewItemIntent);
//                startActivityForResult(saveNewItemIntent,0);
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /**Inflate the menu: adds items to the action bar if it is present. require menu_change_item.xml
         getMenuInflater().inflate(R.menu.menu_change_item, menu);**/
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onBackPressed();
        return true;
    }

}


