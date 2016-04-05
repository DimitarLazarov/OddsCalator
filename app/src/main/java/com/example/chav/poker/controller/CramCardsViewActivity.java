package com.example.chav.poker.controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.chav.poker.R;
import com.example.chav.poker.adapters.CardAdapter;

import java.util.ArrayList;
import java.util.List;

import model.CramCard;

public class CramCardsViewActivity extends AppCompatActivity {

    private TextView mTitle;
    private RecyclerView mRecyclerViewCards;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cram_cards_view);

        mTitle = (TextView) findViewById(R.id.cram_deck_title);
        mRecyclerViewCards = (RecyclerView) findViewById(R.id.my_cram_cards_recycler);

        CramCard card1 = new CramCard("hahah", "da da");
        CramCard card2 = new CramCard("hahah", "da da");
        CramCard card3 = new CramCard("hahah", "da da");
        CramCard card4 = new CramCard("hahah", "da da");
        List<CramCard> cards = new ArrayList<>();
        cards.add(card1);
        cards.add(card2);
        cards.add(card3);
        cards.add(card4);
        CardAdapter adapter = new CardAdapter(cards);
        mRecyclerViewCards.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerViewCards.setAdapter(adapter);


    }
}
