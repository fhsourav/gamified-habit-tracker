import java.util.Calendar;
import java.util.Date;

public class StreakUpdater {
	private int streak;
	private Date today;
	private Date streakLimit;

	public StreakUpdater(int streak, int periodicity, Date lastCheckedIn) {
		this.streak = streak;
		Calendar calendar = Calendar.getInstance();
		today = calendar.getTime();
		calendar.setTime(lastCheckedIn);
		calendar.add(calendar.DAY_OF_MONTH, periodicity);
		streakLimit = calendar.getTime();
	}

	public int updateStreak() {
		if(today.compareTo(streakLimit) <= 0) {
			streak++;
		}
		else {
			streak = 0;
		}
		return streak;
	}

}
