public class Program {
	
	public static void main(String[] args) {
		for (int i = 0; i < 10; i++) {
			User tmp;
			if (i % 2 == 0) {
				tmp = new User("Man " + i, i * 100);
			}	else {
				tmp = new User("Woman " + i, i * 100);
			}
			System.out.println("User number: '" + i + "', id: '" + tmp.getId() + "';");
		}
	}
}
