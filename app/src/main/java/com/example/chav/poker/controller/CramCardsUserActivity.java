package com.example.chav.poker.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.chav.poker.R;
import com.example.chav.poker.adapters.DeckAdapter;
import com.example.chav.poker.managers.CramDecksManager;
import com.example.chav.poker.managers.UsersManager;

import java.util.ArrayList;
import java.util.List;

import model.CramCard;
import model.CramDeck;

public class CramCardsUserActivity extends AppCompatActivity {

    private Button mCreateNewDeck;
    private Button mMyDecks;
    private RecyclerView mViewOfDecks;
    private ArrayList<CramDeck> mCramDecks;
    private DeckAdapter mDeckAdapter;

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
                startActivity(startActivityNewDeck);
            }
        });

        mMyDecks = (Button)findViewById(R.id.user_cram_sets);
        mMyDecks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMyDecks.isActivated()) {
                    mMyDecks.setActivated(false);
                    mViewOfDecks.setVisibility(View.GONE);
                } else {
                    mMyDecks.setActivated(true);
                    mViewOfDecks.setVisibility(View.VISIBLE);
                }
            }
        });


        mCramDecks= new ArrayList<>();
        mDeckAdapter = new DeckAdapter(mCramDecks);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mViewOfDecks.setLayoutManager(layoutManager);
        mViewOfDecks.setAdapter(mDeckAdapter);
        mViewOfDecks.addItemDecoration(new DeckAdapter.VerticalSpaceItemDecoration(3));

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!mCramDecks.isEmpty()) {
            mCramDecks.clear();
        }
        mViewOfDecks.removeAllViews();
        mCramDecks.addAll(CramDecksManager.getInstance(this).getUsersCramDecks(UsersManager.getInstance(this).getCurrentUser().getId()));
        mDeckAdapter.notifyDataSetChanged();
    }
}
