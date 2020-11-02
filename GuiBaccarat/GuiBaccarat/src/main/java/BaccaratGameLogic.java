import java.util.ArrayList;

public class BaccaratGameLogic {
	
	//checks to see if the hands has a value of 8 or 9. 
	//If none do, we choose the hand that is the closest to 8 or 9
	public String whoWon(ArrayList<Card> hand1,ArrayList<Card> hand2) {
		
		int hand1Val = handTotal(hand1);
		int hand2Val = handTotal(hand2);
		
		boolean hand1WinCond = false;
		boolean hand2WinCond = false;
		
		//check which hands have reached the natural state, if at all
		if (hand1Val == 8 || hand1Val == 9) {
			hand1WinCond = true;
		}
		
		if (hand2Val == 8 || hand2Val == 9) {
			hand2WinCond = true;
		}
		
		//both hands are naturals
		if (hand1WinCond == true && hand2WinCond == true) {
			return "Draw";
		} else if (hand1WinCond == true && hand2WinCond == false) {//player's hand is natural
			return "Self";
		} else if (hand1WinCond == false && hand2WinCond == true) {//dealer's hand is natural
			return "Banker";
		}
		
		//now we check if the player/dealer can draw another card, since that would mean that we arent done
		if (evaluatePlayerDraw(hand1)) {
			return "No Win Yet";
		}
		
		if (evaluateBankerDraw(hand2,hand1.get(hand1.size()-1))) {
			return "No Win Yet";
		}
		
		//after exhausting our options otherwise, we check for who is the closest to 9
		
		if (hand1Val == hand2Val) {//the hands are equally as far from 9
			return "Draw";
		} else if (hand1Val > hand2Val) {//the player's hand is closer than the dealers
			return "Self";
		} else {//the dealer's hand is closer than the players
			return "Banker";
		}
	}
	
	//counts up the total value of the hand, independent of who's hand it belongs to
	public int handTotal(ArrayList<Card> hand) {
		int totalHandVal = 0;
		
		//go through the whole hand and count the values of the cards
		for (Card card : hand) {
			switch (card.value) {//card nums 1-9 are counted as normal (Ace == 1)
			case 1:
			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
			case 7:
			case 8:
			case 9:
				totalHandVal += card.value;
				break;
			case 10://all cards nums/types after 9 are not to be counted towards the total, so we add 0 to it
			case 11:
			case 12: 
			case 13:
				totalHandVal += 0;
				break;
			
			}
			//whenever the hand gets larger than 9, we bring it back down to the acceptable value
			//i.e. a hand value of 15 gets brought down to 5
			if (totalHandVal > 9) {
				totalHandVal -= 10;
			}
		}
		return totalHandVal;
	}
	
