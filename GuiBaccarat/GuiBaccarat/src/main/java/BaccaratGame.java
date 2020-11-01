import java.util.ArrayList;
import java.util.Scanner;

public class BaccaratGame {
	
	public ArrayList<Card> playerHand;
	public ArrayList<Card> bankerHand;
	public BaccaratDealer theDealer;
	public double currentBet;
	public double totalWinnings;
	
	public BaccaratGame() {
		this.playerHand = new ArrayList<Card>();
		this.bankerHand = new ArrayList<Card>();
		this.theDealer = new BaccaratDealer();
	}
	
	public double evaluateWinnings() {
		Card firstPickByDealer = theDealer.drawOne();
		int discardLimiter;
		
		if (firstPickByDealer.value >= 10) {
			discardLimiter = 0;
		} else {
			discardLimiter = firstPickByDealer.value;
		}
		
		for (int i = 0; i < discardLimiter; ++i) {
			Card burn = theDealer.drawOne();
		}
		
		System.out.println("Who do you bet on: Banker, Draw, or Self?");
		Scanner sc = new Scanner(System.in);
		String betOn = sc.next();
		
//		while (betOn != "Banker" || betOn != "Draw" || betOn != "Self") {
//			System.out.println("Who do you bet on: Banker, Draw, or Self?");
//			betOn = sc.next();
//		}
		
		System.out.println("How Much To Bet? ");
		double bet = sc.nextDouble();
		
//		while (bet < 0) {
//			System.out.println("How Much To Bet? ");
//			bet = sc.nextDouble();
//		}
		
		Card lastDrawnbyPlayer = null;
		
		for (int i = 0; i < 2; ++i) {
			Card c = theDealer.drawOne();
			Card d = theDealer.drawOne();
			playerHand.add(c);
			bankerHand.add(d);
			lastDrawnbyPlayer = c;
		}
		
		BaccaratGameLogic bgl = new BaccaratGameLogic();
		
		if (bgl.isNaturalHand(playerHand)) {
			return calculateWin(bet,betOn,"Self");
		} else if (bgl.isNaturalHand(bankerHand)) {
			return calculateWin(bet,betOn,"Banker");
		}
		
		String wonYet = bgl.whoWon(playerHand, playerHand);
		
		if (wonYet == "Draw") {
			return calculateWin(bet,betOn,wonYet);
			
		} else {
				if (bgl.evaluatePlayerDraw(playerHand) && theDealer.deckSize() > 0) {
					lastDrawnbyPlayer = theDealer.drawOne();
					playerHand.add(lastDrawnbyPlayer);
					wonYet = bgl.whoWon(playerHand, playerHand);
					if (wonYet == "Player") {
						return calculateWin(bet,betOn,wonYet);
						
					}
				}
				
				if (bgl.evaluateBankerDraw(bankerHand, lastDrawnbyPlayer)) {
					bankerHand.add(theDealer.drawOne());
					wonYet = bgl.whoWon(playerHand, playerHand);
					if (wonYet == "Dealer") {
						return calculateWin(bet,betOn,wonYet);
						
					}
				}
			}
			
			wonYet = bgl.whoWon(playerHand, playerHand);
			return calculateWin(bet,betOn,wonYet);
			
			
	}
	
	public double calculateWin(double betInit,String betOn,String message) {
		double winnings = 0;
		if (message != betOn) {
			System.out.println("Oh, so sorry, you didnt bet right, you lost your bet");
			System.out.println("Winnings: $0");
		} else {
			if (betOn == "Banker") {
				winnings = (betInit*2) - (betInit*0.05);
				System.out.println("You got the right bet! Banker takes 5% as commission tho");
				System.out.println("Winnings: $" + winnings);
			} else if (betOn == "Draw") {
				winnings = betInit * 8;
				System.out.println("WOW! You guessed right on the draw, and will be specially rewarded");
				System.out.println("Winnings: $" + winnings);
			} else {
				winnings = betInit * 2;
				System.out.println("Nice Confidence, and it has paid off");
				System.out.println("Winnings: $" + winnings);
			}
		}
		return winnings;
	}

}
