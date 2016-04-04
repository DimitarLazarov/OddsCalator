package com.example.chav.poker;


import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class BasicQuizResultFragment extends DialogFragment {

    private TextView mResultText;
    private TextView mResultPoints;
    private TextView mCurrentResult;
    private ImageButton mCancel;
    private ImageButton mStartAgain;

    private String mTitle;
    private String mMessage;
    private String mScore;
    private QuizMessageCallback mQuizMessageCallback;

    public BasicQuizResultFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mQuizMessageCallback = (QuizMessageCallback) activity;
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
        mScore = getArguments().getString("score");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_basic_quiz_result, container, false);

        getDialog().setCanceledOnTouchOutside(false);

        mResultText = (TextView) v.findViewById(R.id.basic_quiz_result_text);
        mResultText.setText(mTitle);
        mResultPoints = (TextView) v.findViewById(R.id.basic_quiz_points_result);
        mResultPoints.setText(mMessage);
        mCurrentResult = (TextView) v.findViewById(R.id.basic_quiz_current_result);
        mCurrentResult.setText(mScore);
        mStartAgain = (ImageButton) v.findViewById(R.id.basic_quiz_new_game);
        mCancel = (ImageButton) v.findViewById(R.id.basic_quiz_cancel);

        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), QuizSelectionActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

        mStartAgain.setVisibility(View.VISIBLE);
        mStartAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mQuizMessageCallback.onMessageAcknowledged();
                getDialog().hide();
            }
        });

        return v;
    }

    interface QuizMessageCallback{
        void onMessageAcknowledged();
    }

}
