package FinanceUI;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
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
	private static final String ACCOUNTS_FILE_PATH = "accounts.csv";
	private static final String CSS_FILE_PATH = "financeStyle.css";

	@Override
    public void start(Stage stage) {
		
		homeScene = getHomeScene(stage);
		newAccScene = getNewAccScene(stage);
		
        stage.setScene(homeScene);
        stage.show();
		
    }
	
	/**
	 * Returns scene for home page.
	 * 
	 * @param stage
	 * @return Home scene
	 */
	public Scene getHomeScene(Stage stage) {
		BorderPane pane = new BorderPane();
		HBox top = new HBox();
		VBox center = new VBox();
		VBox left = new VBox();
		VBox right = new VBox();
		
		Label title = new Label("Money Money Money");
		
		Label accsLbl = new Label("Accounts");
		Button newAccBtn = new Button("Add New Account");
		Label transLbl = new Label("Upcoming Transactions");
		Button transBtn = new Button("Schedule Transaction");
		
		top.getChildren().add(title);
		pane.setTop(top);
		pane.setRight(right);
		pane.setLeft(left);
		center.getChildren().add(accsLbl);
		center.getChildren().add(newAccBtn);
		center.getChildren().add(transLbl);
		center.getChildren().add(transBtn);
		pane.setCenter(center);
		
		newAccBtn.setOnAction(e -> stage.setScene(newAccScene));
		homeScene = new Scene(pane, 600, 450);
		homeScene.getStylesheets().add(CSS_FILE_PATH);
		return homeScene;
	}
	
	/**
	 * Returns scene for new account page.
	 * 
	 * @param stage
	 * @returnn New account scene
	 */
	public Scene getNewAccScene(Stage stage) {
		VBox pane = new VBox();
		HBox titlePane = new HBox();
		VBox fieldPane = new VBox();
		HBox btnPane = new HBox();
		
		Label lbl = new Label("Define New Account");
		TextField nameTF = new TextField();
		DatePicker dp = new DatePicker(date);
		TextField balanceTF = new TextField();
		Label accountLBL = new Label("Enter Account Name");
		Label dateLBL = new Label("Enter Opening Date of Your Account");
		Label balanceLBL = new Label("Enter Starting Balance");
		
		Button addBtn = new Button("Add Account");
		Button cancelBtn = new Button("Cancel");
		
		
		titlePane.getChildren().add(lbl);
		fieldPane.getChildren().addAll(accountLBL, nameTF, dateLBL, dp, balanceLBL, balanceTF);
		btnPane.getChildren().add(addBtn);
		btnPane.getChildren().add(cancelBtn);
		pane.getChildren().add(titlePane);
		pane.getChildren().add(fieldPane);
		pane.getChildren().add(btnPane);
		
		addBtn.setOnAction(e ->{
				String accountName = nameTF.getText();
				LocalDate openingDate = dp.getValue();
				String balanceStr = balanceTF.getText();
				if (isDuplicateAccount(accountName)) {
					showAlert("Duplicate Account", "An account with this name already exists.");
				} else if (openingDate.isAfter(LocalDate.now())) {
					showAlert("Invalid Date", "Opening date cannot be in the future.");
				} else if (!isDouble(balanceStr)) {
					showAlert("Invalid Balance", "Starting balance must be a valid number.");
				} else {
					saveAccountData(accountName, openingDate, balanceStr);
					showAlert("Valid New Account Submission", "New account saved successfully.");
					nameTF.clear();
					dp.setValue(LocalDate.now());
					balanceTF.clear();
					stage.setScene(homeScene);
				}
		});

		cancelBtn.setOnAction(e -> stage.setScene(homeScene));
		
		newAccScene = new Scene(pane, 600, 450);
		newAccScene.getStylesheets().add(CSS_FILE_PATH);
		return newAccScene;
	}

	/**
	 * Saves new account data to flat file.
	 *
	 * @param accountName
	 * @param openingDate
	 * @param balanceStr
	 */
	private void saveAccountData(String accountName, LocalDate openingDate, String balanceStr) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(ACCOUNTS_FILE_PATH, true))) {
			String line = accountName + "," + openingDate + "," + balanceStr;
			writer.write(line);
			writer.newLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Checks that desired new account name is not a duplicate.
	 *
	 * @param accountName
	 * @return
	 */
	private boolean isDuplicateAccount(String accountName) {
		try (BufferedReader reader = new BufferedReader(new FileReader(ACCOUNTS_FILE_PATH))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] parts = line.split(",");
				if (parts[0].trim().equalsIgnoreCase(accountName)) {
					return true;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Checks that desired new account balance is a valid number.
	 * @param str
	 * @return
	 */
	private boolean isDouble(String str) {
		try {
			Double.parseDouble(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	/**
	 * Prompts user with reason for failed submission of new account.
	 *
	 * @param title
	 * @param message
	 */
	private void showAlert(String title, String message) {
		Alert alert = new Alert(Alert.AlertType.WARNING);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}
	
    public static void main(String[] args) {
        launch(args);
    }

}
