package com.example.chav.poker.controller;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.chav.poker.R;

import java.util.Random;

import ver4.poker.Card;
import ver4.poker.CardSet;
import ver4.poker.HandEval;
import ver4.showdown.Enumerator;

public class SpeedQuizActivity extends AppCompatActivity implements SpeedQuizResultFragment.QuizMessageCallback{

    private static final int PLAYER_ONE_WIN = 1;
    private static final int PLAYER_TWO_WIN = 2;
    private static final int TIE = 3;

    private Button mPlayerOneCardOne;
    private Button mPlayerOneCardTwo;
    private Button mPlayerTwoCardOne;
    private Button mPlayerTwoCardTwo;
    private Button mBoardCardOne;
    private Button mBoardCardTwo;
    private Button mBoardCardThree;
    private Button mBoardCardFour;
    private Button mBoardCardFive;
//    private RelativeLayout mPlayerOneCards;
//    private RelativeLayout mPlayerTwoCards;

    private LinearLayout mPlayerOneCards;
    private LinearLayout mPlayerTwoCards;


    private LinearLayout mBoardCards;
    private ImageButton mReset;
    private TextView mScore;
    private TextView mTimer;
    private View mViewOccuredEvent;

//    private double mPlayerOneWinningChance;
//    private double mPlayerTwoWinningChance;

    private CardSet mDeck;
    private CardSet mBoard;
    private CardSet[] mPlayers;
    private int mCorrectAnswers;
    private int mTotalAnswers;
    private CountDownTimer mCountDownTimer;
    private int mUserChoice;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quizes_experimental);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            Window w = getWindow();
//            w.setStatusBarColor(ContextCompat.getColor(this, R.color.darkGreen));
//        }

        mPlayerOneCardOne = (Button) findViewById(R.id.basic_quiz_player_one_card_one);
        mPlayerOneCardTwo = (Button) findViewById(R.id.basic_quiz_player_one_card_two);
        mPlayerTwoCardOne = (Button) findViewById(R.id.basic_quiz_player_two_card_one);
        mPlayerTwoCardTwo = (Button) findViewById(R.id.basic_quiz_player_two_card_two);
        mBoardCardOne = (Button) findViewById(R.id.basic_quiz_card_one_board);
        mBoardCardTwo = (Button) findViewById(R.id.basic_quiz_card_two_board);
        mBoardCardThree = (Button) findViewById(R.id.basic_quiz_card_three_board);
        mBoardCardFour = (Button) findViewById(R.id.basic_quiz_card_four_board);
        mBoardCardFive = (Button) findViewById(R.id.basic_quiz_card_five_board);
        mPlayerOneCards = (LinearLayout) findViewById(R.id.basic_quiz_player_one_card_holder);
        mPlayerTwoCards = (LinearLayout) findViewById(R.id.basic_quiz_player_two_card_holder);
//        mReset = (ImageButton) findViewById(R.id.basic_quiz_button_reset);
        mBoardCards = (LinearLayout) findViewById(R.id.basic_quiz_board);
        mScore = (TextView) findViewById(R.id.basic_quiz_score_counter);
        mTimer = (TextView) findViewById(R.id.basic_quiz_timer);


//        mReset.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                prepareBoard();
//            }
//        });

        prepareBoard();

        mPlayerOneCards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUserChoice = PLAYER_ONE_WIN;
   //             startAnimationn(mPlayerOneCards);
                mViewOccuredEvent = mPlayerOneCards;
                startCalculate();
//                new CalculateOdds().execute(mBoard);
            }
        });
        mPlayerTwoCards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUserChoice = PLAYER_TWO_WIN;
     //           startAnimationn(mPlayerTwoCards);
                mViewOccuredEvent = mPlayerTwoCards;
                startCalculate();
