package ace.voidapps.gamifiedhabittracker.model;

import java.time.LocalDate;
import java.util.List;

public class Admin extends User {

	private List<Client> clientList;
	private List<Reward> rewardList;
	private List<Feedback> feedbackList;

	public Admin(int userId, String username, String password, String email, String firstname, String lastName, LocalDate birthDate) {
		super(userId, username, password, email, firstname, lastName, birthDate, true);
	}

	public List<Client> getClientList() {
		return clientList;
	}

	public void setClientList(List<Client> clientList) {
		this.clientList = clientList;
	}

	public List<Reward> getRewardList() {
		return rewardList;
	}

	public void setRewardList(List<Reward> rewardList) {
		this.rewardList = rewardList;
	}

	public List<Feedback> getFeedbackList() {
		return feedbackList;
	}

	public void setFeedbackList(List<Feedback> feedbackList) {
		this.feedbackList = feedbackList;
	}
}
