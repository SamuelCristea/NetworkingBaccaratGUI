import java.io.File;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ErrorBox {
	//error box when the user has done goofed on an input
		ErrorBox(int errorType) {
			
			//generate the actual text box for the error
			Stage errorStage = new Stage();
			errorStage.setTitle("Error");
			errorStage.setMinHeight(100);
			errorStage.setMinWidth(400);
			Text uDonGoofed = new Text();
			
			try {//play the error sound
		        File f = new File("wrong.mp3");
		        Media hit = new Media(f.toURI().toString());
		        MediaPlayer mediaPlayer = new MediaPlayer(hit);
		        mediaPlayer.play();
		    } catch(Exception ex) {
		        ex.printStackTrace();
		        System.out.println("Exception: " + ex.getMessage());
		    }
			
			//display what type of error is displayed
			switch(errorType) {
			case (1) :
				uDonGoofed.setText("Please enter a port number");
				break;
			case (2) :
				uDonGoofed.setText("Invalid Port Number");
				break;
			case (3):
				uDonGoofed.setText("Could not connect to that server, try another one");
				break;
			case (4):
				uDonGoofed.setText("Press Start Before Running the Program");
				break;
			}
			
			//assemble the screen
			BorderPane bp = new BorderPane();
			bp.setCenter(uDonGoofed);
			Scene scene = new Scene(bp);
			errorStage.setScene(scene);
			errorStage.show();
		}
}
