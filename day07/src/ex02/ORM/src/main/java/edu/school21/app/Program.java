package edu.school21.app;

import edu.school21.models.User;
import edu.school21.processor.OrmManager;

public class Program {
	public static void main(String[] args) {
		OrmManager ormManager = new OrmManager();

		User user = new User(null, "Bob", "Black", 34);
		ormManager.save(user);
		System.out.println(ormManager.findById(user.getId(), user.getClass()));

		user.setFirstName("Mike");
		user.setLastName("Grey");
		user.setAge(18);
		ormManager.update(user);
		System.out.println(ormManager.findById(user.getId(), user.getClass()));
	}
}
