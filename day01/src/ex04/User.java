public class User {
	private Integer				id;
	private String				name;
	private Integer				balance;
	private TransactionsList	transactionsList = new TransactionsLinkedList();

	public User(String name, Integer balance) {
		this.name = name;
		this.id = UserIdsGenerator.getInstance().generateId();
		this.balance = balance;
	}

	public TransactionsList getTransactionsList() {
		return (transactionsList);
	}
	
	public Integer getId() {
		return (id);
	}

	public String getName() {
		return (name);
	}

	public Integer getBalance() {
		return (balance);
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean setBalance(Integer balance) {
		if (balance < 0) {
			return (false);
		}
		this.balance = balance;
		return (true);
	}

	@Override
	public String toString() {
		return "User{" +
				"identifier=" + id +
				", name='" + name + '\'' +
				", balance=" + balance +
				'}';
	}
}
