package com.example.chav.poker.controller.cram;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.example.chav.poker.R;
import com.example.chav.poker.adapters.DeckAdapter;
import com.example.chav.poker.managers.CramCardsManager;
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

        Typeface myTypeface = Typeface.createFromAsset(getAssets(), "HelveticaRoman.ttf");

        mViewOfDecks = (RecyclerView) findViewById(R.id.my_cram_cards_recycler);
        mCreateNewDeck = (Button)findViewById(R.id.user_cram_add_deck);
        mCreateNewDeck.setTypeface(myTypeface);
        mCreateNewDeck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startActivityNewDeck = new Intent(v.getContext(), CreateCramDeckActivity.class);
                startActivityNewDeck.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
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
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(mViewOfDecks);

    }

    ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        public boolean onMove(RecyclerView recyclerView,
                              RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return true;
        }

        @Override
        public void onSwiped(final RecyclerView.ViewHolder viewHolder, int swipeDir) {
            //Remove swiped item from list and notify the RecyclerView
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(CramCardsUserActivity.this);
            alertDialogBuilder.setMessage("Are you sure you want to delete " + mCramDecks.get(viewHolder.getAdapterPosition()).getTitle() + " deck?");
            alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    CramCardsManager.getInstance(getBaseContext()).removeCard(mCramDecks.get(viewHolder.getAdapterPosition()).getId());
                    //TODO delete logic here
                    mDeckAdapter.notifyDataSetChanged();
                }
            });
            alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mDeckAdapter.notifyDataSetChanged();
                }
            });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();

        }
    };

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
