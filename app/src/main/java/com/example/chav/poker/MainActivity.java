package com.example.chav.poker;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.chav.poker.communicators.AddCardCommunicator;

import ver4.poker.Card;
import ver4.poker.CardSet;
import ver4.showdown.Enumerator;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AddCardCommunicator {

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
    private int playerOccuredEvent;
    private double mPlayerOneWinningChance;
    private double mPlayerTwoWinningChance;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPOneCardOne = (Button) findViewById(R.id.player_one_card_one);
        mPOneCardTwo = (Button) findViewById(R.id.player_one_card_two);
        mPTwoCardOne = (Button) findViewById(R.id.player_two_card_one);
        mPTwoCardTwo = (Button) findViewById(R.id.player_two_card_two);
        mBoardCardOne = (Button) findViewById(R.id.card_one_board);
        mBoardCardTwo = (Button) findViewById(R.id.card_two_board);
        mBoardCardThree = (Button) findViewById(R.id.card_three_board);
        mBoardCardFour = (Button) findViewById(R.id.card_four_board);
        mBoardCardFive = (Button) findViewById(R.id.card_five_board);
        mPlayerOneWinOdds = (TextView) findViewById(R.id.player_one_odds_win);
        mPlayerTwoWinOdds = (TextView) findViewById(R.id.player_two_odds_win);
        mPlayerOneTieOdds = (TextView) findViewById(R.id.player_one_odds_tie);
        mPlayerTwoTieOdds = (TextView) findViewById(R.id.player_two_odds_tie);
        mResetButton = (Button) findViewById(R.id.button_reset);
        mAddPlayerButton = (Button) findViewById(R.id.button_add_player);

        mPOneCardOne.setOnClickListener(this);
        mPOneCardTwo.setOnClickListener(this);
        mPTwoCardOne.setOnClickListener(this);
        mPTwoCardTwo.setOnClickListener(this);

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
        mPlayerOneWinningChance = wins[0] * 100.0 / pots;
        mPlayerTwoWinningChance = wins[1] * 100.0 / pots;

        mPlayerOneWinOdds.setText(String.format("%.2f%%", mPlayerOneWinningChance));
        mPlayerTwoWinOdds.setText(String.format("%.2f%%", mPlayerTwoWinningChance));
    }

    @Override
    public void onClick(View v) {
        AddCardFragment d = new AddCardFragment();
        FragmentManager fm = getSupportFragmentManager();
        switch(v.getId()) {
            case R.id.player_one_card_one:
                d.show(fm, "sth");
                buttonOccuredEvend = mPOneCardOne;
                playerOccuredEvent = 0;
                break;
            case R.id.player_one_card_two:
                d.show(fm, "sth");
                buttonOccuredEvend = mPOneCardTwo;
                playerOccuredEvent = 0;
                break;
            case R.id.player_two_card_one:
                d.show(fm, "sth");
                buttonOccuredEvend = mPTwoCardOne;
                playerOccuredEvent = 1;
                break;
            case R.id.player_two_card_two:
                d.show(fm, "sth");
                buttonOccuredEvend = mPTwoCardTwo;
                playerOccuredEvent = 1;
                break;
        }
    }

    public void startCalculate(){
        new CalculateOdds().execute(null,null,null);
    }

    @Override
    public void addCart(String string) {
        buttonOccuredEvend.setText(string);
        Card card = new Card(string);
        players[playerOccuredEvent].add(card);
        deck.remove(card);
        if (52 - deck.size() == 4) {
            startCalculate();
        }
        String player1Card = String.valueOf(players[0].size());
        String player1Card2 = String.valueOf(players[1].size());
        Log.e("Player1", player1Card);
        Log.e("Player1", player1Card2);

    }

    class CalculateOdds extends AsyncTask<Void, Void, Void> {
        Enumerator enumerator;
        double pots;

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            assignOdds(enumerator.getWins(), enumerator.getSplits(), pots);
        }

        @Override
        protected Void doInBackground(Void... params) {
            enumerator = new Enumerator(0, 1, deck, players, 0, new CardSet());
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
