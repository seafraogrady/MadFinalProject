package com.example.neswtilt;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.BreakIterator;
import java.text.CollationElementIterator;
import java.util.ArrayList;
import java.util.List;

public class HighScores extends AppCompatActivity {
ListView highScore;
    ListView lv;

    TextView textView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DatabaseHandler db = new DatabaseHandler(this);

        List<HighScoreClass> highscore = db.getAllHighscore();
        setContentView(R.layout.activity_high_scores);
highScore = findViewById(R.id.listview1);


        Intent i = getIntent();
        String highScores = getIntent().getStringExtra("hiscore");

        textView2.setText(highScores);
    }
    public void getHighScore(View view) {

        String name;
        String highscore;


        DatabaseHandler db = new DatabaseHandler(this);
        int  userCount = db.getHighscoreCount();


        Log.i("User count: ", String.valueOf(userCount));

        List<HighScoreClass> highscoreList = db.getAllHighscore();
        highscoreList = db.top5Highscore();
        List<String> highScoresStr;
        highScoresStr = new ArrayList<>();

        int j = 1;
        for (HighScoreClass cn2 : highscoreList) {


            String log = "Id: " + cn2.getID() + " ,Name: " + cn2.getName() + " ,Highscore: " +
                    cn2.getHighscore();

            //
            highScoresStr.add(j++ + ":" +

                    cn2.getName() + "\t" +
                    cn2.getHighscore());
            //if(cn2.getHighscore() >= )
            Log.i("Score: ", log);

        }
        Log.i("divider", "==================");
        Log.i("divider","Scores in list<>>");
        for (String ss : highScoresStr) {
            Log.i("Score:", ss);
        }
        ArrayAdapter itemsAdapter =
                new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, highScoresStr);
        lv.setAdapter(itemsAdapter);
    }



}