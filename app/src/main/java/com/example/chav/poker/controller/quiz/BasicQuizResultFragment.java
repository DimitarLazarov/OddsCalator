package com.example.chav.poker.controller.quiz;


import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.chav.poker.R;
import com.example.chav.poker.controller.SavedSharedPreferences;

/**
 * A simple {@link Fragment} subclass.
 */
public class BasicQuizResultFragment extends DialogFragment {


    public static final String INCORRECT = "incorrect";

    private TextView mResultText;
    private TextView mCurrentStreak;
    private TextView mBestStreak;
    private ImageButton mCancel;
    private ImageButton mStartAgain;

    private String mTitle;
    private String mMessage;
//    private String mScore;
    private int mScore;
    private int mBestScore;
    private BasicQuizMessageCallback mQuizMessageCallback;

    public BasicQuizResultFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mQuizMessageCallback = (BasicQuizMessageCallback) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement QuizMessageCallback");
        }
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTitle = getArguments().getString("title");
        mMessage = getArguments().getString("message");
        mScore = getArguments().getInt("score");

    }

    private boolean checkNewBestResult(int score) {
        mBestScore = SavedSharedPreferences.getBestScore(getActivity());
        if (score > mBestScore) {
            SavedSharedPreferences.setBestScore(getActivity().getBaseContext(), score);
            mBestScore = score;
            return true;
        }
        return false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_basic_quiz_result, container, false);

        getDialog().setCanceledOnTouchOutside(false);
        mBestScore = SavedSharedPreferences.getBestScore(getActivity());

        mResultText = (TextView) v.findViewById(R.id.basic_quiz_result_text);
        mResultText.setText(mTitle);

        mCurrentStreak = (TextView) v.findViewById(R.id.basic_quiz_current_streak);
        mCurrentStreak.setText(mMessage);
        mBestStreak = (TextView) v.findViewById(R.id.basic_quiz_longest_streak);



        mStartAgain = (ImageButton) v.findViewById(R.id.basic_quiz_new_game);
        if (mTitle.equalsIgnoreCase(INCORRECT)) {
            mStartAgain.setImageResource(R.drawable.button_reset_two);
            if(checkNewBestResult(mScore)){
                mBestStreak.setText("New Best Streak: " + mBestScore);
            } else {
                mBestStreak.setText("Best Streak: " + mBestScore);
            }

        } else {

            mBestStreak.setText("Best Streak: " + mBestScore);
        }
        mCancel = (ImageButton) v.findViewById(R.id.basic_quiz_cancel);

        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mQuizMessageCallback.onCancel();
                getDialog().hide();
            }
        });

        mStartAgain.setVisibility(View.VISIBLE);
        mStartAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mQuizMessageCallback.onPlayAgain();
                getDialog().hide();
            }
        });

        return v;
    }

    interface BasicQuizMessageCallback{
        void onPlayAgain();
        void onCancel();
    }
}
