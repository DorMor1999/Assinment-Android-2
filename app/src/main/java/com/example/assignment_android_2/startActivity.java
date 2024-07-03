package com.example.assignment_android_2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

public class startActivity extends AppCompatActivity {


    private MaterialButton main_BTN_play;
    private MaterialButton main_BTN_records;
    private RadioButton radio_slow;
    private RadioButton radio_fast;
    private RadioButton radio_buttons;
    private RadioButton radio_sensors;
    private RadioGroup radio_group_game_speed;
    private RadioGroup radio_group_movement;
    private String gameSpeed;
    private String movement;

    private final String SLOW = "slow";
    private final String FAST = "fast";
    private final String BUTTONS = "buttons";
    private final String SENSORS = "sensors";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        findViews();
        initViews();
    }

    private void initViews() {
        //go to others activities
        main_BTN_play.setOnClickListener(v -> playClicked());
        main_BTN_records.setOnClickListener(v -> recordsClicked());




        radio_slow.setChecked(true); // Set "Slow" as the default checked RadioButton
        gameSpeed = SLOW;
        radio_buttons.setChecked(true); // Set "Buttons" as the default checked RadioButton
        movement = BUTTONS;
        // Dealing with changes in game options
        radio_group_game_speed.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.radio_slow) {
                gameSpeed = SLOW;
            } else if (checkedId == R.id.radio_fast) {
                gameSpeed = FAST;
            }
        });

        radio_group_movement.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.radio_buttons) {
                movement = BUTTONS;
            } else if (checkedId == R.id.radio_sensors) {
                movement = SENSORS;
            }
        });


    }

    private void recordsClicked() {
        //move to records activity
        Intent intent = new Intent(this, recordsActivity.class);
        startActivity(intent);
        finish();
    }

    private void playClicked(){
        Log.d("game speed", gameSpeed);
        Log.d("movement", movement);
        //move to game activity
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(MainActivity.KEY_GAME_SPEED, gameSpeed);
        intent.putExtra(MainActivity.KEY_MOVEMENT, movement);
        startActivity(intent);
        finish();
    }

    private void findViews() {
        main_BTN_play = findViewById(R.id.main_BTN_play);
        main_BTN_records = findViewById(R.id.main_BTN_records);
        radio_slow = findViewById(R.id.radio_slow);
        radio_fast = findViewById(R.id.radio_fast);
        radio_buttons = findViewById(R.id.radio_buttons);
        radio_sensors = findViewById(R.id.radio_sensors);
        radio_group_game_speed = findViewById(R.id.radio_group_game_speed);
        radio_group_movement = findViewById(R.id.radio_group_movement);

    }
}