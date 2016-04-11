package com.example.chav.poker.controller.menu;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chav.poker.R;
import com.example.chav.poker.controller.SavedSharedPreferences;
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

        setStatusBarTranslucent(true);

        Typeface myTypeface = Typeface.createFromAsset(getAssets(), "HelveticaRoman.ttf");
        mUsername = (EditText) findViewById(R.id.login_username);
        mUsername.setTypeface(myTypeface);
        mPassword = (EditText) findViewById(R.id.login_password);
        mPassword.setTypeface(myTypeface);
        mSubmit = (Button) findViewById(R.id.login_submit_button);
        mSubmit.setTypeface(myTypeface);
        mCancel = (Button) findViewById(R.id.login_cancel_button);
        mCancel.setTypeface(myTypeface);

        mUsername.setNextFocusDownId(R.id.login_password);
        mUsername.setTypeface(myTypeface);
        mUsername.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN)
                        && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    mUsername.clearFocus();
                    mPassword.requestFocus();
                    return true;
                }
                return false;
            }
        });

        mPassword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN)
                        && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    if (UsersManager.getInstance(v.getContext()).login(mUsername.getText().toString(), mPassword.getText().toString())) {
                        User user = UsersManager.getInstance(v.getContext()).getUser(mUsername.getText().toString());
                        UsersManager.getInstance(v.getContext()).setCurrentUser(user);
                        SavedSharedPreferences.setUserName(v.getContext(), user.getUsername());
                        Intent i = new Intent(v.getContext(), QuizSelectionActivity.class);
                        startActivity(i);
                        return true;
                    } else {
                        Toast.makeText(v.getContext(), "Invalid username or password", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                }
                return false;
            }
        });

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
                } else {
                    Toast.makeText(v.getContext(), "Invalid username or password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    protected void setStatusBarTranslucent(boolean makeTranslucent) {
        if (makeTranslucent) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }
}
