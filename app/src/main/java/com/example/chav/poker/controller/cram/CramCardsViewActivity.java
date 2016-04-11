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
import android.widget.TextView;

import com.example.chav.poker.R;
import com.example.chav.poker.adapters.CardAdapter;
import com.example.chav.poker.managers.CramCardsManager;
import com.example.chav.poker.managers.CramDecksManager;

import java.util.ArrayList;

import model.CramCard;

public class CramCardsViewActivity extends AppCompatActivity {

    private static final int CARD_REQUEST = 1;
    private TextView mTitle;
    private RecyclerView mRecyclerViewCards;
    private Button mCramMode;
    private Button mAddCard;
    private CardAdapter mCardAdapter;
    private ArrayList<CramCard> mCramCards;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cram_cards_view);

        setStatusBarTranslucent(true);

        Typeface myTypeface = Typeface.createFromAsset(getAssets(), "HelveticaRoman.ttf");

        mTitle = (TextView) findViewById(R.id.cram_deck_title);
        mTitle.setTypeface(myTypeface);
        String name = getIntent().getExtras().getString("title");
        mTitle.setText(name);

        mRecyclerViewCards = (RecyclerView) findViewById(R.id.my_cram_cards_recycler);
        mCramMode = (Button) findViewById(R.id.cram_cards_view_mode_button);
        mCramMode.setTypeface(myTypeface);
        mCramMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), CramModeActivity.class);
                long deckId = getIntent().getExtras().getLong("deck_id");
                intent.putExtra("deck_id", deckId);
                startActivity(intent);
            }
        });
        mCramCards = new ArrayList<>();

        mCardAdapter = new CardAdapter(mCramCards);
        mRecyclerViewCards.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerViewCards.setAdapter(mCardAdapter);

        mAddCard = (Button) findViewById(R.id.add_cram_card_button);
        mAddCard.setTypeface(myTypeface);
        mAddCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), CramCardCreateActivity.class);
                startActivityForResult(i, CARD_REQUEST);
            }
        });
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerViewCards);

    }

    ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        public boolean onMove(RecyclerView recyclerView,
                              RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return true;
        }

        @Override
        public void onSwiped(final RecyclerView.ViewHolder viewHolder, int swipeDir) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(CramCardsViewActivity.this);
            if (mCramCards.size() == 1) {
                alertDialogBuilder.setMessage("Are you sure you want to delete " + mTitle.getText().toString() + " Deck?");
                alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final long deckId = getIntent().getExtras().getLong("deck_id");
                        CramCardsManager.getInstance(getBaseContext()).removeCard(mCramCards.get(viewHolder.getAdapterPosition()).getId());
                        CramDecksManager.getInstance(CramCardsViewActivity.this).removeDeck(deckId);
                        finish();
                    }
                });
                alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mCardAdapter.notifyDataSetChanged();
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            } else {
                alertDialogBuilder.setMessage("Are you sure you want to delete " + mCramCards.get(viewHolder.getAdapterPosition()).getQuestion() + " Card?");
                alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        CramCardsManager.getInstance(getBaseContext()).removeCard(mCramCards.get(viewHolder.getAdapterPosition()).getId());
                        mCramCards.remove(viewHolder.getAdapterPosition());
                        mCardAdapter.notifyDataSetChanged();
                    }
                });
                alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mCardAdapter.notifyDataSetChanged();
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }

        }
    };

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case CARD_REQUEST:
                if (resultCode == RESULT_OK) {
                    String front = data.getExtras().getString("front");
                    String back = data.getExtras().getString("back");
                    long deckId = getIntent().getExtras().getLong("deck_id");
                    CramCard cramCard = new CramCard(front, back);
                    mCramCards.add(cramCard);
                    CramCardsManager.getInstance(this).addCard(deckId, cramCard);
                    mCardAdapter.notifyDataSetChanged();
                }
        }
    }

    protected void setStatusBarTranslucent(boolean makeTranslucent) {
        if (makeTranslucent) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }
}
