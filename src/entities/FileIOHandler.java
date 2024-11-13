package entities;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;

public class FileIOHandler {
	
	private static final String ACCOUNTS_FILE_PATH = "src/data_files/accounts.csv";
	private static final String TRANSACTIONTYPES_FILE_PATH = "src/data_files/transactionTypes.csv";
	private static final String TRANSACTIONS = "src/data_files/transactions.csv";
	
	/**
	 * 
	 * @return All accounts on file
	 */
	public static ArrayList<Account> loadAccounts() {
		ArrayList<Account> accounts = new ArrayList<Account>();
		
		try (BufferedReader reader = new BufferedReader(new FileReader(ACCOUNTS_FILE_PATH))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] parts = line.split(",");
				String name = parts[0].trim();
				LocalDate date = LocalDate.parse(parts[1].trim());
				double balance = Double.valueOf(parts[2].trim());
				Account acc = new Account(name, date, balance);
				accounts.add(acc);
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		Collections.sort(accounts);
		return accounts;
	}
	
//	public static Transaction[] loadTransactions() {
//		return new Transaction[0];
//	}
	
	/**
	 * Adds an account to the end of the accounts file
	 * 
	 * @param acc
	 */
	public static void writeAccount(Account acc) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(ACCOUNTS_FILE_PATH, true))) {
			String line = acc.getName() + "," + acc.getStartDate() + "," + acc.getBalance();
			writer.write(line);
			writer.newLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Creates an account from the data given and adds to the end of the accounts file
	 * 
	 * @param name
	 * @param date
	 * @param balance
	 */
	public static void writeAccount(String name, LocalDate date, double balance) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(ACCOUNTS_FILE_PATH, true))) {
			String line = name + "," + date + "," + balance;
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
	 * @return Whether the account is a duplicate
	 */
	public static boolean isDuplicateAccount(String accountName) {
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
	
	public static ArrayList<TransactionType> loadTransTypes() {
		ArrayList<TransactionType> transTypes = new ArrayList<TransactionType>();
		
		try (BufferedReader reader = new BufferedReader(new FileReader(TRANSACTIONTYPES_FILE_PATH))) {
			String line;
			while ((line = reader.readLine()) != null) {
				TransactionType transType = new TransactionType(line);
				transTypes.add(transType);
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return transTypes;
	}
	
	public static void writeTransType(TransactionType type) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(TRANSACTIONTYPES_FILE_PATH, true))) {
			String line = type.getName();
			writer.write(line);
			writer.newLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void writeTransType(String name) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(TRANSACTIONTYPES_FILE_PATH, true))) {
			String line = name;
			writer.write(line);
			writer.newLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static boolean isDuplicateTransType(String typeName) {
		try (BufferedReader reader = new BufferedReader(new FileReader(TRANSACTIONTYPES_FILE_PATH))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] parts = line.split(",");
				if (parts[0].trim().equalsIgnoreCase(typeName)) {
					return true;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	public static void writeTransaction(Account account, TransactionType type, LocalDate date, String desc, Double amount) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(TRANSACTIONS, true))) {
			String line = account.toString() + "," + type.toString() + "," + date + "," + desc + "," + amount;
			writer.write(line);
			writer.newLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
