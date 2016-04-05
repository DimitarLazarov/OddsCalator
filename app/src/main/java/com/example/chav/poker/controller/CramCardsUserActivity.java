package com.example.chav.poker.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.example.chav.poker.R;
import com.example.chav.poker.adapters.DeckAdapter;

import java.util.ArrayList;
import java.util.List;

import model.CramDeck;

public class CramCardsUserActivity extends AppCompatActivity {

    private Button mCreateNewDeck;
    private Button mMyDecks;
    private RecyclerView mViewOfDecks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cram_cards_user);

        mViewOfDecks = (RecyclerView) findViewById(R.id.my_cram_cards_recycler);
        mCreateNewDeck = (Button)findViewById(R.id.user_cram_add_deck);
        mCreateNewDeck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startActivityNewDeck = new Intent(v.getContext(), CreateCramDeckActivity.class);
                v.getContext().startActivity(startActivityNewDeck);
            }
        });
        mMyDecks = (Button)findViewById(R.id.user_cram_sets);
        mMyDecks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMyDecks.isActivated()) {
                    mMyDecks.setActivated(false);
                    mViewOfDecks.setVisibility(View.GONE);
                }
                else {
                    mMyDecks.setActivated(true);
                    mViewOfDecks.setVisibility(View.VISIBLE);
                }
            }
        });

        CramDeck deck1 = new CramDeck("Title1");
        CramDeck deck2 = new CramDeck("Title2");
        CramDeck deck3 = new CramDeck("Title3");
        CramDeck deck4 = new CramDeck("Title4");
        List<CramDeck> deckCards = new ArrayList<>();
        deckCards.add(deck1);
        deckCards.add(deck2);
        deckCards.add(deck3);
        deckCards.add(deck4);


        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mViewOfDecks.setLayoutManager(layoutManager);
        DeckAdapter adapter = new DeckAdapter(deckCards);
        mViewOfDecks.addItemDecoration(new DeckAdapter.VerticalSpaceItemDecoration(3));
        mViewOfDecks.setAdapter(adapter);


    }
}
