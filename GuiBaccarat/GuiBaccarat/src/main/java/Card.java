
public class Card {
	
	//what part of the deck does this card belong to (heart, club, spade, diamond)
	String suit;
	//what value is the card labeled as (ace = 1, king/queen/jack = 0)
	int value;
	
	public Card(String theSuit, int theValue) {
		switch (theSuit) {
		case "1":
			this.suit = "Spade";
			break;
		case "2":
			this.suit = "Heart";
			break;
		case "3":
			this.suit = "Club";
			break;
		case "4":
			this.suit = "Diamond";
		}
		this.value = theValue;
	}

}
