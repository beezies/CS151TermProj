package entities;


public class ScheduledTransaction implements TransactionInt<ScheduledTransaction> {

	private long ID;
	private Account account;
	private TransactionType type;
	private String frequency;
	private String name;
	private int day;
	private double amount;
	
	
	public ScheduledTransaction(long ID, Account account, TransactionType type, String frequency, String name, 
			int day, double amount) {
		this(account, type, frequency, name, day, amount);
		this.ID = ID;
	}
	public ScheduledTransaction(Account account, TransactionType type, String frequency, String name, 
			int day, double amount) {
		this.ID = this.hashCode();
		this.account = account;
		this.type = type;
		this.frequency = frequency;
		this.name = name;
		this.day = day;
		this.amount = amount;
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
	public String getFrequency() {
		return frequency;
	}
	public String getName() {
		return name;
	}
	public int getDay() {
		return day;
	}
	
	public Double getAmount() {
		return amount;
	}
	
	public String toString() {
		return name;
	}

	@Override
	public int compareTo(ScheduledTransaction t) {
		return day - t.getDay();
	}

}
