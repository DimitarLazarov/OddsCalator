package com.example.chav.poker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class QuizSelectionActivity extends AppCompatActivity {

    private Button mBasicQuiz;
    private Button mAdvancedQuiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_selection);

        mBasicQuiz = (Button) findViewById(R.id.basic_quiz);
        mBasicQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), BasicQuizActivity.class);
                startActivity(i);
            }
        });
        mAdvancedQuiz = (Button) findViewById(R.id.advanced_quiz);

    }
}
