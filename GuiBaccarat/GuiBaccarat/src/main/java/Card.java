
public class Card {
	
	//what part of the deck does this card belong to (heart, club, spade, diamond)
	String suit;
	//what value is the card labeled as (ace = 1, king/queen/jack = 0)
	int value;
	
	public Card(String theSuit, int theValue) {
		this.suit = theSuit;
		this.value = theValue + 1;
	}

}
