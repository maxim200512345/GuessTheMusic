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
import com.codecademy.app.db.models.StatsEntity2;
import com.codecademy.app.models.SongsItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


public class WhoIsSingerGame extends AppCompatActivity {
    //ui
    private ImageButton btn1, btn2, btn3, btn4;
    private TextView rd, btnText1,btnText2,btnText3,btnText4, timerView;
    //logic
    private int reference;
    private String author;
    private List<SongsItem> list;
    private Random r;
    private MediaPlayer mPlayer;
    private AlertDialog myAlertDialog;
    private long userTime;
    private int round;
    private DbStats dbStats;
    private CountDownTimer timer;
    private static final int limit = 7;
    private static final String TAG = "MyTag";
    private int turn = 1, numForDb = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //only landscape rule
        windowSettings();

        setContentView(R.layout.activity_classic_game);
        //get from intent count of rounds
        String fromIntent = getIntent().getStringExtra("round");
        round = Integer.parseInt(fromIntent);
        ////////////////////////////////////
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
            if (btnText1.getText().toString().equalsIgnoreCase(author)){ // check if author of song equals it database element

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
            if (btnText2.getText().toString().equalsIgnoreCase(author)){

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
        btn3.setOnClickListener(v -> {
            turn++;
            timer.cancel();
            mPlayer.stop();
            if (btnText3.getText().toString().equalsIgnoreCase(author)){

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
            if (btnText4.getText().toString().equalsIgnoreCase(author)){

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
        //dbRoom
        dbStats = DbStats.getInstance(this);
        //last time
        timerView = findViewById(R.id.timerView);
        //list of DB songs
        list = new ArrayList<>();
    }
    private void clickCorrectSettings(){ // new question starting

        numForDb++;
        showPromIfTrueDialog(this);

    }
    private void clickFalseSettings(){ //new question starting
        if (numForDb > 0) numForDb--;
        showPromIfFalseDialog(this);

    }
    private void clickFinishSettings(){ // game finishing
        mPlayer.stop();
        timer.cancel();
        showDialog(WhoIsSingerGame.this);

    }

    private void windowSettings(){
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
    @SuppressLint("SetTextI18n")
    private void newQuestion(int number){
        //запуск песни
        if (turn >= round) {
            timer.cancel();
            mPlayer.stop();
            showDialog(WhoIsSingerGame.this);
        }
        SongsItem ref = list.get(number - 1);

        reference = ref.getRef();
        author = ref.getAuthor();
        /*String txt = "ваш результат - " + count;
        counter.setText(txt);*/
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
        int correct_song = r.nextInt(4) + 1;

        int firstButton = number - 1;
        int secondButton;
        int thirdButton;
        int fourthButton;

        switch (correct_song){
            case 1:
                btnText1.setText(list.get(firstButton).getAuthor());

                do {
                    secondButton = r.nextInt(list.size());
                }while (secondButton == firstButton);
                do {
                    thirdButton = r.nextInt(list.size());
                }while (thirdButton == firstButton || thirdButton == secondButton);
                do {
                    fourthButton = r.nextInt(list.size());
                }while (fourthButton == firstButton || fourthButton == secondButton || fourthButton == thirdButton);
                btnText2.setText(list.get(secondButton).getAuthor());
                btnText3.setText(list.get(thirdButton).getAuthor());
                btnText4.setText(list.get(fourthButton).getAuthor());
                break;
            case 2:
                btnText2.setText(list.get(firstButton).getAuthor());

                do {
                    secondButton = r.nextInt(list.size());
                }while (secondButton == firstButton);
                do {
                    thirdButton = r.nextInt(list.size());
                }while (thirdButton == firstButton || thirdButton == secondButton);
                do {
                    fourthButton = r.nextInt(list.size());
                }while (fourthButton == firstButton || fourthButton == secondButton || fourthButton == thirdButton);
                btnText1.setText(list.get(secondButton).getAuthor());
                btnText3.setText(list.get(thirdButton).getAuthor());
                btnText4.setText(list.get(fourthButton).getAuthor());
                break;
            case 3:
                btnText3.setText(list.get(firstButton).getAuthor());

                do {
                    secondButton = r.nextInt(list.size());
                }while (secondButton == firstButton);
                do {
                    thirdButton = r.nextInt(list.size());
                }while (thirdButton == firstButton || thirdButton == secondButton);
                do {
                    fourthButton = r.nextInt(list.size());
                }while (fourthButton == firstButton || fourthButton == secondButton || fourthButton == thirdButton);
                btnText2.setText(list.get(secondButton).getAuthor());
                btnText1.setText(list.get(thirdButton).getAuthor());
                btnText4.setText(list.get(fourthButton).getAuthor());
                break;
            case 4:
                btnText4.setText(list.get(firstButton).getAuthor());

                do {
                    secondButton = r.nextInt(list.size());
                }while (secondButton == firstButton);
                do {
                    thirdButton = r.nextInt(list.size());
                }while (thirdButton == firstButton || thirdButton == secondButton);
                do {
                    fourthButton = r.nextInt(list.size());
                }while (fourthButton == firstButton || fourthButton == secondButton || fourthButton == thirdButton);
                btnText2.setText(list.get(secondButton).getAuthor());
                btnText3.setText(list.get(thirdButton).getAuthor());
                btnText1.setText(list.get(fourthButton).getAuthor());
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
    }

    public void showDialog(Context context) {//Dialog if game end
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("игра окончена")
                .setMessage("отличный результат")
                .setIcon(R.mipmap.ic_launcher)
                .setPositiveButton("ОК", (dialog, id) -> {
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
                .setMessage("это был автор " + author +
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
    public void showPromIfFalseDialog(Context context){ // dialog if answer is incorrect
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("неверно")
                .setMessage("это был автор " + author +
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

                        showPromIfFalseDialog(getBaseContext());
                    }
                    else{

                        showDialog(WhoIsSingerGame.this);
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

                    mPlayer.stop();
                    turn++;
                    timer.cancel();
                    if (turn <= round) {
                        showPromIfFalseDialog(WhoIsSingerGame.this);
                    }
                    else{
                        showDialog(WhoIsSingerGame.this);
                    }

                }
            }.start();
        }

    }
    private void addToDb(int num){
        dbStats.dao2().insertResult(new StatsEntity2(num));
    }

}