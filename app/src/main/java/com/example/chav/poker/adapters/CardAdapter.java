package com.example.chav.poker.adapters;

import android.graphics.Rect;
import android.graphics.Typeface;
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
        private boolean mShowOrHide = false;
        private TextView mQuestionCard;
        private View mSeparator;
        private TextView mAnswerCard;
        private TextView mAnswerHelper;
        private TextView mQuestionHelper;

        public CardViewHolder(View itemView) {
            super(itemView);
            Typeface myTypeface = Typeface.createFromAsset(itemView.getContext().getAssets(), "HelveticaRoman.ttf");
            mQuestionCard = (TextView) itemView.findViewById(R.id.view_holder_card_question_view);
            mQuestionCard.setTypeface(myTypeface);
            mAnswerHelper = (TextView) itemView.findViewById(R.id.view_holder_cram_answer_helper);
            mAnswerHelper.setVisibility(View.GONE);
            mQuestionHelper = (TextView) itemView.findViewById(R.id.view_holder_cram_question_helper);
            mQuestionHelper.setVisibility(View.GONE);
            mAnswerCard = (TextView) itemView.findViewById(R.id.view_holder_card_answer_view);
            mAnswerCard.setTypeface(myTypeface);
            mAnswerCard.setVisibility(View.GONE);
            mSeparator = (View) itemView.findViewById(R.id.view_separator);
            mSeparator.setVisibility(View.GONE);
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
    public void onBindViewHolder(final CardViewHolder holder, int position) {
        holder.mQuestionCard.setText(cards.get(position).getQuestion());
        holder.mAnswerCard.setText(cards.get(position).getAnswer());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.mAnswerCard.getVisibility() == View.GONE) {
                    holder.mAnswerHelper.setVisibility(View.VISIBLE);
                    holder.mQuestionHelper.setVisibility(View.VISIBLE);
                    holder.mAnswerCard.setVisibility(View.VISIBLE);
                    holder.mSeparator.setVisibility(View.VISIBLE);
                }
                else {
                    holder.mAnswerHelper.setVisibility(View.GONE);
                    holder.mQuestionHelper.setVisibility(View.GONE);
                    holder.mAnswerCard.setVisibility(View.GONE);
                    holder.mSeparator.setVisibility(View.GONE);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return cards.size();
    }



}
