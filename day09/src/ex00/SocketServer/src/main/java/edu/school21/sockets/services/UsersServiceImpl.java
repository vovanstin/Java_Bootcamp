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
	public void signUp(String email, String password) {
		if (email == null || email.isEmpty())
			throw new RuntimeException("Error! Email can't be empty!");

		if (password == null || password.isEmpty())
			throw new RuntimeException("Error! Password can't be null or empty!");

		if (usersRepository.findByEmail(email).isPresent())
			throw new RuntimeException("Error! User with this email already exists!");

		usersRepository.save(new User(null, email, passwordEncoder.encode(password)));
	}
}
