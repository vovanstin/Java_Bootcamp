public class IllegalTransactionException extends RuntimeException {
	
	@Override
	public String getMessage() {
		return ("Not enough money");
	}
}
