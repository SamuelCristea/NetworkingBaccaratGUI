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
	
	@FXML
	public TextField ClientIPAddress;
	
	@FXML
	private Button ConnectButton;
	
	@FXML
	public TextField ClientPortNum;
	
	@FXML
	private MenuItem betYourself;
	
	@FXML
	private MenuItem betBanker;
	
	@FXML
	private MenuItem betTie;

	@FXML
	private Button quit;
	
	ListView<String> listItems, listItems2;
	
	Stage stage;
	
	private Pair<String,Integer> betBundle;
	private boolean hasBet = false;
	
	
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
		BetAmountWindow baw = new BetAmountWindow(1,betBundle);
		
	}
	
	public void displayBankerScreen() throws IOException {
		BetAmountWindow baw = new BetAmountWindow(2,betBundle);
		
	}
	
	public void displayTieScreen() throws IOException {
		BetAmountWindow baw = new BetAmountWindow(3,betBundle);
		
	}
	//-------------------------------------------------------

}
