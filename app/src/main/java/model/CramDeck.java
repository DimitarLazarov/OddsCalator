package model;

import java.util.HashSet;

/**
 * Created by Mitakaa on 04-Apr-16.
 */
public class CramDeck {
    private HashSet<CramCard> deck;

    public CramDeck(){
        deck = new HashSet<CramCard>();
    }

    public HashSet<CramCard> getCards(){
        return deck;
    }


    public void addCramCard(CramCard card) {
        deck.add(card);
    }

    public void removeCramCard(CramCard card) {
        deck.remove(card);
    }
}
