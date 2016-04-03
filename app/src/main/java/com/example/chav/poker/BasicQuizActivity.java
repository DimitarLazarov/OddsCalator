package com.example.chav.poker;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.Random;

import ver4.poker.Card;
import ver4.poker.CardSet;
import ver4.showdown.Enumerator;

public class BasicQuizActivity extends AppCompatActivity {

    private Button mPlayerOneCardOne;
    private Button mPlayerOneCardTwo;
    private Button mPlayerTwoCardOne;
    private Button mPlayerTwoCardTwo;
    private Button mBoardCardOne;
    private Button mBoardCardTwo;
    private Button mBoardCardThree;
    private Button mBoardCardFour;
    private Button mBoardCardFive;
    private RelativeLayout mPlayerOneCards;
    private RelativeLayout mPlayerTwoCards;

    private double mPlayerOneWinningChance;
    private double mPlayerTwoWinningChance;

//    private ArrayList<Card> mDealtCards;
    private CardSet mDeck;
    private CardSet mBoard;
    private CardSet[] mPlayers;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_quiz);

        mDeck = CardSet.shuffledDeck();
        mPlayers = new CardSet[2];
        mPlayers[0] = drawCards(2);
        mPlayers[1] = drawCards(2);
        Random r = new Random();
        mBoard = drawCards(r.nextInt(3) + 3);

        mPlayerOneCardOne = (Button) findViewById(R.id.basic_quiz_player_one_card_one);
        mPlayerOneCardTwo = (Button) findViewById(R.id.basic_quiz_player_one_card_two);
        mPlayerTwoCardOne = (Button) findViewById(R.id.basic_quiz_player_two_card_one);
        mPlayerTwoCardTwo = (Button) findViewById(R.id.basic_quiz_player_two_card_two);
        mBoardCardOne = (Button) findViewById(R.id.basic_quiz_card_one_board);
        mBoardCardTwo = (Button) findViewById(R.id.basic_quiz_card_two_board);
        mBoardCardThree = (Button) findViewById(R.id.basic_quiz_card_three_board);
        mBoardCardFour = (Button) findViewById(R.id.basic_quiz_card_four_board);
        mBoardCardFive = (Button) findViewById(R.id.basic_quiz_card_five_board);

        setBoard(mPlayers, mBoard);

        mPlayerOneCards = (RelativeLayout) findViewById(R.id.basic_quiz_player_one_card_holder);
        mPlayerOneCards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Card card = mDeck.iterator().next();
                Toast.makeText(v.getContext(), card.rankOf().toChar() + "      " + card.suitOf().toChar(), Toast.LENGTH_SHORT).show();
            }
        });
        mPlayerTwoCards = (RelativeLayout) findViewById(R.id.basic_quiz_player_two_card_holder);
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

    private void setBoard(CardSet[] players, CardSet board) {
        int i = 0;
        Card card = null;

        while (players[0].iterator().hasNext()) {
            i++;
            card = players[0].iterator().next();
            switch (i) {
                case 1:
                    Log.d("iterate", "p1.p1");
                    setPlayerCard(card, mPlayerOneCardOne);
                    break;
                case 2:
                    Log.d("iterate", "p1.p2");
                    setPlayerCard(card, mPlayerOneCardTwo);
                    break;
                default:
                    return;

            }
        }

        while (players[1].iterator().hasNext()) {
            i++;
            card = players[1].iterator().next();
            switch (i) {
                case 1:
                    Log.d("iterate", "p2.p1");
                    setPlayerCard(card, mPlayerOneCardOne);
                    break;
                case 2:
                    Log.d("iterate", "p2.p2");
                    setPlayerCard(card, mPlayerOneCardTwo);
                    break;
                default:
                    return;
            }
        }

        while (board.iterator().hasNext()) {
            i++;
            card = players[1].iterator().next();
            switch (i) {
                case 1:
                    setPlayerCard(card, mBoardCardOne);
                    break;
                case 2:
                    setPlayerCard(card, mBoardCardTwo);
                    break;
                case 3:
                    setPlayerCard(card, mBoardCardThree);
                    break;
                case 4:
                    setPlayerCard(card, mBoardCardFour);
                    break;
                case 5:
                    setPlayerCard(card, mBoardCardFive);
                    break;
                default:
                    return;
            }
        }


//        mPlayerOneCardOne.setText(players[0].iterator().next().rankOf().toChar());
//        mPlayerOneCardTwo.setText(players[0].iterator().next().rankOf().toChar());
//        mPlayerTwoCardOne.setText(players[1].iterator().next().rankOf().toChar());
//        mPlayerTwoCardTwo.setText(players[1].iterator().next().rankOf().toChar());
//        mBoardCardOne.setText(board.iterator().next().rankOf().toChar());
//        mBoardCardTwo.setText(board.iterator().next().rankOf().toChar());
//        mBoardCardThree.setText(board.iterator().next().rankOf().toChar());
//        if (board.iterator().hasNext()) {
//            mBoardCardFour.setText(board.iterator().next().rankOf().toChar());
//        }
//        if (board.iterator().hasNext()) {
//            mBoardCardFive.setText(board.iterator().next().rankOf().toChar());
//        }

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void setPlayerCard(Card card, Button button) {
        button.setTextSize(40);
        Drawable dr = getCardSuit(card.suitOf().toChar());
        button.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, dr);
        button.setPadding(0, 60, 0, 60);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void setBoardCard(Card card, Button button) {
        button.setTextSize(30);
        Drawable image = getCardSuit(card.suitOf().toChar());
        Bitmap bitmap = ((BitmapDrawable) image).getBitmap();
        Drawable resizedImage = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 70, 70, true));
        button.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, resizedImage);
        button.setPadding(0, 50, 0, 50);
    }


    private void assignOdds(long[] wins, long[] ties, double pots) {
        mPlayerOneWinningChance = wins[0] * 100.0 / pots;
        mPlayerTwoWinningChance = wins[1] * 100.0 / pots;
//        double mPlayerOneTieChance = ties[0] * 100.0 / pots;
//        double mPlayerTwoTieChance = ties[1] * 100.0 / pots;


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



//        mPlayerOneTieOdds.setText(String.format("%.2f%%", mPlayerOneTieChance));
//        mPlayerTwoTieOdds.setText(String.format("%.2f%%", mPlayerTwoTieChance));
    }


    class CalculateOdds extends AsyncTask<CardSet, Void, Void> {
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
}
