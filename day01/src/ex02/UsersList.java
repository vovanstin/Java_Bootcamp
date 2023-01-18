public interface UsersList {
	Integer defaultSize = 10;

	void addUser(User user);

	User getUserById(Integer id) throws UserNotFoundException;

	User getUserByIndex(Integer index) throws UserNotFoundException;

	Integer getUserNum();
}
