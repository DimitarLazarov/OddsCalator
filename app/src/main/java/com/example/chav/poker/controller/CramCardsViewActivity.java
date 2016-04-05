package com.example.chav.poker.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.chav.poker.R;
import com.example.chav.poker.adapters.CardAdapter;
import com.example.chav.poker.managers.CramCardsManager;

import java.util.ArrayList;
import java.util.List;

import model.CramCard;

public class CramCardsViewActivity extends AppCompatActivity {

    private TextView mTitle;
    private RecyclerView mRecyclerViewCards;
    private Button mCramMode;
    private CardAdapter mCardAdapter;
    private ArrayList<CramCard> mCramCards;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cram_cards_view);

        mTitle = (TextView) findViewById(R.id.cram_deck_title);
        mRecyclerViewCards = (RecyclerView) findViewById(R.id.my_cram_cards_recycler);
        mCramMode = (Button) findViewById(R.id.cram_cards_view_mode_button);
        mCramMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), CramModeActivity.class);
                startActivity(intent);
            }
        });

//        CramCard card1 = new CramCard("hahah", "da da");
//        CramCard card2 = new CramCard("hahah", "da da");
//        CramCard card3 = new CramCard("hahah", "da da");
//        CramCard card4 = new CramCard("hahah", "da da");
        mCramCards = new ArrayList<>();
//        mCramCards.add(card1);
//        mCramCards.add(card2);
//        mCramCards.add(card3);
//        mCramCards.add(card4);

        mCardAdapter = new CardAdapter(mCramCards);
        mRecyclerViewCards.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerViewCards.setAdapter(mCardAdapter);


    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!mCramCards.isEmpty()) {
            mCramCards.clear();
        }
        long deckId = getIntent().getExtras().getLong("deck_id");
        mCramCards.addAll(CramCardsManager.getInstance(this).getDeckCards(deckId));
        mCardAdapter.notifyDataSetChanged();
    }
}
