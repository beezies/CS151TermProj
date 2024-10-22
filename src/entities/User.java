package entities;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class User {
	
	private Account[] accounts;
//	private Transaction[] transactions;
	
	public User(Account[] accounts) {
		this.setAccounts(accounts);
	}

	public Account[] getAccounts() {
		return accounts;
	}

	public void setAccounts(Account[] accounts) {
		this.accounts = accounts;
	}

}
