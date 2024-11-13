package entities;

import java.time.LocalDate;

public class Transaction implements Comparable<Transaction>{
	private Account account;
	private TransactionType type;
	private LocalDate date;
	private String desc;
	private Double amount;
	
	public Transaction(Account account, TransactionType type, LocalDate transDate, String desc, Double amount)
	{
		this.account = account;
		this.type = type;
		this.date = transDate;
		this.desc = desc;
		this.amount = amount;
	}
	
	@Override
	public int compareTo(Transaction t) {
		return t.getDate().compareTo(this.date);
	}

	public Account getAccount() {
		return account;
	}

	public TransactionType getType() {
		return type;
	}

	public String getDesc() {
		return desc;
	}

	public Double getAmount() {
		return amount;
	}
	
	public LocalDate getDate() {
		return date;
	}
}
