package com.example.chav.poker;


import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.chav.poker.communicators.AddCardCommunicator;


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
        String cardStrength = "";
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
                cardStrength = "2" + cardSuit;
                communicator.addCart(cardStrength);
                onDestroyView();
                break;
            case R.id.treys_button:
                cardStrength = "3" + cardSuit;
                communicator.addCart(cardStrength);
                onDestroyView();
                break;
            case R.id.fours_button:
                cardStrength = "4" + cardSuit;
                communicator.addCart(cardStrength);
                onDestroyView();
                break;
            case R.id.fives_button:
                cardStrength = "5" + cardSuit;
                communicator.addCart(cardStrength);
                onDestroyView();
                break;
            case R.id.sixes_button:
                cardStrength = "6" + cardSuit;
                communicator.addCart(cardStrength);
                onDestroyView();
                break;
            case R.id.sevens_button:
                cardStrength = "7" + cardSuit;
                communicator.addCart(cardStrength);
                onDestroyView();
                break;
            case R.id.eights_button:
                cardStrength = "8" + cardSuit;
                communicator.addCart(cardStrength);
                onDestroyView();
                break;
            case R.id.nines_button:
                cardStrength = "9" + cardSuit;
                communicator.addCart(cardStrength);
                onDestroyView();
                break;
            case R.id.tens_button:
                cardStrength = "T" + cardSuit;
                communicator.addCart(cardStrength);
                onDestroyView();
                break;
            case R.id.jacks_button:
                cardStrength = "J" + cardSuit;
                communicator.addCart(cardStrength);
                onDestroyView();
                break;
            case R.id.queens_button:
                cardStrength = "Q" + cardSuit;
                communicator.addCart(cardStrength);
                onDestroyView();
                break;
            case R.id.kings_button:
                cardStrength = "K" + cardSuit;
                communicator.addCart(cardStrength);
                onDestroyView();
                break;
            case R.id.aces_button:
                cardStrength = "A" + cardSuit;
                communicator.addCart(cardStrength);
                onDestroyView();
                break;
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
                setPressed(true,false,false,false);
                break;
            case R.id.diamonds_button:
                cardSuitPower = 1;
                setPressed(false,true,false,false);
                break;
            case R.id.hearts_button:
                cardSuitPower = 2;
                setPressed(false,false,true,false);
                break;
            case R.id.spades_button:
                cardSuitPower = 3;
                setPressed(false,false,false,true);
                break;
        }
        return true;
    }

    public void setPressed(boolean clubs, boolean diamonds, boolean hearts, boolean spades){
        mClubs.setPressed(clubs);
        mDiamonds.setPressed(diamonds);
        mHearts.setPressed(hearts);
        mSpades.setPressed(spades);
    }
}
