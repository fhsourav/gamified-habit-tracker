package ace.voidapps.gamifiedhabittracker.model;

import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;

public class Habit {

	private int habitId;
	private Client client;
	private String title;
	private String details;
	private Period periodicity;
	private List<Reward> unlockedRewards;
	private Period streak;
	private LocalDateTime lastCheckedIn;
	private int exp;

	public Habit(int habitId, Client client, String title, String details, Period periodicity, List<Reward> unlockedRewards, Period streak, LocalDateTime lastCheckedIn, int exp) {
		this.habitId = habitId;
		this.client = client;
		this.title = title;
		this.details = details;
		this.periodicity = periodicity;
		this.unlockedRewards = unlockedRewards;
		this.streak = streak;
		this.lastCheckedIn = lastCheckedIn;
		this.exp = exp;
	}

	public int getHabitId() {
		return habitId;
	}

	public void setHabitId(int habitId) {
		this.habitId = habitId;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public Period getPeriodicity() {
		return periodicity;
	}

	public void setPeriodicity(Period periodicity) {
		this.periodicity = periodicity;
	}

	public List<Reward> getUnlockedRewards() {
		return unlockedRewards;
	}

	public void setUnlockedRewards(List<Reward> unlockedRewards) {
		this.unlockedRewards = unlockedRewards;
	}

	public Period getStreak() {
		return streak;
	}

	public void setStreak(Period streak) {
		this.streak = streak;
	}

	public LocalDateTime getLastCheckedIn() {
		return lastCheckedIn;
	}

	public void setLastCheckedIn(LocalDateTime lastCheckedIn) {
		this.lastCheckedIn = lastCheckedIn;
	}

	public int getExp() {
		return exp;
	}

	public void setExp(int exp) {
		this.exp = exp;
	}
}
