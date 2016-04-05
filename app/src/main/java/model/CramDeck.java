package model;

import java.util.HashSet;

/**
 * Created by Mitakaa on 04-Apr-16.
 */
public class CramDeck {

    private long mId;
    private String mTitle;
    private HashSet<CramCard> mDeckCards;

    public CramDeck(String title) {
        mTitle = title;
        mDeckCards = new HashSet<CramCard>();
    }

    public CramDeck(long id, String title) {
        mId = id;
        mTitle = title;
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

    public String getTitle() {
        return mTitle;
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }
}
