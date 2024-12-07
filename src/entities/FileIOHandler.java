package entities;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
//import java.util.Collections;

public class FileIOHandler {
	
	private static final String ACCOUNTS_FILE_PATH = "src/data_files/accounts.csv";
	private static final String TRANSACTIONTYPES_FILE_PATH = "src/data_files/transactionTypes.csv";
	private static final String TRANSACTIONS_FILE_PATH = "src/data_files/transactions.csv";
	private static final String SCHEDULEDTRANSACTIONS_FILE_PATH = "src/data_files/scheduledTransactions.csv";
	private static final String TEMP_FILE_PATH = "src/data_files/tmp.csv";
	
	/**
	 * 
	 * @return All accounts on file
	 */
	public ArrayList<String[]> loadAccounts() {
		ArrayList<String[]> accounts = new ArrayList<String[]>();
		
		try (BufferedReader reader = new BufferedReader(new FileReader(ACCOUNTS_FILE_PATH))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] parts = line.split(",");
				String name = parts[0].trim();
				String date = parts[1].trim();
				String balance = parts[2].trim();
//				LocalDate date = LocalDate.parse(parts[1].trim());
//				double balance = Double.valueOf(parts[2].trim());
//				Account acc = new Account(name, date, balance);
				String[] acc = {name, date, balance};
				accounts.add(acc);
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
//		Collections.sort(accounts);
		return accounts;
	}
	
	/**
	 * Creates an account from the data given and adds to the end of the accounts file
	 * 
	 * @param name
	 * @param date
	 * @param balance
	 */
	public void writeAccount(String name, LocalDate date, double balance) {
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
	public boolean isDuplicateAccount(String accountName) {
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
	
	public ArrayList<String> loadTransTypes() {
		ArrayList<String> transTypesS = new ArrayList<String>();
		
		try (BufferedReader reader = new BufferedReader(new FileReader(TRANSACTIONTYPES_FILE_PATH))) {
			String line;
			while ((line = reader.readLine()) != null) {
//				TransactionType transType = new TransactionType(line);
				transTypesS.add(line.trim());
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return transTypesS;
	}
	
	public void writeTransType(TransactionType type) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(TRANSACTIONTYPES_FILE_PATH, true))) {
			String line = type.getName();
			writer.write(line);
			writer.newLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void writeTransType(String name) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(TRANSACTIONTYPES_FILE_PATH, true))) {
			String line = name;
			writer.write(line);
			writer.newLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public boolean isDuplicateTransType(String typeName) {
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
	
	public void writeTransaction(Transaction trans) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(TRANSACTIONS_FILE_PATH, true))) {
			String line = trans.getAccount() + "," + trans.getType() + "," + trans.getDate() + "," + 
					trans.getDesc() + "," + trans.getAmount() + "," + trans.getID();
			writer.write(line);
			writer.newLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<String[]> loadTransactions() {
		ArrayList<String[]> transactions = new ArrayList<String[]>();
		ArrayList<String[]> accs = loadAccounts();
		ArrayList<String> types = loadTransTypes();
		
		try (BufferedReader reader = new BufferedReader(new FileReader(TRANSACTIONS_FILE_PATH))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] parts = line.split(",");
				String accName = parts[0].trim();
				String acc = "";
				for (String[] a:accs) {
					if (a[0].equals(accName)) acc = a[0];
				}
				String typeString = parts[1].trim();
				String type = "";
				for (String s : types) {
					if (s.equals(typeString)) type = s;
				}
//				LocalDate date = LocalDate.parse(parts[2].trim());
				String date = parts[2].trim();
				String desc = parts[3].trim();
				String amt = parts[4].trim();
				String ID = parts[5].trim();
				String[] t = {ID, acc, type, date, desc, amt};
				transactions.add(t);
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return transactions;
	}
	public void writeScheduledTransaction(String acc, String type, String freq, String name, int day, Double amt, Long ID) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(SCHEDULEDTRANSACTIONS_FILE_PATH, true))) {
			String line = acc + "," + type + "," + freq + "," + name
					+ "," + day + "," + amt + "," + ID;
			writer.write(line);
			writer.newLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean isDuplicateSchedule(String schedName) {
		try (BufferedReader reader = new BufferedReader(new FileReader(SCHEDULEDTRANSACTIONS_FILE_PATH))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] parts = line.split(",");
				if (parts[3].trim().equalsIgnoreCase(schedName)) {
					return true;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public ArrayList<String[]> loadScheduledTransactions() {
		ArrayList<String[]> schedTransactions = new ArrayList<String[]>();
		
		try (BufferedReader reader = new BufferedReader(new FileReader(SCHEDULEDTRANSACTIONS_FILE_PATH))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] parts = line.split(",");
				String accName = parts[0].trim();
				String type = parts[1].trim();
				String frequency = parts[2].trim();
				String name = parts[3].trim();
				String day = parts[4].trim();
				String amt = parts[5].trim();
				String ID = parts[6].trim();
				String[] t = {accName, type, frequency, name, day, amt, ID};
				schedTransactions.add(t);
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return schedTransactions;
	}

	/**
	 * 
	 * @param trans Transaction to delete
	 */
	public void deleteTrans(Transaction trans) {
		File file = new File(TRANSACTIONS_FILE_PATH);
		File tmp = new File(TEMP_FILE_PATH);
		try {
			FileReader r = new FileReader(file);
			FileWriter w = new FileWriter(TEMP_FILE_PATH, true);
			BufferedReader reader = new BufferedReader(r);
			BufferedWriter writer = new BufferedWriter(w);
			String line;
			while ((line = reader.readLine()) != null) {
				String[] parts = line.split(",");
				Long id = Long.valueOf(parts[5].trim());
				if (id.equals(trans.getID())) {
					continue;
				} else {
					writer.write(line);
					writer.newLine();
				}
			} 
			reader.close();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (!file.delete()) {
            System.out.println("Could not delete original file.");
            return;
        }
        if (!tmp.renameTo(file)) {
            System.out.println("Could not rename temp file.");
        }
	}

	/**
	 * 
	 * @param trans Scheduled transaction to delete
	 */
	public void deleteSchedTrans(ScheduledTransaction trans) {
		File file = new File(SCHEDULEDTRANSACTIONS_FILE_PATH);
		File tmp = new File(TEMP_FILE_PATH);
		try {
			FileReader r = new FileReader(file);
			FileWriter w = new FileWriter(TEMP_FILE_PATH, true);
			BufferedReader reader = new BufferedReader(r);
			BufferedWriter writer = new BufferedWriter(w);
			String line;
			while ((line = reader.readLine()) != null) {
				String[] parts = line.split(",");
				Long id = Long.valueOf(parts[6].trim());
				if (id.equals(trans.getID())) {
					continue;
				} else {
					writer.write(line);
					writer.newLine();
				}
			} 
			reader.close();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (!file.delete()) {
            System.out.println("Could not delete original file.");
            return;
        }
        if (!tmp.renameTo(file)) {
            System.out.println("Could not rename temp file.");
        }
	}
}
