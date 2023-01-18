import java.util.UUID;

public class Program {
	
	public static void main(String[] args) {
		UsersArrayList users = new UsersArrayList();

		for (int i = 0; i < 10; i++) {
			User tmp;
			if (i % 2 == 0) {
				tmp = new User("Man " + i, i * 100);
			}	else {
				tmp = new User("Woman " + i, i * 100);
			}
			users.addUser(tmp);
		}
		for (int i = 2; i < 10; i++) {
			System.out.println(users.getUserById(i).toString());
		}
		System.out.println();
		for (int i = 3; i < 10; i++) {
			UUID id = UUID.randomUUID();
			Transaction debit = new Transaction(id, users.getUserById(i), users.getUserById(2), Transaction.transferType.DEBIT, 5);
			Transaction credit = new Transaction(id, users.getUserById(i), users.getUserById(2), Transaction.transferType.CREDIT, 5);

			users.getUserById(i).getTransactionsList().addTransaction(credit);
			users.getUserById(2).getTransactionsList().addTransaction(debit);
			debit.doTransfer();
		}
		for (int i = 2; i < 10; i++) {
			System.out.println(users.getUserById(i).toString());
		}
		System.out.println();
		System.out.println("User's {name=" + users.getUserById(2).getName() + "} Transfer list:");
		for (Transaction a : users.getUserById(2).getTransactionsList().toArray()) {
			System.out.println(a);
		}
		System.out.println("User's {name=" + users.getUserById(3).getName() + "} Transfer list:");
		for (Transaction a : users.getUserById(3).getTransactionsList().toArray()) {
			System.out.println(a);
		}
		System.out.println();
		users.getUserById(2).getTransactionsList().removeTransaction(users.getUserById(3).getTransactionsList().toArray()[0].getId());
		System.out.println("User's {name=" + users.getUserById(2).getName() + "} Transfer list:");
		for (Transaction a : users.getUserById(2).getTransactionsList().toArray()) {
			System.out.println(a);
		}
		System.out.println("User's {name=" + users.getUserById(3).getName() + "} Transfer list:");
		for (Transaction a : users.getUserById(3).getTransactionsList().toArray()) {
			System.out.println(a);
		}
	}
}
