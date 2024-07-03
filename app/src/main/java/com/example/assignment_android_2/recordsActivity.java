package com.example.assignment_android_2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.assignment_android_2.Data.RecordList;
import com.example.assignment_android_2.Data.SharePreferencesManager;
import com.example.assignment_android_2.Fragments.ListFragment;
import com.example.assignment_android_2.Fragments.MapFragment;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;

public class recordsActivity extends AppCompatActivity {

    private FrameLayout records_FRAME_list;
    private FrameLayout records_FRAME_map;

    private ListFragment listFragment;
    private MapFragment mapFragment;

    private MaterialButton menu_BTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);


        findViews();
        initViews();
    }

    private void findViews() {
        records_FRAME_list = findViewById(R.id.records_FRAME_list);
        records_FRAME_map = findViewById(R.id.records_FRAME_map);
        menu_BTN = findViewById(R.id.menu_BTN);
    }

    private void initViews(){
        menu_BTN.setOnClickListener(v -> menuClicked());
        listFragment = new ListFragment();

        //show fragments
        getSupportFragmentManager().beginTransaction().add(R.id.records_FRAME_list,listFragment).commit();
        mapFragment = new MapFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.records_FRAME_map,mapFragment).commit();
    }

    private void menuClicked() {
        //move to start activity
        Intent intent = new Intent(this, startActivity.class);
        startActivity(intent);
        finish();
    }
}