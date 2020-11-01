import java.io.IOException;
import javafx.util.Pair;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class BetAmountWindow{
	
	@FXML
	private Button confirmBet;
	
	@FXML
	private TextField betAmt;
	
	@FXML
	private Button yesClose;
	
	@FXML
	private Button noClose;
	
	private Stage s;
	
	private Stage warningStage;
	
	private boolean hasBetBeenSent = false;
	
	public String typeOfBet;
	
	public BetAmountWindow(int typeOfBet,Pair<String,Integer> bBun) throws IOException{
		
		Parent root = FXMLLoader.load(getClass().getResource("Amt2Bet.fxml"));
		Scene scene = new Scene(root, 600, 227);
		s = new Stage();
		s.setTitle("Bet");
		if (typeOfBet == 1) {
			s.setTitle("Bet on Yourself");
			this.typeOfBet = "Self";
		} else if (typeOfBet == 2) {
			s.setTitle("Bet on Banker");
			this.typeOfBet = "Banker";
		} else if (typeOfBet == 3) {
			s.setTitle("Bet on Draw");
			this.typeOfBet = "Draw";
		}
		
		s.setScene(scene);
		s.show();
		
		s.setOnCloseRequest(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent event) {
				// TODO Auto-generated method stub
				try {
					Parent root = FXMLLoader.load(getClass().getResource("WarningSansBet.fxml"));
					Scene scene = new Scene(root, 200, 100);
					warningStage = new Stage();
					warningStage.setTitle("Confirm?");
					warningStage.setScene(scene);
					warningStage.show();
					
					warningStage.setOnCloseRequest(new EventHandler<WindowEvent>() {

						@Override
						public void handle(WindowEvent event) {
							warningStage.close();
						}
						
					});
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		});;
		
		confirmBet.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				int cashBet = Integer.parseInt(betAmt.getText());
				if (cashBet <= 0) {
					ErrorBox e = new ErrorBox(4);
					betAmt.setText("");
				} else {
					bBun = new Pair<String, Integer>(String.valueOf(typeOfBet),cashBet);
				}
				
			}
			
		});
	}
	
	//buttons between the lines handle the issues when it comes to closing the screen when needed
	//----------------------------------------------------------------------------------
	public void yesToClose() {
		warningStage.close();
		s.close();
	}
	
	public void noToClose() {
		warningStage.close();
	}
	//--------------------------------------------------------------------------------------
	
	public void sendAmountBet() {
		//grab the amount bet and package it as a pair
		int cashBet = Integer.parseInt(betAmt.getText());
		if (cashBet < 0) {//check the bet and make sure it is valid
			cashBet = 0;
		}
		bBun = new Pair<String,Integer>(this.typeOfBet,cashBet);
		hasBetBeenSent = true;
	}
	
	public void weDoneHere() {
		if (hasBetBeenSent) {
			s.close();
		} else {
			ErrorBox e = new ErrorBox(3);
		}
	}

}
