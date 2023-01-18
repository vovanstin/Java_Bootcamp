public class Program {
	
	public static void main(String[] args) {
		User first = new User("Tom", 0);
		first.setBalance(1024);
		first.setId(666);
		System.out.println(first.toString());
		first.setBalance(-228);
		System.out.println(first.toString());

		User second = new User("Alex", 0);
		second.setBalance(123456);
		second.setId(777);
		System.out.println(second.toString());

		System.out.println();

		Transaction transaction = new Transaction();

		transaction.setRecipient(first);
		transaction.setSender(second);
		transaction.setAmount(1000);
		transaction.setCategory(Transaction.transferType.DEBIT);
		transaction.doTransfer();
		System.out.println(first.toString());
		System.out.println(second.toString());

		System.out.println();

		Transaction transactionCredit = new Transaction();
		transactionCredit.setSender(first);
		transactionCredit.setRecipient(second);
		transactionCredit.setAmount(-500);
		transactionCredit.setCategory(Transaction.transferType.CREDIT);
		transactionCredit.doTransfer();
		System.out.println(first.toString());
		System.out.println(second.toString());
	}
}
