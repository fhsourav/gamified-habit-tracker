package ace.voidapps.gamifiedhabittracker.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Client extends User {

	private List<Habit> habitList;
	private List<Reward> rewardList;
	private List<Client> friendList;
	private List<Feedback> feedbackList;
	private int exp;

	public Client(int userId, String username, String password, String email, String firstname, String lastName, LocalDate birthDate) {
		super(userId, username, password, email, firstname, lastName, birthDate, false);
		habitList = new ArrayList<>();
		rewardList = new ArrayList<>();
		friendList = new ArrayList<>();
		feedbackList = new ArrayList<>();
		exp = 0;
	}

	public List<Habit> getHabitList() {
		return habitList;
	}

	public void setHabitList(List<Habit> habitList) {
		this.habitList = habitList;
	}

	public List<Reward> getRewardList() {
		return rewardList;
	}

	public void setRewardList(List<Reward> rewardList) {
		this.rewardList = rewardList;
	}

	public List<Client> getFriendList() {
		return friendList;
	}

	public void setFriendList(List<Client> friendList) {
		this.friendList = friendList;
	}

	public List<Feedback> getFeedbackList() {
		return feedbackList;
	}

	public void setFeedbackList(List<Feedback> feedbackList) {
		this.feedbackList = feedbackList;
	}

	public int getExp() {
		return exp;
	}

	public void setExp(int exp) {
		this.exp = exp;
	}
}
