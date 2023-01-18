public class Program {
	
	public static void main(String[] args) {
		UsersArrayList users = new UsersArrayList();

		for (int i = 0; i < 100; i++) {
			User tmp;
			if (i % 2 == 0) {
				tmp = new User("Man " + i, i * 100);
			}	else {
				tmp = new User("Woman " + i, i * 100);
			}
			users.addUser(tmp);
		}

		System.out.println("User with id 21: " + users.getUserById(21).toString());
		System.out.println("Total capacity: " + users.getUserNum());
	}
}
