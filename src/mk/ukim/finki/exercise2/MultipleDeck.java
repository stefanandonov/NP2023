package mk.ukim.finki.exercise2;

public class MultipleDeck {

    private Deck[] decks;

    public MultipleDeck(int numDecks) {
        decks = new Deck[numDecks];

        for (int i = 0; i < numDecks; i++)
            decks[i] = new Deck();
    }

    @Override
    public String toString() {
        if (decks.length == 0) return "There are no decks.";

        StringBuilder stringBuilder = new StringBuilder();
        for (Deck deck : decks) {
            stringBuilder.append(deck.toString()).append("\n");
        }
        return stringBuilder.toString();
    }

    public static void main(String[] args) {
        MultipleDeck multipleDeck1 = new MultipleDeck(3);
        System.out.println("------ TEST PRINT MUL-DECK FULL ------");
        System.out.println(multipleDeck1);

        MultipleDeck multipleDeck2 = new MultipleDeck(0);
        System.out.println("------ TEST PRINT MUL-DECK EMPTY ------");
        System.out.println(multipleDeck2);
    }
}