//                new CalculateOdds().execute(mBoard);
            }
        });

        mBoardCards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUserChoice = TIE;
       //         startAnimationn(mBoardCards);
                mViewOccuredEvent = mBoardCards;
                startCalculate();
            }
        });
        startTimer();

    }

    private void startTimer() {

        final long[] seconds = new long[1];
        final long[] miliseconds = new long[1];
        final long[] microseconds = new long[1];

        mCountDownTimer = new CountDownTimer(30000, 10) {

            @Override
            public void onTick(long millisUntilFinished) {
                seconds[0] = millisUntilFinished / 1000;
                miliseconds[0] = (millisUntilFinished % 100) / 10;
                microseconds[0] = millisUntilFinished % 10;
//                mTimer.setText(String.valueOf(millisUntilFinished/1000));
                mTimer.setText(String.valueOf(seconds[0] + "." + microseconds[0] + microseconds[0]));
            }

            @Override
            public void onFinish() {
                mTimer.setText("0.00");
                startResultsFragment();
            }
        }.start();

    }

    private void startCalculate() {
        if (mBoard.toArray().length >= 3 && mBoard.toArray().length <=4) {
            new CalculateOdds().execute(mBoard);
        }

        if (mBoard.toArray().length == 5) {
            int handValue0, handValue1;
            long[] holeHand = new long[mPlayers.length];
            int i = 0;
            for (CardSet cs : mPlayers)
                holeHand[i++] = HandEval.encode(cs);
            long board = HandEval.encode(mBoard);
            handValue0 = HandEval.hand7Eval(board | holeHand[0]);
            handValue1 = HandEval.hand7Eval(board | holeHand[1]);
            long wins[] = new long[2];
            long ties[] = new long[2];
            ties[0] = 0;
            ties[1] = 0;
            double pots = 1;

            if (handValue0 > handValue1) {
                wins[0] = 1;
                wins[1] = 0;
                if (mUserChoice == getWinner(wins, ties, pots)) {
                    mCorrectAnswers++;
                    mTotalAnswers++;
                    mScore.setText("Score: " + mCorrectAnswers);
                    prepareBoard();
//                    startWiningFragment();
//                    Toast.makeText(getBaseContext(), "Good job " + mPlayerOneWinningChance + "    " + mPlayerTwoWinningChance, Toast.LENGTH_SHORT).show();
                } else {
                    mTotalAnswers++;
                    startAnimationn(mViewOccuredEvent);
                    prepareBoard();
//                    startLosingFragment();
//                    Toast.makeText(getBaseContext(), "Incorrect " + mPlayerOneWinningChance + "    " + mPlayerTwoWinningChance, Toast.LENGTH_SHORT).show();
                }
            } else if (handValue0 < handValue1) {
                wins[0] = 0;
                wins[1] = 1;
                if (mUserChoice == getWinner(wins, ties, pots)) {
                    mCorrectAnswers++;
                    mTotalAnswers++;
                    mScore.setText("Score: " + mCorrectAnswers);
                    prepareBoard();
//                    startWiningFragment();
//                    Toast.makeText(getBaseContext(), "Good job " + mPlayerOneWinningChance + "    " + mPlayerTwoWinningChance, Toast.LENGTH_SHORT).show();
                } else {
                    mTotalAnswers++;
                    startAnimationn(mViewOccuredEvent);
                    prepareBoard();
//                    startLosingFragment();
//                    Toast.makeText(getBaseContext(), "Incorrect " + mPlayerOneWinningChance + "    " + mPlayerTwoWinningChance, Toast.LENGTH_SHORT).show();
                }
            } else {
                wins[0] = 0;
                wins[1] = 0;
                ties[0] = 1;
                ties[1] = 1;
                if (mUserChoice == getWinner(wins, ties, pots)) {
                    mCorrectAnswers++;
                    mTotalAnswers++;
                    mScore.setText("Score: " + mCorrectAnswers);
                    prepareBoard();
//                    startWiningFragment();
//                    Toast.makeText(getBaseContext(), "Good job " + mPlayerOneWinningChance + "    " + mPlayerTwoWinningChance, Toast.LENGTH_SHORT).show();
                } else {
                    mTotalAnswers++;
                    startAnimationn(mViewOccuredEvent);
                    prepareBoard();
//                    startLosingFragment();
//                    Toast.makeText(getBaseContext(), "Incorrect " + mPlayerOneWinningChance + "    " + mPlayerTwoWinningChance, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private CardSet drawCards(int numberOfCards) {
        CardSet cards = new CardSet();
        for (int i = 0; i < numberOfCards; i++) {
            Card card = mDeck.iterator().next();
            mDeck.remove(card);
            cards.add(card);
        }
        return cards;
    }

    private void resetResult(){
        mCorrectAnswers = 0;
        mScore.setText("Score: 0");
    }

    private void prepareBoard(){

        mDeck = CardSet.shuffledDeck();
        mPlayers = new CardSet[2];
        mPlayers[0] = drawCards(2);
        mPlayers[1] = drawCards(2);
        Random r = new Random();
        int rGen = r.nextInt(3) + 3;
        mBoard = drawCards(rGen);
        if (rGen >= 3) {
            mBoardCardFour.setText("");
            mBoardCardFour.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            mBoardCardFive.setText("");
            mBoardCardFive.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        }
        setBoard(mPlayers, mBoard);
    }

    private void setBoard(CardSet[] players, CardSet board) {
        Card cardOne = null;
        Card cardTwo = null;
        Object[] x = players[0].toArray();
        cardOne = (Card) x[0];
        cardTwo = (Card) x[1];
        setPlayerCard(cardOne,mPlayerOneCardOne);
        setPlayerCard(cardTwo,mPlayerOneCardTwo);

        x = players[1].toArray();
        cardOne = (Card) x[0];
        cardTwo = (Card) x[1];
        setPlayerCard(cardOne, mPlayerTwoCardOne);
        setPlayerCard(cardTwo, mPlayerTwoCardTwo);

        x = board.toArray();
        for (int j = 0; j < x.length; j++) {
            cardOne = (Card) x[j];
            switch (j) {
                case 0:
                    setBoardCard(cardOne, mBoardCardOne);
                    break;
                case 1:
                    setBoardCard(cardOne, mBoardCardTwo);
                    break;
                case 2:
                    setBoardCard(cardOne, mBoardCardThree);
                    break;
                case 3:
                    setBoardCard(cardOne, mBoardCardFour);
                    break;
                case 4:
                    setBoardCard(cardOne, mBoardCardFive);
                    break;
            }
        }

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void setPlayerCard(Card card, Button button) {
        String cardRank  = card.rankOf().toChar()+"";
        button.setText(cardRank);

        if (card.suitOf().toChar() == 'c' || card.suitOf().toChar() == 's') {
            button.setTextColor(ContextCompat.getColor(this, R.color.blackCards));
        } else {
            button.setTextColor(ContextCompat.getColor(this, R.color.redCards));
        }

        button.setTextSize(40);
        Drawable dr = getCardSuit(card.suitOf().toChar());
        button.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, dr);
        button.setPadding(0, 60, 0, 60);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void setBoardCard(Card card, Button button) {
        String cardRank  = card.rankOf().toChar()+"";
        button.setTextSize(30);
        button.setText(cardRank);

        if (card.suitOf().toChar() == 'c' || card.suitOf().toChar() == 's') {
            button.setTextColor(ContextCompat.getColor(this, R.color.blackCards));
        } else {
            button.setTextColor(ContextCompat.getColor(this, R.color.redCards));
        }

        Drawable image = getCardSuit(card.suitOf().toChar());
        Bitmap bitmap = ((BitmapDrawable) image).getBitmap();
        Drawable resizedImage = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 70, 70, true));
        button.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, resizedImage);
        button.setPadding(0, 50, 0, 50);
    }


    private int getWinner(long[] wins, long[] ties, double pots) {
        double mPlayerOneWinningChance = wins[0] * 100.0 / pots;
        double mPlayerTwoWinningChance = wins[1] * 100.0 / pots;
//        double mPlayerOneTieChance = ties[0] * 100.0 / pots;
//        double mPlayerTwoTieChance = ties[1] * 100.0 / pots;
        if (mPlayerOneWinningChance > mPlayerTwoWinningChance) {
            return PLAYER_ONE_WIN;
        } else if (mPlayerOneWinningChance < mPlayerTwoWinningChance) {
            return PLAYER_TWO_WIN;
        }

        return TIE; //TODO add tie


//        mPlayerOneTieOdds.setText(String.format("%.2f%%", mPlayerOneTieChance));
//        mPlayerTwoTieOdds.setText(String.format("%.2f%%", mPlayerTwoTieChance));
    }

    @Override
    public void onMessageAcknowledged() {
        prepareBoard();
        resetResult();
        startTimer();
    }

    private void startResultsFragment(){
        SpeedQuizResultFragment speedQuizResultFragment = new SpeedQuizResultFragment();
        FragmentManager fm = getSupportFragmentManager();
        Bundle args = new Bundle();
        args.putString("title", "Results");
        args.putString("message", "Correct: " + mCorrectAnswers);
        args.putString("score", "Total: "  + mTotalAnswers);
        speedQuizResultFragment.setArguments(args);
        speedQuizResultFragment.show(fm, "tagged");
    }

    private void startAnimationn(View v) {
        //loading xml from anim folder
        Animation localAnimation = AnimationUtils.loadAnimation(this, R.anim.shake_animation);
        //You can now apply the animation to a view
        v.startAnimation(localAnimation);
    }


    private void startWiningFragment() {
        SpeedQuizResultFragment speedQuizResultFragment = new SpeedQuizResultFragment();
        FragmentManager fm = getSupportFragmentManager();
        Bundle args = new Bundle();
        args.putString("title", "Correct!");
        args.putString("message", "+5 points!");
        args.putString("score", "250 points!!");
        speedQuizResultFragment.setArguments(args);
        speedQuizResultFragment.show(fm, "tagged");
    }

    private void startLosingFragment() {
        SpeedQuizResultFragment speedQuizResultFragment = new SpeedQuizResultFragment();
        FragmentManager fm = getSupportFragmentManager();
        Bundle args = new Bundle();
        args.putString("title", "Incorrect");
        args.putString("message", "Try Again");
        args.putString("score", "250 points");
        speedQuizResultFragment.setArguments(args);
        speedQuizResultFragment.show(fm, "tagged");
    }

    public Drawable getCardSuit(char suit) {
        Drawable image = null;
        switch (suit) {
            case 'c':
                image = ContextCompat.getDrawable(this, R.drawable.image_very_small);
                break;
            case 'd':
                image = ContextCompat.getDrawable(this, R.drawable.diamonds_very_small);
                break;
            case 'h':
                image = ContextCompat.getDrawable(this, R.drawable.hearts_very_small);
                break;
            case 's':
                image = ContextCompat.getDrawable(this, R.drawable.spade_very_small);
                break;
        }

        return image;
    }


    class CalculateOdds extends AsyncTask<CardSet, Void, Void> {
        Enumerator enumerator;
        double pots;


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if( mUserChoice == getWinner(enumerator.getWins(), enumerator.getSplits(), pots)) {
                mCorrectAnswers++;
                mTotalAnswers++;
                mScore.setText("Score: " + mCorrectAnswers);
                prepareBoard();
//                startWiningFragment();
//                Toast.makeText(getBaseContext(), "Good job " + mPlayerOneWinningChance + "    " + mPlayerTwoWinningChance, Toast.LENGTH_SHORT).show();
            } else {
                startAnimationn(mViewOccuredEvent);
                mTotalAnswers++;
                prepareBoard();
//                startLosingFragment();
//                Toast.makeText(getBaseContext(), "Incorrect " + mPlayerOneWinningChance + "    " + mPlayerTwoWinningChance, Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        protected Void doInBackground(CardSet... params) {
            enumerator = new Enumerator(0, 1, mDeck, mPlayers, 0, params[0]);
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mCountDownTimer.cancel();
    }
}
