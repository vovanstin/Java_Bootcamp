package edu.school21.exceptions;

public class EntityNotFoundException extends RuntimeException {
	public EntityNotFoundException() {
		super("Entity not found!");
	}
}
