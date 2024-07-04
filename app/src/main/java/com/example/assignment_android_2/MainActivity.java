package com.example.assignment_android_2;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import com.example.assignment_android_2.Data.RecordList;
import com.example.assignment_android_2.Data.Record;
import com.example.assignment_android_2.Data.SharePreferencesManager;
import com.example.assignment_android_2.Interfaces.MoveCallback;
import com.example.assignment_android_2.Logic.GameManager;
import com.example.assignment_android_2.Utilities.MoveDetector;
import com.example.assignment_android_2.Utilities.SignalManager;
import com.example.assignment_android_2.Utilities.SoundPlayer;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textview.MaterialTextView;
import com.google.gson.Gson;

import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity {

    public static final String KEY_GAME_SPEED = "KEY_GAME_SPEED";
    public static final String KEY_MOVEMENT = "KEY_MOVEMENT";
    private AppCompatImageView[] main_IMG_hearts;
    private ExtendedFloatingActionButton main_FAB_left_arrow;
    private ExtendedFloatingActionButton main_FAB_right_arrow;
    private AppCompatImageView[][] main_matrix_IMG;
    private AppCompatImageView[] main_cols_IMG_cars;
    private MaterialTextView main_LBL_points;
    private GameManager gameManager;
    private final long DELAYSLOW = 1000L;
    private final long DELAYFAST = 750L;
    private static long DELAY;
    private long startTime;

    private boolean timerOn = false;

    private Timer timer;

    private final String REASON_OTHER = "other";
    private final String REASON_TIMER = "timer";
    private SoundPlayer soundPlayerCrush;
    private MoveDetector moveDetector;
    private String gameSpeed;
    private String movement;
    private final String SLOW = "slow";
    private final String FAST = "fast";
    private final String BUTTONS = "buttons";
    private final String SENSORS = "sensors";
    private final String KEY_RECORDS_SPM = "recordList";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();
        gameManager = new GameManager(main_IMG_hearts.length, main_matrix_IMG.length, main_matrix_IMG[0].length);
        decideGameMods();
        initViews();
        initMoveDetector();
        initDelay();
    }

    private void decideGameMods() {
        Intent previousIntent = getIntent();
        gameSpeed = previousIntent.getStringExtra(KEY_GAME_SPEED);
        movement = previousIntent.getStringExtra(KEY_MOVEMENT);
    }

    private void initDelay(){
        if (gameSpeed.equals(FAST)){
            DELAY = DELAYFAST;
        } else if (gameSpeed.equals(SLOW)) {
            DELAY = DELAYSLOW;
        }
    }

    private void initMoveDetector() {
        if (!movement.equals(SENSORS)){
            return;
        }
        else{
            moveDetector = new MoveDetector(this,
                    new MoveCallback() {
                        @Override
                        public void moveLeft() {
                            // what to do if x move left
                            moveClicked("left");
                        }

                        @Override
                        public void moveRight() {
                            // what to do if x move right
                            moveClicked("right");
                        }
                    }
            );
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (movement.equals(SENSORS)){
            moveDetector.start();
        }
        startTimer();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (movement.equals(SENSORS)){
            moveDetector.stop();
        }
        stopTimer();
    }

    private void findViews() {
        //array of hearts
        main_IMG_hearts = new AppCompatImageView[]{
                findViewById(R.id.main_IMG_heart1),
                findViewById(R.id.main_IMG_heart2),
                findViewById(R.id.main_IMG_heart3)
        };

        //arrows buttons
        main_FAB_left_arrow = findViewById(R.id.main_FAB_left_arrow);
        main_FAB_right_arrow = findViewById(R.id.main_FAB_right_arrow);

        //matrix of barriers
        main_matrix_IMG = new AppCompatImageView[][]{
                {
                        findViewById(R.id.main_matrix_pos_00),
                        findViewById(R.id.main_matrix_pos_01),
                        findViewById(R.id.main_matrix_pos_02),
                        findViewById(R.id.main_matrix_pos_03),
                        findViewById(R.id.main_matrix_pos_04)
                },
                {
                        findViewById(R.id.main_matrix_pos_10),
                        findViewById(R.id.main_matrix_pos_11),
                        findViewById(R.id.main_matrix_pos_12),
                        findViewById(R.id.main_matrix_pos_13),
                        findViewById(R.id.main_matrix_pos_14)
                },
                {
                        findViewById(R.id.main_matrix_pos_20),
                        findViewById(R.id.main_matrix_pos_21),
                        findViewById(R.id.main_matrix_pos_22),
                        findViewById(R.id.main_matrix_pos_23),
                        findViewById(R.id.main_matrix_pos_24)
                },
                {
                        findViewById(R.id.main_matrix_pos_30),
                        findViewById(R.id.main_matrix_pos_31),
                        findViewById(R.id.main_matrix_pos_32),
                        findViewById(R.id.main_matrix_pos_33),
                        findViewById(R.id.main_matrix_pos_34)
                },
                {
                        findViewById(R.id.main_matrix_pos_40),
                        findViewById(R.id.main_matrix_pos_41),
                        findViewById(R.id.main_matrix_pos_42),
                        findViewById(R.id.main_matrix_pos_43),
                        findViewById(R.id.main_matrix_pos_44)
                },
                {
                        findViewById(R.id.main_matrix_pos_50),
                        findViewById(R.id.main_matrix_pos_51),
                        findViewById(R.id.main_matrix_pos_52),
                        findViewById(R.id.main_matrix_pos_53),
                        findViewById(R.id.main_matrix_pos_54)
                },
                {
                        findViewById(R.id.main_matrix_pos_60),
                        findViewById(R.id.main_matrix_pos_61),
                        findViewById(R.id.main_matrix_pos_62),
                        findViewById(R.id.main_matrix_pos_63),
                        findViewById(R.id.main_matrix_pos_64)
                }
                ,
                {
                        findViewById(R.id.main_matrix_pos_70),
                        findViewById(R.id.main_matrix_pos_71),
                        findViewById(R.id.main_matrix_pos_72),
                        findViewById(R.id.main_matrix_pos_73),
                        findViewById(R.id.main_matrix_pos_74)
                }
                ,
                {
                        findViewById(R.id.main_matrix_pos_80),
                        findViewById(R.id.main_matrix_pos_81),
                        findViewById(R.id.main_matrix_pos_82),
                        findViewById(R.id.main_matrix_pos_83),
                        findViewById(R.id.main_matrix_pos_84)
                }
                ,
                {
                        findViewById(R.id.main_matrix_pos_90),
                        findViewById(R.id.main_matrix_pos_91),
                        findViewById(R.id.main_matrix_pos_92),
                        findViewById(R.id.main_matrix_pos_93),
                        findViewById(R.id.main_matrix_pos_94)
                }

        };

        //array of cols cars
        main_cols_IMG_cars = new AppCompatImageView[]{
                findViewById(R.id.main_col_car_0),
                findViewById(R.id.main_col_car_1),
                findViewById(R.id.main_col_car_2),
                findViewById(R.id.main_col_car_3),
                findViewById(R.id.main_col_car_4)
        };

        //points text view
        main_LBL_points = findViewById(R.id.main_LBL_points);
    }

    private void initViews() {
        if (!movement.equals(BUTTONS)){
            main_FAB_left_arrow.setVisibility(View.INVISIBLE);
            main_FAB_right_arrow.setVisibility(View.INVISIBLE);
        }
        else {
            main_FAB_left_arrow.setOnClickListener(v -> moveClicked("left"));
            main_FAB_right_arrow.setOnClickListener(v -> moveClicked("right"));
        }
    }

    private void moveClicked(String direction) {
        gameManager.moveCar(direction);
        refreshUI(REASON_OTHER);
    }

    private void refreshUI(String reason){
        //lost
        if(gameManager.isGameLost()){
            Log.d("lost","lost");
            stopTimer();
            updateRecords();
            moveToRecordActivity();
            return;
        }
        //game on
        else{
            //move car part
            showCarUI();
            //update matrix
            updateMatrixUI();
            //update hearts
            updateHeartsUI();
            //update points
            updatePointsUI(reason);

        }
    }

    private void moveToRecordActivity() {
        //move to game activity
        Intent intent = new Intent(this, recordsActivity.class);
        intent.putExtra(recordsActivity.KEY_LAST_SCORE, String.valueOf(gameManager.getPoints()));
        startActivity(intent);
        finish();
    }

    ;

    private void updateRecords(){
        Gson gson = new Gson();
        //getting data
        String recordListAsJson = SharePreferencesManager
                .getInstance()
                .getString(KEY_RECORDS_SPM, "");
        RecordList recordList = gson.fromJson(recordListAsJson, RecordList.class);
        if (recordList == null){
            recordList = new RecordList();
        }
        Record newRecord = new Record(gameManager.getPoints(), 32.1129923, 34.8182147);
        recordList.addRecord(newRecord);
        Log.d("RecordList", recordList.toString());
        String newRecordListAsJson = gson.toJson(recordList);
        //saving data
        SharePreferencesManager
                .getInstance()
                .putString(KEY_RECORDS_SPM, newRecordListAsJson);
    }

    private void updatePointsUI(String reason){
        // +1 every timer
        if (reason.equals(REASON_TIMER)){
            gameManager.updatePointsByTimer();
        }
        // +5 every money collision
        gameManager.updatePointsByMoney();
        main_LBL_points.setText(String.valueOf(gameManager.getPoints()));
    }

    private void updateHeartsUI(){
        boolean crushNow = gameManager.checkCrushAndUpdateLivesAndNumCrushes();
        if (crushNow){
            soundPlayerCrush = new SoundPlayer(this);
            soundPlayerCrush.playSound(R.raw.car_crash);
            toastAndVibrate("crush " + gameManager.getNumCrushes() +" !!!");
            main_IMG_hearts[gameManager.getNumCrushes() - 1].setVisibility(View.INVISIBLE);
            soundPlayerCrush.stopSound();
        }

    }
    private void updateMatrixUI(){
        for (int i = 0; i < gameManager.getMatrixRows(); i++) {
            for (int j = 0; j < gameManager.getMatrixCols(); j++) {
                String currentCol = gameManager.getMatrix()[i][j];
                if(currentCol.equals(gameManager.getNONE())){
                    main_matrix_IMG[i][j].setVisibility(View.INVISIBLE);
                } else if (currentCol.equals(gameManager.getBARRIER())) {
                    main_matrix_IMG[i][j].setImageResource(R.drawable.barrier);
                    main_matrix_IMG[i][j].setVisibility(View.VISIBLE);
                }else if (currentCol.equals(gameManager.getCOINS())) {
                    main_matrix_IMG[i][j].setImageResource(R.drawable.coins);
                    main_matrix_IMG[i][j].setVisibility(View.VISIBLE);
                }
            }
        }
    }

    private void showCarUI(){
        int currentCarPos = gameManager.getCarPosition();
        Log.d("pos","" + currentCarPos);
        for (int i = 0; i < gameManager.getMatrixCols(); i++){
            if(i == currentCarPos){
                main_cols_IMG_cars[i].setVisibility(View.VISIBLE);
            }
            else{
                main_cols_IMG_cars[i].setVisibility(View.INVISIBLE);
            }
        }
    }

    private void changeMatrixTime(){
        gameManager.matrixChangePeriod();
        refreshUI(REASON_TIMER);
    }

    private void startTimer() {
        if (!timerOn) {
            Log.d("startTimer", "startTimer: Timer Started");
            startTime = System.currentTimeMillis();
            timerOn = true;
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(() -> changeMatrixTime());
                }
            },0L,DELAY);
        }
    }

    private void stopTimer() {
        timerOn = false;
        Log.d("stopTimer", "stopTimer: Timer Stopped");
        timer.cancel();
    }

    private void toastAndVibrate(String text) {
        SignalManager.getInstance().vibrate(500);
        SignalManager.getInstance().toast(text);
    }

}