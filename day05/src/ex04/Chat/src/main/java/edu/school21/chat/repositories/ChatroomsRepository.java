package edu.school21.chat.repositories;

import java.util.Optional;

import edu.school21.chat.models.Chatroom;

public interface ChatroomsRepository {
	Optional<Chatroom> findById(Long id);
}
