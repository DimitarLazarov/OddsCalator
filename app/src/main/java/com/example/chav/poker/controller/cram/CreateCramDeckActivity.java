package com.example.chav.poker.controller.cram;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.chav.poker.R;
import com.example.chav.poker.adapters.CardAdapter;
import com.example.chav.poker.managers.CramCardsManager;
import com.example.chav.poker.managers.CramDecksManager;
import com.example.chav.poker.managers.UsersManager;

import java.util.ArrayList;

import model.CramCard;
import model.CramDeck;

public class CreateCramDeckActivity extends AppCompatActivity {

    public static final int CARD_REQUEST = 1;

    private ImageButton mButtonCancel;
    private ImageButton mButtonNext;
    private ImageButton mButtonCreateDeck;

    private Button mButtonAddCard;
    private RecyclerView mRecyclerCreatedCards;
    private EditText mTitleText;
    private ArrayList<CramCard> mCramCards;
    private CardAdapter mCardAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_cram_deck);

        setStatusBarTranslucent(true);

        mCramCards = new ArrayList<>();


        Typeface myTypeface = Typeface.createFromAsset(getAssets(), "HelveticaRoman.ttf");
        mButtonAddCard = (Button) findViewById(R.id.create_deck_add_card);
        mButtonAddCard.setTypeface(myTypeface);

        mButtonCancel = (ImageButton) findViewById(R.id.create_deck_cancel);
        mButtonNext = (ImageButton) findViewById(R.id.create_deck_next);
        mButtonCreateDeck = (ImageButton) findViewById(R.id.create_deck_create_cram_deck);




        mRecyclerCreatedCards = (RecyclerView) findViewById(R.id.create_deck_recycler_add_cram_cards);
        mTitleText = (EditText) findViewById(R.id.create_deck_title_deck);
        mTitleText.setTypeface(myTypeface);

        mButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mButtonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mTitleText.getText().toString().isEmpty()) {
                    mButtonNext.setVisibility(View.GONE);
                    mButtonCreateDeck.setVisibility(View.VISIBLE);
                    mButtonAddCard.setVisibility(View.VISIBLE);
                    mTitleText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    mTitleText.setBackground(null);
                    mTitleText.setTextSize(30);
                    mTitleText.setTextColor(ContextCompat.getColor(v.getContext(), R.color.cardColor));
                    mTitleText.setClickable(false);
                    mTitleText.setFocusable(false);
                    mTitleText.setKeyListener(null);
                    mTitleText.setGravity(Gravity.CENTER);
                    mRecyclerCreatedCards.setVisibility(View.VISIBLE);
                    mCardAdapter = new CardAdapter(mCramCards);
                    mRecyclerCreatedCards.setLayoutManager(new LinearLayoutManager(v.getContext()));
                    mRecyclerCreatedCards.setAdapter(mCardAdapter);
                    ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
                    itemTouchHelper.attachToRecyclerView(mRecyclerCreatedCards);
                }
                else {
                    Toast.makeText(CreateCramDeckActivity.this, "Please enter title of deck.", Toast.LENGTH_SHORT).show();
                }
            }
        });


        mButtonCreateDeck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCramCards.size() > 2) {
                    CramDeck deck = new CramDeck(mTitleText.getText().toString());
                    CramDecksManager.getInstance(v.getContext()).addDeck(UsersManager.getInstance(v.getContext()).getCurrentUser(), deck);

                    for (CramCard cramCard : mCramCards) {
                        CramCardsManager.getInstance(v.getContext()).addCard(deck.getId(), cramCard);
                    }
                    finish();
                }
                else {
                    Toast.makeText(CreateCramDeckActivity.this, "You need at least 3 cards to create deck.", Toast.LENGTH_SHORT).show();
                }
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

    ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        public boolean onMove(RecyclerView recyclerView,
                              RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return true;
        }

        @Override
        public void onSwiped(final RecyclerView.ViewHolder viewHolder, int swipeDir) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(CreateCramDeckActivity.this);
            alertDialogBuilder.setMessage("Are u sure delete this?");
            alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
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
    };

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
