package com.example.chav.poker.adapters;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.chav.poker.R;

import java.util.List;

import model.CramCard;
import model.CramDeck;

/**
 * Created by Mitakaa on 05-Apr-16.
 */
public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder>{

    private List<CramCard> cards;

    public CardAdapter(List<CramCard> cards) {
        this.cards = cards;
    }


    public static class CardViewHolder extends RecyclerView.ViewHolder {
        TextView mQuestionCard;
        View mSeparator;
        TextView mAnswerCard;

        public CardViewHolder(View itemView) {
            super(itemView);
            mQuestionCard = (TextView) itemView.findViewById(R.id.view_holder_card_question_view);
            mAnswerCard = (TextView) itemView.findViewById(R.id.view_holder_card_answer_view);
            mSeparator = (View) itemView.findViewById(R.id.view_separator);
        }
    }

    public static class VerticalSpaceItemDecoration extends RecyclerView.ItemDecoration {
        private final int mVerticalSpaceHeight;

        public VerticalSpaceItemDecoration(int mVerticalSpaceHeight) {
            this.mVerticalSpaceHeight = mVerticalSpaceHeight;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                                   RecyclerView.State state) {
            outRect.bottom = mVerticalSpaceHeight;
        }
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_holder_card, parent, false);
        CardViewHolder cvh = new CardViewHolder(v);
        return cvh;
    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {
        holder.mQuestionCard.setText(cards.get(position).getQuestion());
        holder.mAnswerCard.setText(cards.get(position).getAnswer());

    }

    @Override
    public int getItemCount() {
        return cards.size();
    }



}
