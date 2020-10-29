
public class Card {
	
	//what part of the deck does this card belong to (heart, club, spade, diamond)
	String suite;
	//what value is the card labeled as (ace = 1, king/queen/jack = 0)
	int value;
	
	public Card(String theSuite, int theValue) {
		this.suite = theSuite;
		this.value = theValue;
	}

}
