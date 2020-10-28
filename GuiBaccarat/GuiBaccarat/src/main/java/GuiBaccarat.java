
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
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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
	ListView<String> ServerView;
	
	@FXML
	ToggleButton pwrButton;
	
	@FXML
	TextField ServerPortNum;
	
	boolean wasPwrButtonPressed = false;
	boolean prevPortUsed = false;
	String pn;
	
	
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
			});

		},p);
	}
}
