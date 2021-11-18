import java.util.ArrayList;

public class Reward {

	String title;
	String rewardType;
	String description;
	int unlockStreak;
	boolean unlockCompletion;

	String checkRequirement() {
		return null;
	}

	void setTitle(String title) {
		this.title = title;
	}

	void setRewardType(String rewardType) {
		this.rewardType = rewardType;
	}

	void setDescription(String description) {
		this.description = description;
	}

	void setUnlockStreak(int unlockStreak) {
		this.unlockStreak = unlockStreak;
	}

	void setUnlockCompletion(boolean unlockCompletion) {
		this.unlockCompletion = unlockCompletion;
	}

}
