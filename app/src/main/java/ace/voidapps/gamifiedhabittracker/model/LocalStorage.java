package ace.voidapps.gamifiedhabittracker.model;

import java.time.LocalDate;

public class LocalStorage {

	private static LocalStorage localStorage;

	private int authAction, periodicity;
	private String uid, uname, password, email, firstName, lastName, hid, habitTitle, habitDetails;
	private LocalDate birthdate;

	private User user;
	private Habit habit;

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

	public void clearAll() {
		localStorage = new LocalStorage();
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

	public String getHid() {
		return hid;
	}

	public void setHid(String hid) {
		this.hid = hid;
	}

	public String getHabitTitle() {
		return habitTitle;
	}

	public void setHabitTitle(String habitTitle) {
		this.habitTitle = habitTitle;
	}

	public String getHabitDetails() {
		return habitDetails;
	}

	public void setHabitDetails(String habitDetails) {
		this.habitDetails = habitDetails;
	}

	public int getPeriodicity() {
		return periodicity;
	}

	public void setPeriodicity(int periodicity) {
		this.periodicity = periodicity;
	}

	public LocalDate getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(LocalDate birthdate) {
		this.birthdate = birthdate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Habit getHabit() {
		return habit;
	}

	public void setHabit(Habit habit) {
		this.habit = habit;
	}
}
