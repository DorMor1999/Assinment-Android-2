package com.example.assignment_android_2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.assignment_android_2.Data.RecordList;
import com.example.assignment_android_2.Data.SharePreferencesManager;
import com.example.assignment_android_2.Fragments.ListFragment;
import com.example.assignment_android_2.Fragments.MapFragment;
import com.example.assignment_android_2.Interfaces.Callback_ListItemClicked;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;

public class recordsActivity extends AppCompatActivity implements Callback_ListItemClicked {

    private FrameLayout records_FRAME_list;
    private FrameLayout records_FRAME_map;

    private ListFragment listFragment;
    private MapFragment mapFragment;
    public static final String KEY_LAST_SCORE = "KEY_LAST_SCORE";

    private MaterialButton menu_BTN;
    private TextView last_score_label;

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
        last_score_label = findViewById(R.id.last_score_label);
    }

    private void initViews() {
        menu_BTN.setOnClickListener(v -> menuClicked());
        listFragment = new ListFragment();
        listFragment.setCallbackListItemClicked(this);
        checkLastScoreAndDisplay();

        // Show fragments
        getSupportFragmentManager().beginTransaction().add(R.id.records_FRAME_list, listFragment).commit();
        mapFragment = new MapFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.records_FRAME_map, mapFragment).commit();
    }

    @Override
    public void listItemClicked(double lat, double lon) {
        if (mapFragment != null) {
            mapFragment.zoom(lat, lon);
            Log.d("cords",  lat + " " + lon);
        }
    }

    private void checkLastScoreAndDisplay() {
        Intent previousIntent = getIntent();
        String lastScore = previousIntent.getStringExtra(KEY_LAST_SCORE);
        if (lastScore != null) {
            last_score_label.setText("Last score: " + lastScore);
            last_score_label.setVisibility(View.VISIBLE);
        } else {
            last_score_label.setVisibility(View.GONE);
        }
    }

    private void menuClicked() {
        // Move to start activity
        Intent intent = new Intent(this, startActivity.class);
        startActivity(intent);
        finish();
    }
}