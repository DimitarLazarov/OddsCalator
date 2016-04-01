package com.example.chav.poker;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

public class CardSelectionActivity extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_selection);

        mDeuces = (Button) findViewById(R.id.deuces_button);
        mTreys = (Button) findViewById(R.id.treys_button);
        mFours = (Button) findViewById(R.id.fours_button);
        mFives = (Button) findViewById(R.id.fives_button);
        mSixes = (Button) findViewById(R.id.sixes_button);
        mSevens = (Button) findViewById(R.id.sevens_button);
        mEights = (Button) findViewById(R.id.eights_button);
        mNines = (Button) findViewById(R.id.nines_button);
        mTens = (Button) findViewById(R.id.tens_button);
        mJacks = (Button) findViewById(R.id.jacks_button);
        mQueens = (Button) findViewById(R.id.queens_button);
        mKings = (Button) findViewById(R.id.kings_button);
        mAces = (Button) findViewById(R.id.aces_button);
        mHearts = (ImageButton) findViewById(R.id.hearts_button);
        mDiamonds = (ImageButton) findViewById(R.id.diamonds_button);
        mClubs = (ImageButton) findViewById(R.id.clubs_button);
        mSpades = (ImageButton) findViewById(R.id.spades_button);

        Typeface font = Typeface.createFromAsset(getAssets(), "Helvetica.ttf");
        mDeuces.setTypeface(font);
        mTreys.setTypeface(font);
        mFours.setTypeface(font);
        mFives.setTypeface(font);
        mSixes.setTypeface(font);
        mSevens.setTypeface(font);
        mEights.setTypeface(font);
        mNines.setTypeface(font);
        mTens.setTypeface(font);
        mJacks.setTypeface(font);
        mQueens.setTypeface(font);
        mKings.setTypeface(font);
        mAces.setTypeface(font);

    }
}
