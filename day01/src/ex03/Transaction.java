import java.util.UUID;

public class Transaction {
	
	enum transferType {
        DEBIT,
        CREDIT
    }
	
	private UUID			id;
	private User			recipient;
	private User			sender;
	private transferType	category;
	private Integer			amount;
	
	Transaction(UUID id, User recipient, User sender,
				transferType category, Integer amount) {
		this.id = id;
		this.recipient = recipient;
		this.sender = sender;
		this.category = category;
		this.amount = amount;
	}
	
	public void setId(UUID id) {
		this.id = id;		
	}

	public void setRecipient(User user) {
		recipient = user;
	}

	public void setSender(User user) {
		sender = user;
	}

	public void setCategory(transferType category) {
		this.category = category;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public UUID getId() {
		return (id);
	}

	public User getRecipient() {
		return (recipient);
	}

	public User getSender() {
		return (sender);
	}

	public transferType getCategory() {
		return (category);
	}

	public Integer getAmount() {
		return (amount);
	}

	public void doTransfer() {
		if (sender.getBalance() >= this.amount) {
			sender.setBalance(amount * -1 + sender.getBalance());
			recipient.setBalance(amount + recipient.getBalance());
		} else {
			System.err.println("Can't send money. Not enough balance.");
		}
	}

	@Override
	public String toString() {
		return ("Transaction{" +
				"identifier=" + id +
				", sender=" + sender +
				", recipient=" + recipient +
				", transferCategory=" + category +
				", transferAmount=" + amount +
				'}');
	}
}
