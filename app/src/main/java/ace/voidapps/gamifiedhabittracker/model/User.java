package ace.voidapps.gamifiedhabittracker.model;

import java.time.LocalDate;

public abstract class User {

	private String userId;
	private String username;
	private String email;
	private String firstname;
	private String lastName;
	private LocalDate birthDate;
	private boolean isAdmin;

	public User(String userId, String username, String email, String firstname, String lastName, LocalDate birthDate, boolean isAdmin) {
		this.userId = userId;
		this.username = username;
		this.email = email;
		this.firstname = firstname;
		this.lastName = lastName;
		this.birthDate = birthDate;
		this.isAdmin = isAdmin;
	}

	public String getUserId() {
		return userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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