	//determines if the banker is able to draw, and checks if the special cases allow them to draw
	public boolean evaluateBankerDraw(ArrayList<Card> hand, Card playerCard) {
		
		int totalHand = handTotal(hand);
		
		//when the hand total is at/larger than 7, the dealer cannot draw
		if (totalHand >= 7) {
			return false;
		} 
		
		//we get the first 2 cards in the dealer's deck and evaluate what value they are
		//Reminder, 10/Jack/Queen/King == 0
		Card card1 = hand.get(0);
		Card card2 = hand.get(1);
		
		int firstCardInHand, secondCardInHand;
		
		if (card1.value >= 10) {
			firstCardInHand = 0;
		} else {
			firstCardInHand = card1.value;
		}
		
		if (card2.value >= 10) {
			secondCardInHand = 0;
		} else {
			secondCardInHand = card2.value;
		}
		
		int totalOf2Cards = firstCardInHand + secondCardInHand;
		
		//the only way that the dealer can draw another card is as if the first two cards in the hand add up to zero
		//then we must see the last card the player drew and the value of the dealer's
		//hand to determine if the dealer can draw a card, where Y means that the dealer can draw
		//Table of when the dealer can draw:
		//      Player's Last Drawn Card
		//Bank | N | 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9
		//   9 |___|___|___|___|___|___|___|___|___|___|___
	    //   8 |___|___|___|___|___|___|___|___|___|___|___
		//   7 |___|___|___|___|___|___|___|___|___|___|___
		//   6 |___|___|___|___|___|___|___|_Y_|_Y_|___|___
		//   5 |_Y_|___|___|___|___|_Y_|_Y_|_Y_|_Y_|___|___
		//   4 |_Y_|___|___|_Y_|_Y_|_Y_|_Y_|_Y_|_Y_|___|___
		//   3 |_Y_|_Y_|_Y_|_Y_|_Y_|_Y_|_Y_|_Y_|_Y_|___|_Y_
		//   2 |_Y_|_Y_|_Y_|_Y_|_Y_|_Y_|_Y_|_Y_|_Y_|_Y_|_Y_
		//   1 |_Y_|_Y_|_Y_|_Y_|_Y_|_Y_|_Y_|_Y_|_Y_|_Y_|_Y_
		//   0 |_Y_|_Y_|_Y_|_Y_|_Y_|_Y_|_Y_|_Y_|_Y_|_Y_|_Y_
		if (totalOf2Cards == 0) {
			if (playerCard == null) {
				return true;
			} else {
				switch(playerCard.value) {
				case 1:
					if (totalHand <= 3) {
						return true;
					}
					break;
				case 2:
				case 3:
					if (totalHand <= 4) {
						return true;
					}
					break;
				case 4:
				case 5:
					if (totalHand <= 5) {
						return true;
					}
					break;
				case 6:
				case 7:
					if (totalHand <= 6) {
						return true;
					}
					break;
				case 8:
					if (totalHand <= 2) {
						return true;
					}
					break;
				case 9:
					if (totalHand <= 3) {
						return true;
					}
					break;
				case 10:
				case 11:
				case 12:
				case 13:
					if (totalHand <= 3) {
						return true;
					}
					break;
				}
			}
		}
		
		return false;
	}
	
	//tells if the player is able to draw another card, no special cases here, just if the player's hand is
	//6 or bigger that they are not able to
	public boolean evaluatePlayerDraw(ArrayList<Card> hand) {
		
		int playerHandValue = handTotal(hand);
		
		if (playerHandValue >= 6) {
			return false;
		}
		return true;
	}
	
	//determines if a drawn hand is a natural one, used for the beginning draw
	public boolean isNaturalHand(ArrayList<Card> hand) {
		if (handTotal(hand) == 9 || handTotal(hand) == 8) {
			return true;
		}
		return false;
	}
	
	//similar to whoWon, but without the option on the player
	public String whoWasClosest(ArrayList<Card> hand1,ArrayList<Card> hand2) {
		
		int hand1Val = handTotal(hand1);
		int hand2Val = handTotal(hand2);
		
		boolean hand1WinCond = false;
		boolean hand2WinCond = false;
		
		//check which hands have reached the natural state, if at all
		if (hand1Val == 8 || hand1Val == 9) {
			hand1WinCond = true;
		}
		
		if (hand2Val == 8 || hand2Val == 9) {
			hand2WinCond = true;
		}
		
		//both hands are naturals
		if (hand1WinCond == true && hand2WinCond == true) {
			return "Draw";
		} else if (hand1WinCond == true && hand2WinCond == false) {//player's hand is natural
			return "Self";
		} else if (hand1WinCond == false && hand2WinCond == true) {//dealer's hand is natural
			return "Banker";
		}
		
		//after exhausting our options otherwise, we check for who is the closest to 9
		
		if (hand1Val == hand2Val) {//the hands are equally as far from 9
			return "Draw";
		} else if (hand1Val > hand2Val) {//the player's hand is closer than the dealers
			return "Self";
		} else {//the dealer's hand is closer than the players
			return "Banker";
		}
	}

}
