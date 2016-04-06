package com.example.chav.poker.controller;

import android.app.Fragment;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.chav.poker.R;

import java.util.ArrayList;
import java.util.Random;

import model.CramCard;

public class CramModeActivity extends AppCompatActivity {

    private FrameLayout mCardLayout;
    private Button mCorrectButton;
    private Button mWrongButton;
    private Random mRandomGenerator = new Random();
    private CramCard mSelectedCard;
    private ArrayList<CramCard> mCards = new ArrayList<>();
    private boolean mPressed = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cram_mode);

        CramCard card1 = new CramCard("Who are u?", "Player.");
        CramCard card2 = new CramCard("Are u ok?", "Im ok.");
        CramCard card3 = new CramCard("Nice a?", "nice.");
        CramCard card4 = new CramCard("Fuck u?", "No no.");
        mCards.add(card1);
        mCards.add(card2);
        mCards.add(card3);
        mCards.add(card4);

        int nextCard = mRandomGenerator.nextInt(mCards.size());
        mSelectedCard = mCards.remove(nextCard);

        mCardLayout = (FrameLayout) findViewById(R.id.cram_mode_frame_layout);
        mCardLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flipCard();
            }
        });

        mCorrectButton = (Button) findViewById(R.id.cram_mode_button_correct);
        mCorrectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCards.size() == 0) {
                    finish();
                } else {
                    int nextCard = mRandomGenerator.nextInt(mCards.size());
                    mSelectedCard = mCards.remove(nextCard);
                    CramCardFrontEndFragment backFragment = new CramCardFrontEndFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("textOfCard", mSelectedCard.getQuestion());
                    backFragment.setArguments(bundle);

                    getFragmentManager()
                            .beginTransaction()
                            .add(R.id.cram_mode_frame_layout, backFragment)
                            .commit();

                }
            }
        });
        mWrongButton = (Button) findViewById(R.id.cram_mode_button_wrong);




        CramCardFrontEndFragment backFragment = new CramCardFrontEndFragment();
        Bundle bundle = new Bundle();
        bundle.putString("textOfCard", mSelectedCard.getQuestion());
        backFragment.setArguments(bundle);

        getFragmentManager()
                .beginTransaction()
                .add(R.id.cram_mode_frame_layout, backFragment)
                .commit();
    }

    private void flipCard() {

        // Create and commit a new fragment transaction that adds the fragment for
        // the back of the card, uses custom animations, and is part of the fragment
        // manager's back stack.
        if(!mPressed) {
            mPressed = true;
            CramCardFrontEndFragment frontFragment = new CramCardFrontEndFragment();
            Bundle bundle = new Bundle();
            bundle.putString("textOfCard", mSelectedCard.getAnswer());
            frontFragment.setArguments(bundle);

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
                    .replace(R.id.cram_mode_frame_layout, frontFragment)

                            // Add this transaction to the back stack, allowing users to press
                            // Back to get to the front of the card.
                    .addToBackStack(null)

                            // Commit the transaction.
                    .commit();
        }
        else {
            mPressed = false;
            CramCardFrontEndFragment backFragment = new CramCardFrontEndFragment();
            Bundle bundle = new Bundle();
            bundle.putString("textOfCard", mSelectedCard.getQuestion());
            backFragment.setArguments(bundle);
            getFragmentManager()
                    .beginTransaction()

                            // Replace the default fragment animations with animator resources
                            // representing rotations when switching to the back of the card, as
                            // well as animator resources representing rotations when flipping
                            // back to the front (e.g. when the system Back button is pressed).
                    .setCustomAnimations(
                            R.anim.fragment_card_flip_left_in,
                            R.anim.fragment_card_flip_left_out,
                            R.anim.fragment_card_flip_rigth_in,
                            R.anim.fragment_card_flip_rigth_out)

                            // Replace any fragments currently in the container view with a
                            // fragment representing the next page (indicated by the
                            // just-incremented currentPage variable).
                    .replace(R.id.cram_mode_frame_layout, backFragment)

                            // Add this transaction to the back stack, allowing users to press
                            // Back to get to the front of the card.
                    .addToBackStack(null)

                            // Commit the transaction.
                    .commit();
        }
    }
}
