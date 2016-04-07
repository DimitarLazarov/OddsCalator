package com.example.chav.poker.controller;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.chav.poker.R;

public class QuizSelectionActivity extends AppCompatActivity implements InstructionsQuizesFragment.QuizSelectionCallback{

    private Button mSpeedQuiz;
    private Button mCram;
    private Button mBasicQuiz;
    private boolean mBasicQuizInstructions = true;
    private boolean mSpeedQuizInstructions = true;
    private final String mInstructionsBasicQuiz = "BASIC ODDS QUIZ.\nYou have to choose the winner or the one who has more percentage to win " +
            "the hand. You choose with a tap on the player cards for player win and on board cards for tie. You have only 5 seconds to do this. The main " +
            "goal of this quiz is to make winstreak of winning hands.";
    private final String mInstructionsSpeedQuiz = "SPEED ODDS QUIZ.\nYou have to choose the winner or the one who has more percentage to win " +
            "the hand. You choose with a tap on the player cards for player win and on board cards for tie. You have 60 secnods. The main goal is " +
            " to choose as many as possible winning hands.";

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
                if (mSpeedQuizInstructions) {
                    InstructionsQuizesFragment instructionsFragment = new InstructionsQuizesFragment();
                    FragmentManager fm = getFragmentManager();
                    Bundle args = new Bundle();
                    args.putString("message", mInstructionsSpeedQuiz);
                    instructionsFragment.setArguments(args);
                    instructionsFragment.show(fm, "SpeedQuiz");
                }
                else {
                    Intent intent = new Intent(QuizSelectionActivity.this, SpeedQuizActivity.class);
                    startActivity(intent);
                }
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
                if (mBasicQuizInstructions) {
                    InstructionsQuizesFragment instructionsFragment = new InstructionsQuizesFragment();
                    FragmentManager fm = getFragmentManager();
                    Bundle args = new Bundle();
                    args.putString("message", mInstructionsBasicQuiz);
                    instructionsFragment.setArguments(args);
                    instructionsFragment.show(fm, "BasicQuiz");
                }
                else {
                    Intent intent = new Intent(QuizSelectionActivity.this, BasicQuizActivity.class);
                    startActivity(intent);
                }
            }
        });


    }

    @Override
    public void startBasicQuizGame(boolean instructions) {
        mBasicQuizInstructions = !instructions;
        Intent intent = new Intent(QuizSelectionActivity.this, BasicQuizActivity.class);
        startActivity(intent);
    }

    @Override
    public void startSpeedQuizGame(boolean instructions) {
        mSpeedQuizInstructions = !instructions;
        Intent intent = new Intent(QuizSelectionActivity.this, SpeedQuizActivity.class);
        startActivity(intent);
    }


}
