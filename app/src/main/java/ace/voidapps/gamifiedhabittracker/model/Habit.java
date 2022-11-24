package ace.voidapps.gamifiedhabittracker.model;

import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;

public class Habit {

	private int habitId;
	private String title;
	private String details;
	private Period periodicity;
	private List<Reward> unlockedRewards;
	private Period streak;
	private LocalDateTime lastCheckedIn;

}
