package ace.voidapps.gamifiedhabittracker.model;

import java.util.List;

public class Reward {

	private int rewardId;
	private String title;
	/**
	 * 	0 = streak
	 * 	1 = exp
	 * 	2 = completion
	 */
	private int rewardType;
	private String details;
	private int unlockStreak;
	private int unlockExp;

	public Reward(int rewardId, String title, int rewardType, String details) {
		this.rewardId = rewardId;
		this.title = title;
		this.rewardType = rewardType;
		if (rewardType == 0) {
			unlockStreak = 0;
			unlockExp = -1;
		} else if (rewardType == 1) {
			unlockStreak = -1;
			unlockExp = 0;
		} else {
			unlockStreak = unlockExp = -1;
		}
		this.details = details;
	}

	public int getRewardId() {
		return rewardId;
	}

	public void setRewardId(int rewardId) {
		this.rewardId = rewardId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getRewardType() {
		return rewardType;
	}

	public void setRewardType(int rewardType) {
		this.rewardType = rewardType;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public int getUnlockStreak() {
		return unlockStreak;
	}

	public void setUnlockStreak(int unlockStreak) {
		if (rewardType == 0) {
			this.unlockStreak = unlockStreak;
		} else {
			System.err.println("Reward type mismatch");
		}
	}

	public int getUnlockExp() {
		return unlockExp;
	}

	public void setUnlockExp(int unlockExp) {
		if (rewardType == 1) {
			this.unlockExp = unlockExp;
		} else {
			System.err.println("Reward type mismatch");
		}
	}
}
