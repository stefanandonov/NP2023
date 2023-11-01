package mk.ukim.finki.exercise2;

import java.util.*;

public class Deck {

    private static final int NUM_CARDS = 52;
    private static final int NUM_CARDS_OF_TYPE = 13;

    private PlayingCard[] cards;
    private boolean[] isDealt;
    private int numLeft;

    public Deck() {
        cards = new PlayingCard[NUM_CARDS];
        isDealt = new boolean[NUM_CARDS];
        numLeft = NUM_CARDS;

        for (int i = 0; i < PlayingCardType.values().length; i++) {
            for (int j = 0; j < NUM_CARDS_OF_TYPE; j++) {
                cards[i * NUM_CARDS_OF_TYPE + j] =
                        new PlayingCard(j + 1, PlayingCardType.values()[i]);
            }
        }
    }

    public void shuffle() {
        List<PlayingCard> playingCardList = Arrays.asList(cards);
        Collections.shuffle(playingCardList);
    }

    public PlayingCard dealCard() {
        int card = (int) (Math.random() * NUM_CARDS);

        if (!isDealt[card]) {
            isDealt[card] = true;
            numLeft--;
            return cards[card];
        }
        return dealCard();
    }

    public boolean hasCardsLeft() {
        return numLeft > 0;
    }

    @Override
    public String toString() {
        if (!hasCardsLeft()) return "The deck is empty.";

        StringBuilder stringBuilder = new StringBuilder();
        for (PlayingCard card : cards) {
            stringBuilder.append(card.toString()).append("\n");
        }
        return stringBuilder.toString();
    }

    public static void main(String[] args) {
        Deck firstDeck = new Deck();
        System.out.println("------ TEST PRINT DECK FULL ------");
        System.out.println(firstDeck);

        System.out.println("------ TEST SHUFFLE DECK ------");
        firstDeck.shuffle();
        System.out.println(firstDeck);

        System.out.println("------ TEST HAS CARDS LEFT IN DECK ------");
        System.out.println(firstDeck.hasCardsLeft());

        System.out.println("------ TEST DEAL CARDS IN DECK ------");
        while (firstDeck.hasCardsLeft()) {
            System.out.println(firstDeck.dealCard());
        }

        System.out.println("------ TEST HAS CARDS LEFT IN DECK ------");
        System.out.println(firstDeck.hasCardsLeft());

        System.out.println("------ TEST PRINT DECK EMPTY ------");
        System.out.println(firstDeck);
    }
}