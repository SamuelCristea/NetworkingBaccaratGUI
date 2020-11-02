import java.util.ArrayList;
import java.util.Scanner;

public class BaccaratGame {
	
	public ArrayList<Card> playerHand;
	public ArrayList<Card> bankerHand;
	public BaccaratDealer theDealer;
	public double currentBet;
	public double totalWinnings;
	
	//Constructor that avoids the problem of null pointers for making them
	public BaccaratGame() {
		this.playerHand = new ArrayList<Card>();
		this.bankerHand = new ArrayList<Card>();
		this.theDealer = new BaccaratDealer();
	}
	
	//The game logic personified. Actually uses what we have for the Logic, Dealer, and Card classes
	public double evaluateWinnings() {
		//get the dealer's first pick from the deck, and burns the amount of cards the pulled one is worth
		Card firstPickByDealer = theDealer.drawOne();
		int discardLimiter;
		
		//process the value of the card depending on the card's value
		if (firstPickByDealer.value >= 10) {
			discardLimiter = 0;
		} else {
			discardLimiter = firstPickByDealer.value;
		}
		
		//burn the cards; we will never use the burn card at all
		for (int i = 0; i < discardLimiter; ++i) {
			Card burn = theDealer.drawOne();
		}
		
		//CLI stuff, for testing and bugfixing
		//-------------------------------------------------------------
		System.out.println("Who do you bet on: Banker, Draw, or Self?");
		Scanner sc = new Scanner(System.in);
		String betOn = sc.next();
		System.out.println(betOn);
		
		
		System.out.println("How Much To Bet? ");
		double bet = sc.nextDouble();
		sc.close();
		//-------------------------------------------------------------
		
		//we keep track of the player's last drawn card for validating efforts
		Card lastDrawnbyPlayer = null;
		
//		for (int i = 0; i < 2; ++i) {
//			Card c = theDealer.drawOne();
//			Card d = theDealer.drawOne();
//			playerHand.add(c);
//			bankerHand.add(d);
//			lastDrawnbyPlayer = c;
//		}
		
		BaccaratGameLogic bgl = new BaccaratGameLogic();
		//we identify if the dealt hand is a natural one, either the banker's or the player's
		if (bgl.isNaturalHand(playerHand)) {
//			System.out.println("In the natural section");
//			System.out.println("Self");
//			System.out.println(betOn);
//			System.out.println(betOn == "Self");
			return calculateWin(bet,betOn,"Self");
		} else if (bgl.isNaturalHand(bankerHand)) {
//			System.out.println("In the natural section");
//			System.out.println("Banker");
//			System.out.println(betOn);
//			System.out.println(betOn == "Banker");
			return calculateWin(bet,betOn,"Banker");
		}
		
		//we did not find a natural hand, so we see if anyone won at all
		String wonYet = bgl.whoWon(playerHand, playerHand);
		
		//the dealer and the player have reached a draw
		if (wonYet == "Draw") {
//			System.out.println("In the draw section");
//			System.out.println(wonYet);
//			System.out.println(betOn);
//			System.out.println(betOn == wonYet);
			return calculateWin(bet,betOn,wonYet);
			
		} else if (wonYet == "No Win Yet") {//no win has been detected yet, so we see if anyone can draw a card
				if (bgl.evaluatePlayerDraw(playerHand)) {//can the player draw? If so, they take a card and we see if they won
					lastDrawnbyPlayer = theDealer.drawOne();
					playerHand.add(lastDrawnbyPlayer);
					wonYet = bgl.whoWon(playerHand, playerHand);
					if (wonYet == "Self") {
//						System.out.println("In the self section");
//						System.out.println(wonYet);
//						System.out.println(betOn);
//						System.out.println(betOn == wonYet);
						return calculateWin(bet,betOn,wonYet);
					}
				}
				//can the banker draw? If so, they draw a card
				//this comes after the player since thier draw relies on the player's last card drawn
				if (bgl.evaluateBankerDraw(bankerHand, lastDrawnbyPlayer)) {
					bankerHand.add(theDealer.drawOne());
					wonYet = bgl.whoWon(playerHand, playerHand);
					if (wonYet == "Banker") {
//						System.out.println("In the banker section");
//						System.out.println(wonYet);
//						System.out.println(betOn);
//						System.out.println(betOn == wonYet);
						return calculateWin(bet,betOn,wonYet);
						
					}
				}
			}
			
			//We have exhausted all other options here, so we will see who was the closest to 9 and declare a winner there
			//if applicable
			wonYet = bgl.whoWasClosest(playerHand, playerHand);
//			System.out.println("In the closest section");
//			System.out.println(String.valueOf(wonYet));
//			System.out.println(String.valueOf(betOn));
//			System.out.println(String.valueOf(betOn) == String.valueOf(betOn));
			return calculateWin(bet,String.valueOf(betOn),betOn);	
	}
	
	//How we calculate if a player has won and if so, what their payout is
	public double calculateWin(double betInit,String betOn,String message) {
		double winnings = 0;//we havent won anything yet, so winnings are at $0
		String bo = String.valueOf(betOn);
		String m = message;
//		System.out.println(bo);
//		System.out.println(m);
//		System.out.println("On bo and m being equal: " + (bo == m));
//		System.out.println("On m being Banker: " + (m == "Banker")); 
//		System.out.println("On m being Draw: " + (m == "Draw")); 
//		System.out.println("On m being Self: " + (m == "Self"));
		
		if (bo == m) {//what the player betted on was the same as what won the round
			if (m == "Banker") {//Since the player got Banker, the table takes a 5% commission and 2x odds otherwise
				winnings = (betInit*2) - (betInit*0.05);
				System.out.println("You got the right bet! Banker takes 5% as commission tho");
				System.out.println("Winnings: $" + winnings);
				return winnings;
			}
			if (m == "Self") {//they won on themselves, so a straight 2x odds payout without a comission
				winnings = betInit * 2;
				System.out.println("Nice Confidence, and it has paid off");
				System.out.println("Winnings: $" + winnings);
				return winnings;
			}
			if (m == "Draw") {//The grail, if they get a draw correctly, they get an 8x payout!
				winnings = betInit * 8;
				System.out.println("WOW! You guessed right on the draw, and will be specially rewarded");
				System.out.println("Winnings: $" + winnings);
				return winnings;
			}
		} else {//the player has very bad luck, so they win nothing and go home poor
			System.out.println("Oh, so sorry, you didnt bet right, you lost your bet");
			System.out.println("Winnings: $0");
			return winnings;
		}
		
		return 0;
		
	}

}
