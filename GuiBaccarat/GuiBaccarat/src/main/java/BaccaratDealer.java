import java.util.ArrayList;
import java.util.Random;

public class BaccaratDealer {
	
	public ArrayList<Card> deck;
	
	public BaccaratDealer() {
		generateDeck();
	}
	
	//generates a deck of 52 cards, generated as if it was an "unopened" deck
	public void generateDeck() {
		deck = new ArrayList<Card>();
		String[] suitNames = {"Spade","Heart","Club","Diamond"};
		for (int i = 0; i < 4; ++i) {//4 being the number of suits in a deck of cards
			for (int j = 0; j < 13; ++j) {//13 representing the possible values of cards, 11 = jack, 12 = queen, 13 = king
				Card newCard = new Card(suitNames[i],j);
				deck.add(newCard);
			}
		}
	}
	
	//deals the hand for the player, which is a hand of 2 cards
	public ArrayList<Card> dealHand() {
		ArrayList<Card> drawnHand = new ArrayList<Card>();
		
		for (int i = 0; i < 2; ++i) {
			Card genCard = drawOne();
			drawnHand.add(genCard);
		}
		return drawnHand;
	}
	
	//draws one card from the deck and removes it from the deck, as if it was actually shuffled and the randomly chosen card was drawn from the top
	public Card drawOne() {
		
		Random r = new Random();
		
		//get the suit of the card to be drawn
		int suitIndex = r.nextInt(4);
		//get the card's face value
		int valueIndex = r.nextInt(13);
		
		//grab and removed the randomly drawn card from the deck
		Card generatedCard = deck.get((suitIndex * 10) + valueIndex);
		deck.remove((suitIndex * 10) + valueIndex);
		
		return generatedCard;
	}
	
	//removes the old deck from circulation and replaces it with a new "unopened" deck
	public void shuffleDeck() {
		for (Card card : deck) {
			deck.remove(card);
		}
		generateDeck();
	}
	
	public int deckSize() {
		return deck.size();
	}
	
}
