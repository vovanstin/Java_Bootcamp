package edu.school21.chat.repositories;

import java.util.Optional;

import edu.school21.chat.models.User;

public interface UsersRepository {
	Optional<User> findById(Long id);
}
