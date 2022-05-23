package com.codecademy.app.ui;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.codecademy.app.R;
import com.codecademy.app.db.DbStats;
import com.codecademy.app.db.models.StatsEntity1;
import com.codecademy.app.db.models.StatsEntity2;
import com.codecademy.app.db.models.StatsEntity3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Statistics extends AppCompatActivity {

    private TextView classicResult, yesOrNoResult, whoIsSingerResult;
    private DbStats dbStats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        init();
        set1Stats();
        set2Stats();
        set3Stats();


    }
    private void init(){
        classicResult = findViewById(R.id.classicResult);
        yesOrNoResult = findViewById(R.id.YesOrNoResult);
        whoIsSingerResult = findViewById(R.id.whoIsSingerResult);
        dbStats = DbStats.getInstance(this);
    }

    private void set1Stats(){
        List<Integer> stats1 = new ArrayList<>();
        List<StatsEntity1> statsEntity1 = dbStats.dao1().getStats1();
        for (int i = 0; i < statsEntity1.size(); i++){
            stats1.add(statsEntity1.get(i).getStats1());
        }
        if (stats1.size() != 0) {
            int max1 = Collections.max(stats1);
            String data = "Лучший результат - " + max1;
            classicResult.setText(data);
        }
    }

    private void set2Stats(){
        List<Integer> stats2 = new ArrayList<>();
        List<StatsEntity2> statsEntity2 = dbStats.dao2().getStats2();
        for (int i = 0; i < statsEntity2.size(); i++){
            stats2.add(statsEntity2.get(i).getStats2());
        }
        if (stats2.size() != 0) {
            int max1 = Collections.max(stats2);
            String data = "Лучший результат - " + max1;
            yesOrNoResult.setText(data);
        }
    }

    private void set3Stats(){
        List<Integer> stats3 = new ArrayList<>();
        List<StatsEntity3> statsEntity3 = dbStats.dao3().getStats3();
        for (int i = 0; i < statsEntity3.size(); i++){
            stats3.add(statsEntity3.get(i).getStats3());
        }
        if (stats3.size() != 0) {
            int max1 = Collections.max(stats3);
            String data = "Лучший результат - " + max1;
            whoIsSingerResult.setText(data);
        }
    }

}