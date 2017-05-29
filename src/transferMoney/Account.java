package transferMoney;

public class Account{

	private static int counter = 0;
	private static final DollarAmount DEFAULT_BALANCE = new DollarAmount(100000);
	private final int account = counter++;
	private DollarAmount balance;
	
	public Account() {
	
		this(DEFAULT_BALANCE);
	}
	
	public Account(DollarAmount balance) {
		
		this.balance = balance;
	}

	public int getAccount() {
		return account;
	}

	public DollarAmount getBalance() {
		return balance;
	}

	public void debit(DollarAmount amount) {
		
		balance.setValue(balance.getValue() - amount.getValue());
	}
	
	public void credit(DollarAmount amount) {
		
		balance.setValue(balance.getValue() + amount.getValue());
	}
}
