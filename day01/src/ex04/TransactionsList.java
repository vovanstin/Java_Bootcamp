import java.util.UUID;

public interface TransactionsList {
	
	void addTransaction(Transaction transaction);
	
	void removeTransaction(UUID id) throws TransactionNotFoundException;

	Transaction[] toArray();
}
