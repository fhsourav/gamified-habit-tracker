package ace.voidapps.gamifiedhabittracker.model;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class LocalStorage {

	private static LocalStorage localStorage;

	public static final String REALTIME_DATABASE = "https://gamified-habit-tracker-default-rtdb.asia-southeast1.firebasedatabase.app/";

	private int authAction, periodicity;
	private String uid, uname, password, email, firstName, lastName, hid, habitTitle, habitDetails;
	private LocalDate birthdate;

	private User user;
//	private Habit habit;

	private Map<String, Habit> habitMap;

	private LocalStorage() {
		habitMap = new HashMap<>();
	}

	public static LocalStorage getInstance() {
		if (localStorage == null) {
			localStorage = new LocalStorage();
		}
		return localStorage;
	}

	public void clearAll() {
		localStorage = new LocalStorage();
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getHid() {
		return hid;
	}

	public void setHid(String hid) {
		this.hid = hid;
	}

	public Habit getHabit() {
		return habitMap.get(hid);
	}

	public void addHabit(Habit habit) {
		habitMap.put(habit.getHabitId(), habit);
	}

}
