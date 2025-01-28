import java.util.*;

class Card {
    private String suit;
    private String rank;
    private int value;

    public Card(String suit, String rank, int value) {
        this.suit = suit;
        this.rank = rank;
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return rank + " of " + suit;
    }
}

class Deck {
    private List<Card> cards;

    public Deck() {
        String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};
        String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};
        int[] values = {2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14};
        
        cards = new ArrayList<>();
        for (String suit : suits) {
            for (int i = 0; i < ranks.length; i++) {
                cards.add(new Card(suit, ranks[i], values[i]));
            }
        }
        shuffle();
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public Card drawCard() {
        return cards.isEmpty() ? null : cards.remove(0);
    }

    public boolean isEmpty() {
        return cards.isEmpty();
    }
}

public class WarGame {

    // ANSI escape codes for colors
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String CYAN = "\u001B[36m";
    public static final String BOLD = "\u001B[1m";

    public static void main(String[] args) {
        Deck deck = new Deck();
        List<Card> player1Deck = new ArrayList<>();
        List<Card> player2Deck = new ArrayList<>();

        // Distribute cards evenly between players
        while (!deck.isEmpty()) {
            player1Deck.add(deck.drawCard());
            if (!deck.isEmpty()) {
                player2Deck.add(deck.drawCard());
            }
        }

        playGame(player1Deck, player2Deck);
    }

    public static void playGame(List<Card> player1Deck, List<Card> player2Deck) {
        Scanner scanner = new Scanner(System.in);

        // Score counters
        int player1Score = 0;
        int player2Score = 0;

        System.out.println(CYAN + BOLD + "Welcome to War Game!" + RESET);
        System.out.println(CYAN + BOLD + "Each player will draw a card, and the higher card wins the round!" + RESET);
        System.out.println();

        while (!player1Deck.isEmpty() && !player2Deck.isEmpty()) {
            // Check if any player has won
            if (player1Score == 10) {
                System.out.println(GREEN + BOLD + "Player 1 wins with a score of 10!" + RESET);
                break;
            } else if (player2Score == 10) {
                System.out.println(GREEN + BOLD + "Player 2 wins with a score of 10!" + RESET);
                break;
            }

            System.out.println(CYAN + BOLD + "Press Enter to draw cards..." + RESET);
            scanner.nextLine();
            System.out.println();

            Card card1 = player1Deck.remove(0);
            Card card2 = player2Deck.remove(0);

            System.out.println(BLUE + BOLD + "Player 1 draws: " + RESET + card1);
            System.out.println(BLUE + BOLD + "Player 2 draws: " + RESET + card2);
            System.out.println();

            if (card1.getValue() > card2.getValue()) {
                System.out.println(GREEN + BOLD + "Player 1 wins this round!" + RESET);
                player1Deck.add(card1);
                player1Deck.add(card2);
                player1Score++; // Increment Player 1's score
            } else if (card2.getValue() > card1.getValue()) {
                System.out.println(RED + BOLD + "Player 2 wins this round!" + RESET);
                player2Deck.add(card1);
                player2Deck.add(card2);
                player2Score++; // Increment Player 2's score
            } else {
                System.out.println(YELLOW + BOLD + "It's a tie! Cards are discarded." + RESET);
            }

            System.out.println();
            System.out.println(RED + BOLD + "ROUND STATS:" + RESET);
            //System.out.println(BLUE + "Player 1 has " + player1Deck.size() + " cards left." + RESET);
            //System.out.println(BLUE + "Player 2 has " + player2Deck.size() + " cards left." + RESET);
            System.out.println(GREEN + "Player 1 score: " + player1Score + RESET);
            System.out.println(RED + "Player 2 score: " + player2Score + RESET);
            System.out.println();

            // Delay for the next round to give a more dramatic feel
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        scanner.close();
        System.out.println(CYAN + BOLD + "Game Over!" + RESET);
    }
}
