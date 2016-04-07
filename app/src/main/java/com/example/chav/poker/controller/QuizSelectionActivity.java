package com.example.chav.poker.controller;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.chav.poker.R;

public class QuizSelectionActivity extends AppCompatActivity {

    private Button mSpeedQuiz;
    private Button mCram;
    private Button mBasicQuiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_selection);

        Typeface myTypeface = Typeface.createFromAsset(getAssets(), "HelveticaRoman.ttf");

        mSpeedQuiz = (Button) findViewById(R.id.speed_quiz);
        mSpeedQuiz.setTypeface(myTypeface);
        mSpeedQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), SpeedQuizActivity.class);
                startActivity(i);
            }
        });
        mCram = (Button) findViewById(R.id.cram_cards);
        mCram.setTypeface(myTypeface);
        mCram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), CramCardsUserActivity.class);
                startActivity(i);
            }
        });
        mBasicQuiz = (Button) findViewById(R.id.basic_quiz);
        mBasicQuiz.setTypeface(myTypeface);
        mBasicQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), BasicQuizActivity.class);
                startActivity(i);
            }
        });


    }
}
