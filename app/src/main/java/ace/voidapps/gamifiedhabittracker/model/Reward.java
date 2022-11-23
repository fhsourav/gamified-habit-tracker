package ace.voidapps.gamifiedhabittracker.model;

import java.util.List;

/**
 * rewardType int:
 */
public class Reward {

	private String title;
	/**
	 * 	0 = daily, 1 = weekly, 2 = biweekly, 3 = monthly, 4 = half-yearly, 5 = yearly and so on
	 */
	private int rewardType;
	private String description;
	private int unlockStreak;
	private boolean isUnlocked;

}
