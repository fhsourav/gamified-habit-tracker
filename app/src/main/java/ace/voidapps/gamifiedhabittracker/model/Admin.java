package ace.voidapps.gamifiedhabittracker.model;

import java.time.LocalDate;
import java.util.List;

public class Admin extends User {

	private List<Client> clientList;
	private List<Reward> rewardList;
	private List<Feedback> allFeedbacks;

	public Admin(int userId, String username, String password, String email, String firstname, String lastName, LocalDate birthDate) {
		super(userId, username, password, email, firstname, lastName, birthDate, true);
	}
}
