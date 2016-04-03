package com.example.chav.poker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;

import ver4.poker.Card;
import ver4.poker.CardSet;

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
//    private ArrayList<Card> mDealtCards;
    private CardSet deck;
    private CardSet[] players;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_quiz);

        deck = CardSet.shuffledDeck();
        players = new CardSet[2];
        players[0] = new CardSet();
        players[1] = new CardSet();
        Card card = deck.iterator().next();
        Toast.makeText(this, card.rankOf().toChar() + "      " + card.suitOf().toChar(), Toast.LENGTH_SHORT);
        mPlayerOneCardOne = (Button) findViewById(R.id.basic_quiz_player_one_card_one);
        mPlayerOneCardTwo = (Button) findViewById(R.id.basic_quiz_player_one_card_two);
        mPlayerTwoCardOne = (Button) findViewById(R.id.basic_quiz_player_two_card_one);
        mPlayerTwoCardTwo = (Button) findViewById(R.id.basic_quiz_player_two_card_two);
        mBoardCardOne = (Button) findViewById(R.id.basic_quiz_card_one_board);
        mBoardCardTwo = (Button) findViewById(R.id.basic_quiz_card_two_board);
        mBoardCardThree = (Button) findViewById(R.id.basic_quiz_card_three_board);
        mBoardCardFour = (Button) findViewById(R.id.basic_quiz_card_four_board);
        mBoardCardFive = (Button) findViewById(R.id.basic_quiz_card_five_board);

        mPlayerOneCards = (RelativeLayout) findViewById(R.id.basic_quiz_player_one_card_holder);
        mPlayerTwoCards = (RelativeLayout) findViewById(R.id.basic_quiz_player_two_card_holder);
    }
}
