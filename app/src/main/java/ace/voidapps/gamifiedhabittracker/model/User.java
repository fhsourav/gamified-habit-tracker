package ace.voidapps.gamifiedhabittracker.model;

import java.time.LocalDate;

public class User {

	private int userId;
	private String username;
	private String password;
	private String email;
	private String firstname;
	private String lastName;
	private LocalDate birthDate;
	private boolean isAdmin;

	public User(int userId, String username, String password, String email, String firstname, String lastName, LocalDate birthDate) {
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.email = email;
		this.firstname = firstname;
		this.lastName = lastName;
		this.birthDate = birthDate;
		isAdmin = false;
	}

	public User(int userId, String username, String password, String email, String firstname, String lastName, LocalDate birthDate, boolean isAdmin) {
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.email = email;
		this.firstname = firstname;
		this.lastName = lastName;
		this.birthDate = birthDate;
		this.isAdmin = isAdmin;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

}
