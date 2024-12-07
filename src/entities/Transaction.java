package entities;

import java.time.LocalDate;

public class Transaction implements TransactionInt<Transaction>{
	
	private long ID;
	private Account account;
	private TransactionType type;
	private LocalDate date;
	private String desc;
	private Double amount;
	
	public Transaction(Account account, TransactionType type, LocalDate transDate, String desc, Double amount)
	{
		this.ID = hashCode();
		this.account = account;
		this.type = type;
		this.date = transDate;
		this.desc = desc;
		this.amount = amount;
	}
	
	public Transaction(long ID, Account acc, TransactionType type2, LocalDate date2, String desc2, double amt) {
		this(acc, type2, date2, desc2, amt);
		this.ID = ID;
	}

	public long getID() {
		return ID;
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
	
	public String toString() {
		return desc;
	}

	@Override
	public int compareTo(Transaction o) {
		return o.getDate().compareTo(this.date);
	}
}
