package com.example.chav.poker.controller.calculator;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.chav.poker.R;
import com.example.chav.poker.communicators.AddCardCommunicator;

import java.util.ArrayList;
import java.util.Locale;

import ver4.poker.Card;
import ver4.poker.CardSet;
import ver4.poker.HandEval;
import ver4.showdown.Enumerator;

public class OddsCalatorActivity extends AppCompatActivity implements View.OnClickListener, AddCardCommunicator {

    public static final int PLAYER_ONE = 0;
    public static final int PLAYER_TWO = 1;
    public static final int BOARD_CARD = 2;
    public static final int PLAYER_CARDS = 2;

    private Button mPOneCardOne;
    private Button mPOneCardTwo;
    private Button mPTwoCardOne;
    private Button mPTwoCardTwo;
    private Button mButtonOccurredEvent;
    private Button mNextButtonToClick;
    private Button mBoardCardOne;
    private Button mBoardCardTwo;
    private Button mBoardCardThree;
    private Button mBoardCardFour;
    private Button mBoardCardFive;
    private ImageButton mResetButton;
    private ImageButton mBackButton;
    private TextView mPlayerOneWinOdds;
    private TextView mPlayerTwoWinOdds;
    private TextView mPlayerOneTieOdds;
    private TextView mPlayerTwoTieOdds;
    private CardSet mDeck;
    private CardSet[] mPlayers;
    private CardSet mBoardCards;
    private int mPlayerOccuredEvent;
    private int mAllPlayersCards;
    private int mAllBoardCards;
    private ArrayList<Card> mDealtCards;
    private Card mCartToRemove = null;
    private ArrayList<Button> mAllButtons;
    private ChangeCardListener mChangeCardListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oddscalator);

        setStatusBarTranslucent(true);

        Typeface myTypeface = Typeface.createFromAsset(getAssets(), "HelveticaRoman.ttf");
        mPOneCardOne      = (Button) findViewById(R.id.basic_quiz_player_one_card_one);
        mPOneCardOne.setTypeface(myTypeface);
        mPOneCardTwo      = (Button) findViewById(R.id.basic_quiz_player_one_card_two);
        mPOneCardTwo.setTypeface(myTypeface);
        mPTwoCardOne      = (Button) findViewById(R.id.basic_quiz_player_two_card_one);
        mPTwoCardOne.setTypeface(myTypeface);
        mPTwoCardTwo      = (Button) findViewById(R.id.basic_quiz_player_two_card_two);
        mPTwoCardTwo.setTypeface(myTypeface);
        mBoardCardOne     = (Button) findViewById(R.id.basic_quiz_card_one_board);
        mBoardCardOne.setTypeface(myTypeface);
        mBoardCardTwo     = (Button) findViewById(R.id.basic_quiz_card_two_board);
        mBoardCardTwo.setTypeface(myTypeface);
        mBoardCardThree   = (Button) findViewById(R.id.basic_quiz_card_three_board);
        mBoardCardThree.setTypeface(myTypeface);
        mBoardCardFour    = (Button) findViewById(R.id.basic_quiz_card_four_board);
        mBoardCardFour.setTypeface(myTypeface);
        mBoardCardFive    = (Button) findViewById(R.id.basic_quiz_card_five_board);
        mBoardCardFive.setTypeface(myTypeface);
        mPlayerOneWinOdds = (TextView) findViewById(R.id.player_one_odds_win);
        mPlayerOneWinOdds.setTypeface(myTypeface);
        mPlayerTwoWinOdds = (TextView) findViewById(R.id.player_two_odds_win);
        mPlayerTwoWinOdds.setTypeface(myTypeface);
        mPlayerOneTieOdds = (TextView) findViewById(R.id.player_one_odds_tie);
        mPlayerOneTieOdds.setTypeface(myTypeface);
        mPlayerTwoTieOdds = (TextView) findViewById(R.id.player_two_odds_tie);
        mPlayerTwoTieOdds.setTypeface(myTypeface);
        mResetButton      = (ImageButton) findViewById(R.id.button_reset);
        mBackButton       = (ImageButton) findViewById(R.id.button_back);
        mBackButton.setOnClickListener(this);
        mResetButton.setOnClickListener(this);

        mAllButtons = new ArrayList<Button>();
        mAllButtons.add(mPOneCardOne);
        mAllButtons.add(mPOneCardTwo);
        mAllButtons.add(mPTwoCardOne);
        mAllButtons.add(mPTwoCardTwo);
        mAllButtons.add(mBoardCardOne);
        mAllButtons.add(mBoardCardTwo);
        mAllButtons.add(mBoardCardThree);
        mAllButtons.add(mBoardCardFour);
        mAllButtons.add(mBoardCardFive);

        mChangeCardListener = new ChangeCardListener();

        resetStates();

    }

    private void resetStates() {

        resetAllButtonsStates();

        mPOneCardOne.setClickable(true);
        mPOneCardOne.setBackgroundResource(R.drawable.card_back);

        mPTwoCardOne.setClickable(true);
        mPTwoCardOne.setBackgroundResource(R.drawable.card_back);

        mDeck = CardSet.freshDeck();
        mPlayers = new CardSet[PLAYER_CARDS];
        mPlayers[PLAYER_ONE] = new CardSet();
        mPlayers[PLAYER_TWO] = new CardSet();
        mBoardCards = new CardSet();
        mAllPlayersCards = 0;
        mAllBoardCards = 0;
        mDealtCards = new ArrayList<Card>();
    }

    private void resetAllButtonsStates() {
        for(Button button : mAllButtons) {
            button.setOnClickListener(this);
            button.setClickable(false);
            button.setText("");
            button.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            button.setBackgroundResource(R.drawable.card_front);
        }
    }

    public void resetAllTextViewWithAnimation() {

        animateTextView((Float.parseFloat(mPlayerOneWinOdds.getText().toString())), (float)0, mPlayerOneWinOdds);
        animateTextView((Float.parseFloat(mPlayerTwoWinOdds.getText().toString())), (float)0, mPlayerTwoWinOdds);
        animateTextView((Float.parseFloat(mPlayerOneTieOdds.getText().toString())), (float)0, mPlayerOneTieOdds);
        animateTextView((Float.parseFloat(mPlayerTwoTieOdds.getText().toString())), (float)0, mPlayerTwoTieOdds);
    }

    public void animateTextView(float initialValue, float finalValue, final TextView  textview) {

        ValueAnimator valueAnimator = ValueAnimator.ofFloat(initialValue, finalValue);
        valueAnimator.setDuration(500);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {

                textview.setText(String.format(Locale.US, "%.2f", valueAnimator.getAnimatedValue()));

            }
        });
        valueAnimator.start();

    }

    private void assignOdds(long[] wins, long[] ties, double pots) {
        double mPlayerOneWinningChance = wins[PLAYER_ONE] * 100.0 / pots;
        double mPlayerTwoWinningChance = wins[PLAYER_TWO] * 100.0 / pots;
        double mPlayerOneTieChance     = ties[PLAYER_ONE] * 100.0 / pots;
        double mPlayerTwoTieChance     = ties[PLAYER_TWO] * 100.0 / pots;

        animateTextView((Float.parseFloat(mPlayerOneWinOdds.getText().toString())),
                        (float)mPlayerOneWinningChance, mPlayerOneWinOdds);
        animateTextView((Float.parseFloat(mPlayerTwoWinOdds.getText().toString())),
                        (float)mPlayerTwoWinningChance, mPlayerTwoWinOdds);
        animateTextView((Float.parseFloat(mPlayerOneTieOdds.getText().toString())),
                        (float)mPlayerOneTieChance, mPlayerOneTieOdds);
        animateTextView((Float.parseFloat(mPlayerTwoTieOdds.getText().toString())),
                        (float)mPlayerTwoTieChance, mPlayerTwoTieOdds);

    }

    @Override
    public void onClick(View v) {
        AddCardFragment d = new AddCardFragment();
        FragmentManager fm = getSupportFragmentManager();
        switch(v.getId()) {
            case R.id.basic_quiz_player_one_card_one:
                d.show(fm, "addCard");
                mButtonOccurredEvent = mPOneCardOne;
                mNextButtonToClick = mPOneCardTwo;
                mPlayerOccuredEvent = PLAYER_ONE;
                break;
            case R.id.basic_quiz_player_one_card_two:
                d.show(fm, "addCard");
                mButtonOccurredEvent = mPOneCardTwo;
                mNextButtonToClick = new Button(this);
                mNextButtonToClick.setVisibility(View.GONE);
                mPlayerOccuredEvent = PLAYER_ONE;
                break;
            case R.id.basic_quiz_player_two_card_one:
                d.show(fm, "addCard");
                mButtonOccurredEvent = mPTwoCardOne;
                mNextButtonToClick = mPTwoCardTwo;
                mPlayerOccuredEvent = PLAYER_TWO;
                break;
            case R.id.basic_quiz_player_two_card_two:
                d.show(fm, "addCard");
                mButtonOccurredEvent = mPTwoCardTwo;
                mNextButtonToClick = new Button(this);
                mNextButtonToClick.setVisibility(View.GONE);
                mPlayerOccuredEvent = PLAYER_TWO;
                break;
            case R.id.basic_quiz_card_one_board:
                d.show(fm, "addCard");
                mButtonOccurredEvent = mBoardCardOne;
                mPlayerOccuredEvent = BOARD_CARD;
                mNextButtonToClick = mBoardCardTwo;
                break;
            case R.id.basic_quiz_card_two_board:
                d.show(fm, "addCard");
                mButtonOccurredEvent = mBoardCardTwo;
                mPlayerOccuredEvent = BOARD_CARD;
                mNextButtonToClick = mBoardCardThree;
                break;
            case R.id.basic_quiz_card_three_board:
                d.show(fm, "addCard");
                mButtonOccurredEvent = mBoardCardThree;
                mPlayerOccuredEvent = BOARD_CARD;
                mNextButtonToClick = mBoardCardFour;
                break;
            case R.id.basic_quiz_card_four_board:
                d.show(fm, "addCard");
                mButtonOccurredEvent = mBoardCardFour;
                mPlayerOccuredEvent = BOARD_CARD;
                mNextButtonToClick = mBoardCardFive;
                break;
            case R.id.basic_quiz_card_five_board:
                d.show(fm, "addCard");
                mButtonOccurredEvent = mBoardCardFive;
                mNextButtonToClick = new Button(this);
                mNextButtonToClick.setVisibility(View.GONE);
                mPlayerOccuredEvent = BOARD_CARD;
                break;
            case R.id.button_back:
                finish();
                break;
            case R.id.button_reset:
                resetStates();
                resetAllTextViewWithAnimation();
                break;
        }
    }

    public void startCalculate(){
        new CalculateOdds().execute(mBoardCards, null, null);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean addCard(String receivedCard) {
        Card card = new Card(receivedCard);
        if (mDealtCards.contains(card)) {
            return false;
        }
        else {
            mButtonOccurredEvent.setOnClickListener(mChangeCardListener);
            mNextButtonToClick.setBackgroundResource(R.drawable.card_back);
            mNextButtonToClick.setClickable(true);
            int color = getSuitColor(receivedCard.charAt(1));
            mButtonOccurredEvent.setText(receivedCard.charAt(0) + "");
            mButtonOccurredEvent.setTextColor(color);
            mButtonOccurredEvent.setBackgroundResource(R.drawable.card_front);
            if (mPlayerOccuredEvent == BOARD_CARD) {
                mBoardCards.add(card);
                mAllBoardCards++;
                addBoardCard(receivedCard);
            } else {
                mPlayers[mPlayerOccuredEvent].add(card);
                mAllPlayersCards++;
                addPlayerCard(receivedCard);
            }
            mDeck.remove(card);
            mDealtCards.add(card);
            checkForCalculations();
            return true;
        }

    }

    public boolean replaceCard(String receivedCard){
        Card card = new Card(receivedCard);
        if (mDealtCards.contains(card)) {
            return false;
        }
        else {
            mDealtCards.remove(mCartToRemove);
            mDeck.remove(card);
            mDeck.add(mCartToRemove);
            mDealtCards.add(card);
            int color = getSuitColor(receivedCard.charAt(1));
            mButtonOccurredEvent.setText(receivedCard.charAt(0) + "");
            mButtonOccurredEvent.setTextColor(color);
            mButtonOccurredEvent.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            if (mPlayerOccuredEvent == BOARD_CARD) {
                mBoardCards.add(card);
                addBoardCard(receivedCard);

            } else {
                mPlayers[mPlayerOccuredEvent].add(card);
                addPlayerCard(receivedCard);
            }

            removePreviousCard(mCartToRemove);
            checkForCalculations();

            return true;
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void addPlayerCard(String card) {
        Drawable image = getCardSuit(card.charAt(1));
        mButtonOccurredEvent.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, image);
        mButtonOccurredEvent.setTextSize(40);
        mButtonOccurredEvent.setPadding(0, 60, 0, 60);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void addBoardCard(String card) {
        mButtonOccurredEvent.setTextSize(30);
        Drawable image = getCardSuitBoard(card.charAt(1));
        mButtonOccurredEvent.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, image);
        mButtonOccurredEvent.setPadding(0, 50, 0, 50);
    }

    public void removePreviousCard(Card card){
        for(CardSet cardset : mPlayers) {
            if (cardset.contains(card)){
                cardset.remove(card);
                return;
            }
        }
        mBoardCards.remove(card);
    }

    private int getSuitColor(char c) {
        if(c == 'S' || c == 'C') {
            return ContextCompat.getColor(this, R.color.blackCards);
        }
        else
            return ContextCompat.getColor(this, R.color.redCards);
    }

    public void checkForCalculations() {
        if (mAllPlayersCards == 4 && mAllBoardCards == 0) {
            startCalculate();
            mBoardCardOne.setClickable(true);
            mBoardCardOne.setBackgroundResource(R.drawable.card_back);
        }

        if (mAllBoardCards >= 3 && mAllBoardCards <= 4) {
            startCalculate();
        }

        if (mAllBoardCards == 5) {
            int handValue0, handValue1;
            long[] holeHand = new long[mPlayers.length];
            int i = 0;
            for (CardSet cs : mPlayers)
                holeHand[i++] = HandEval.encode(cs);
            long board = HandEval.encode(mBoardCards);
            handValue0 = HandEval.hand7Eval(board | holeHand[PLAYER_ONE]);
            handValue1 = HandEval.hand7Eval(board | holeHand[PLAYER_TWO]);
            long wins[] = new long[mPlayers.length];
            long ties[] = new long[mPlayers.length];
            ties[PLAYER_ONE] = 0;
            ties[PLAYER_TWO] = 0;
            double pots = 1;

            if(handValue0 > handValue1) {
                wins[PLAYER_ONE] = 1;
                wins[PLAYER_TWO] = 0;
                assignOdds(wins, ties, pots);
            }
            else if (handValue0 < handValue1) {
                wins[PLAYER_ONE] = 0;
                wins[PLAYER_TWO] = 1;
                assignOdds(wins, ties, pots);
            }
            else {
                wins[PLAYER_ONE] = 0;
                wins[PLAYER_TWO] = 0;
                ties[PLAYER_ONE] = 1;
                ties[PLAYER_TWO] = 1;
                assignOdds(wins, ties, pots);
            }
        }
    }


    public Drawable getCardSuit(char suit) {
        Drawable image = null;
        switch (suit) {
            case 'C':
                image = ContextCompat.getDrawable(this, R.drawable.image_very_small);
                break;
            case 'D':
                image = ContextCompat.getDrawable(this, R.drawable.diamonds_very_small);
                break;
            case 'H':
                image = ContextCompat.getDrawable(this, R.drawable.hearts_very_small);
                break;
            case 'S':
                image = ContextCompat.getDrawable(this, R.drawable.spade_very_small);
                break;
        }

        return image;
    }

    public Drawable getCardSuitBoard(char suit) {
        Drawable image = null;
        switch (suit) {
            case 'C':
                image = ContextCompat.getDrawable(this, R.drawable.image_very_small_board);
                break;
            case 'D':
                image = ContextCompat.getDrawable(this, R.drawable.diamonds_very_small_board);
                break;
            case 'H':
                image = ContextCompat.getDrawable(this, R.drawable.hearts_very_small_board);
                break;
            case 'S':
                image = ContextCompat.getDrawable(this, R.drawable.spade_very_small_board);
                break;
        }

        return image;
    }

    public char getCardSuit(Drawable[] suit) {
        char suitOfCard = 'A';
        for (Drawable drawable : suit) {
            if (drawable != null) {
                if (    drawable.getConstantState().equals(ContextCompat.getDrawable(this, R.drawable.image_very_small).getConstantState()) ||
                        drawable.getConstantState().equals(ContextCompat.getDrawable(this, R.drawable.image_very_small_board).getConstantState())) {
                    suitOfCard = 'C';
                    break;
                }
                if (    drawable.getConstantState().equals(ContextCompat.getDrawable(this, R.drawable.diamonds_very_small).getConstantState()) ||
                        drawable.getConstantState().equals(ContextCompat.getDrawable(this, R.drawable.diamonds_very_small_board).getConstantState())) {
                    suitOfCard = 'D';
                    break;
                }
                if (    drawable.getConstantState().equals(ContextCompat.getDrawable(this, R.drawable.hearts_very_small).getConstantState()) ||
                        drawable.getConstantState().equals(ContextCompat.getDrawable(this, R.drawable.hearts_very_small_board).getConstantState())) {
                    suitOfCard = 'H';
                    break;
                }
                if (    drawable.getConstantState().equals(ContextCompat.getDrawable(this, R.drawable.spade_very_small).getConstantState()) ||
                        drawable.getConstantState().equals(ContextCompat.getDrawable(this, R.drawable.spade_very_small_board).getConstantState())) {
                    suitOfCard = 'S';
                    break;
                }
            }
        }

        return suitOfCard;
    }

    private class ChangeCardListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            AddCardFragment d = new AddCardFragment();
            FragmentManager fm = getSupportFragmentManager();
            d.show(fm, "replaceCard");
            mButtonOccurredEvent = (Button)v;
            Drawable[] drawables = ((Button) v).getCompoundDrawables();
            switch (v.getId()) {
                case R.id.basic_quiz_player_one_card_one:
                case R.id.basic_quiz_player_one_card_two:
                    mPlayerOccuredEvent = PLAYER_ONE;
                    break;
                case R.id.basic_quiz_player_two_card_one:
                case R.id.basic_quiz_player_two_card_two:
                    mPlayerOccuredEvent = PLAYER_TWO;
                    break;
                default:
                    mPlayerOccuredEvent = BOARD_CARD;

            }
            mCartToRemove = new Card(((Button)v).getText().toString() + getCardSuit(drawables));
        }
    }

    private class CalculateOdds extends AsyncTask<CardSet, Void, Void> {
        Enumerator enumerator;
        double pots;

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            assignOdds(enumerator.getWins(), enumerator.getSplits(), pots);
        }

        @Override
        protected Void doInBackground(CardSet... params) {
            enumerator = new Enumerator(0, 1, mDeck, mPlayers, 0, params[0]);
            enumerator.run();

            for (long l : enumerator.getWins()) {
                pots += l;

            }

            for (double l : enumerator.getPartialPots()) {
                pots += l;
            }

            return null;
        }
    }

    protected void setStatusBarTranslucent(boolean makeTranslucent) {
        if (makeTranslucent) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

}
