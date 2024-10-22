package entities;

import java.time.LocalDate;

public class Account {
	
	private String name;
	private LocalDate startDate;
	private double balance;
	
	public Account(String name, LocalDate date, double startBalance) {
		this.setName(name);
		this.setStartDate(date);
		this.setBalance(startBalance);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}
	

}
