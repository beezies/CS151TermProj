package entities;

import java.time.LocalDate;

public class Transaction {
	private Account account;
	private TransactionType type;
	private LocalDate transDate;
	private String desc;
	private Double amount;
	
	public Transaction(Account account, TransactionType type, LocalDate transDate, String desc, Double amount)
	{
		this.account = account;
		this.type = type;
		this.transDate = transDate;
		this.desc = desc;
		this.amount = amount;
	}
	
}
