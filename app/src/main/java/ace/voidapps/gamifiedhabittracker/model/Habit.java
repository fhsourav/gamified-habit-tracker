package ace.voidapps.gamifiedhabittracker.model;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

public class Habit {

	private String habitId;
	private Client client;
	private String title;
	private String details;
	private Period periodicity;
	private List<Reward> unlockedRewards;
	private int streak;
	private LocalDate lastCheckedIn;
	private final LocalDateTime createdOn;
	private int exp;

	@RequiresApi(api = Build.VERSION_CODES.O)
	public Habit(String habitId, Client client, String title, String details, Period periodicity, List<Reward> unlockedRewards, int streak, LocalDate lastCheckedIn, int exp) {
		this.habitId = habitId;
		this.client = client;
		this.title = title;
		this.details = details;
		this.periodicity = periodicity;
		this.unlockedRewards = unlockedRewards;
		this.streak = streak;
		this.lastCheckedIn = lastCheckedIn;
		this.exp = exp;
		createdOn = LocalDateTime.now();
	}

	@RequiresApi(api = Build.VERSION_CODES.O)
	public Habit(String habitId, Client client, String title, String details, Period periodicity, LocalDate lastCheckedIn) {
		this(habitId, client, title, details, periodicity, new ArrayList<Reward>(), 0, lastCheckedIn, 0);
	}

	public String getHabitId() {
		return habitId;
	}

	public void setHabitId(String habitId) {
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

	public int getStreak() {
		return streak;
	}

	public void setStreak(int streak) {
		this.streak = streak;
	}

	public LocalDate getLastCheckedIn() {
		return lastCheckedIn;
	}

	public void setLastCheckedIn(LocalDate lastCheckedIn) {
		this.lastCheckedIn = lastCheckedIn;
	}

	public LocalDateTime getCreatedOn() {
		return createdOn;
	}

	public int getExp() {
		return exp;
	}

	public void setExp(int exp) {
		this.exp = exp;
	}

	@RequiresApi(api = Build.VERSION_CODES.O)
	public void checkIn() {
		LocalDate checkInTime = LocalDate.now();
		if (checkInTime.isAfter(lastCheckedIn.plus(periodicity).plusDays(1))) {
			streak = 1;
		} else {
			streak++;
		}
		lastCheckedIn = checkInTime;
	}
}
