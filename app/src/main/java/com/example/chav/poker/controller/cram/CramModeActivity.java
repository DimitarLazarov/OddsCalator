package com.example.chav.poker.controller.cram;

import android.app.FragmentManager;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.chav.poker.R;
import com.example.chav.poker.managers.CramCardsManager;

import java.util.ArrayList;
import java.util.Random;

import model.CramCard;

public class CramModeActivity extends AppCompatActivity implements CramModeResultFragment.CramModeMessageCallback {


    private final String question = "Question";
    private final String answer = "Answer";
    private FrameLayout mCardLayout;
    private Button mCorrectButton;
    private Button mWrongButton;
    private ImageButton mBackButton;
    private TextView mCramModeTitle;
    private Random mRandomGenerator = new Random();
    private CramCard mSelectedCard;
    private ArrayList<CramCard> mCards = new ArrayList<>();
    private ArrayList<CramCard> mUsedCards = new ArrayList<>();
    private boolean mPressed = false;
    private CramCardFrontEndFragment mBackFragment;
    private CramCardFrontEndFragment mFrontFragment;
    private FragmentManager manager;
    private TextView mTextCurrentCard;
    private int mCorrectAnswers;
    private int mAllCards;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cram_mode);

        setStatusBarTranslucent(true);

        Typeface myTypeface = Typeface.createFromAsset(getAssets(), "HelveticaRoman.ttf");
        mCramModeTitle = (TextView) findViewById(R.id.cram_mode);
        mCramModeTitle.setTypeface(myTypeface);

        manager = getFragmentManager();

        long deckId = getIntent().getExtras().getLong("deck_id");
        mCards.addAll(CramCardsManager.getInstance(this).getDeckCards(deckId));

        mTextCurrentCard = (TextView) findViewById(R.id.cram_mode_current_card);

        mCardLayout = (FrameLayout) findViewById(R.id.cram_mode_frame_layout);
        mCardLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flipCard();
            }
        });

        mBackButton = (ImageButton) findViewById(R.id.cram_mode_button_back);
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mCorrectButton = (Button) findViewById(R.id.cram_mode_button_correct);
        mCorrectButton.setTypeface(myTypeface);
        mCorrectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCards.size() == 0) {
                    mCorrectAnswers++;
                    startResultsFragment();
                } else {
                    mPressed = false;
                    mCorrectAnswers++;
                    cleanOldFragments();
                    int nextCard = mRandomGenerator.nextInt(mCards.size());
                    mSelectedCard = mCards.remove(nextCard);
                    mUsedCards.add(mSelectedCard);
                    addFrontFragment();

                }
            }
        });
        mWrongButton = (Button) findViewById(R.id.cram_mode_button_wrong);
        mWrongButton.setTypeface(myTypeface);
        mWrongButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCards.size() == 0) {
                    startResultsFragment();
                } else {
                    mPressed = false;
                    cleanOldFragments();
                    int nextCard = mRandomGenerator.nextInt(mCards.size());
                    mSelectedCard = mCards.remove(nextCard);
                    mUsedCards.add(mSelectedCard);
                    addFrontFragment();
                }
            }
        });

        mBackFragment = new CramCardFrontEndFragment();

        int nextCard = mRandomGenerator.nextInt(mCards.size());
        mAllCards = mCards.size();
        mSelectedCard = mCards.remove(nextCard);
        mUsedCards.add(mSelectedCard);
        mCorrectAnswers = 0;
        mFrontFragment = new CramCardFrontEndFragment();
        Bundle bundle = new Bundle();
        bundle.putString("textOfCard", mSelectedCard.getQuestion());
        bundle.putString("cardtype", question);
        mFrontFragment.setArguments(bundle);

        manager
                .beginTransaction()
                .add(R.id.cram_mode_frame_layout, mFrontFragment)
                .commit();
        mTextCurrentCard.setText("Card " + (mAllCards - mCards.size()) + "/" + mAllCards);


    }

    private void prepareNewGame() {
        cleanOldFragments();
        mPressed = false;
        if(mUsedCards.size() != 0){
            mCards.addAll(mUsedCards);
            mUsedCards.clear();
        }
        int nextCard = mRandomGenerator.nextInt(mCards.size());
        mAllCards = mCards.size();
        mSelectedCard = mCards.remove(nextCard);
        mUsedCards.add(mSelectedCard);
        mCorrectAnswers = 0;
        addFrontFragment();
    }


    private void cleanOldFragments(){
        manager .beginTransaction()
                .setCustomAnimations(0, R.anim.slide_out_left)
                .remove(mFrontFragment)
                .commit();
        manager .beginTransaction()
                .setCustomAnimations(0, R.anim.slide_out_left)
                .remove(mBackFragment)
                .commit();
    }

    private void addFrontFragment(){
        mFrontFragment = new CramCardFrontEndFragment();
        Bundle bundle = new Bundle();
        bundle.putString("textOfCard", mSelectedCard.getQuestion());
        bundle.putString("cardtype", question);
        mFrontFragment.setArguments(bundle);

        manager
                .beginTransaction()
                .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                .add(R.id.cram_mode_frame_layout, mFrontFragment)
                .commit();
        mTextCurrentCard.setText("Card " + (mAllCards - mCards.size()) + "/" + mAllCards);
    }


    private void flipCard() {

        // Create and commit a new fragment transaction that adds the fragment for
        // the back of the card, uses custom animations, and is part of the fragment
        // manager's back stack.
        if(!mPressed) {
            mPressed = true;
            mBackFragment = new CramCardFrontEndFragment();
            Bundle bundle = new Bundle();
            bundle.putString("textOfCard", mSelectedCard.getAnswer());
            bundle.putString("cardtype", answer);
            mBackFragment.setArguments(bundle);

            manager
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
                    .replace(R.id.cram_mode_frame_layout, mBackFragment)

                            // Add this transaction to the back stack, allowing users to press
                            // Back to get to the front of the card.
                    .addToBackStack(null)

                            // Commit the transaction.
                    .commit();
        }
        else {
            mPressed = false;
            mFrontFragment = new CramCardFrontEndFragment();
            Bundle bundle = new Bundle();
            bundle.putString("textOfCard", mSelectedCard.getQuestion());
            bundle.putString("cardtype", question);
            mFrontFragment.setArguments(bundle);
            manager
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
                    .replace(R.id.cram_mode_frame_layout, mFrontFragment)

                            // Add this transaction to the back stack, allowing users to press
                            // Back to get to the front of the card.
                    .addToBackStack(null)

                            // Commit the transaction.
                    .commit();
        }
    }

    private void startResultsFragment(){
        CramModeResultFragment cramModeResultFragment = new CramModeResultFragment();
        Bundle args = new Bundle();
        args.putString("title", "Results");
        args.putString("message", "Correct: " + mCorrectAnswers);
        args.putString("score", "Total: "  + mAllCards);
        cramModeResultFragment.setArguments(args);
        cramModeResultFragment.show(manager, "tag");
    }


    @Override
    public void onPrepareNewGame() {
        prepareNewGame();
    }

    @Override
    public void onCancelCramMode() {
        finish();
    }

    protected void setStatusBarTranslucent(boolean makeTranslucent) {
        if (makeTranslucent) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }
}
