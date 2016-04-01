package com.example.chav.poker;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import ver4.poker.Card;
import ver4.poker.CardSet;
import ver4.showdown.Enumerator;

public class MainActivity extends AppCompatActivity {

    private Button mPOneCardOne;
    private Button mPOneCardTwo;
    private Button mPTwoCardOne;
    private Button mPTwoCardTwo;
    private Button mBoardCardOne;
    private Button mBoardCardTwo;
    private Button mBoardCardThree;
    private Button mBoardCardFour;
    private Button mBoardCardFive;
    private Button mCalculate;
    private TextView pOne;
    private TextView pTwo;
    private CardSet deck;
    private CardSet[] players;
    private double mPlayerOneWinningChance;
    private double mPlayerTwoWinningChance;
    private Button button;

    private int fragmentCounter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPOneCardOne = (Button) findViewById(R.id.player_card_one);
        mPOneCardTwo = (Button) findViewById(R.id.player_card_two);
        mPTwoCardOne = (Button) findViewById(R.id.card_one_p_two);
        mPTwoCardTwo = (Button) findViewById(R.id.card_two_p_two);
        mBoardCardOne = (Button) findViewById(R.id.card_one_board);
        mBoardCardTwo = (Button) findViewById(R.id.card_two_board);
        mBoardCardThree = (Button) findViewById(R.id.card_three_board);
        mBoardCardFour = (Button) findViewById(R.id.card_four_board);
        mBoardCardFive = (Button) findViewById(R.id.card_five_board);
        pOne = (TextView) findViewById(R.id.odds_p_one);
        pTwo = (TextView) findViewById(R.id.odds_p_two);
        mCalculate = (Button) findViewById(R.id.calculate);
        mCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CalculateOdds().execute();
            }
        });


        deck = CardSet.freshDeck();
        players = new CardSet[3];
        players[0] = new CardSet();
        players[1] = new CardSet();
        players[2] = new CardSet();
        players[0].add(new Card("Ad"));
        players[0].add(new Card("Ac"));
        players[1].add(new Card("Jd"));
        players[1].add(new Card("Jc"));
        players[2].add(new Card("Td"));
        players[2].add(new Card("Tc"));
        deck.remove(new Card("Ad"));
        deck.remove(new Card("Ac"));
        deck.remove(new Card("Jd"));
        deck.remove(new Card("Jc"));
        deck.remove(new Card("Td"));
        deck.remove(new Card("Tc"));

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

        pOne.setText(String.format("%.2f%%", mPlayerOneWinningChance));
        pTwo.setText(String.format("%.2f%%", mPlayerTwoWinningChance));
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
