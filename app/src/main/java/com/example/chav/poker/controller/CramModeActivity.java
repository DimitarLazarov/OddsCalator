package com.example.chav.poker.controller;

import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;

import com.example.chav.poker.R;

public class CramModeActivity extends AppCompatActivity {

    private FrameLayout mCardLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cram_mode);

        mCardLayout = (FrameLayout) findViewById(R.id.cram_mode_frame_layout);
        mCardLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flipCard();
            }
        });

        CramCardFrontEndFragment fragment = new CramCardFrontEndFragment();
        getFragmentManager()
                .beginTransaction()
                .add(R.id.cram_mode_frame_layout, fragment)
                .commit();
    }

    private void flipCard() {

        // Create and commit a new fragment transaction that adds the fragment for
        // the back of the card, uses custom animations, and is part of the fragment
        // manager's back stack.

        Log.e("greshka","sss");
        getFragmentManager()
                .beginTransaction()

                        // Replace the default fragment animations with animator resources
                        // representing rotations when switching to the back of the card, as
                        // well as animator resources representing rotations when flipping
                        // back to the front (e.g. when the system Back button is pressed).
                .setCustomAnimations(
                        R.anim.fragment_card_flip_rigth_in,
                        R.anim.fragment_card_flip_rigth_out,
                        R.anim.fragment_card_flip_left_in,
                        R.anim.fragment_card_flip_left_out)

                        // Replace any fragments currently in the container view with a
                        // fragment representing the next page (indicated by the
                        // just-incremented currentPage variable).
                .replace(R.id.cram_mode_frame_layout, new CardsAndOddsFragment())

                        // Add this transaction to the back stack, allowing users to press
                        // Back to get to the front of the card.
                .addToBackStack(null)

                        // Commit the transaction.
                .commit();
    }
}
