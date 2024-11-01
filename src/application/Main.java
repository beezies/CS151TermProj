package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

import entities.Account;
import entities.FileIOHandler;
import entities.User;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Main extends Application{
	
	Scene homeScene;
	Scene newAccScene;
	Scene transTypeScene;
	
	User user;
	LocalDate date = LocalDate.now();
	
	private static final String CSS_FILE_PATH = "application/app_files/financeStyle.css";

	@Override
    public void start(Stage stage) {
				
		homeScene = getHomeScene(stage);
		newAccScene = getNewAccScene(stage);
		transTypeScene = getTransTypeScene(stage);
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
		Label blnk = new Label("        ");
		Label blnk2 = new Label("        ");
		Label blnk3 = new Label("        ");		
		
		Label accsLbl = new Label("Accounts");
		Button newAccBtn = new Button("Add New Account");
		Label transLbl = new Label("Transactions");
		Button transTypeBtn = new Button("Add new Transaction Type");
		
		TableView<Account> accTable = new TableView<Account>();
		accTable.setColumnResizePolicy((param) -> {
			double totalWidth = accTable.getWidth();
	        double equalWidth = totalWidth / 3;
	        for (TableColumn<?, ?> column : accTable.getColumns()) {
	        	column.setPrefWidth(equalWidth);
	        }
	            return true;
	        });
		TableColumn<Account, String> nameCol = new TableColumn<>("Name");
		nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
	    TableColumn<Account, LocalDate> dateCol = new TableColumn<>("Opening Date");
	    dateCol.setCellValueFactory(new PropertyValueFactory<>("startDate"));
	    TableColumn<Account, Double> balCol = new TableColumn<>("Balance");
	    balCol.setCellValueFactory(new PropertyValueFactory<>("balance"));
		accTable.getColumns().add(nameCol);
		accTable.getColumns().add(dateCol);
		accTable.getColumns().add(balCol);
		for (Account a : FileIOHandler.loadAccounts()) {
			accTable.getItems().add(a);
			System.out.println(a.getName());
		}
		
		top.getChildren().add(title);
		right.getChildren().add(blnk);
		left.getChildren().add(blnk2);
		pane.setTop(top);
		pane.setRight(right);
		pane.setLeft(left);
		center.getChildren().add(accsLbl);
		center.getChildren().add(accTable);
		center.getChildren().add(newAccBtn);
		center.getChildren().add(transLbl);
		center.getChildren().add(transTypeBtn);
		center.getChildren().add(blnk3);
		pane.setCenter(center);
		
		newAccBtn.setOnAction(e -> stage.setScene(newAccScene));
		transTypeBtn.setOnAction(e -> stage.setScene(transTypeScene));
		homeScene = new Scene(pane, 650, 600);
		homeScene.getStylesheets().add(CSS_FILE_PATH);
		return homeScene;
	}

	/**
	 * Returns scene for new account page.
	 * 
	 * @param stage
	 * @return New account scene
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
				if (accountName.equals("")) {
					showAlert("Invalid Account Name", "Must enter a valid account name.");
				} else if (FileIOHandler.isDuplicateAccount(accountName)) {
					showAlert("Duplicate Account", "An account with this name already exists.");
				} else if (openingDate.isAfter(LocalDate.now())) {
					showAlert("Invalid Date", "Opening date cannot be in the future.");
				} else if (!isDouble(balanceStr)) {
					showAlert("Invalid Balance", "Starting balance must be a valid number.");
				} else {
					FileIOHandler.writeAccount(accountName, openingDate, Double.valueOf(balanceStr));
					showAlert("Valid New Account Submission", "New account saved successfully.");
					nameTF.clear();
					dp.setValue(LocalDate.now());
					balanceTF.clear();
					stage.setScene(getHomeScene(stage));
				}
		});

		cancelBtn.setOnAction(e -> stage.setScene(homeScene));
		
		newAccScene = new Scene(pane, 600, 450);
		newAccScene.getStylesheets().add(CSS_FILE_PATH);
		return newAccScene;
	}
	
	/**
	 * Returns scene for new transaction type page.
	 * 
	 * @param stage
	 * @return New transaction type scene
	 */
	public Scene getTransTypeScene(Stage stage){
		VBox pane = new VBox();
		HBox title = new HBox();
		HBox buttonPane = new HBox();
		
		Label lbl = new Label("Define New Transaction Type");
		TextField typeTF = new TextField();
		Button typeBtn = new Button("add Transaction Type");
		Button cancelBtn = new Button("Cancel");
		
		title.getChildren().add(lbl);
		buttonPane.getChildren().addAll(typeBtn, cancelBtn);
		pane.getChildren().addAll(title, typeTF, buttonPane);
		pane.setSpacing(100);
		
		typeBtn.setOnAction(e ->{
			String typeName = typeTF.getText();
			if(typeName.equals(""))
				showAlert("Invalid Transaction Type Name", "Must enter a valid transaction type name");
			else if(FileIOHandler.isDuplicateTransType(typeName))
				showAlert("Duplicate Transaction Type Name", "A transaction type with this name already exists");
			else
			{
				FileIOHandler.writeTransType(typeName);
				showAlert("Valid New Transaction Type Submission", "Transaction type saved successfully");
				typeTF.clear();
				stage.setScene(getHomeScene(stage));
			}
		});
		
		cancelBtn.setOnAction(e -> stage.setScene(homeScene));
		
		transTypeScene = new Scene(pane, 600, 450);
		transTypeScene.getStylesheets().add(CSS_FILE_PATH);
		return transTypeScene;
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
