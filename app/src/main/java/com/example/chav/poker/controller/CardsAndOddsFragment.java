package com.example.chav.poker.controller;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.chav.poker.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CardsAndOddsFragment extends Fragment {


    public CardsAndOddsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cards_and_odds, container, false);
    }

}
