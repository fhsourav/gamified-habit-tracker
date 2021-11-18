import java.util.ArrayList;

public class Client extends User {

	ArrayList<Habit> habitList;
	ArrayList<Reward> userRewards;
	ArrayList<Client> friendlist;
	ArrayList<Feedback> feedbacks;

	void checkIn() {

	}

	void checkRewards() {

	}

	void addHabit() {

	}

	void editHabit() {

	}

	void removeHabit() {

	}

	void resetHabit() {

	}

	void checkHabit() {

	}

	ArrayList<Habit> getHabitList() {
		return habitList;
	}

	void addFriend(Client x) {
		friendlist.add(x);
	}

	void removeFriend() {

	}

}
