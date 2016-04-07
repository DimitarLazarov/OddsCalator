package com.example.chav.poker.controller;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chav.poker.R;

public class CramCardCreateActivity extends AppCompatActivity {

    private EditText mFront;
    private EditText mBack;
    private Button mCancel;
    private Button mConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cram_card_create);

        Typeface myTypeface = Typeface.createFromAsset(getAssets(), "HelveticaRoman.ttf");
        mFront = (EditText) findViewById(R.id.create_cram_card_front);
        mFront.setTypeface(myTypeface);
        mBack = (EditText) findViewById(R.id.create_cram_card_back);
        mBack.setTypeface(myTypeface);
        mCancel = (Button) findViewById(R.id.create_cram_card_cancel);
        mCancel.setTypeface(myTypeface);
        mConfirm = (Button) findViewById(R.id.create_cram_card_confirm);
        mConfirm.setTypeface(myTypeface);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        mConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mFront.getText().toString().isEmpty() && !mBack.getText().toString().trim().isEmpty()) {
                    finishWithResult();
                } else {
                    Toast.makeText(v.getContext(), "Please enter valid values", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void finishWithResult(){
        Bundle conData = new Bundle();
        conData.putString("front", mFront.getText().toString());
        conData.putString("back", mBack.getText().toString());
        Intent intent = new Intent();
        intent.putExtras(conData);
        setResult(RESULT_OK, intent);
        finish();
    }
}
