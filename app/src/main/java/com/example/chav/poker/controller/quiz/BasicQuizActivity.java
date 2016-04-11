package com.example.chav.poker.controller.quiz;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.os.Vibrator;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.chav.poker.R;

import java.util.Random;

import ver4.poker.Card;
import ver4.poker.CardSet;
import ver4.poker.HandEval;
import ver4.showdown.Enumerator;

public class BasicQuizActivity extends AppCompatActivity implements BasicQuizResultFragment.BasicQuizMessageCallback{

    private static final int PLAYER_ONE_WIN = 1;
    private static final int PLAYER_TWO_WIN = 2;
    private static final int PLAYER_ONE = 0;
    private static final int PLAYER_TWO = 1;
    private static final int PLAYERS = 2;
    private static final int CARDS_COUNT_PER_PLAYER = 2;
    private static final int TIE = 3;
    public static final int PLAYER_CARD_TEXT_SIZE = 40;
    public static final int PLAYER_CARD_PADDING = 60;
    public static final int BOARD_CARD_TEXT_SIZE = 30;
    public static final int BOARD_CARD_PADDING = 50;
    public static final String BASIC_QUIZ_FRAGMENT_WIN_TITLE = "title";
    public static final String BASIC_QUIZ_FRAGMENT_CURRENT_STREAK = "current_streak";
    public static final int TIME_DOUBLE_CLICK_PREVENT = 1000;
    public static final int TIME_VIBRATION = 100;

    private Button mPlayerOneCardOne;
    private Button mPlayerOneCardTwo;
    private Button mPlayerTwoCardOne;
    private Button mPlayerTwoCardTwo;
    private Button mBoardCardOne;
    private Button mBoardCardTwo;
    private Button mBoardCardThree;
    private Button mBoardCardFour;
    private Button mBoardCardFive;
    private LinearLayout mPlayerOneCards;
    private LinearLayout mPlayerTwoCards;
    private LinearLayout mBoardCards;
    private TextView mScore;
    private TextView mTimer;

