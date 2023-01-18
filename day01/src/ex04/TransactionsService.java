import java.util.UUID;

public class TransactionsService {
	
	private UsersList users = new UsersArrayList();

	public void addUser(String name, Integer balance) {
		users.addUser(new User(name, balance));
	}

	public Integer getUserBalance(Integer userId) {
		return (users.getUserById(userId).getBalance());
	}

	public void doTransfer(Integer recipientId, Integer senderId, Integer amount)
			throws IllegalTransactionException {
		if (recipientId == senderId) {
			return ;
		}
		else if (users.getUserById(senderId).getBalance() < amount) {
			throw new IllegalTransactionException();
		}
		
		UUID id = UUID.randomUUID();
		Transaction debit = new Transaction(id, users.getUserById(recipientId),
											users.getUserById(senderId),
											Transaction.transferType.DEBIT, amount);
		Transaction credit = new Transaction(id, users.getUserById(recipientId),
											users.getUserById(senderId),
											Transaction.transferType.CREDIT, amount);

		users.getUserById(recipientId).getTransactionsList().addTransaction(credit);
		users.getUserById(senderId).getTransactionsList().addTransaction(debit);
		debit.doTransfer();
	}

	public Transaction[] getUserTransactions(Integer userId) {
		return (users.getUserById(userId).getTransactionsList().toArray());
	}

	public void removeUserTransaction(UUID transactionId, Integer userId) {
		users.getUserById(userId).getTransactionsList().removeTransaction(transactionId);
	}

	public Transaction[] getUnpairedTransactions () {
		TransactionsLinkedList all = new TransactionsLinkedList();
		TransactionsLinkedList unpaired = new TransactionsLinkedList();
		UUID [] checked;
		int countChecked = 0;
		int len = 0;
		int j;
		int k;

		for (int i = 0; i < users.getUserNum(); i++) {
			if (users.getUserByIndex(i).getTransactionsList().toArray() == null) {
				continue ;
			}
			for (Transaction t : users.getUserByIndex(i).getTransactionsList().toArray()) {
				all.addTransaction(t);
			}
			len += users.getUserByIndex(i).getTransactionsList().toArray().length;
		}
		checked = new UUID[len];
		for (int i = 0; i < len; i++) {
			if (all.toArray()[i] != null) {
				for (j = i + 1; j < len; j++) {
					if (all.toArray()[i].getId() == all.toArray()[j].getId()) {
						checked[countChecked++] = all.toArray()[i].getId();
						break ;
					}
				}
				for (k = 0; k < countChecked; k++) {
					if (checked[k] == all.toArray()[i].getId()) {
						break ;
					}
				}
				if (k == countChecked) {
					unpaired.addTransaction(all.toArray()[i]);
				}
			}
		}
		return (unpaired.toArray());
	}
}
