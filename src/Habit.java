import java.util.ArrayList;
import java.util.Date;

public class Habit {

	String title;
	String details;
	int periodicity;
	ArrayList<Reward> availableRewards;
	ArrayList<Reward> unlockedRewards;
	int streak;
	Date lastCheckedIn;

	void setTitle(String title) {
		this.title = title;
	}

	void setDetails(String details) {
		this.details = details;
	}

	void checkIn() {
		StreakUpdater updater = new StreakUpdater(streak, periodicity, lastCheckedIn);
		streak = updater.updateStreak();
	}

	int getStreak() {
		return streak;
	}

	void resetRewards() {
		unlockedRewards = new ArrayList<>();
	}

	void resetStreak() {
		streak = 0;
	}

}
