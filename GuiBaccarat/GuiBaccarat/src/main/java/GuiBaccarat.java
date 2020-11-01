
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
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class GuiBaccarat extends Application{

	
	TextField s1,s2,s3,s4, c1;
	Button serverChoice,clientChoice,b1;
	HashMap<String, Scene> sceneMap;
	GridPane grid;
	HBox buttonBox;
	VBox clientBox;
	Scene startScene;
	BorderPane startPane;
	Server serverConnection;
	Client clientConnection;
	
	ListView<String> listItems, listItems2;
	
	@FXML
	MenuButton clientList;
	
	@FXML
	ListView<String> ServerView;
	
	@FXML
	ToggleButton pwrButton;
	
	@FXML
	TextField ServerPortNum;
	
	@FXML
	Text numClientsConnected;
	
	boolean wasPwrButtonPressed = false;
	boolean prevPortUsed = false;
	String pn;
	int totalClients = 0;
	BaccaratGame g;
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		Parent root = FXMLLoader.load(getClass().getResource("ServerSideGUI.FXML"));
		Scene scene = new Scene(root, 456, 400); 
		primaryStage.setTitle("Baccarat");
		primaryStage.setScene(scene);
		
		primaryStage.show();
		
		listItems = new ListView<String>();
		listItems2 = new ListView<String>();
		ServerView = new ListView<String>();
		pwrButton = new ToggleButton();
		if (clientList == null)
			System.out.println("REEEEEEEEEEEEEE");
		clientList = new MenuButton();
		g = new BaccaratGame();
		g.evaluateWinnings();
		
		
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });

	}
	
	//changes the text on the power button and sets the server to listen to a specific port
	public void changePowerText() {
		
		if (wasPwrButtonPressed) {//server was turned off by user
			
			pwrButton.setText("Turn Server On");
			wasPwrButtonPressed = false;
			
		} else {//server was turned on by user
			
			//verify that it isnt an empty string passed through
			if (ServerPortNum.getText() != "") {
				pwrButton.setText("Turn Server Off");
				pn = ServerPortNum.getText();
				//verify that the port number is set within correct bounds
				if (Integer.parseInt(pn) > 65535 || Integer.parseInt(pn) < 0) {
					ErrorBox e = new ErrorBox(2);
				} else {
					serverOpen();
					wasPwrButtonPressed = true;
					
				}
				
			} else {
				ErrorBox e = new ErrorBox(1);
			}
		}
	}
	
	//pops open the server to connect to a specific port
	private void serverOpen() {
		int p = Integer.parseInt(pn);
		serverConnection = new Server(data -> {
			Platform.runLater(()->{
				ServerView.getItems().add(data.toString());
				updateClientsConnected();
			});

		},p);
		
	}
	
	//updates the count of the clients connected and makes an info page for each player's stats
	private void updateClientsConnected() {
		int currentClientsConnected = serverConnection.getNumClientsConnected();
		if (totalClients != currentClientsConnected) {
			numClientsConnected.setText(String.valueOf(currentClientsConnected));
			addInNewClient();
			totalClients = currentClientsConnected;
		}
	}
	
	//makes the client info page whenever a new client connects
	private void addInNewClient() {
		int currentClientCount = serverConnection.getNumClientsConnected();
		if (totalClients < currentClientCount) {
			MenuItem mi = new MenuItem("Client #" + String.valueOf(currentClientCount));
			mi.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					try {
						Stage clientInfo = new Stage();
						Parent root = FXMLLoader.load(getClass().getResource("ClientInfo.fxml"));
						Scene scene = new Scene(root, 400, 400);
						clientInfo.setTitle("Client #" + String.valueOf(currentClientCount) + "'s Information");
						clientInfo.setScene(scene);
						clientInfo.show();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				
			});
			if (clientList == null)
				System.out.println("Da fuq");
			clientList.getItems().add(mi);
		} 
	}
}
