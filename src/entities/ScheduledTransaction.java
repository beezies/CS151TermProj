package entities;


public class ScheduledTransaction implements Comparable<ScheduledTransaction> {

	private Account account;
	private TransactionType type;
	private String frequency;
	private String name;
	private int day;
	private double amount;
	
	
	public ScheduledTransaction(Account account, TransactionType type, String frequency, String name, 
			int day, double amount) {
		this.account = account;
		this.type = type;
		this.frequency = frequency;
		this.name = name;
		this.day = day;
		this.amount = amount;
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
	public double getAmount() {
		return amount;
	}
	@Override
	public int compareTo(ScheduledTransaction schedule)
	{
		return schedule.getName().compareTo(name);
	}

}
