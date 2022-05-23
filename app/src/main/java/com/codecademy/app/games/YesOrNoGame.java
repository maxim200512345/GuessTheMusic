package com.codecademy.app.games;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.codecademy.app.R;
import com.codecademy.app.db.DatabaseYesOrNo;
import com.codecademy.app.db.DbStats;
import com.codecademy.app.db.models.StatsEntity3;
import com.codecademy.app.models.YesOrNoItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class YesOrNoGame extends AppCompatActivity {
    private ImageButton yesBtn, noBtn;
    private List<YesOrNoItem> items;
    private DatabaseYesOrNo db;
    private YesOrNoItem yesOrNoItem;
    private TextView textYes, textNo, question, roundView, timerView;
    private Random random;
    private CountDownTimer timer;
    private DbStats dbStats;
    private int numForDb = 0;

    private int correct_question,round, turn, counterForRound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_yes_or_no_game);
        init();
        //проблемный код после вылетает активность
        String fromIntent = getIntent().getStringExtra("round");
        round = Integer.parseInt(fromIntent);
        ////////////////////////////////////////////
        makingList();

        newQuestion(turn);
        listeners();

    }
    private void makingList(){
        for (int i = 0; i < new DatabaseYesOrNo().questions.length; i++) {
            items.add(new YesOrNoItem(new DatabaseYesOrNo().questions[i], new DatabaseYesOrNo().answers[i]));
        }
        Collections.shuffle(items);
    }
    private void listeners()
    {
        yesBtn.setOnClickListener(v -> {
            turn++;
            timer.cancel();
            if (textYes.getText().toString().equalsIgnoreCase(items.get(correct_question).getAnswer()))
            {

                if (turn <= round) {
                    numForDb++;
                    showPromIfTrueDialog(this);
                }
                else{

                    showDialog(YesOrNoGame.this);
                }
            }
            else{
                if (numForDb > 0) numForDb--;
                showPromIfFalseDialog(this);
            }
        });
        noBtn.setOnClickListener(v -> {
            turn++;
            timer.cancel();
            if (textNo.getText().toString().equalsIgnoreCase(items.get(correct_question).getAnswer()))
            {

                if (turn <= round) {
                    numForDb++;
                    showPromIfTrueDialog(this);

                }
                else{
                    showDialog(YesOrNoGame.this);
                }
            }
            else{
                if (numForDb > 0) numForDb--;
                showPromIfFalseDialog(this);
            }
        });
    }

    private void init() {
        textYes = findViewById(R.id.textYes);
        yesBtn = findViewById(R.id.yesBtn);
        noBtn = findViewById(R.id.noBtn);
        textNo = findViewById(R.id.textNo);
        question = findViewById(R.id.question);
        timerView = findViewById(R.id.timerView);
        items = new ArrayList<>();
        db = new DatabaseYesOrNo();
        random = new Random();
        roundView = findViewById(R.id.rd);
        turn = 1;
        dbStats = DbStats.getInstance(this);
        counterForRound = 1;
        textYes.setText("yes");
        textNo.setText("no");
    }

    @SuppressLint("SetTextI18n")
    private void newQuestion(int number){

        if (turn >= round) showDialog(this);
        startTimer();
        roundView.setText(Integer.toString(turn));
        correct_question = random.nextInt(4) + 1;
        question.setText(items.get(correct_question).getQuestion());
        int firstButton = number - 1;
        int secondButton;


        switch (correct_question){
            case 1:
                textYes.setText("yes");
                textNo.setText("no");
                break;
            case 2:
                textNo.setText("yes");
                textYes.setText("no");
                break;

        }
    }
    private void showDialog(Context context){
        timer.cancel();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("игра окончена")
                .setMessage("отличный результат")
                .setIcon(R.mipmap.ic_launcher)
                .setPositiveButton("ОК", (dialog, id) -> {
                    // Закрываем окно
                    addToDb(numForDb);
                    dialog.dismiss();
                    finish();

                });
        if(!((Activity) context).isFinishing())
        {
            builder.show();
        }

    }
    public  void startTimer(){
        if (timer == null){
            timer = new CountDownTimer(15000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    String times = "осталось до конца раунда - " + millisUntilFinished / 1000;
                    timerView.setText(times);
                }

                @Override
                public void onFinish() {

                    timer.cancel();
                    turn++;
                    if (turn <= round) {
                        showPromIfFalseDialog(YesOrNoGame.this);
                    }
                    else{
                        showDialog(YesOrNoGame.this);
                    }
                }
            }.start();
        }
        else{
            timer.cancel();
            timer = new CountDownTimer(15000, 1000) {
                @SuppressLint("SetTextI18n")
                @Override
                public void onTick(long millisUntilFinished) {
                    timerView.setText("осталось до конца раунда - " + millisUntilFinished / 1000);
                }

                @Override
                public void onFinish() {

                    turn++;
                    timer.cancel();
                    if (turn <= round) {
                        showPromIfFalseDialog(YesOrNoGame.this);
                    }
                    else {
                        showDialog(YesOrNoGame.this);
                        Toast.makeText(YesOrNoGame.this, "finish", Toast.LENGTH_SHORT).show();
                    }

                }
            }.start();
        }

    }
    public void showPromIfTrueDialog(Context context){ // dialog if answer is correct
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("верно")
                .setMessage("вы правы," +
                        "продолжаем?")
                .setIcon(R.mipmap.ic_launcher)
                .setPositiveButton("да", (dialog, id) -> {
                    // Закрываем окно
                    newQuestion(turn);
                    dialog.dismiss();


                });
        builder.show();
    }
    public void showPromIfFalseDialog(Context context){ // dialog if answer is incorrect
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("неверно")
                .setMessage("вы ошиблись," +
                        "продолжаем?")
                .setIcon(R.mipmap.ic_launcher)
                .setPositiveButton("да", (dialog, id) -> {
                    // Закрываем окно
                    newQuestion(turn);
                    dialog.dismiss();
                });
        builder.show();
    }
    private void addToDb(int num){
        dbStats.dao3().insertResult(new StatsEntity3(num));
    }
}