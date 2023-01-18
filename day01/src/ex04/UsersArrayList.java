public class UsersArrayList implements UsersList{
	private Integer size = defaultSize;
	private Integer	countElem = 0;
	private User[]	array = new User[size];

	@Override
	public void addUser(User user) throws NullPointerException {
		if (user == null) {
			throw new NullPointerException();
		}
		if (countElem == size - 1) {
			array = reallocUsers();
		}
		array[countElem] = user;
		countElem++;
	}

	private User[] reallocUsers() {
		size += size;
		User[] temp = new User[size];
		for (int i = 0; i <= countElem; i++) {
			temp[i] = array[i];
		}
		return (temp);
	}

	@Override
	public User getUserById(Integer id) throws UserNotFoundException {
		for (int i = 0; i < countElem; i++) {
			if (array[i].getId() == id) {
				return (array[i]);
			}
		}
		throw new UserNotFoundException();
	}

	@Override
	public User getUserByIndex(Integer index) throws UserNotFoundException,
			ArrayIndexOutOfBoundsException {
		if (index >= countElem) {
			throw new UserNotFoundException();
		} else if (index < 0) {
			throw new ArrayIndexOutOfBoundsException();
		}
		return (array[index]);
	}

	@Override
	public Integer getUserNum() {
		return (countElem);
	}
}
