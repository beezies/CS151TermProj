package entities;


public interface TransactionInt<T extends TransactionInt<T>> extends Comparable<T>  {
	
	public long getID();
	
	public Account getAccount();
	
	public TransactionType getType();
	
	public Double getAmount();
	
}
