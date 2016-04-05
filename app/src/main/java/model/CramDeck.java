package model;

import java.util.HashSet;

/**
 * Created by Mitakaa on 04-Apr-16.
 */
public class CramDeck {

    private String mTitle;
    private HashSet<CramCard> mDeckCards;

    public CramDeck(){
        mDeckCards = new HashSet<CramCard>();
    }

    public HashSet<CramCard> getCards(){
        return mDeckCards;
    }


    public void addCramCard(CramCard card) {
        mDeckCards.add(card);
    }

    public void removeCramCard(CramCard card) {
        mDeckCards.remove(card);
    }
}
