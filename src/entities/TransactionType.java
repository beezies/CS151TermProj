package entities;

public class TransactionType {
	private String name;
	
	public TransactionType(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	@Override
	public String toString() {
		return name;
	}
	
}
