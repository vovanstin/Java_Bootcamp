package edu.school21.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import edu.school21.exceptions.AlreadyAuthenticatedException;
import edu.school21.exceptions.EntityNotFoundException;
import edu.school21.models.User;
import edu.school21.repositories.UsersRepository;

public class UsersServiceImplTest {
	
	UsersRepository usersRepository = Mockito.mock(UsersRepository.class);
	UsersServiceImpl usersServiceImpl = new UsersServiceImpl(usersRepository);
	private final String CORRECT_LOGIN = "CORRECT_LOGIN";
	private final String CORRECT_PASSWORD = "CORRECT_PASSWORD";
	private final String INCORRECT_LOGIN = "INCORRECT_LOGIN";
	private final String INCORRECT_PASSWORD = "INCORRECT_PASSWORD";
	User user;

	@BeforeEach
	void init() {
		user = new User(1L, CORRECT_LOGIN, CORRECT_PASSWORD, false);
		when(usersRepository.findByLogin(CORRECT_LOGIN)).thenReturn(user);
		when(usersRepository.findByLogin(INCORRECT_LOGIN)).thenThrow(new EntityNotFoundException());
	}

	@Test
	void authenticateTestCorrect() {
		assertTrue(usersServiceImpl.authenticate(CORRECT_LOGIN, CORRECT_PASSWORD));
		Mockito.verify(usersRepository).update(user);
	}

	@Test
	void authenticateTestIncorrectLogin() {
		assertThrows(EntityNotFoundException.class, () -> {
			usersServiceImpl.authenticate(INCORRECT_LOGIN, INCORRECT_PASSWORD);
		});
	}

	@Test
	void authenticateTestIncorrectPassword() {
		assertFalse(usersServiceImpl.authenticate(CORRECT_LOGIN, INCORRECT_PASSWORD));
	}

	@Test
	void authenticateTestAuthTrue() {
		user.setAuthStatus(true);
		assertThrows(AlreadyAuthenticatedException.class, () -> {
			usersServiceImpl.authenticate(CORRECT_LOGIN, CORRECT_PASSWORD);
		});
	}

}
