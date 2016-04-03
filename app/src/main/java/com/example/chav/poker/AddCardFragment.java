package com.example.chav.poker;


import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.chav.poker.communicators.AddCardCommunicator;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddCardFragment extends DialogFragment implements View.OnClickListener, View.OnTouchListener{


    AddCardCommunicator communicator;

    private Button mDeuces;
    private Button mTreys;
    private Button mFours;
    private Button mFives;
    private Button mSixes;
    private Button mSevens;
    private Button mEights;
    private Button mNines;
    private Button mTens;
    private Button mJacks;
    private Button mQueens;
    private Button mKings;
    private Button mAces;
    private ImageButton mHearts;
    private ImageButton mDiamonds;
    private ImageButton mClubs;
    private ImageButton mSpades;
    private int cardSuitPower = 0;
    private ArrayList<Button> mRanks;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        communicator = (AddCardCommunicator) activity;
    }

    public AddCardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_add_card, container, false);
        mRanks = new ArrayList<>();

        mDeuces =       (Button) v.findViewById(R.id.deuces_button);
        mTreys =        (Button) v.findViewById(R.id.treys_button);
        mFours =        (Button) v.findViewById(R.id.fours_button);
        mFives =        (Button) v.findViewById(R.id.fives_button);
        mSixes =        (Button) v.findViewById(R.id.sixes_button);
        mSevens =       (Button) v.findViewById(R.id.sevens_button);
        mEights =       (Button) v.findViewById(R.id.eights_button);
        mNines =        (Button) v.findViewById(R.id.nines_button);
        mTens =         (Button) v.findViewById(R.id.tens_button);
        mJacks =        (Button) v.findViewById(R.id.jacks_button);
        mQueens =       (Button) v.findViewById(R.id.queens_button);
        mKings =        (Button) v.findViewById(R.id.kings_button);
        mAces =         (Button) v.findViewById(R.id.aces_button);
        mHearts =       (ImageButton) v.findViewById(R.id.hearts_button);
        mDiamonds =     (ImageButton) v.findViewById(R.id.diamonds_button);
        mClubs =        (ImageButton) v.findViewById(R.id.clubs_button);
        mSpades =       (ImageButton) v.findViewById(R.id.spades_button);

        mRanks.add(mDeuces);
        mRanks.add(mTreys);
        mRanks.add(mFours);
        mRanks.add(mFives);
        mRanks.add(mSixes);
        mRanks.add(mSevens);
        mRanks.add(mEights);
        mRanks.add(mNines);
        mRanks.add(mTens);
        mRanks.add(mJacks);
        mRanks.add(mQueens);
        mRanks.add(mKings);
        mRanks.add(mAces);

        mDeuces.setOnClickListener(this);
        mTreys.setOnClickListener(this);
        mFours.setOnClickListener(this);
        mFives.setOnClickListener(this);
        mSixes.setOnClickListener(this);
        mSevens.setOnClickListener(this);
        mEights.setOnClickListener(this);
        mNines.setOnClickListener(this);
        mTens.setOnClickListener(this);
        mJacks.setOnClickListener(this);
        mQueens.setOnClickListener(this);
        mKings.setOnClickListener(this);
        mAces.setOnClickListener(this);
        mHearts.setOnTouchListener(this);
        mDiamonds.setOnTouchListener(this);
        mClubs.setOnTouchListener(this);
        mClubs.setPressed(true);
        mSpades.setOnTouchListener(this);


        return v;
    }

    @Override
    public void onClick(View v) {
        char cardSuit = 'C';
        if(cardSuitPower == 0){
            cardSuit = 'C';
        }
        else if(cardSuitPower == 1) {
            cardSuit = 'D';
        }
        else if(cardSuitPower == 2) {
            cardSuit = 'H';
        }
        else {
            cardSuit = 'S';
        }

        switch (v.getId()) {
            case R.id.deuces_button:
                communicateWithActivity('2', cardSuit);
                break;
            case R.id.treys_button:
                communicateWithActivity('3', cardSuit);
                break;
            case R.id.fours_button:
                communicateWithActivity('4', cardSuit);
                break;
            case R.id.fives_button:
                communicateWithActivity('5', cardSuit);
                break;
            case R.id.sixes_button:
                communicateWithActivity('6', cardSuit);
                break;
            case R.id.sevens_button:
                communicateWithActivity('7', cardSuit);
                break;
            case R.id.eights_button:
                communicateWithActivity('8', cardSuit);
                break;
            case R.id.nines_button:
                communicateWithActivity('9', cardSuit);
                break;
            case R.id.tens_button:
                communicateWithActivity('T', cardSuit);
                break;
            case R.id.jacks_button:
                communicateWithActivity('J', cardSuit);
                break;
            case R.id.queens_button:
                communicateWithActivity('Q', cardSuit);
                break;
            case R.id.kings_button:
                communicateWithActivity('K', cardSuit);
                break;
            case R.id.aces_button:
                communicateWithActivity('A', cardSuit);
                break;
        }
    }

    private void communicateWithActivity(char cardPower, char suitPower) {
        String cardStrength = "" + cardPower + suitPower;
        if (communicator.addCard(cardStrength))
            onDestroyView();
        else
            Toast.makeText(getActivity(), "Card Already chosen!", Toast.LENGTH_SHORT).show();
    }

    private void changeColorButtonsText(int color) {
        for (Button button : mRanks) {
            button.setTextColor(ContextCompat.getColor(getContext(), color));
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
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch(v.getId()) {
            case R.id.clubs_button:
                cardSuitPower = 0;
                setPressed(true, false, false, false);
                changeColorButtonsText(R.color.blackCards);
                break;
            case R.id.diamonds_button:
                cardSuitPower = 1;
                setPressed(false,true,false,false);
                changeColorButtonsText(R.color.redCards);
                break;
            case R.id.hearts_button:
                cardSuitPower = 2;
                setPressed(false,false,true,false);
                changeColorButtonsText(R.color.redCards);
                break;
            case R.id.spades_button:
                cardSuitPower = 3;
                setPressed(false,false,false,true);
                changeColorButtonsText(R.color.blackCards);
                break;
        }
//        v.getBackground().setColorFilter(Color.parseColor("#424242"), PorterDuff.Mode.MULTIPLY);
        v.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_OVER);
//        v.getBackground().setColorFilter((new LightingColorFilter(0x000000, 0xFFAA0000)));
        return true;
    }

    public void setPressed(boolean clubs, boolean diamonds, boolean hearts, boolean spades){
        mClubs.setPressed(clubs);
        mDiamonds.setPressed(diamonds);
        mHearts.setPressed(hearts);
        mSpades.setPressed(spades);

    }
}
