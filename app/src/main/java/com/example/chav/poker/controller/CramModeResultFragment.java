package com.example.chav.poker.controller;


import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.Typeface;
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
public class CramModeResultFragment extends DialogFragment {


    private TextView mResultText;
    private TextView mResultPoints;
    private TextView mCurrentResult;
    private ImageButton mCancel;
    private ImageButton mStartAgain;

    private String mTitle;
    private String mMessage;
    private String mScore;
    private CramModeMessageCallback mCramModeCallback;

    public CramModeResultFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCramModeCallback = (CramModeMessageCallback) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement CramModeMessageCallback");
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
        mScore = getArguments().getString("score");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_cram_mode_result, container, false);

        getDialog().setCanceledOnTouchOutside(false);

        Typeface myTypeface = Typeface.createFromAsset(v.getContext().getAssets(), "HelveticaRoman.ttf");
        mResultText = (TextView) v.findViewById(R.id.cram_mode_result_text);
        mResultText.setTypeface(myTypeface);
        mResultText.setText(mTitle);
        mResultPoints = (TextView) v.findViewById(R.id.cram_mode_points_result);
        mResultPoints.setTypeface(myTypeface);
        mResultPoints.setText(mMessage);
        mCurrentResult = (TextView) v.findViewById(R.id.cram_mode_current_result);
        mCurrentResult.setTypeface(myTypeface);
        mCurrentResult.setText(mScore);
        mStartAgain = (ImageButton) v.findViewById(R.id.cram_mode_new_game);
        mCancel = (ImageButton) v.findViewById(R.id.cram_mode_cancel);

        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCramModeCallback.onCancelCramMode();
                getDialog().hide();
            }
        });

        mStartAgain.setVisibility(View.VISIBLE);
        mStartAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCramModeCallback.onPrepareNewGame();
                getDialog().hide();
            }
        });

        return v;
    }

    interface CramModeMessageCallback{
        void onPrepareNewGame();
        void onCancelCramMode();
    }

}


