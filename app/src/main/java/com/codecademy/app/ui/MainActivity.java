package com.codecademy.app.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;

import com.codecademy.app.R;
import com.codecademy.app.db.DbStats;
import com.codecademy.app.db.models.StatsEntity1;
import com.codecademy.app.db.models.StatsEntity2;
import com.codecademy.app.db.models.StatsEntity3;

public class MainActivity extends AppCompatActivity {
    ImageButton btnStart;
    Button btnStats;
    Animation scaleUp, scaleDown;
    DbStats dbStats;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_App);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        dbStats = DbStats.getInstance(this);







        btnStats = findViewById(R.id.buttonStats);
        btnStart = (ImageButton) findViewById(R.id.buttonStart);
        scaleUp = AnimationUtils.loadAnimation(this, R.anim.scale_up);
        scaleDown = AnimationUtils.loadAnimation(this, R.anim.scale_down);
        btnStart.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN){
                btnStart.startAnimation(scaleUp);
                Intent intent = new Intent(MainActivity.this , SelectTypeOfGame.class);
                startActivity(intent);
            }else if(event.getAction() == MotionEvent.ACTION_UP){
                btnStart.startAnimation(scaleDown);
            }
            return true;
        });
        btnStats.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Statistics.class);
            startActivity(intent);
        });
    }
}