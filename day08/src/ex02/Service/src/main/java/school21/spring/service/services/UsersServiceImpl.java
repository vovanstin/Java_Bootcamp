package school21.spring.service.services;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import school21.spring.service.models.User;
import school21.spring.service.repositories.UsersRepository;

@Component
public class UsersServiceImpl implements UsersService {

	private final UsersRepository usersRepository;

	@Autowired
	public UsersServiceImpl(@Qualifier("usersRepositoryJdbcTemplate") UsersRepository usersRepository) {
		this.usersRepository = usersRepository;
	}

	@Override
	public String signUp(String email) {
		if (email == null || email.isEmpty())
			throw new RuntimeException("Error! Email can't be empty!");

		if (usersRepository.findByEmail(email).isPresent())
			throw new RuntimeException("Error! User with this email already exists!");

		String password = generatePassword(10);

		usersRepository.save(new User(null, email, password));
		return password;
	}

	private String generatePassword(int length) {
		String capitalCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String lowerCaseLetters = "abcdefghijklmnopqrstuvwxyz";
		String specialCharacters = "!@#$";
		String numbers = "1234567890";
		String combinedChars = capitalCaseLetters + lowerCaseLetters + specialCharacters + numbers;
		Random random = new Random();
		char[] password = new char[length];

		password[0] = lowerCaseLetters.charAt(random.nextInt(lowerCaseLetters.length()));
		password[1] = capitalCaseLetters.charAt(random.nextInt(capitalCaseLetters.length()));
		password[2] = specialCharacters.charAt(random.nextInt(specialCharacters.length()));
		password[3] = numbers.charAt(random.nextInt(numbers.length()));

		for (int i = 4; i < length; i++) {
			password[i] = combinedChars.charAt(random.nextInt(combinedChars.length()));
		}
		return String.copyValueOf(password);
	}

}
