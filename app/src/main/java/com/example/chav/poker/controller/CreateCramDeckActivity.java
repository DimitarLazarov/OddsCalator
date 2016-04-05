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
import android.widget.Toast;

import com.example.chav.poker.R;
import com.example.chav.poker.adapters.CardAdapter;
import com.example.chav.poker.managers.CramCardsManager;
import com.example.chav.poker.managers.CramDecksManager;
import com.example.chav.poker.managers.UsersManager;

import java.util.ArrayList;
import java.util.List;

import model.CramCard;
import model.CramDeck;

public class CreateCramDeckActivity extends AppCompatActivity {

    public static final int CARD_REQUEST = 1;

    private Button mButtonCancel;
    private Button mButtonNext;
    private Button mButtonCreateDeck;
    private Button mButtonAddCard;
    private RecyclerView mRecyclerCreatedCards;
    private EditText mTitleText;
    private ArrayList<CramCard> mCramCards;
    private CardAdapter mCardAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_cram_deck);
        mCramCards = new ArrayList<>();

        mButtonCancel = (Button) findViewById(R.id.create_deck_cancel);
        mButtonNext = (Button) findViewById(R.id.create_deck_next);
        mButtonAddCard = (Button) findViewById(R.id.create_deck_add_card);
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
                    mRecyclerCreatedCards.setVisibility(View.VISIBLE);
                    mCardAdapter = new CardAdapter(mCramCards);
                    mRecyclerCreatedCards.setLayoutManager(new LinearLayoutManager(v.getContext()));
                    mRecyclerCreatedCards.setAdapter(mCardAdapter);
                }
            }
        });

        mButtonCreateDeck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CramDeck deck = new CramDeck(mTitleText.getText().toString());
                CramDecksManager.getInstance(v.getContext()).addDeck(UsersManager.getInstance(v.getContext()).getCurrentUser(), deck);
//                Toast.makeText(v.getContext(), deck.getTitle(), Toast.LENGTH_SHORT).show();
                for (CramCard cramCard : mCramCards) {
                    CramCardsManager.getInstance(v.getContext()).addCard(deck, cramCard);
//                    Toast.makeText(v.getContext(), cramCard.getQuestion(), Toast.LENGTH_SHORT).show();
                    //TODO set name of Cram Deck unique and catch SQLiteException
                }
                finish();
            }
        });

        mButtonAddCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), CramCardCreateActivity.class);
                startActivityForResult(i, CARD_REQUEST);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CARD_REQUEST:
                if (resultCode == RESULT_OK) {
                    String front = data.getExtras().getString("front");
                    String back = data.getExtras().getString("back");
                    mCramCards.add(new CramCard(front, back));
                    mCardAdapter.notifyDataSetChanged();
                    Toast.makeText(this, front + back, Toast.LENGTH_SHORT).show();
                }
        }
    }


}
