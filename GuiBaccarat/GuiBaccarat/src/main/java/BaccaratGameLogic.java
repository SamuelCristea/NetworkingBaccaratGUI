import java.util.ArrayList;

public class BaccaratGameLogic {
	
	//checks to see if the hands has a value of 8 or 9. 
	//If none do, we choose the hand that is the closest to 8 or 9
	public String whoWon(ArrayList<Card> hand1,ArrayList<Card> hand2) {
		
		int hand1Val = handTotal(hand1);
		int hand2Val = handTotal(hand2);
		
		boolean hand1WinCond = false;
		boolean hand2WinCond = false;
		
		if (hand1Val == 8 || hand1Val == 9) {
			hand1WinCond = true;
		}
		
		if (hand2Val == 8 || hand2Val == 9) {
			hand2WinCond = true;
		}
		
		if (hand1WinCond == true && hand2WinCond == true) {
			return "Tie";
		} else if (hand1WinCond == true && hand2WinCond == false) {
			return "Hand 1";
		} else if (hand1WinCond == false && hand2WinCond == true) {
			return "Hand 2";
		}
		
		if (evaluatePlayerDraw(hand1)) {
			return "No Win Yet";
		}
		
		if (evaluateBankerDraw(hand2,hand1.get(hand1.size()-1))) {
			return "No Win Yet";
		}
		
		if (hand1Val == hand2Val) {
			return "Tie";
		} else if (hand1Val > hand2Val) {
			return "Hand 1";
		} else {
			return "Hand 2";
		}
	}
	
	public int handTotal(ArrayList<Card> hand) {
		int totalHandVal = 0;
		
		for (Card card : hand) {
			switch (card.value) {
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
			case 10:
			case 11:
			case 12: 
			case 13:
				totalHandVal += 0;
				break;
			
			}
			if (totalHandVal > 9) {
				totalHandVal -= 10;
			}
		}
		return totalHandVal;
	}
	
	public boolean evaluateBankerDraw(ArrayList<Card> hand, Card playerCard) {
		
		if (handTotal(hand) >= 7) {
			return false;
		} 
		
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
		
		if (totalOf2Cards == 0) {
			if (playerCard == null) {
				return true;
			} else {
				switch(playerCard.value) {
				case 1:
					if (handTotal(hand) <= 3) {
						return true;
					}
					break;
				case 2:
				case 3:
					if (handTotal(hand) <= 4) {
						return true;
					}
					break;
				case 4:
				case 5:
					if (handTotal(hand) <= 5) {
						return true;
					}
					break;
				case 6:
				case 7:
					if (handTotal(hand) <= 6) {
						return true;
					}
					break;
				case 8:
					if (handTotal(hand) <= 2) {
						return true;
					}
					break;
				case 9:
					if (handTotal(hand) <= 3) {
						return true;
					}
					break;
				case 10:
				case 11:
				case 12:
				case 13:
					if (handTotal(hand) <= 3) {
						return true;
					}
					break;
				}
			}
		}
		
		return false;
	}
	
	public boolean evaluatePlayerDraw(ArrayList<Card> hand) {
		
		int playerHandValue = handTotal(hand);
		
		if (playerHandValue >= 6) {
			return false;
		}
		return true;
	}

}
