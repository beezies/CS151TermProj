package FinanceUI;

import java.time.LocalDate;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
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
		BorderPane pane = new BorderPane();
		HBox top = new HBox();
		VBox center = new VBox();
		
		Label title = new Label("Money Money Money");
		title.setPadding(new Insets(50, 100, 50, 230));
		
		Label accsLbl = new Label("Accounts");
		Button newAccBtn = new Button("Add New Account");
		Label transLbl = new Label("Upcoming Transactions");
		Button transBtn = new Button("Schedule Transaction");
		
		top.getChildren().add(title);
		pane.setTop(top);
		center.getChildren().add(accsLbl);
		center.getChildren().add(newAccBtn);
		center.getChildren().add(transLbl);
		center.getChildren().add(transBtn);
		pane.setCenter(center);
		
		newAccBtn.setOnAction(e -> stage.setScene(newAccScene));
		return new Scene(pane, 600, 450);
	}
	
	public Scene getNewAccScene(Stage stage) {
		VBox pane = new VBox();
		VBox fieldPane = new VBox();
		HBox btnPane = new HBox();
		
		Label lbl = new Label("To be implemented: new account page \n(Account name,"
				+ " \ncreation date, \nstarting balance)");
		TextField nameTF = new TextField();
		nameTF.setPromptText("Enter Account Name");
		DatePicker dp = new DatePicker(date);
		TextField balanceTF = new TextField();
		balanceTF.setPromptText("Enter Starting Balance");
		
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
