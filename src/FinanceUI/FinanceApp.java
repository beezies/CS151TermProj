package FinanceUI;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

import javafx.application.Application;
import javafx.geometry.Insets;
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
	private static final String FILE_PATH = "accounts.csv";

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
	
	/**
	 * Returns scene for new account page.
	 * 
	 * @param stage
	 * @returnn New account scene
	 */
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
					stage.setScene(homeScene);
				}

				nameTF.clear();
				dp.setValue(LocalDate.now());
				balanceTF.clear();

				stage.setScene(homeScene);
		});

		cancelBtn.setOnAction(e -> stage.setScene(homeScene));
		return new Scene(pane, 450, 450);
	}

	/**
	 * Saves new account data to flat file.
	 *
	 * @param accountName
	 * @param openingDate
	 * @param balanceStr
	 */
	private void saveAccountData(String accountName, LocalDate openingDate, String balanceStr) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
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
		try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
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
