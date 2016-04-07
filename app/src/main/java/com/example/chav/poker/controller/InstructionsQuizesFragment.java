package com.example.chav.poker.controller;


import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class InstructionsQuizesFragment extends DialogFragment {

    private TextView mMessageToShow;
    private ImageButton mCancel;
    private ImageButton mStartPlay;

    private String mMessage;
    private QuizSelectionCallback mQuizSelectionCallback;

    public InstructionsQuizesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mQuizSelectionCallback = (QuizSelectionCallback) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement QuizSelectionCallback");
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
        mMessage = getArguments().getString("message");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_instructions_quizes, container, false);

        getDialog().setCanceledOnTouchOutside(false);

        mMessageToShow = (TextView) v.findViewById(R.id.quiz_selection_instructions);
        mMessageToShow.setText(mMessage);

        mCancel = (ImageButton) v.findViewById(R.id.quiz_selection_cancel);
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDestroyView();
            }
        });

        mStartPlay = (ImageButton) v.findViewById(R.id.quiz_selection_play);
        mStartPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tag = getTag();
                if (tag.equals("BasicQuiz")) {
                    mQuizSelectionCallback.startBasicQuizGame();
                }
                if (tag.equals("SpeedQuiz")) {
                    mQuizSelectionCallback.startSpeedQuizGame();
                }
                onDestroyView();
            }
        });

        return v;
    }


    public interface QuizSelectionCallback {
        void startBasicQuizGame();
        void startSpeedQuizGame();
    }
}
