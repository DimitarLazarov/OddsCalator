package com.example.chav.poker.controller.cram;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.example.chav.poker.R;
import com.example.chav.poker.adapters.DeckAdapter;
import com.example.chav.poker.managers.CramDecksManager;
import com.example.chav.poker.managers.UsersManager;

import java.util.ArrayList;

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

        setStatusBarTranslucent(true);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            Window w = getWindow();
//            w.setStatusBarColor(ContextCompat.getColor(this, R.color.darkGreen));
//        }

        Typeface myTypeface = Typeface.createFromAsset(getAssets(), "HelveticaRoman.ttf");

        mViewOfDecks = (RecyclerView) findViewById(R.id.my_cram_cards_recycler);
        mCreateNewDeck = (Button)findViewById(R.id.user_cram_add_deck);
        mCreateNewDeck.setTypeface(myTypeface);
        mCreateNewDeck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startActivityNewDeck = new Intent(v.getContext(), CreateCramDeckActivity.class);
                startActivity(startActivityNewDeck);
            }
        });

        mMyDecks = (Button)findViewById(R.id.user_cram_sets);
        mMyDecks.setTypeface(myTypeface);
        mMyDecks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCramDecks.size() != 0) {
                    if (mMyDecks.isActivated()) {
                        mMyDecks.setActivated(false);
                        mViewOfDecks.setVisibility(View.GONE);
                    } else {
                        mMyDecks.setActivated(true);
                        mViewOfDecks.setVisibility(View.VISIBLE);
                    }
                } else {
                    Toast.makeText(CramCardsUserActivity.this, "You have no decks.", Toast.LENGTH_SHORT).show();
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
        mMyDecks.setActivated(false);
        mViewOfDecks.setVisibility(View.GONE);
        mViewOfDecks.removeAllViews();
        mCramDecks.addAll(CramDecksManager.getInstance(this).getUsersCramDecks(UsersManager.getInstance(this).getCurrentUser().getId()));
        mDeckAdapter.notifyDataSetChanged();
    }

    protected void setStatusBarTranslucent(boolean makeTranslucent) {
        if (makeTranslucent) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }
}
