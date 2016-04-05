package com.example.chav.poker.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import com.example.chav.poker.R;
import com.example.chav.poker.adapters.CardAdapter;

import java.util.ArrayList;
import java.util.List;

import model.CramCard;
import model.CramDeck;

public class CreateCramDeckActivity extends AppCompatActivity {

    Button mButtonCancel;
    Button mButtonNext;
    Button mButtonCreateDeck;
    RecyclerView mRecyclerCreatedCards;
    EditText mTitleText;
    private ArrayList<CramCard> mCramCards;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_cram_deck);
        mCramCards = new ArrayList<>();

        mButtonCancel = (Button) findViewById(R.id.create_deck_cancel);
        mButtonNext = (Button) findViewById(R.id.create_deck_next);
        mButtonCreateDeck = (Button) findViewById(R.id.create_deck_create_cram_deck);
        mRecyclerCreatedCards = (RecyclerView) findViewById(R.id.create_deck_recycler_add_cram_cards);
        mTitleText = (EditText) findViewById(R.id.create_deck_title_deck);

        mButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cancelIntent = new Intent(v.getContext(), CramCardsUserActivity.class);
                v.getContext().startActivity(cancelIntent);
            }
        });

        mButtonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTitleText.getText().toString() != "") {
                    mButtonNext.setVisibility(View.GONE);
                    mButtonCreateDeck.setVisibility(View.VISIBLE);
                    mTitleText.setClickable(false);
                    mTitleText.setFocusable(false);
                    CramDeck deck = new CramDeck(mTitleText.getText().toString());
                    mRecyclerCreatedCards.setVisibility(View.VISIBLE);
                    CardAdapter adapter = new CardAdapter(mCramCards);
                    mRecyclerCreatedCards.setLayoutManager(new LinearLayoutManager(v.getContext()));
                    mRecyclerCreatedCards.setAdapter(adapter);
                }
            }
        });
    }
}
