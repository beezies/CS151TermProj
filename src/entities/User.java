package entities;

import java.time.LocalDate;
import java.util.ArrayList;

public class User {
	
	FileIOHandler io = new FileIOHandler();
	
	private ArrayList<Account> accounts;
//	private ArrayList<Transactions> transactions;
	
	public User(ArrayList<Account> accounts) {
		this.accounts = accounts;
	}

	public ArrayList<Account> getAccounts() {
		return accounts;
	}

	public void addAccounts(ArrayList<Account> accounts) {
		for (Account a : accounts) {
			this.accounts.add(a);
		}
	}
	
	public void addAccount(Account account) {
		this.accounts.add(account);
	}
	
	public void addAccount(String name, LocalDate date, double balance) {
		Account account = new Account(name, date, balance);
		this.accounts.add(account);
	}

}
