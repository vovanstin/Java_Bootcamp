package school21.spring.service.application;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import school21.spring.service.models.User;
import school21.spring.service.repositories.UsersRepository;

public class Main {

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
		UsersRepository usersRepository = context.getBean("usersRepositoryJdbc", UsersRepository.class);
		makeActions(usersRepository, 0L);
		for (Long i = 1L; i < 11; i++) {
			usersRepository.delete(i);
		}
		System.out.println();
		usersRepository = context.getBean("usersRepositoryJdbcTemplate", UsersRepository.class);
		makeActions(usersRepository, 10L);
	}

	public static void makeActions(UsersRepository usersRepository, Long prefix) {
		User[] users = {
				new User(null, "123zx@mail.com"),
				new User(null, "234xcv@mail.com"),
				new User(null, "345cvbr@mail.com"),
				new User(null, "456vbnrt@mail.com"),
				new User(null, "567bnmrfrg@mail.com"),
				new User(null, "678nm,rgrg@mail.com"),
				new User(null, "789m,.@mail.com"),
				new User(null, "890qweasdfadf@mail.com"),
				new User(null, "987tyfyj123@mail.com"),
				new User(null, "765ihk345@mail.com"),
		};

		System.out.println("Save users...");
		for (User u : users)
			usersRepository.save(u);

		System.out.println("Users:");
		for (User u : usersRepository.findAll())
			System.out.println(u);

		System.out.println("\nUpdate user...");
		usersRepository.update(new User(5L + prefix, "newEmail@mail.com"));

		System.out.println("\nFind by id:");
		System.out.println(usersRepository.findById(5L + prefix));

		System.out.println("\nDelete:");
		usersRepository.delete(5L + prefix);
		System.out.println(usersRepository.findById(5L + prefix));
	}
}
