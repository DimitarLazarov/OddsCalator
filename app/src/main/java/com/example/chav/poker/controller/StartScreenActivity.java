package com.example.chav.poker.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.chav.poker.R;

public class StartScreenActivity extends AppCompatActivity {

    private Button mOddsButton;
    private Button mQuizButton;
    private Button mLoginButton;
    private Button mRegisterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);

        mOddsButton = (Button) findViewById(R.id.odds_button);
        mOddsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartScreenActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        mQuizButton = (Button) findViewById(R.id.quiz_button);
        mQuizButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), QuizSelectionActivity.class);
                startActivity(intent);
            }
        });

        mLoginButton = (Button) findViewById(R.id.login_button);
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mRegisterButton = (Button) findViewById(R.id.register_button);
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
