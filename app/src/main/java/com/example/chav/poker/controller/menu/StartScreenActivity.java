package com.example.chav.poker.controller.menu;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chav.poker.R;
import com.example.chav.poker.controller.SavedSharedPreferences;
import com.example.chav.poker.controller.calculator.OddsCalatorActivity;
import com.example.chav.poker.managers.UsersManager;

import model.User;

public class StartScreenActivity extends AppCompatActivity {

    private Button mOddsButton;
    private Button mQuizButton;
    private Button mLoginButton;
    private Button mRegisterButton;
    private Button mSignOutButton;

    private TextView mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);

        setStatusBarTranslucent(true);

        Typeface myTypeface = Typeface.createFromAsset(getAssets(), "HelveticaRoman.ttf");
        Typeface myTypefaceHeader = Typeface.createFromAsset(getAssets(), "HelveticaThin.ttf");

        mTitle = (TextView) findViewById(R.id.start_title);
        mTitle.setTypeface(myTypefaceHeader);
        mQuizButton = (Button) findViewById(R.id.quiz_button);
        mQuizButton.setTypeface(myTypeface);
        mOddsButton = (Button) findViewById(R.id.odds_button);
        mOddsButton.setTypeface(myTypeface);
        mLoginButton = (Button) findViewById(R.id.login_button);
        mLoginButton.setTypeface(myTypeface);
        mRegisterButton = (Button) findViewById(R.id.register_button);
        mRegisterButton.setTypeface(myTypeface);
        mSignOutButton = (Button) findViewById(R.id.register_sing_out);
        mSignOutButton.setTypeface(myTypeface);

        mOddsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartScreenActivity.this, OddsCalatorActivity.class);
                startActivity(intent);
            }
        });

        mQuizButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UsersManager.getInstance(v.getContext()).getCurrentUser() == null) {
                    Toast.makeText(v.getContext(), "Sign in to access Cards and Quiz", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(v.getContext(), QuizSelectionActivity.class);
                startActivity(intent);
            }
        });

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
            }
        });

        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), RegisterActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
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

    @Override
    protected void onResume() {
        super.onResume();
        checkForActiveUser();
    }

    private void checkForActiveUser(){
        if (SavedSharedPreferences.getUsername(this).length() != 0) {
            User user = UsersManager.getInstance(this).getUser(SavedSharedPreferences.getUsername(this));
            UsersManager.getInstance(this).setCurrentUser(user);
            mLoginButton.setVisibility(View.GONE);
            mRegisterButton.setVisibility(View.GONE);
            mSignOutButton.setVisibility(View.VISIBLE);
        }

    }

    protected void setStatusBarTranslucent(boolean makeTranslucent) {
        if (makeTranslucent) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }
}
