public class Program {
	
	public static void main(String[] args) {
		TransactionsService service = new TransactionsService();
		Transaction []unpaired;

		for (int i = 0; i < 10; i++) {
			if (i % 2 == 0) {
				service.addUser("Man " + i, i * 100);
			}	else {
				service.addUser("Woman " + i, i * 100);
			}
		}
		for (int i = 1; i < 11; i++) {
			System.out.println(service.getUserBalance(i));
		}
		System.out.println();
		for (int i = 3; i < 11; i++) {
			service.doTransfer(i, 2, 5);
		}
		for (int i = 2; i < 11; i++) {
			System.out.println(service.getUserBalance(i));
		}
		System.out.println();
		System.out.println("User's {id=2} Transfer list:");
		for (Transaction a : service.getUserTransactions(2)) {
			System.out.println(a);
		}
		System.out.println("User's {id=3} Transfer list:");
		for (Transaction a : service.getUserTransactions(3)) {
			System.out.println(a);
		}
		System.out.println();
		unpaired = service.getUnpairedTransactions();
		if (unpaired == null) {
			System.out.println("OK");
		} else {
			for (Transaction t : unpaired) {
				System.out.println(t.toString());
			}
		}
		System.out.println();
		service.removeUserTransaction(service.getUserTransactions(2)[0].getId(), 2);
		unpaired = service.getUnpairedTransactions();
		if (unpaired == null) {
			System.out.println("OK");
		} else {
			for (Transaction t : unpaired) {
				System.out.println(t.toString());
			}
		}
	}
}
