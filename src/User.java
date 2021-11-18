public class User {
	int userID;
	String name;
	String password;

	boolean verifyLogin() {
		return false;
	}

	boolean login() {
		return false;
	}

	boolean logout() {
		return false;
	}

	void setName(String name) {
		this.name = name;
	}

	String getName() {
		return name;
	}

	void setPassword(String password) {
		this.password = password;
	}

}