    private CardSet mDeck;
    private CardSet mBoard;
    private CardSet[] mPlayers;
    private int mCurrentWiningStreak;
    private CountDownTimer mCountDownTimer;
    private int mUserChoice;
    private long mLastClickTime;
    private Vibrator mVibe;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quizzes);
        mVibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        setStatusBarTranslucent(true);

        mPlayerOneCardOne   = (Button) findViewById(R.id.basic_quiz_player_one_card_one);
        mPlayerOneCardTwo   = (Button) findViewById(R.id.basic_quiz_player_one_card_two);
        mPlayerTwoCardOne   = (Button) findViewById(R.id.basic_quiz_player_two_card_one);
        mPlayerTwoCardTwo   = (Button) findViewById(R.id.basic_quiz_player_two_card_two);
        mBoardCardOne       = (Button) findViewById(R.id.basic_quiz_card_one_board);
        mBoardCardTwo       = (Button) findViewById(R.id.basic_quiz_card_two_board);
        mBoardCardThree     = (Button) findViewById(R.id.basic_quiz_card_three_board);
        mBoardCardFour      = (Button) findViewById(R.id.basic_quiz_card_four_board);
        mBoardCardFive      = (Button) findViewById(R.id.basic_quiz_card_five_board);
        mPlayerOneCards     = (LinearLayout) findViewById(R.id.basic_quiz_player_one_card_holder);
        mPlayerTwoCards     = (LinearLayout) findViewById(R.id.basic_quiz_player_two_card_holder);
        mBoardCards         = (LinearLayout) findViewById(R.id.basic_quiz_board);
        mScore              = (TextView) findViewById(R.id.basic_quiz_score_counter);
        mScore.setText("Current Streak: 0");
        mTimer              = (TextView) findViewById(R.id.basic_quiz_timer);

        prepareBoard();

        mPlayerOneCards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < TIME_DOUBLE_CLICK_PREVENT){
                    return;
                }
                mVibe.vibrate(TIME_VIBRATION);
                mLastClickTime = SystemClock.elapsedRealtime();
                mUserChoice = PLAYER_ONE_WIN;
                startCalculate();
            }
        });
        mPlayerTwoCards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < TIME_DOUBLE_CLICK_PREVENT){
                    return;
                }
                mVibe.vibrate(TIME_VIBRATION);
                mLastClickTime = SystemClock.elapsedRealtime();
                mUserChoice = PLAYER_TWO_WIN;
                startCalculate();
            }
        });

        mBoardCards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < TIME_DOUBLE_CLICK_PREVENT){
                    return;
                }
                mVibe.vibrate(TIME_VIBRATION);
                mLastClickTime = SystemClock.elapsedRealtime();
                mUserChoice = TIE;
                startCalculate();
            }
        });
        startTimer();

    }

    private void startTimer() {

        final long[] seconds = new long[1];
        final long[] milliseconds = new long[1];
        final long[] microseconds = new long[1];

        mCountDownTimer = new CountDownTimer(5000, 10) {

            @Override
            public void onTick(long millisUntilFinished) {
                seconds[0] = millisUntilFinished / TIME_DOUBLE_CLICK_PREVENT;
                milliseconds[0] = (millisUntilFinished % 100) / 10;
                microseconds[0] = millisUntilFinished % 10;
                mTimer.setText(String.valueOf(seconds[0] + "." + microseconds[0] + microseconds[0]));
            }

            @Override
            public void onFinish() {
                mCurrentWiningStreak = 0;
                mTimer.setText("0.00");
                startLosingFragment();
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
            long wins[] = new long[PLAYERS];
            long ties[] = new long[PLAYERS];
            ties[PLAYER_ONE] = 0;
            ties[PLAYER_TWO] = 0;
            double pots = 1;

            if (handValue0 > handValue1) {
                wins[PLAYER_ONE] = 1;
                wins[PLAYER_TWO] = 0;
                if (mUserChoice == getWinner(wins, pots)) {
                    mCurrentWiningStreak++;
                    mCountDownTimer.cancel();
                    startWiningFragment();
                } else {
                    mCountDownTimer.cancel();
                    mCurrentWiningStreak = 0;
                    startLosingFragment();
                }
            } else if (handValue0 < handValue1) {
                wins[PLAYER_ONE] = 0;
                wins[PLAYER_TWO] = 1;
                if (mUserChoice == getWinner(wins, pots)) {
                    mCurrentWiningStreak++;
                    mCountDownTimer.cancel();
                    startWiningFragment();
                } else {
                    mCountDownTimer.cancel();
                    startLosingFragment();
                }
            } else {
                wins[PLAYER_ONE] = 0;
                wins[PLAYER_TWO] = 0;
                ties[PLAYER_ONE] = 1;
                ties[PLAYER_TWO] = 1;
                if (mUserChoice == getWinner(wins, pots)) {
                    mCurrentWiningStreak++;
                    mCountDownTimer.cancel();
                    startWiningFragment();
                } else {
                    startLosingFragment();
                    mCountDownTimer.cancel();
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

    private void prepareBoard(){

        mDeck = CardSet.shuffledDeck();
        mPlayers = new CardSet[PLAYERS];
        mPlayers[PLAYER_ONE] = drawCards(CARDS_COUNT_PER_PLAYER);
        mPlayers[PLAYER_TWO] = drawCards(CARDS_COUNT_PER_PLAYER);
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
        Object[] playerCards = players[PLAYER_ONE].toArray();
        cardOne = (Card) playerCards[0];
        cardTwo = (Card) playerCards[1];
        setPlayerCard(cardOne,mPlayerOneCardOne);
        setPlayerCard(cardTwo,mPlayerOneCardTwo);

        playerCards = players[PLAYER_TWO].toArray();
        cardOne = (Card) playerCards[0];
        cardTwo = (Card) playerCards[1];
        setPlayerCard(cardOne, mPlayerTwoCardOne);
        setPlayerCard(cardTwo, mPlayerTwoCardTwo);

        playerCards = board.toArray();
        for (int j = 0; j < playerCards.length; j++) {
            cardOne = (Card) playerCards[j];
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

        button.setTextSize(PLAYER_CARD_TEXT_SIZE);
        Drawable dr = getCardSuit(card.suitOf().toChar());
        button.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, dr);
        button.setPadding(0, PLAYER_CARD_PADDING, 0, PLAYER_CARD_PADDING);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void setBoardCard(Card card, Button button) {
        String cardRank  = card.rankOf().toChar()+"";
        button.setTextSize(BOARD_CARD_TEXT_SIZE);
        button.setText(cardRank);

        if (card.suitOf().toChar() == 'c' || card.suitOf().toChar() == 's') {
            button.setTextColor(ContextCompat.getColor(this, R.color.blackCards));
        } else {
            button.setTextColor(ContextCompat.getColor(this, R.color.redCards));
        }

        Drawable image = getCardSuit(card.suitOf().toChar());
        Bitmap bitmap = ((BitmapDrawable) image).getBitmap();
        Drawable resizedSuitImage = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 70, 70, true));
        button.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, resizedSuitImage);
        button.setPadding(0, BOARD_CARD_PADDING, 0, BOARD_CARD_PADDING);
    }


    private int getWinner(long[] wins, double pots) {
        double mPlayerOneWinningChance = wins[PLAYER_ONE] * 100.0 / pots;
        double mPlayerTwoWinningChance = wins[PLAYER_TWO] * 100.0 / pots;
        if (mPlayerOneWinningChance > mPlayerTwoWinningChance) {
            return PLAYER_ONE_WIN;
        } else if (mPlayerOneWinningChance < mPlayerTwoWinningChance) {
            return PLAYER_TWO_WIN;
        }

        return TIE;

    }


    private void startWiningFragment() {
        mScore.setText("Current Streak: " + mCurrentWiningStreak);
        mCountDownTimer.cancel();
        BasicQuizResultFragment basicQuizMessageCallback = new BasicQuizResultFragment();
        basicQuizMessageCallback.setCancelable(false);
        android.app.FragmentManager fm = getFragmentManager();
        Bundle args = new Bundle();
        args.putString(BASIC_QUIZ_FRAGMENT_WIN_TITLE, "Good Job!");
        args.putInt(BASIC_QUIZ_FRAGMENT_CURRENT_STREAK, mCurrentWiningStreak);
        basicQuizMessageCallback.setArguments(args);
        basicQuizMessageCallback.show(fm, "tagged");

    }

    private void startLosingFragment() {
        mCountDownTimer.cancel();
        BasicQuizResultFragment basicQuizMessageCallback = new BasicQuizResultFragment();
        basicQuizMessageCallback.setCancelable(false);
        android.app.FragmentManager fm = getFragmentManager();
        Bundle args = new Bundle();
        args.putString("title", "Incorrect");
        args.putInt(BASIC_QUIZ_FRAGMENT_CURRENT_STREAK, mCurrentWiningStreak);
        basicQuizMessageCallback.setArguments(args);
        basicQuizMessageCallback.show(fm, "tagged");
        mScore.setText("Current Streak: 0");
        mCurrentWiningStreak = 0;
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

    @Override
    public void onPlayAgain() {
        prepareBoard();
        startTimer();
    }

    @Override
    public void onCancel() {
        finish();
    }


    class CalculateOdds extends AsyncTask<CardSet, Void, Void> {
        Enumerator enumerator;
        double pots;


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if( mUserChoice == getWinner(enumerator.getWins(), pots)) {
                mCurrentWiningStreak++;
                startWiningFragment();
            } else {
                startLosingFragment();
            }

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mCountDownTimer.cancel();
    }

    protected void setStatusBarTranslucent(boolean makeTranslucent) {
        if (makeTranslucent) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

}
