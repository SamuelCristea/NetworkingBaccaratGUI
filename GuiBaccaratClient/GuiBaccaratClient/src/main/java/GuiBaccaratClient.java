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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

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
	
	ListView<String> listItems, listItems2;
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		Parent root = FXMLLoader.load(getClass().getResource("main.FXML"));
		Scene scene = new Scene(root, 300, 300); 
		primaryStage.setTitle("BaccaratClients");
		primaryStage.setScene(scene);
		
		primaryStage.show();
		
		listItems = new ListView<String>();
		listItems2 = new ListView<String>();
		
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });

	}
	
	//when the user types in the data and presses the Connect button, we enter it in to connect to that server specified
	public void getClientConnect() {
		String portnum = ClientPortNum.getText();
		String ipaddy = ClientIPAddress.getText();

		clientConnection = new Client(data->{
			Platform.runLater(()->{;   // this is commented --> listItems2.getItems().add(data.toString())
							});
			});
			
		
		
		clientConnection.sendToClient(portnum, ipaddy);
		clientConnection.start();
	}
}