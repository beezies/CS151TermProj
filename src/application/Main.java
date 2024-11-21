package application;

import java.time.LocalDate;
import java.util.ArrayList;

import entities.Account;
import entities.FileIOHandler;
import entities.Transaction;
import entities.TransactionType;
import entities.User;
import entities.ScheduledTransaction;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

@SuppressWarnings("deprecation")
public class Main extends Application{
	
	Stage stage;
	
	Scene homeScene;
	Scene newAccScene;
	Scene newTransTypeScene;
	Scene newTransScene;
	Scene transScene;
	Scene newSchedTransScene;
	Scene schedTransScene;
	
	User user;
	LocalDate date = LocalDate.now();
	
	private static final String CSS_FILE_PATH = "application/style/financeStyle.css";

	@Override
    public void start(Stage stage) {
		
		this.stage = stage;
				
		homeScene = getHomeScene();
		newAccScene = getNewAccScene();
		newTransTypeScene = getNewTransTypeScene();
		newTransScene = getEditTransScene(null);
		transScene = getTransScene();
		newSchedTransScene = getEditSchedTransScene(null);
		schedTransScene = getSchedTransScene();
        stage.setScene(homeScene);
        stage.show();
		
    }

	/**
	 * Returns scene for home page (useful for reloading home scene after data changes)
	 * 
	 * @return Home scene
	 */
	@SuppressWarnings("unchecked")
	public Scene getHomeScene() {
		BorderPane pane = new BorderPane();
		HBox top = new HBox();
		VBox center = new VBox();
		VBox left = new VBox();
		VBox right = new VBox();
		
		Label title = new Label("Money Money Money");
		
		Button newAccBtn = new Button("Add New Account");
		Button transBtn = new Button("View Transactions");
		Button schedTransBtn = new Button("View Scheduled Transactions");
		Button newTransBtn = new Button("Add Transaction");
		Button newSchedTransBtn = new Button("Add Scheduled Transaction");
		Button newTransTypeBtn = new Button("Add new Transaction Type");
		
		TableView<Account> accTable = new TableView<Account>();
		TableColumn<Account, String> label = new TableColumn<>("Accounts");
		TableColumn<Account, String> nameCol = new TableColumn<>("Name");
		nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
	    TableColumn<Account, LocalDate> dateCol = new TableColumn<>("Opening Date");
	    dateCol.setCellValueFactory(new PropertyValueFactory<>("startDate"));
	    TableColumn<Account, Double> balCol = new TableColumn<>("Balance");
	    balCol.setCellValueFactory(new PropertyValueFactory<>("balance"));
	    label.getColumns().addAll(nameCol, dateCol, balCol);
	    accTable.getColumns().add(label);
	    accTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
	    
		for (Account a : FileIOHandler.loadAccounts()) {
			accTable.getItems().add(a);
		}
		
		top.getChildren().add(title);
		pane.setTop(top);
		pane.setRight(right);
		pane.setLeft(left);
		center.getChildren().add(accTable);
		center.getChildren().add(newAccBtn);
		center.getChildren().add(transBtn);
		center.getChildren().add(schedTransBtn);
		center.getChildren().add(newTransBtn);
		center.getChildren().add(newSchedTransBtn);
		center.getChildren().add(newTransTypeBtn);
		center.setSpacing(10);
		pane.setCenter(center);
		
		newAccBtn.setOnAction(e -> stage.setScene(newAccScene));
		transBtn.setOnAction(e -> stage.setScene(getTransScene()));
		newTransBtn.setOnAction(e -> stage.setScene(getEditTransScene(null)));
		newTransTypeBtn.setOnAction(e -> stage.setScene(newTransTypeScene));
		newSchedTransBtn.setOnAction(e -> stage.setScene(getEditSchedTransScene(null)));
		schedTransBtn.setOnAction(e -> stage.setScene(getSchedTransScene()));
		
		homeScene = new Scene(pane, 800, 700);
		homeScene.getStylesheets().add(CSS_FILE_PATH);
		return homeScene;
	}

