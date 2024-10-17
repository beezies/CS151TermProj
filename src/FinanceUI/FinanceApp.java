package FinanceUI;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class FinanceApp extends Application{
	
	VBox homePane = new VBox();
	Label label = new Label("To be implemented: home page \n(Add account button, \naccounts view, "
			+ "\nschedule transaction button, \ntransactions view)");
	Button newAccButton = new Button("Add New Account");
	Scene homeScene;
	Scene newAccScene;
	
	@Override
    public void start(Stage stage) {
				
		homePane.getChildren().add(label);
		homePane.getChildren().add(newAccButton);
		
		homeScene = new Scene(homePane, 300, 300);
        stage.setScene(homeScene);
        stage.show();
		
		newAccButton.setOnAction(e -> stage.setScene(getNewAccScene()));
    }
	
	public Scene getNewAccScene() {
		VBox pane = new VBox();
		Label lbl = new Label("To be implemented: new account page \n(Account name,"
				+ " \ncreation date, \nstarting balance)");
		pane.getChildren().add(lbl);
		return new Scene(pane, 300, 300);
	}
	
    public static void main(String[] args) {
        launch(args);
    }

}
