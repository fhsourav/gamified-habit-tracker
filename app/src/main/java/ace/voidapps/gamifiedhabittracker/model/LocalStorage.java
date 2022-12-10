package ace.voidapps.gamifiedhabittracker.model;

import java.time.LocalDate;

public class LocalStorage {

	private static LocalStorage localStorage;

	private int authAction;
	private String uid, uname, password, email, firstName, lastName;
	private LocalDate birthdate;

	private LocalStorage() {

	}

	public static LocalStorage getInstance() {
		if (localStorage == null) {
			localStorage = new LocalStorage();
		}
		return localStorage;
	}

	public int getAuthAction() {
		return authAction;
	}

	/**
	 * 0 = sign up
	 * 1 = log in
	 * 2 = sign out
	 * @return
	 */
	public void setAuthAction(int authAction) {
		this.authAction = authAction;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
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

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public LocalDate getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(LocalDate birthdate) {
		this.birthdate = birthdate;
	}
}