	/**
	 * Returns scene for new account page.
	 * 
	 * @return New account scene
	 */
	public Scene getNewAccScene() {
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
				String accountName = nameTF.getText().trim();
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
					stage.setScene(getHomeScene());
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
	 * @return New transaction type scene
	 */
	public Scene getNewTransTypeScene(){
		VBox pane = new VBox();
		HBox title = new HBox();
		HBox buttonPane = new HBox();
		
		Label lbl = new Label("Define New Transaction Type");
		TextField typeTF = new TextField();
		Button typeBtn = new Button("Add Transaction Type");
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
				stage.setScene(getHomeScene());
			}
		});
		
		cancelBtn.setOnAction(e -> stage.setScene(homeScene));
		
		newTransTypeScene = new Scene(pane, 600, 450);
		newTransTypeScene.getStylesheets().add(CSS_FILE_PATH);
		return newTransTypeScene;
	}
	
	/**
	 * Returns scene to add a new transaction.
	 * @param trans If provided, the transaction to edit. If null, will create a new transaction.
	 * 
	 * @return
	 */
	private Scene getEditTransScene(Transaction trans) {
		Boolean editMode = (trans != null);
		
		VBox pane = new VBox();
		HBox title = new HBox();
		HBox buttonPane = new HBox();
		
		Label titleLbl = new Label("Add New Transaction");
		Label chooseAcc = new Label("Choose account that made this transaction");
		ChoiceBox<Account> accounts = new ChoiceBox<Account>();
		Label chooseType = new Label("Choose transaction type");
		ChoiceBox<TransactionType> types = new ChoiceBox<TransactionType>();
		Label chooseDate = new Label("Date of transactions");
		DatePicker dp = new DatePicker(date);
		Label descLbl = new Label("Short Description of Transaction");
		TextField descTF = new TextField();
		Label payLbl = new Label("Payment Amount");
		TextField payTF = new TextField();
		Label depLbl = new Label("Deposit Amount");
		TextField depTF = new TextField();
		Button transBtn = new Button("Add Transaction");
		Button cancelBtn = new Button("Cancel");
		Button delBtn = new Button("Delete");
		delBtn.setStyle("""
				-fx-base: rgb(233, 62, 22);
				""");
		
		accounts.getItems().addAll(FileIOHandler.loadAccounts());
		types.getItems().addAll(FileIOHandler.loadTransTypes());
		
		if (editMode) {
			System.out.println(trans);
			titleLbl.setText("Edit Transaction");
			accounts.setValue(trans.getAccount());
			types.setValue(trans.getType());
			dp.setValue(trans.getDate());
			descTF.setText(trans.getDesc());
			if (trans.getAmount() <= 0) {
				payTF.setText(Double.toString(trans.getAmount()));
			} else { 
				depTF.setText(Double.toString(trans.getAmount())); 
			}
			transBtn.setText("Save");
		} else {
			accounts.setValue(accounts.getItems().getFirst());
			types.setValue(types.getItems().getFirst());
		}
		
		title.getChildren().add(titleLbl);
		buttonPane.getChildren().addAll(transBtn, cancelBtn);
		if (editMode) buttonPane.getChildren().add(delBtn);
		pane.getChildren().addAll(title, chooseAcc, accounts, chooseType, types, chooseDate, dp, descLbl, descTF,
				payLbl, payTF, depLbl, depTF, buttonPane);
		
		transBtn.setOnAction(e ->{
			Account account = accounts.getValue();
			TransactionType type = types.getValue();
			LocalDate transDate = dp.getValue();
			String desc = descTF.getText();
			String payment = payTF.getText();
			String deposit = depTF.getText();
			boolean payIsDouble = isDouble(payment);
			boolean depIsDouble = isDouble(deposit);
			if(account == null)
				showAlert("Empty Account", "Must choose an account");
			else if(type == null)
				showAlert("Empty Transaction Type", "Must choose a transaction type");
			else if(transDate.isAfter(LocalDate.now()))
				showAlert("Invalid Date", "Transaction date cannot be in the future");
			else if(desc == "")
				showAlert("Invalid Description", "Must enter a description");
			else if(!payIsDouble && !depIsDouble)
				showAlert("Invalid Payment/Deposit", "Must enter a valid payment or deposit amount");
			else if(payIsDouble && depIsDouble)
				showAlert("Invalid Payment and Deposit", "Cannot enter both a payment and a deposit amount");
			else
			{
				Double amount;
				if (payIsDouble)
					amount = -Double.parseDouble(payment);
				else
					amount = Double.parseDouble(deposit);
				if (editMode) FileIOHandler.deleteTrans(trans);
				FileIOHandler.writeTransaction(account, type, transDate, desc, amount);
				showAlert("Valid New Transaction Sumbission", "Transaction saved successfully");
				dp.setValue(LocalDate.now());
				descTF.clear();
				payTF.clear();
				depTF.clear();
				stage.setScene(getHomeScene());
			}	
		});
		if (editMode) {
			cancelBtn.setOnAction(e -> stage.setScene(transScene));
			delBtn.setOnAction(e -> {
				FileIOHandler.deleteTrans(trans);
				stage.setScene(getTransScene());
			});
		} else {
			cancelBtn.setOnAction(e -> stage.setScene(homeScene));
		}
		newTransScene = new Scene(pane, 600, 600);
		newTransScene.getStylesheets().add(CSS_FILE_PATH);
		return newTransScene;
	}
	
	/**
	 * Returns scene to show all transactions.
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Scene getTransScene() {
		BorderPane pane = new BorderPane();
		HBox top = new HBox();
		VBox center = new VBox();
		VBox left = new VBox();
		VBox right = new VBox();
		
		Label title = new Label("Transactions");
		TextField filterField = new TextField();
		filterField.setPromptText("Filter transactions...");
		
		ArrayList<Transaction> allTransactions = FileIOHandler.loadTransactions();
		FilteredList<Transaction> filteredTransactions = new FilteredList<>(FXCollections.observableArrayList(allTransactions), p -> true);
		
		TableView<Transaction> transTable = new TableView<Transaction>(filteredTransactions);
		TableColumn<Transaction, Account> accCol = new TableColumn<>("Account");
		accCol.setCellValueFactory(new PropertyValueFactory<>("account"));
	    TableColumn<Transaction, TransactionType> typeCol = new TableColumn<>("Type");
	    typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
	    TableColumn<Transaction, Double> amtCol = new TableColumn<>("Amount");
	    amtCol.setCellValueFactory(new PropertyValueFactory<>("amount"));
	    TableColumn<Transaction, LocalDate> dateCol = new TableColumn<>("Date");
	    dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
	    TableColumn<Transaction, String> descCol = new TableColumn<>("Description");
	    descCol.setCellValueFactory(new PropertyValueFactory<>("desc"));
	    
	    transTable.getColumns().addAll(accCol, typeCol, amtCol, dateCol, descCol);
	    transTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
	    
	    filterField.textProperty().addListener((observable, oldValue, newValue) -> {
	        filteredTransactions.setPredicate(transaction -> {
	            if (newValue == null || newValue.isEmpty()) {
	                return true;
	            }
	            String lowerCaseFilter = newValue.toLowerCase();
	            if (transaction.getDesc().toString().toLowerCase().contains(lowerCaseFilter)) {
	                return true;
	            } 
	            else return false; 
	        });
	    });
	    
	    transTable.setOnMouseClicked((MouseEvent event) -> {
	        if (event.getButton().equals(MouseButton.PRIMARY)) {
	        	if (event.getClickCount() == 2) {
	            int index = transTable.getSelectionModel().getSelectedIndex();
	            Transaction t = transTable.getItems().get(index);
	            
	            stage.setScene(getEditTransScene(t));
	            System.out.println(t);
	        	}
	        }
	    });
		
		Button backBtn = new Button("Back");
		top.getChildren().add(title);
		pane.setTop(top);
		pane.setRight(right);
		pane.setLeft(left);
		center.getChildren().add(filterField);
		center.getChildren().add(transTable);
		center.getChildren().add(backBtn);
		center.setSpacing(10);
		pane.setCenter(center);
		
		backBtn.setOnAction(e -> stage.setScene(homeScene));
		
		transScene = new Scene(pane, 700, 600);
		transScene.getStylesheets().add(CSS_FILE_PATH);
		return transScene;
	}
	
	/**
	 * Scene for adding a scheduled transaction.
	 * 
	 * @param trans If provided, the transaction to edit. If null, create a new transaction.
	 * @return
	 */
	private Scene getEditSchedTransScene(ScheduledTransaction trans) {
		Boolean editMode = (trans != null);
		
		VBox pane = new VBox();
		HBox title = new HBox();
		HBox buttonPane = new HBox();
		
		Label titleLbl = new Label("Schedule Transaction");
		Label chooseAcc = new Label("Choose account that will make this transaction");
		ChoiceBox<Account> accounts = new ChoiceBox<Account>();
		Label chooseType = new Label("Choose transaction type");
		ChoiceBox<TransactionType> types = new ChoiceBox<TransactionType>();
		Label chooseFreq = new Label("Choose Frequency of Transaction");
		ChoiceBox<String> frequency = new ChoiceBox<String>();
		Label chooseName = new Label("Name Transaction");
		TextField nameTF = new TextField();
		Label chooseDay = new Label("Day Transaction is Scheduled");
		TextField dayTF = new TextField();
		Label payLbl = new Label("Payment Amount");
		TextField payTF = new TextField();
		Button schedTransBtn = new Button("Add Scheduled Transaction");
		Button cancelBtn = new Button("Cancel");
		Button delBtn = new Button("Delete");
		delBtn.setStyle("""
				-fx-base: rgb(233, 62, 22);
				""");
		
		accounts.getItems().addAll(FileIOHandler.loadAccounts());
		types.getItems().addAll(FileIOHandler.loadTransTypes());
		frequency.getItems().add("Monthly");
		
		if (editMode) {
			titleLbl.setText("Edit Scheduled Transaction");
			accounts.setValue(trans.getAccount());
			types.setValue(trans.getType());
			frequency.setValue(trans.getFrequency());
			nameTF.setText(trans.getName());
			dayTF.setText(Integer.toString(trans.getDay()));
			payTF.setText(Double.toString(trans.getAmount()));
			schedTransBtn.setText("Save");
		} else {
			accounts.setValue(accounts.getItems().getFirst());
			types.setValue(types.getItems().getFirst());
			frequency.setValue(frequency.getItems().getFirst());
		}
		
		title.getChildren().add(titleLbl);	
		buttonPane.getChildren().addAll(schedTransBtn, cancelBtn);
		if (editMode) buttonPane.getChildren().add(delBtn);
		pane.getChildren().addAll(title, chooseAcc, accounts, chooseType, types, chooseFreq, frequency,
				chooseDay,dayTF, chooseName, nameTF,payLbl, payTF, buttonPane);
		
		schedTransBtn.setOnAction(e ->{
			Account account = accounts.getValue();
			TransactionType type = types.getValue();
			String freq = frequency.getValue();
			String name = nameTF.getText();
			String dayText = dayTF.getText();
			String paymentText = payTF.getText();
			boolean payIsDouble = isDouble(paymentText);
			if(account == null)
				showAlert("Empty Account", "Must choose an account");
			else if(type == null)
				showAlert("Empty Transaction Type", "Must choose a transaction type");
			else if(dayText == "")
				showAlert("Invalid Description", "Must enter a day for the transaction");
			else if(name == "")
				showAlert("Invalid Name", "Must enter transaction name");
			else if(FileIOHandler.isDuplicateSchedule(name))
				showAlert("Invalid Name", "Must enter a unique name");
			else if(!payIsDouble)
				showAlert("Invalid Payment", "Must enter a valid payment amount");
			else
			{
				int day = Integer.parseInt(dayText);
				double payment = Double.parseDouble(paymentText);
				if (editMode) FileIOHandler.deleteSchedTrans(trans);
				FileIOHandler.writeScheduledTransaction(account, type, freq, name, day, payment);
				showAlert("Valid New Schedule Transaction Sumbission", "Scheduled Transaction saved successfully");
				nameTF.clear();
				dayTF.clear();
				payTF.clear();
				stage.setScene(getHomeScene());
			}	
		});
		if (editMode) {
			cancelBtn.setOnAction(e -> stage.setScene(schedTransScene));
			delBtn.setOnAction(e -> {
				System.out.println("Deleting..");
				FileIOHandler.deleteSchedTrans(trans);
				stage.setScene(getSchedTransScene());
			});
		} else {
			cancelBtn.setOnAction(e -> stage.setScene(homeScene));
		}
		
		newSchedTransScene = new Scene(pane, 600, 600);
		newSchedTransScene.getStylesheets().add(CSS_FILE_PATH);
		return newSchedTransScene;
	}
	
	@SuppressWarnings("unchecked")
	/**
	 * Scene for showing all scheduled transactions.
	 * 
	 * @return
	 */
	private Scene getSchedTransScene() {
		BorderPane pane = new BorderPane();
		HBox top = new HBox();
		VBox center = new VBox();
		VBox left = new VBox();
		VBox right = new VBox();
		
		Label title = new Label("Scheduled Transactions");
		TextField filter = new TextField();
		filter.setPromptText("Filter transactions...");
		
		ArrayList<ScheduledTransaction> transactions = FileIOHandler.loadScheduledTransactions();
		FilteredList<ScheduledTransaction> filteredTransactions = new FilteredList<>(FXCollections.observableArrayList(transactions), p -> true);
		
		TableView<ScheduledTransaction> transTable = new TableView<ScheduledTransaction>(filteredTransactions);
		TableColumn<ScheduledTransaction, String> nameCol = new TableColumn<>("Name");
		nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
		TableColumn<ScheduledTransaction, Account> accCol = new TableColumn<>("Account");
		accCol.setCellValueFactory(new PropertyValueFactory<>("account"));
	    TableColumn<ScheduledTransaction, TransactionType> typeCol = new TableColumn<>("Type");
	    typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
	    TableColumn<ScheduledTransaction, Double> amtCol = new TableColumn<>("Amount");
	    amtCol.setCellValueFactory(new PropertyValueFactory<>("amount"));
	    TableColumn<ScheduledTransaction, Integer> dateCol = new TableColumn<>("Day");
	    dateCol.setCellValueFactory(new PropertyValueFactory<>("day"));
	    TableColumn<ScheduledTransaction, String> freqCol = new TableColumn<>("Frequency");
	    freqCol.setCellValueFactory(new PropertyValueFactory<>("frequency"));
	    
	    transTable.getColumns().addAll(nameCol, accCol, typeCol, amtCol, dateCol, freqCol);
	    transTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
	    
	    filter.textProperty().addListener((observable, oldValue, newValue) -> {
	        filteredTransactions.setPredicate(transaction -> {
	            if (newValue == null || newValue.isEmpty()) {
	                return true;
	            }
	            String lowerCaseFilter = newValue.toLowerCase();
	            if (transaction.getName().toString().toLowerCase().contains(lowerCaseFilter)) {
	                return true;
	            } 
	            else return false; 
	        });
	    });
	    transTable.setOnMouseClicked((MouseEvent event) -> {
	        if (event.getButton().equals(MouseButton.PRIMARY)) {
	        	if (event.getClickCount() == 2) {
	            int index = transTable.getSelectionModel().getSelectedIndex();
	            ScheduledTransaction t = transTable.getItems().get(index);
	            stage.setScene(getEditSchedTransScene(t));
	            System.out.println(t);
	        	}
	        }
	    });
	    
		Button backBtn = new Button("Back");
		top.getChildren().add(title);
		pane.setTop(top);
		pane.setRight(right);
		pane.setLeft(left);
		center.getChildren().add(filter);
		center.getChildren().add(transTable);
		center.getChildren().add(backBtn);
		center.setSpacing(10);
		pane.setCenter(center);
		
		backBtn.setOnAction(e -> stage.setScene(homeScene));
		
		transScene = new Scene(pane, 800, 600);
		transScene.getStylesheets().add(CSS_FILE_PATH);
		return transScene;
	}
	
	/**
	 * Checks that desired new account balance is a valid number.
	 * 
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
	 * Prompts user with reason for failed submission of new account/transaction.
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
