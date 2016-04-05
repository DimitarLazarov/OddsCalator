package com.example.chav.poker.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.chav.poker.R;
import com.example.chav.poker.managers.UsersManager;

import model.User;

public class LoginActivity extends AppCompatActivity {

    private EditText mUsername;
    private EditText mPassword;
    private Button mSubmit;
    private Button mCancel;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mUsername = (EditText) findViewById(R.id.login_username);
        mPassword = (EditText) findViewById(R.id.login_password);
        mSubmit = (Button) findViewById(R.id.login_submit_button);
        mCancel = (Button) findViewById(R.id.login_cancel_button);

        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(UsersManager.getInstance(v.getContext()).login(mUsername.getText().toString(), mPassword.getText().toString())) {
                    User user = UsersManager.getInstance(v.getContext()).getUser(mUsername.getText().toString());
                    UsersManager.getInstance(v.getContext()).setCurrentUser(user);
                    SavedSharedPreferences.setUserName(v.getContext(), user.getUsername());
                    Intent i = new Intent(v.getContext(), QuizSelectionActivity.class);

                    startActivity(i);
                }
            }
        });
    }
}
