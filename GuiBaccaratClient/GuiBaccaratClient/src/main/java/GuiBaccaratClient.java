import java.io.IOException;
import java.util.HashMap;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import javafx.util.Pair;

public class GuiBaccaratClient extends Application{

	
	public TextField s1,s2,s3,s4, c1;
	Button serverChoice,clientChoice,b1;
	HashMap<String, Scene> sceneMap;
	GridPane grid;
	HBox buttonBox;
	VBox clientBox;
	Scene startScene;
	BorderPane startPane;
	Server serverConnection;
	public Client clientConnection;
	
	//Connection Screen elements
	//-------------------------------------------------------------------------
	@FXML
	public TextField ClientIPAddress;
	
	@FXML
	private Button ConnectButton;
	
	@FXML
	public TextField ClientPortNum;
	//-------------------------------------------------------------------------
	
	//Main Screen elements
	//-------------------------------------------------------------------------
	@FXML
	private MenuItem betYourself;
	
	@FXML
	private MenuItem betBanker;
	
	@FXML
	private MenuItem betTie;

	@FXML
	private Button quit;
	//-------------------------------------------------------------------------
	
	//Warning Screen Buttons
	//-------------------------------------------------------------------------
	@FXML
	private Button yesClose;
	
	@FXML
	private Button noClose;
	//-------------------------------------------------------------------------
	
	//Betting Screen elements
	//-------------------------------------------------------------------------
	@FXML
	private Button confirmBet;
	
	@FXML
	private TextField betAmt;
	//-------------------------------------------------------------------------
	
	ListView<String> listItems, listItems2;
	
	Stage stage;
	Stage amtStage;
	
	private Pair<String,Integer> betBundle;
	private boolean hasBet = false;
	private String typeOfBet = "";
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		Parent root = FXMLLoader.load(getClass().getResource("main.FXML"));
		Scene scene = new Scene(root, 300, 300); 
		stage = new Stage();
		amtStage = new Stage();
		primaryStage.setTitle("Connect to the Server");
		primaryStage.setScene(scene);
		stage = primaryStage;
		
		stage.show();
		
		listItems = new ListView<String>();
		listItems2 = new ListView<String>();
		
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });
		
		startScene = primaryStage.getScene();

	}
	
	//when the user types in the data and presses the Connect button, we enter it in to connect to that server specified
	public void getClientConnect() throws Exception{
		String portnum = ClientPortNum.getText();
		String ipaddy = ClientIPAddress.getText();

		clientConnection = new Client(data->{
			Platform.runLater(()->{;   // this is commented --> listItems2.getItems().add(data.toString())
							});
			});
			
		
		
		clientConnection.sendToClient(portnum, ipaddy);
		clientConnection.start();
		
		if (clientConnection.isAlive()) {
			Window window = ConnectButton.getScene().getWindow();
			if(window instanceof Stage) {
				Parent mainWindow = FXMLLoader.load(getClass().getResource("ClientGUI.fxml"));
				Scene scene = new Scene(mainWindow, 600, 400);
				stage = (Stage) window;
				stage.setScene(scene);
			}			
		} else {
			ErrorBox error = new ErrorBox(3);
		}
	}
	
	//these next 3 functions open up the betting screens, and they will bet on what option they selected in the dropdown
	//-------------------------------------------------------
	public void displayYourselfScreen() throws IOException {
		if (!hasBet) {
			try {
				betWindow(1);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			ErrorBox e = new ErrorBox(5);
		}
			
	 }
	
	public void displayBankerScreen() throws IOException {
		if (!hasBet) {
			try {
				betWindow(2);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			ErrorBox e = new ErrorBox(5);
		}
			
	 }
		
	
	public void displayTieScreen() throws IOException {
		if (!hasBet) {
			try {
				betWindow(3);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			ErrorBox e = new ErrorBox(5);
		}
			
	 }
		
	//-------------------------------------------------------
	
	public void quitButton() {
		System.exit(0);
	}
	
	//the betting window, sans being in a separate class since thats dumb apparently
	//-------------------------------------------------------------------------------------------
	public void betWindow(int betFrom) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("Amt2Bet.fxml"));
		Scene scene = new Scene(root, 300, 150);
		Stage warningStage = new Stage();
		amtStage = new Stage();
		if (betFrom == 1) {
			amtStage.setTitle("Bet on Yourself");
			typeOfBet = "Self";
		} else if (betFrom == 2) {
			amtStage.setTitle("Bet on Banker");
			typeOfBet = "Banker";
		} else if (betFrom == 3) {
			amtStage.setTitle("Bet on Draw");
			typeOfBet = "Draw";
		}
		
		
		amtStage.setScene(scene);
		amtStage = this.amtStage;
		amtStage.show();
	}
	//-------------------------------------------------------------------------------------------
	
	public void sendAmountBet() {
		//grab the amount bet and package it as a pair
		int cashBet = Integer.parseInt(betAmt.getText());
		if (cashBet < 0) {//check the bet and make sure it is valid
			cashBet = 0;
		}
		betBundle = new Pair<String,Integer>(typeOfBet,cashBet);
		hasBet = true;
		betAmt.setText("Bet Recorded, Please exit :)");
	}

	
	}

