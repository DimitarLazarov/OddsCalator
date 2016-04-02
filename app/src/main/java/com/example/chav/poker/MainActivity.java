package com.example.chav.poker;

import android.annotation.TargetApi;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chav.poker.communicators.AddCardCommunicator;

import ver4.poker.Card;
import ver4.poker.CardSet;
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
    private Button mBoardCardOne;
    private Button mBoardCardTwo;
    private Button mBoardCardThree;
    private Button mBoardCardFour;
    private Button mBoardCardFive;
    private Button mResetButton;
    private Button mAddPlayerButton;
    private TextView mPlayerOneWinOdds;
    private TextView mPlayerTwoWinOdds;
    private TextView mPlayerOneTieOdds;
    private TextView mPlayerTwoTieOdds;
    private TextView pTwo;
    private CardSet deck;
    private CardSet[] players;
    private CardSet boardCards = new CardSet();
    private int playerOccuredEvent;
    private int allPlayersCards = 0;
    private int allBoardCards = 0;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPOneCardOne      = (Button) findViewById(R.id.player_one_card_one);
        mPOneCardTwo      = (Button) findViewById(R.id.player_one_card_two);
        mPTwoCardOne      = (Button) findViewById(R.id.player_two_card_one);
        mPTwoCardTwo      = (Button) findViewById(R.id.player_two_card_two);
        mBoardCardOne     = (Button) findViewById(R.id.card_one_board);
        mBoardCardTwo     = (Button) findViewById(R.id.card_two_board);
        mBoardCardThree   = (Button) findViewById(R.id.card_three_board);
        mBoardCardFour    = (Button) findViewById(R.id.card_four_board);
        mBoardCardFive    = (Button) findViewById(R.id.card_five_board);
        mPlayerOneWinOdds = (TextView) findViewById(R.id.player_one_odds_win);
        mPlayerTwoWinOdds = (TextView) findViewById(R.id.player_two_odds_win);
        mPlayerOneTieOdds = (TextView) findViewById(R.id.player_one_odds_tie);
        mPlayerTwoTieOdds = (TextView) findViewById(R.id.player_two_odds_tie);
        mResetButton      = (Button) findViewById(R.id.button_reset);
        mAddPlayerButton  = (Button) findViewById(R.id.button_add_player);

        mPOneCardOne.setOnClickListener(this);
        mPOneCardTwo.setOnClickListener(this);
        mPTwoCardOne.setOnClickListener(this);
        mPTwoCardTwo.setOnClickListener(this);

        mBoardCardOne.setOnClickListener(this);
        mBoardCardTwo.setOnClickListener(this);
        mBoardCardThree.setOnClickListener(this);
        mBoardCardFour.setOnClickListener(this);
        mBoardCardFive.setOnClickListener(this);



        deck = CardSet.freshDeck();
        players = new CardSet[2];
        players[0] = new CardSet();
        players[1] = new CardSet();
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
    }

    private void assignOdds(long[] wins, long[] ties, double pots) {
        double mPlayerOneWinningChance = wins[0] * 100.0 / pots;
        double mPlayerTwoWinningChance = wins[1] * 100.0 / pots;
        double mPlayerOneTieChance = ties[0] * 100.0 / pots;
        double mPlayerTwoTieChance = ties[1] * 100.0 / pots;

        mPlayerOneWinOdds.setText(String.format("%.2f%%", mPlayerOneWinningChance));
        mPlayerTwoWinOdds.setText(String.format("%.2f%%", mPlayerTwoWinningChance));

        mPlayerOneTieOdds.setText(String.format("%.2f%%", mPlayerOneTieChance));
        mPlayerTwoTieOdds.setText(String.format("%.2f%%", mPlayerTwoTieChance));
    }

    @Override
    public void onClick(View v) {
        AddCardFragment d = new AddCardFragment();
        FragmentManager fm = getSupportFragmentManager();
        switch(v.getId()) {
            case R.id.player_one_card_one:
                d.show(fm, "sth");
                buttonOccuredEvend = mPOneCardOne;
                mPOneCardOne.setClickable(false);
                mPOneCardTwo.setClickable(true);
                mPOneCardTwo.setBackgroundResource(R.drawable.card_plus_sign);
                playerOccuredEvent = PLAYER_ONE;
                allPlayersCards++;
                break;
            case R.id.player_one_card_two:
                d.show(fm, "sth");
                buttonOccuredEvend = mPOneCardTwo;
                mPOneCardTwo.setClickable(false);
                playerOccuredEvent = PLAYER_ONE;
                allPlayersCards++;
                break;
            case R.id.player_two_card_one:
                d.show(fm, "sth");
                buttonOccuredEvend = mPTwoCardOne;
                mPTwoCardOne.setClickable(false);
                mPTwoCardTwo.setClickable(true);
                playerOccuredEvent = PLAYER_TWO;
                allPlayersCards++;
                break;
            case R.id.player_two_card_two:
                d.show(fm, "sth");
                buttonOccuredEvend = mPTwoCardTwo;
                mPTwoCardTwo.setClickable(false);
                playerOccuredEvent = PLAYER_TWO;
                allPlayersCards++;
                break;
            case R.id.card_one_board:
                d.show(fm, "sth");
                buttonOccuredEvend = mBoardCardOne;
                mBoardCardOne.setClickable(false);
                mBoardCardTwo.setClickable(true);
                playerOccuredEvent = BOARD_CARD;
                allBoardCards++;
            case R.id.card_two_board:
                d.show(fm, "sth");
                buttonOccuredEvend = mBoardCardTwo;
                mBoardCardTwo.setClickable(false);
                mBoardCardThree.setClickable(true);
                playerOccuredEvent = BOARD_CARD;
                allBoardCards++;
            case R.id.card_three_board:
                d.show(fm, "sth");
                buttonOccuredEvend = mBoardCardThree;
                mBoardCardThree.setClickable(false);
                mBoardCardFour.setClickable(true);
                playerOccuredEvent = BOARD_CARD;
                allBoardCards++;
            case R.id.card_four_board:
                d.show(fm, "sth");
                buttonOccuredEvend = mBoardCardFour;
                mBoardCardFour.setClickable(false);
                mBoardCardFive.setClickable(true);
                playerOccuredEvent = BOARD_CARD;
                allBoardCards++;
            case R.id.card_five_board:
                d.show(fm, "sth");
                buttonOccuredEvend = mBoardCardFive;
                mBoardCardFive.setClickable(false);
                playerOccuredEvent = BOARD_CARD;
                allBoardCards++;
        }
    }

    public void startCalculate(){
        new CalculateOdds().execute(boardCards,null,null);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void addCart(String string) {
        buttonOccuredEvend.setText(string.charAt(0) + "");
        buttonOccuredEvend.setBackgroundResource(R.color.cardColor);
        Drawable image = getCardSuit(string.charAt(1));
        buttonOccuredEvend.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, image);
        buttonOccuredEvend.setTextSize(40);
        buttonOccuredEvend.setPadding(0, 60, 0, 60);
        Card card = new Card(string);
        if (playerOccuredEvent == BOARD_CARD) {
            boardCards.add(card);
        }
        else
            players[playerOccuredEvent].add(card);
        deck.remove(card);

        if (allPlayersCards == 4) {
            startCalculate();
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
