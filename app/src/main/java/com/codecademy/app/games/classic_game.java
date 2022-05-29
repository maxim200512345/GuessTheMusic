package com.codecademy.app.games;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.codecademy.app.R;
import com.codecademy.app.db.Database;
import com.codecademy.app.db.DbStats;
import com.codecademy.app.db.models.StatsEntity1;
import com.codecademy.app.models.SongsItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


public class classic_game extends AppCompatActivity {
    //ui
    private ImageButton btn1, btn2, btn3, btn4;
    private TextView counter, rd, btnText1,btnText2,btnText3,btnText4, timerView;
    //logic
    private List<SongsItem> list;
    private Random r;
    private MediaPlayer mPlayer;
    private AlertDialog myAlertDialog;
    private long userTime;
    private int round, correct_song, reference;
    private String name;
    private CountDownTimer timer;
    private static final int limit = 7;
    private int count, number;
    private static final String TAG = "myTag";
    private int turn = 1;
    private DbStats dbStats;
    private int numForDb = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //only landscape rule
        windowSettings();

        setContentView(R.layout.activity_classic_game);
        //get from intent count of rounds
        String fromIntent = getIntent().getStringExtra("round");
        round = Integer.parseInt(fromIntent);
        //initialize data
        init();
        /*Log.d(TAG, "round - " + round);*/

