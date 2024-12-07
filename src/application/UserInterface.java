package application;

import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class UserInterface extends Application{

	@Override
	public void start(Stage stage) {
		// TODO Auto-generated method stub
	}
	
	
	
	private void showAlert(String title, String message) {
		Alert alert = new Alert(Alert.AlertType.WARNING);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}

}
