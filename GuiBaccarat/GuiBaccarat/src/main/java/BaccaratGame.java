import java.util.ArrayList;

public class BaccaratGame {
	
	public ArrayList<Card> playerHand;
	public ArrayList<Card> bankerHand;
	public BaccaratDealer theDealer;
	public double currentBet;
	public double totalWinnings;
	
	public double evaluateWinnings() {
		BaccaratGameLogic bgl = new BaccaratGameLogic();
		String winner = bgl.whoWon(playerHand, bankerHand);
		
		if (winner == "Tie") {
			
		} else if (winner == "Player") {
			
		} else if (winner == "Dealer") {
			
		}
		return 0.0;
	}

}
