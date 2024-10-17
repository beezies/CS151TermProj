package FinanceUI;

import java.time.LocalDate;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class FinanceApp extends Application{
	
	Scene homeScene;
	Scene newAccScene;
	LocalDate date = LocalDate.now();
	
	@Override
    public void start(Stage stage) {
		
		homeScene = getHomeScene(stage);
		newAccScene = getNewAccScene(stage);
		
        stage.setScene(homeScene);
        stage.show();
		
    }
	
	public Scene getHomeScene(Stage stage) {
		VBox pane = new VBox();
		Button newAccBtn = new Button("Add New Account");
		Label lbl = new Label("To be implemented: home page \n(Add account button, \naccounts view, "
				+ "\nschedule transaction button, \ntransactions view)");
		pane.getChildren().add(lbl);
		pane.getChildren().add(newAccBtn);
		
		newAccBtn.setOnAction(e -> stage.setScene(newAccScene));
		return new Scene(pane, 450, 450);
	}
	
	public Scene getNewAccScene(Stage stage) {
		VBox pane = new VBox();
		VBox fieldPane = new VBox();
		HBox btnPane = new HBox();
		
		Label lbl = new Label("To be implemented: new account page \n(Account name,"
				+ " \ncreation date, \nstarting balance)");
		TextField nameTF = new TextField("Enter Account Name");
		DatePicker dp = new DatePicker(date);
		TextField balanceTF = new TextField("Enter Starting Balance");
		
		Button addBtn = new Button("Add Account");
		Button cancelBtn = new Button("Cancel");
		
		fieldPane.getChildren().add(lbl);
		fieldPane.getChildren().add(nameTF);
		fieldPane.getChildren().add(dp);
		fieldPane.getChildren().add(balanceTF);
		btnPane.getChildren().add(addBtn);
		btnPane.getChildren().add(cancelBtn);
		pane.getChildren().add(fieldPane);
		pane.getChildren().add(btnPane);
		
		addBtn.setOnAction(e -> stage.setScene(homeScene));
		cancelBtn.setOnAction(e -> stage.setScene(homeScene));
		return new Scene(pane, 450, 450);
	}
	
    public static void main(String[] args) {
        launch(args);
    }

}
