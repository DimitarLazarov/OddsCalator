package com.example.chav.poker;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.chav.poker.communicators.AddCardCommunicator;

import java.util.ArrayList;
import java.util.Locale;

import ver4.poker.Card;
import ver4.poker.CardSet;
import ver4.poker.HandEval;
import ver4.showdown.Enumerator;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AddCardCommunicator {

    public static final int PLAYER_ONE = 0;
    public static final int PLAYER_TWO = 1;
    public static final int BOARD_CARD = 2;
    private Button mPOneCardOne;
    private Button mPOneCardTwo;
    private Button mPTwoCardOne;
    private Button mPTwoCardTwo;
    private Button buttonOccuredEvend;
    private Button nextButtonToClick;
    private Button mBoardCardOne;
    private Button mBoardCardTwo;
    private Button mBoardCardThree;
    private Button mBoardCardFour;
    private Button mBoardCardFive;
    private ImageButton mResetButton;
    private Button mAddPlayerButton;
    private TextView mPlayerOneWinOdds;
    private TextView mPlayerTwoWinOdds;
    private TextView mPlayerOneTieOdds;
    private TextView mPlayerTwoTieOdds;
    private TextView pTwo;
    private CardSet deck;
    private CardSet[] players;
    private CardSet boardCards;
    private int playerOccuredEvent;
    private int allPlayersCards;
    private int allBoardCards;
    private ArrayList<Card> dealtCards;
    private Card cartToRemove = null;
    private ArrayList<Button> allButtons;
    private ChangeCardListener changeCardListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPOneCardOne      = (Button) findViewById(R.id.basic_quiz_player_one_card_one);
        mPOneCardTwo      = (Button) findViewById(R.id.basic_quiz_player_one_card_two);
        mPTwoCardOne      = (Button) findViewById(R.id.basic_quiz_player_two_card_one);
        mPTwoCardTwo      = (Button) findViewById(R.id.basic_quiz_player_two_card_two);
        mBoardCardOne     = (Button) findViewById(R.id.basic_quiz_card_one_board);
        mBoardCardTwo     = (Button) findViewById(R.id.basic_quiz_card_two_board);
        mBoardCardThree   = (Button) findViewById(R.id.basic_quiz_card_three_board);
        mBoardCardFour    = (Button) findViewById(R.id.basic_quiz_card_four_board);
        mBoardCardFive    = (Button) findViewById(R.id.basic_quiz_card_five_board);
        mPlayerOneWinOdds = (TextView) findViewById(R.id.player_one_odds_win);
        mPlayerTwoWinOdds = (TextView) findViewById(R.id.player_two_odds_win);
        mPlayerOneTieOdds = (TextView) findViewById(R.id.player_one_odds_tie);
        mPlayerTwoTieOdds = (TextView) findViewById(R.id.player_two_odds_tie);
        mResetButton      = (ImageButton) findViewById(R.id.button_reset);
//        mAddPlayerButton  = (Button) findViewById(R.id.button_add_player);

        allButtons = new ArrayList<Button>();
        allButtons.add(mPOneCardOne);
        allButtons.add(mPOneCardTwo);
        allButtons.add(mPTwoCardOne);
        allButtons.add(mPTwoCardTwo);
        allButtons.add(mBoardCardOne);
        allButtons.add(mBoardCardTwo);
        allButtons.add(mBoardCardThree);
        allButtons.add(mBoardCardFour);
        allButtons.add(mBoardCardFive);

        changeCardListener = new ChangeCardListener();

//        mPOneCardOne.setOnClickListener(this);
//        mPOneCardTwo.setOnClickListener(this);
//        mPTwoCardOne.setOnClickListener(this);
//        mPTwoCardTwo.setOnClickListener(this);
//
//        mBoardCardOne.setOnClickListener(this);
//        mBoardCardTwo.setOnClickListener(this);
//        mBoardCardThree.setOnClickListener(this);
//        mBoardCardFour.setOnClickListener(this);
//        mBoardCardFive.setOnClickListener(this);

        resetStates();

//        players[2] = new CardSet();
//        players[0].add(new Card("Ad"));
//        players[0].add(new Card("Ac"));
//        players[1].add(new Card("Jd"));
//        players[1].add(new Card("Jc"));
//        players[2].add(new Card("Td"));
//        players[2].add(new Card("Tc"));
//        deck.remove(new Card("Ad"));
//        deck.remove(new Card("Ac"));
//        deck.remove(new Card("Jd"));
//        deck.remove(new Card("Jc"));
//        deck.remove(new Card("Td"));
//        deck.remove(new Card("Tc"));

//        HandEval.HandCategory handCategory = new HandEval.HandCategory();
//        Enumerator enumerator = new Enumerator()
//        HandEval.hand6Eval() handEval = new HandEval();
//        HandEval.ranksMask()
//        HandEval.hand5Eval()
//        Card card = new Card();
//        com.stevebrecher.showdown
//
//        UserInput;
//                Showdown;
//
//        CardSet cardSet = new CardSet();
//        Showdown showdown = new Showdown();
//        Help help = new Help();

        mResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetStates();
                resetAllTextViewWithAnimation();
            }
        });
    }

    private void resetStates() {

        resetAllButtonsStates();

        mPOneCardOne.setClickable(true);
        mPOneCardOne.setBackgroundResource(R.drawable.card_plus_sign);

        mPTwoCardOne.setClickable(true);
        mPTwoCardOne.setBackgroundResource(R.drawable.card_plus_sign);

        deck = CardSet.freshDeck();
        players = new CardSet[2];
        players[0] = new CardSet();
        players[1] = new CardSet();
        boardCards = new CardSet();
        allPlayersCards = 0;
        allBoardCards = 0;
        dealtCards = new ArrayList<Card>();
    }

    private void resetAllButtonsStates() {
        for(Button button : allButtons) {
            button.setOnClickListener(this);
            button.setClickable(false);
            button.setText("");
            button.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            button.setBackgroundResource(R.color.cardColor);
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
        double mPlayerOneWinningChance = wins[0] * 100.0 / pots;
        double mPlayerTwoWinningChance = wins[1] * 100.0 / pots;
        double mPlayerOneTieChance     = ties[0] * 100.0 / pots;
        double mPlayerTwoTieChance     = ties[1] * 100.0 / pots;


//        String winings = String.format("%.2f", mPlayerOneWinningChance);
//        mPlayerOneWinningChance = Float.parseFloat(winings);
//        float floaat = Float.parseFloat(winings);


        animateTextView((Float.parseFloat(mPlayerOneWinOdds.getText().toString())),
                        (float)mPlayerOneWinningChance, mPlayerOneWinOdds);
        animateTextView((Float.parseFloat(mPlayerTwoWinOdds.getText().toString())),
                        (float)mPlayerTwoWinningChance, mPlayerTwoWinOdds);
        animateTextView((Float.parseFloat(mPlayerOneTieOdds.getText().toString())),
                        (float)mPlayerOneTieChance, mPlayerOneTieOdds);
        animateTextView((Float.parseFloat(mPlayerTwoTieOdds.getText().toString())),
                        (float)mPlayerTwoTieChance, mPlayerTwoTieOdds);



//        mPlayerTwoWinOdds.setText(String.format("%.2f%%", mPlayerTwoWinningChance));
//        mPlayerTwoWinOdds.setText(String.format("%.2f%%", mPlayerTwoWinningChance));


//        ValueAnimator animator = new ValueAnimator();
//        animator.setObjectValues(0, (int) mPlayerOneWinningChance);
//        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            public void onAnimationUpdate(ValueAnimator animation) {
//                mPlayerOneWinOdds.setText(String.valueOf(animation.getAnimatedValue()));
//            }
//        });
//        animator.setEvaluator(new TypeEvaluator<Integer>() {
//            public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
//                return Math.round(startValue + (endValue - startValue) * fraction);
//            }
//        });
//        animator.setDuration(500);
//        animator.start();
//
//        ValueAnimator animator2 = new ValueAnimator();
//        animator2.setObjectValues(0, (int) mPlayerTwoWinningChance);
//        animator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            public void onAnimationUpdate(ValueAnimator animation) {
//                mPlayerTwoWinOdds.setText(String.valueOf(animation.getAnimatedValue()));
//            }
//        });
//        animator2.setEvaluator(new TypeEvaluator<Integer>() {
//            public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
//                return Math.round(startValue + (endValue - startValue) * fraction);
//            }
//        });
//        animator2.setDuration(500);
//        animator2.start();
//
//        mPlayerOneTieOdds.setText(String.format("%.2f%%", mPlayerOneTieChance));
//        mPlayerTwoTieOdds.setText(String.format("%.2f%%", mPlayerTwoTieChance));
    }

    @Override
    public void onClick(View v) {
        AddCardFragment d = new AddCardFragment();
        FragmentManager fm = getSupportFragmentManager();
        switch(v.getId()) {
            case R.id.basic_quiz_player_one_card_one:
                d.show(fm, "addCard");
                buttonOccuredEvend = mPOneCardOne;
                nextButtonToClick = mPOneCardTwo;
                playerOccuredEvent = PLAYER_ONE;
                allPlayersCards++;
                break;
            case R.id.basic_quiz_player_one_card_two:
                d.show(fm, "addCard");
                buttonOccuredEvend = mPOneCardTwo;
                nextButtonToClick = new Button(this);
                nextButtonToClick.setVisibility(View.GONE);
                playerOccuredEvent = PLAYER_ONE;
                allPlayersCards++;
                break;
            case R.id.basic_quiz_player_two_card_one:
                d.show(fm, "addCard");
                buttonOccuredEvend = mPTwoCardOne;
                nextButtonToClick = mPTwoCardTwo;
                playerOccuredEvent = PLAYER_TWO;
                allPlayersCards++;
                break;
            case R.id.basic_quiz_player_two_card_two:
                d.show(fm, "addCard");
                buttonOccuredEvend = mPTwoCardTwo;
                nextButtonToClick = new Button(this);
                nextButtonToClick.setVisibility(View.GONE);
                playerOccuredEvent = PLAYER_TWO;
                allPlayersCards++;
                break;
            case R.id.basic_quiz_card_one_board:
                d.show(fm, "addCard");
                buttonOccuredEvend = mBoardCardOne;
                playerOccuredEvent = BOARD_CARD;
                nextButtonToClick = mBoardCardTwo;
                allBoardCards++;
                break;
            case R.id.basic_quiz_card_two_board:
                d.show(fm, "addCard");
                buttonOccuredEvend = mBoardCardTwo;
                playerOccuredEvent = BOARD_CARD;
                nextButtonToClick = mBoardCardThree;
                allBoardCards++;
                break;
            case R.id.basic_quiz_card_three_board:
                d.show(fm, "addCard");
                buttonOccuredEvend = mBoardCardThree;
                playerOccuredEvent = BOARD_CARD;
                nextButtonToClick = mBoardCardFour;
                allBoardCards++;
                break;
            case R.id.basic_quiz_card_four_board:
                d.show(fm, "addCard");
                buttonOccuredEvend = mBoardCardFour;
                playerOccuredEvent = BOARD_CARD;
                nextButtonToClick = mBoardCardFive;
                allBoardCards++;
                break;
            case R.id.basic_quiz_card_five_board:
                d.show(fm, "addCard");
                buttonOccuredEvend = mBoardCardFive;
                nextButtonToClick = new Button(this);
                nextButtonToClick.setVisibility(View.GONE);
                mBoardCardFive.setClickable(false);
                playerOccuredEvent = BOARD_CARD;
                allBoardCards++;
                break;
        }
    }

    public void startCalculate(){
        new CalculateOdds().execute(boardCards, null, null);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean addCard(String receivedCard) {
        Card card = new Card(receivedCard);
        if (dealtCards.contains(card)) {
            return false;
        }
        else {
            buttonOccuredEvend.setOnClickListener(changeCardListener);
            nextButtonToClick.setBackgroundResource(R.drawable.card_plus_sign);
            nextButtonToClick.setClickable(true);
            int color = getSuitColor(receivedCard.charAt(1));
            buttonOccuredEvend.setText(receivedCard.charAt(0) + "");
            buttonOccuredEvend.setTextColor(color);
            buttonOccuredEvend.setBackgroundResource(R.color.cardColor);
            if (playerOccuredEvent == BOARD_CARD) {
                boardCards.add(card);
                addBoardCard(receivedCard, buttonOccuredEvend);

            } else {
                players[playerOccuredEvent].add(card);
                addPlayerCard(receivedCard, buttonOccuredEvend);
            }
            deck.remove(card);
            dealtCards.add(card);
            checkForCalculations();
            return true;
        }

    }

    public boolean replaceCard(String receivedCard){
        Card card = new Card(receivedCard);
        if (dealtCards.contains(card)) {
            return false;
        }
        else {
            dealtCards.remove(cartToRemove);
            deck.remove(card);
            deck.add(cartToRemove);
            dealtCards.add(card);
            int color = getSuitColor(receivedCard.charAt(1));
            buttonOccuredEvend.setText(receivedCard.charAt(0) + "");
            buttonOccuredEvend.setTextColor(color);
            buttonOccuredEvend.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            if (playerOccuredEvent == BOARD_CARD) {
                boardCards.add(card);
                addBoardCard(receivedCard, buttonOccuredEvend);

            } else {
                players[playerOccuredEvent].add(card);
                addPlayerCard(receivedCard, buttonOccuredEvend);
            }

            removePreviousCard(cartToRemove);
            checkForCalculations();

            return true;
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void addPlayerCard(String card, Button whereToAdd) {
        Drawable image = getCardSuit(card.charAt(1));
        buttonOccuredEvend.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, image);
        buttonOccuredEvend.setTextSize(40);
        buttonOccuredEvend.setPadding(0, 60, 0, 60);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void addBoardCard(String card, Button whereToAdd) {
        buttonOccuredEvend.setTextSize(30);
        Drawable image = getCardSuit(card.charAt(1));
        Bitmap bitmap = ((BitmapDrawable) image).getBitmap();
        Drawable resizedImage = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 70, 70, true));
        buttonOccuredEvend.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, resizedImage);
        buttonOccuredEvend.setPadding(0, 50, 0, 50);
    }

    public void removePreviousCard(Card card){
        for(CardSet cardset : players) {
            if (cardset.contains(card)){
                cardset.remove(card);
                return;
            }
        }
        boardCards.remove(card);
    }

    private int getSuitColor(char c) {
        if(c == 'S' || c == 'C') {
            return ContextCompat.getColor(this, R.color.blackCards);
        }
        else
            return ContextCompat.getColor(this, R.color.redCards);
    }

    public void checkForCalculations() {
        if (allPlayersCards == 4 && allBoardCards == 0) {
            startCalculate();
            mBoardCardOne.setClickable(true);
            mBoardCardOne.setBackgroundResource(R.drawable.card_plus_sign);
        }

        if (allBoardCards >= 3 && allBoardCards <=4) {
            startCalculate();
        }

        if (allBoardCards == 5) {
            int handValue0, handValue1;
            long[] holeHand = new long[players.length];
            int i = 0;
            for (CardSet cs : players)
                holeHand[i++] = HandEval.encode(cs);
            long board = HandEval.encode(boardCards);
            handValue0 = HandEval.hand7Eval(board | holeHand[0]);
            handValue1 = HandEval.hand7Eval(board | holeHand[1]);
            long wins[] = new long[2];
            long ties[] = new long[2];
            ties[0] = 0;
            ties[1] = 0;
            double pots = 1;

            if(handValue0 > handValue1) {
                wins[0] = 1;
                wins[1] = 0;
                assignOdds(wins, ties, pots);
            }
            else if (handValue0 < handValue1) {
                wins[0] = 0;
                wins[1] = 1;
                assignOdds(wins, ties, pots);
            }
            else {
                wins[0] = 0;
                wins[1] = 0;
                ties[0] = 1;
                ties[1] = 1;
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

    public char getCardSuit(Drawable[] suit) {
        char suitOfCard = 'A';
        for (Drawable drawable : suit) {
            if (drawable != null) {
                Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
                Drawable resizedImage = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 70, 70, true));
                if (drawable.getConstantState().equals(ContextCompat.getDrawable(this, R.drawable.image_very_small).getConstantState()) ||
                        drawable.getConstantState().equals(resizedImage.getConstantState())) {
                    suitOfCard = 'C';
                    break;
                }
                if (drawable.getConstantState().equals(ContextCompat.getDrawable(this, R.drawable.diamonds_very_small).getConstantState())) {
                    suitOfCard = 'D';
                    break;
                }
                if (drawable.getConstantState().equals(ContextCompat.getDrawable(this, R.drawable.hearts_very_small).getConstantState())) {
                    suitOfCard = 'H';
                    break;
                }
                if (drawable.getConstantState().equals(ContextCompat.getDrawable(this, R.drawable.spade_very_small).getConstantState())) {
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
            buttonOccuredEvend = (Button)v;
            Drawable[] drawables = ((Button) v).getCompoundDrawables();
            switch (v.getId()) {
                case R.id.basic_quiz_player_one_card_one:
                case R.id.basic_quiz_player_one_card_two:
                    playerOccuredEvent = PLAYER_ONE;
                    break;
                case R.id.basic_quiz_player_two_card_one:
                case R.id.basic_quiz_player_two_card_two:
                    playerOccuredEvent = PLAYER_TWO;
                    break;
                default:
                    playerOccuredEvent = BOARD_CARD;

            }
            cartToRemove = new Card(((Button)v).getText().toString() + getCardSuit(drawables));


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
            enumerator = new Enumerator(0, 1, deck, players, 0, params[0]);
            enumerator.run();

            for (long l : enumerator.getWins()) {
                Log.d("asd", "" + l);
                pots += l;

            }
            for (long l : enumerator.getSplits()) {
                Log.d("asd", "" + l);
            }

            for (double l : enumerator.getPartialPots()) {
                Log.d("asd", "" + l);
                pots += l;
            }

            Log.d("asd", "total pots: " + pots);
            return null;
        }
    }

}
