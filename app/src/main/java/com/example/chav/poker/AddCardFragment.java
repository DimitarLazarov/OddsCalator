package com.example.chav.poker;


import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddCardFragment extends DialogFragment {

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

        return v;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

}
