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
		System.out.println(betOn);
		
		
		System.out.println("How Much To Bet? ");
		double bet = sc.nextDouble();
		sc.close();
		
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
			System.out.println("In the natural section");
			System.out.println("Self");
			System.out.println(betOn);
			System.out.println(betOn == "Self");
			return calculateWin(bet,betOn,"Self");
		} else if (bgl.isNaturalHand(bankerHand)) {
			System.out.println("In the natural section");
			System.out.println("Banker");
			System.out.println(betOn);
			System.out.println(betOn == "Banker");
			return calculateWin(bet,betOn,"Banker");
		}
		
		String wonYet = bgl.whoWon(playerHand, playerHand);
		
		if (wonYet == "Draw") {
			System.out.println("In the draw section");
			System.out.println(wonYet);
			System.out.println(betOn);
			System.out.println(betOn == wonYet);
			return calculateWin(bet,betOn,wonYet);
			
		} else if (wonYet == "No Win Yet") {
				if (bgl.evaluatePlayerDraw(playerHand)) {
					lastDrawnbyPlayer = theDealer.drawOne();
					playerHand.add(lastDrawnbyPlayer);
					wonYet = bgl.whoWon(playerHand, playerHand);
					if (wonYet == "Self") {
						System.out.println("In the self section");
						System.out.println(wonYet);
						System.out.println(betOn);
						System.out.println(betOn == wonYet);
						return calculateWin(bet,betOn,wonYet);
						
					}
				}
				
				if (bgl.evaluateBankerDraw(bankerHand, lastDrawnbyPlayer)) {
					bankerHand.add(theDealer.drawOne());
					wonYet = bgl.whoWon(playerHand, playerHand);
					if (wonYet == "Banker") {
						System.out.println("In the banker section");
						System.out.println(wonYet);
						System.out.println(betOn);
						System.out.println(betOn == wonYet);
						return calculateWin(bet,betOn,wonYet);
						
					}
				}
			}
			
			wonYet = bgl.whoWasClosest(playerHand, playerHand);
			System.out.println("In the closest section");
			System.out.println(String.valueOf(wonYet));
			System.out.println(String.valueOf(betOn));
			System.out.println(String.valueOf(betOn) == String.valueOf(betOn));
			return calculateWin(bet,String.valueOf(betOn),betOn);
			
			
	}
	
	public double calculateWin(double betInit,String betOn,String message) {
		double winnings = 0;
		String bo = String.valueOf(betOn);
		String m = message;
		System.out.println(bo);
		System.out.println(m);
		System.out.println("On bo and m being equal: " + (bo == m));
		System.out.println("On m being Banker: " + (m == "Banker")); 
		System.out.println("On m being Draw: " + (m == "Draw")); 
		System.out.println("On m being Self: " + (m == "Self"));
		
		if (bo == m) {
			if (m == "Banker") {
				winnings = (betInit*2) - (betInit*0.05);
				System.out.println("You got the right bet! Banker takes 5% as commission tho");
				System.out.println("Winnings: $" + winnings);
				return winnings;
			}
			if (m == "Self") {
				winnings = betInit * 2;
				System.out.println("Nice Confidence, and it has paid off");
				System.out.println("Winnings: $" + winnings);
				return winnings;
			}
			if (m == "Draw") {
				winnings = betInit * 8;
				System.out.println("WOW! You guessed right on the draw, and will be specially rewarded");
				System.out.println("Winnings: $" + winnings);
				return winnings;
			}
		} else {
			 
			System.out.println("Oh, so sorry, you didnt bet right, you lost your bet");
			System.out.println("Winnings: $0");
			return winnings;
		}
		
		return 0;
		
	}

}
