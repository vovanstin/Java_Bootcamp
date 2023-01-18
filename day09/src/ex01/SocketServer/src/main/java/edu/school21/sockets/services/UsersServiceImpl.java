package edu.school21.sockets.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import edu.school21.sockets.models.User;
import edu.school21.sockets.repositories.UsersRepository;

@Component
public class UsersServiceImpl implements UsersService {

	private final UsersRepository usersRepository;
	private final BCryptPasswordEncoder passwordEncoder;

	@Autowired
	public UsersServiceImpl(UsersRepository usersRepository, BCryptPasswordEncoder passwordEncoder) {
		this.usersRepository = usersRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public void signUp(String username, String password) {
		if (username == null || username.isEmpty())
			throw new RuntimeException("Error! Username can't be empty!");

		if (password == null || password.isEmpty())
			throw new RuntimeException("Error! Password can't be null or empty!");

		if (usersRepository.findByUsername(username).isPresent())
			throw new RuntimeException("Error! User with this username already exists!");

		usersRepository.save(new User(null, username, passwordEncoder.encode(password)));
	}

	@Override
	public User signIn(String username, String password) {
		if (username == null || username.isEmpty())
			throw new RuntimeException("Error! Username can't be null or empty!");

		if (password == null || password.isEmpty())
			throw new RuntimeException("Error! Password can't be null or empty!");

		if (!usersRepository.findByUsername(username).isPresent())
			throw new RuntimeException("Error! Username is not exists!");

		User user = usersRepository.findByUsername(username).get();

		if (passwordEncoder.matches(password, user.getPassword())) {
			return user;
		} else {
			throw new RuntimeException("Error! Wrong password");
		}
	}
}
