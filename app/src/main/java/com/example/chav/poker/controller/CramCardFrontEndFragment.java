package com.example.chav.poker.controller;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.chav.poker.R;


public class CramCardFrontEndFragment extends Fragment {

    private TextView mText;

    public CramCardFrontEndFragment() {
        // Required empty public constructor

    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_cram_card_front_end, container, false);

        Bundle mTextBundle = this.getArguments();
        mText = (TextView) v.findViewById(R.id.cram_card_front_end_text);
        mText.setText(mTextBundle.getString("textOfCard"));

        return v;
    }
}