        //filling List of songs
        for(int i = 0; i < new Database().namesOfSongs.length; i++){
            list.add(new SongsItem(new Database().namesOfSongs[i], new Database().songs[i], new Database().authors[i]));
        }
        Collections.shuffle(list);
        newQuestion(turn);
        btn1.setOnClickListener(v -> {
            turn++;
            timer.cancel();
            mPlayer.stop();
            if (btnText1.getText().toString().equalsIgnoreCase(name)){ // check if name of song equals it database element
                if(turn <= round){
                    clickCorrectSettings();
                }
                else{
                    clickFinishSettings();

                }
            }
            else{
                clickFalseSettings();

            }
        });
        btn2.setOnClickListener(v -> {
            turn++;
            timer.cancel();
            mPlayer.stop();
            if (btnText2.getText().toString().equalsIgnoreCase(name)){
                if(turn <= round) clickCorrectSettings();
                else{
                    clickFinishSettings();

                }
            }
            else{
                clickFalseSettings();
            }
        });
        btn3.setOnClickListener(v -> {
            turn++;
            timer.cancel();
            mPlayer.stop();
            if (btnText3.getText().toString().equalsIgnoreCase(name)){
                if(turn <= round){
                    clickCorrectSettings();

                }
                else{
                    clickFinishSettings();

                }
            }
            else{
                clickFalseSettings();
            }
        });
        btn4.setOnClickListener(v -> {
            turn++;
            timer.cancel();
            mPlayer.stop();
            if (btnText4.getText().toString().equalsIgnoreCase(name)){

                if(turn <= round){
                    clickCorrectSettings();

                }
                else{
                    clickFinishSettings();


                }
            }
            else{
                clickFalseSettings();

            }
        });

    }
    private void init(){
        r = new Random();
        //texts on ImageButton
        btnText1 = findViewById(R.id.btntext1);
        btnText2 = findViewById(R.id.btntext2);
        btnText3 = findViewById(R.id.btntext3);
        btnText4 = findViewById(R.id.btntext4);
        /////////////////////////////////////
        // ImageButtons
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
        ////////////////////////////////////
        //header values
        //current number of round
        rd = findViewById(R.id.rd);
        //last time
        timerView = findViewById(R.id.timerView);
        //list of DB songs
        list = new ArrayList<>();
        dbStats = DbStats.getInstance(this);
    }
    private void clickCorrectSettings(){ // new question starting
        numForDb++;
        showPromIfTrueDialog(this);


    }
    private void clickFalseSettings(){
        if (numForDb > 0) numForDb--;
        showPromIfFalseDialog(this);

    }
    private void clickFinishSettings(){ // game finishing
        timer.cancel();
        mPlayer.stop();
        showDialog(classic_game.this);

    }

    private void windowSettings(){
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
    private void newQuestion(int number){
        //запуск песни
        if (turn >= round){
            timer.cancel();
            mPlayer.stop();
            showDialog(classic_game.this);
        }
        SongsItem ref = list.get(number - 1);

        reference = ref.getRef();
        name = ref.getName();
        String txt = "ваш результат - " + count;
        try {
            counter.setText(txt);
        }catch (NullPointerException e){

        }

        startTimer();
        /*String text = "раунд " + counterForRound +  "/ " + round;
        rd.setText(text);*/
        rd.setText(Integer.toString(turn));
        //make music player
        mPlayer= MediaPlayer.create(this, reference);
        mPlayer.setOnPreparedListener(mp -> {
            //start music
            mPlayer.setLooping(true);
            mPlayer.start();
        });
        //inserting answers to button
        correct_song = r.nextInt(4) + 1;

        int firstButton = number - 1;
        int secondButton;
        int thirdButton;
        int fourthButton;

        switch (correct_song){
            case 1:
                btnText1.setText(list.get(firstButton).getName());

                do {
                    secondButton = r.nextInt(list.size());
                }while (secondButton == firstButton);
                do {
                    thirdButton = r.nextInt(list.size());
                }while (thirdButton == firstButton || thirdButton == secondButton);
                do {
                    fourthButton = r.nextInt(list.size());
                }while (fourthButton == firstButton || fourthButton == secondButton || fourthButton == thirdButton);
                btnText2.setText(list.get(secondButton).getName());
                btnText3.setText(list.get(thirdButton).getName());
                btnText4.setText(list.get(fourthButton).getName());
                break;
            case 2:
                btnText2.setText(list.get(firstButton).getName());

                do {
                    secondButton = r.nextInt(list.size());
                }while (secondButton == firstButton);
                do {
                    thirdButton = r.nextInt(list.size());
                }while (thirdButton == firstButton || thirdButton == secondButton);
                do {
                    fourthButton = r.nextInt(list.size());
                }while (fourthButton == firstButton || fourthButton == secondButton || fourthButton == thirdButton);
                btnText1.setText(list.get(secondButton).getName());
                btnText3.setText(list.get(thirdButton).getName());
                btnText4.setText(list.get(fourthButton).getName());
                break;
            case 3:
                btnText3.setText(list.get(firstButton).getName());

                do {
                    secondButton = r.nextInt(list.size());
                }while (secondButton == firstButton);
                do {
                    thirdButton = r.nextInt(list.size());
                }while (thirdButton == firstButton || thirdButton == secondButton);
                do {
                    fourthButton = r.nextInt(list.size());
                }while (fourthButton == firstButton || fourthButton == secondButton || fourthButton == thirdButton);
                btnText2.setText(list.get(secondButton).getName());
                btnText1.setText(list.get(thirdButton).getName());
                btnText4.setText(list.get(fourthButton).getName());
                break;
            case 4:
                btnText4.setText(list.get(firstButton).getName());

                do {
                    secondButton = r.nextInt(list.size());
                }while (secondButton == firstButton);
                do {
                    thirdButton = r.nextInt(list.size());
                }while (thirdButton == firstButton || thirdButton == secondButton);
                do {
                    fourthButton = r.nextInt(list.size());
                }while (fourthButton == firstButton || fourthButton == secondButton || fourthButton == thirdButton);
                btnText2.setText(list.get(secondButton).getName());
                btnText3.setText(list.get(thirdButton).getName());
                btnText1.setText(list.get(fourthButton).getName());
                break;
        }
    }
    @Override
    public void onPause() {
        super.onPause();
        mPlayer.stop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPlayer.stop();
        timer.cancel();
    }

    public void showDialog(Context context) {//Dialog if game end
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
        try {
            builder.show();
        }
        catch (WindowManager.BadTokenException e) {
            //use a log message
        }

    }
    public void showPromIfTrueDialog(Context context){ // dialog if answer is correct
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("верно")
                .setMessage("это была песня " + name +
                        "продолжаем?")
                .setIcon(R.mipmap.ic_launcher)
                .setPositiveButton("да", (dialog, id) -> {
                    newQuestion(turn);
                    dialog.dismiss();
                });
        try {
            builder.show();
        }
        catch (WindowManager.BadTokenException e) {
            //use a log message
        }
    }
    public void showPromIfFalseDialog(Context context){ // dialog if answer is incorrect
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("неверно")
                .setMessage("это была песня  " + name +
                        " продолжаем?")
                .setIcon(R.mipmap.ic_launcher)
                .setPositiveButton("да", (dialog, id) -> {
                    newQuestion(turn);
                    dialog.dismiss();
                });
        try {
            builder.show();
        }
        catch (WindowManager.BadTokenException e) {
            //use a log message
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

                    mPlayer.stop();
                    turn++;
                    timer.cancel();
                    if (turn <= round) {
                        showPromIfFalseDialog(classic_game.this);
                    }
                    else{
                        showDialog(classic_game.this);
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
                    mPlayer.stop();
                    timer.cancel();
                    if (turn <= round) {
                        showPromIfFalseDialog(classic_game.this);
                    }else{
                        showDialog(classic_game.this);
                    }

                }
            }.start();
        }


    }
    private void addToDb(int num){
        dbStats.dao1().insertResult(new StatsEntity1(num));
    }
}