package com.example.chav.poker.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.chav.poker.R;
import com.example.chav.poker.managers.UsersManager;

import model.User;

public class StartScreenActivity extends AppCompatActivity {

    private Button mOddsButton;
    private Button mQuizButton;
    private Button mLoginButton;
    private Button mRegisterButton;
    private Button mSignOutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);

        mQuizButton = (Button) findViewById(R.id.quiz_button);
        mOddsButton = (Button) findViewById(R.id.odds_button);
        mLoginButton = (Button) findViewById(R.id.login_button);
        mRegisterButton = (Button) findViewById(R.id.register_button);
        mSignOutButton = (Button) findViewById(R.id.register_sing_out);

        if (SavedSharedPreferences.getUsername(this).length() != 0) {
            User user = UsersManager.getInstance(this).getUser(SavedSharedPreferences.getUsername(this));
            UsersManager.getInstance(this).setCurrentUser(user);
            mLoginButton.setVisibility(View.GONE);
            mRegisterButton.setVisibility(View.GONE);
            mSignOutButton.setVisibility(View.VISIBLE);
        }

        mOddsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartScreenActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        mQuizButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UsersManager.getInstance(v.getContext()).getCurrentUser() == null) {
                    Toast.makeText(v.getContext(), "Sign in to access quizes", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(v.getContext(), QuizSelectionActivity.class);
                startActivity(intent);
            }
        });

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });

        mSignOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UsersManager.getInstance(v.getContext()).signOut();
                mSignOutButton.setVisibility(View.GONE);
                mLoginButton.setVisibility(View.VISIBLE);
                mRegisterButton.setVisibility(View.VISIBLE);
                SavedSharedPreferences.clearUserName(v.getContext());
            }
        });
    }
}
