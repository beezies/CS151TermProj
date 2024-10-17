package FinanceUI;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class FinanceApp extends Application{
	
	Scene homeScene;
	Scene newAccScene;
	
	@Override
    public void start(Stage stage) {
		
		homeScene = getHomeScene(stage);
		newAccScene = getNewAccScene(stage);
		
        stage.setScene(homeScene);
        stage.show();
		
    }
	
	public Scene getHomeScene(Stage stage) {
		VBox pane = new VBox();
		Button newAccButton = new Button("Add New Account");
		Label label = new Label("To be implemented: home page \n(Add account button, \naccounts view, "
				+ "\nschedule transaction button, \ntransactions view)");
		pane.getChildren().add(label);
		pane.getChildren().add(newAccButton);
		
		newAccButton.setOnAction(e -> stage.setScene(newAccScene));
		return new Scene(pane, 300, 300);
	}
	
	public Scene getNewAccScene(Stage stage) {
		VBox pane = new VBox();
		Button backButton = new Button("Cancel");
		Label lbl = new Label("To be implemented: new account page \n(Account name,"
				+ " \ncreation date, \nstarting balance)");
		pane.getChildren().add(backButton);
		pane.getChildren().add(lbl);
		
		backButton.setOnAction(e -> stage.setScene(homeScene));
		return new Scene(pane, 300, 300);
	}
	
    public static void main(String[] args) {
        launch(args);
    }

}
