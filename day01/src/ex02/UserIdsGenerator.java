public class UserIdsGenerator {
	
	private static Integer lastId;

	private UserIdsGenerator() {
		lastId = 0;
	}

	private static class UserIdsGeneratorHolder {
		public static final UserIdsGenerator instance = new UserIdsGenerator();
	}

	public static UserIdsGenerator getInstance() {
		return (UserIdsGeneratorHolder.instance);
	}
	
	public Integer generateId() {
		lastId += 1;
		return (lastId);
	}
}
