package com.codecademy.app.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.codecademy.app.R;
import com.codecademy.app.games.WhoIsSingerGame;
import com.codecademy.app.games.YesOrNoGame;
import com.codecademy.app.games.classic_game;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class SelectTypeOfGame extends AppCompatActivity {

    private ImageView classicButton, yesOrNoButton, whoIsSingerButton;

    private EditText number;

    private FloatingActionButton fab;

    LayoutInflater li;
    View promptsView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        windowSettings();
        setContentView(R.layout.activity_select_type_of_game);
        init();
        listeners();
        /*lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position)
                {
                    case 0:
                        makeDialog();


                        break;
                    case 1:
                        Intent intent2 = new Intent(SelectTypeOfGame.this, YesOrNoGame.class);
                        startActivity(intent2);
                        break;
                    case 2:
                        Intent intent3 = new Intent(SelectTypeOfGame.this, WhoIsSingerGame.class);
                        startActivity(intent3);
                        break;
                }
            }
        });*/
    }
    private void listeners(){
        classicButton.setOnClickListener(v -> makeFirstDialog());
        yesOrNoButton.setOnClickListener(v -> makeSecondDialog());
        whoIsSingerButton.setOnClickListener(v -> makeThirdDialog());
        fab.setOnClickListener(v -> {
            startActivity(new Intent(this, MainActivity.class));
        });


    }
    private void windowSettings(){
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
    private void init(){
        fab = findViewById(R.id.floatingActionButton);
        classicButton = findViewById(R.id.classicButton);
        yesOrNoButton = findViewById(R.id.yesOrNoButton);
        whoIsSingerButton = findViewById(R.id.whoIsSingerButton);
        li = LayoutInflater.from(SelectTypeOfGame.this);
        promptsView = li.inflate(R.layout.activity_alert_dialog, null);
        number = promptsView.findViewById(R.id.edText);


    }
    public void makeFirstDialog(){

        LayoutInflater li = LayoutInflater.from(SelectTypeOfGame.this);
        View promptsView = li.inflate(R.layout.activity_alert_dialog, null);
        AlertDialog.Builder mDialogBuilder = new AlertDialog.Builder(SelectTypeOfGame.this);
        mDialogBuilder.setView(promptsView);
        EditText userTime = promptsView.findViewById(R.id.edText);
        mDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        (dialog, which) -> {
                            Intent intent = new Intent(getBaseContext(), classic_game.class);
                            intent.putExtra("round", userTime.getText().toString());
                            startActivity(intent);
                        })
                .setNegativeButton("отмена",
                        (dialog, which) -> dialog.cancel());
        AlertDialog alertDialog = mDialogBuilder.create();
        alertDialog.show();

    }
    public void makeSecondDialog(){
        LayoutInflater li = LayoutInflater.from(SelectTypeOfGame.this);
        View promptsView = li.inflate(R.layout.activity_alert_dialog, null);
        AlertDialog.Builder mDialogBuilder = new AlertDialog.Builder(SelectTypeOfGame.this);
        mDialogBuilder.setView(promptsView);
        EditText userTime = promptsView.findViewById(R.id.edText);
        mDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        (dialog, which) -> {
                            Intent intent = new Intent(getBaseContext(), YesOrNoGame.class);
                            intent.putExtra("round", userTime.getText().toString());
                            startActivity(intent);
                        })
                .setNegativeButton("отмена",
                        (dialog, which) -> dialog.cancel());
        AlertDialog alertDialog = mDialogBuilder.create();
        alertDialog.show();
    }
    public void makeThirdDialog(){
        LayoutInflater li = LayoutInflater.from(SelectTypeOfGame.this);
        View promptsView = li.inflate(R.layout.activity_alert_dialog, null);
        AlertDialog.Builder mDialogBuilder = new AlertDialog.Builder(SelectTypeOfGame.this);
        mDialogBuilder.setView(promptsView);
        EditText userTime = promptsView.findViewById(R.id.edText);
        mDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        (dialog, which) -> {
                            Intent intent = new Intent(getBaseContext(), WhoIsSingerGame.class);
                            intent.putExtra("round", userTime.getText().toString());
                            startActivity(intent);
                        })
                .setNegativeButton("отмена",
                        (dialog, which) -> dialog.cancel());
        AlertDialog alertDialog = mDialogBuilder.create();
        alertDialog.show();
    }


}