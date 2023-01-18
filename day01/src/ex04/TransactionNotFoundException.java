public class TransactionNotFoundException extends RuntimeException {
	
	@Override
	public String getMessage() {
		return ("Transaction not found!");
	}
}
