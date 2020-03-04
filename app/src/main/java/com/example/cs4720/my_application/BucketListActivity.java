package com.example.cs4720.my_application;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

/** an Activity for the main list **/
public class BucketListActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    ArrayList<BucketItem> bucketList;
    private int currentPos;
    private String TAG = "BucketListAct";
    private ArrayList<Parcelable> List;


    /** onCreate() calls createInitialBucketList**/
    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bucket_list);

        toolbar = (Toolbar) findViewById(R.id.addtoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("BucketList");

        if (savedInstanceState != null) {
            Log.d(TAG, "RESTORE IN ONCREATE");
            if (savedInstanceState.getSerializable("list") != null) {
                bucketList = (ArrayList<BucketItem>) savedInstanceState.getSerializable("list");
            }
        } else {
            Log.d(TAG, "INITIALIZE");
            bucketList = createInitialBucketList();
        }

        recyclerView = (RecyclerView) findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new BucketListAdapter(bucketList);
        addEditItemFeature((BucketListAdapter) adapter);
        recyclerView.setAdapter(adapter);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BucketListActivity.this, AddItemActivity.class);
                intent.putExtra("bucket_list", bucketList);
                startActivityForResult(intent, 1);

            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        Log.d(TAG, "onResume");
        super.onResume();
        adapter.notifyItemRemoved(currentPos);
        adapter.notifyItemInserted(currentPos);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /**Inflate the menu: adds items to the action bar if it's present**/
        getMenuInflater().inflate(R.menu.menu_bucket_list, menu);
        return true;
    }

    public static Date parseDate(String date) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (ParseException e) {
            return null;
        }
    }


    /** Static method called createInitialBucketList() t
     * hat creates the initial, hard-coded bucket list ArrayList and returns it.
     * This will be called in the onCreate() in the BucketListActivity**/
    public static ArrayList<BucketItem> createInitialBucketList() {
        ArrayList<BucketItem> bucketList = new ArrayList<>();
        bucketList.add(new BucketItem("This is initial BucketList", false,"Create initial bucketList", parseDate("2018-09-17"),39.9,28.8 ));
        bucketList.add(new BucketItem("CS4720 homework", false, "Finish Android mini project", parseDate("2018-01-27"), 0, 0));
        bucketList.add(new BucketItem("Pick classes", false, "Ask Prof", parseDate("2018-01-30"), 150, 150));
        bucketList.add(new BucketItem("Graduate", false, "Plan housing for fam", parseDate("2018-05-19"), 100, 300));
        bucketList.add(new BucketItem("Taxes", false, "Learn and figure out", parseDate("2018-04-20"), 20.5, 100.4));
        return bucketList;
    }

    public void addEditItemFeature(BucketListAdapter adapter) {
        adapter.setOnItemClickListener(new BucketListAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {

                currentPos = position;
                String title = bucketList.get(position).getTitle().toString();
                String description = bucketList.get(position).getTitle().toString();
                Double sLatitude = bucketList.get(position).getLatitude();
                Double sLongtitude = bucketList.get(position).getLongitude();
                Date date = bucketList.get(position).getDate();
                boolean check = bucketList.get(position).isChecked();

                BucketItem bucketItem = new BucketItem(title, check, description, date, sLatitude, sLongtitude);
                Intent intent = new Intent(BucketListActivity.this, EditItemActivity.class);
                intent.putExtra("EditItem", bucketItem);
                intent.putExtra("bucket_list", bucketList);
                setResult(RESULT_OK, intent);
                startActivityForResult(intent, 0);

            }
        });
    }

    /** onActivityResult(int requestCode, int resultCode, Intent data)
     * to parse the result and add the new item to the ArrayList in this activity**/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0 && resultCode == RESULT_OK) {
            Bundle extraData = data.getExtras();
            if (extraData != null) {
                BucketItem bucketItem = (BucketItem) extraData.getSerializable("item"); //Obtaining data
                bucketList.set(currentPos, bucketItem);
                Collections.sort(bucketList);
                adapter.notifyItemRangeChanged(0, bucketList.size());
            }
        }

        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bundle extraData = data.getExtras();
            if (extraData != null) {
                BucketItem bucketItem = (BucketItem) extraData.getSerializable("item"); //Obtaining data
                bucketList.add(bucketItem);
                Collections.sort(bucketList);
                adapter.notifyItemRangeChanged(0, bucketList.size());
            }
        }
    }

    //TODO: save instance after rotation

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        Log.d(TAG, "Rotate");
        savedInstanceState.putSerializable("list", bucketList);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onPause(){
        super.onPause();
        Log.d(TAG,"onPause");
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.d(TAG,"onStop");
    }
}
