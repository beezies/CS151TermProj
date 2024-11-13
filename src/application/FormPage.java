package application;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public interface FormPage {
	
	public Scene getScene(Stage stage);
	
	/**
	 * Prompts user with reason for failed submission of new account/transaction.
	 *
	 * @param title
	 * @param message
	 */
	static void showAlert(String title, String message) {
		Alert alert = new Alert(Alert.AlertType.WARNING);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}
}
