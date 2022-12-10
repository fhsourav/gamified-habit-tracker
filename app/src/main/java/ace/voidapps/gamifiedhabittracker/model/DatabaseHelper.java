package ace.voidapps.gamifiedhabittracker.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

	private static final String TABLE_USERS = "users";
	private static final String TABLE_HABITS = "habits";
	private static final String TABLE_REWARDS = "rewards";
	private static final String TABLE_FEEDBACKS = "feedbacks";
	private static final String TABLE_FRIENDS = "friends";
	private static final String TABLE_UNLOCKED_REWARDS = "unlocked_rewards";
	private static final String USER_ID = "UserId";
	private static final String USERNAME = "Username";
	private static final String PASSWORD = "Password";
	private static final String EMAIL = "Email";
	private static final String FIRST_NAME = "FirstName";
	private static final String LAST_NAME = "LastName";
	private static final String BIRTH_DATE = "BirthDate";
	private static final String IS_ADMIN = "IsAdmin";
	private static final String HABIT_ID = "HabitId";
	private static final String HABIT_TITLE = "HabitTitle";
	private static final String HABIT_DETAILS = "HabitDetails";
	private static final String PERIODICITY = "Periodicity";
	private static final String STREAK = "Streak";
	private static final String LAST_CHECKED_IN = "LastCheckedIn";
	private static final String REWARD_ID = "RewardId";
	private static final String REWARD_TITLE = "RewardTitle";
	private static final String REWARD_TYPE = "RewardType";
	private static final String REWARD_DETAILS = "RewardDetails";
	private static final String UNLOCK_STREAK = "UnlockStreak";
	private static final String FEEDBACK_ID = "FeedbackId";
	private static final String FEEDBACK_TITLE = "FeedbackTitle";
	private static final String FEEDBACK_CONTENT = "FeedbackContent";
	private static final String FRIEND_1_ID = "Friend1Id";
	private static final String FRIEND_2_ID = "Friend2Id";
	private static final String FRIEND_ADDED_ON = "FriendAddedOn";
	private static final String REWARD_UNLOCKED_ON = "RewardUnlockedOn";

	public DatabaseHelper(@Nullable Context context) {
		super(context, "habit_tracker.db", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase sqLiteDatabase) {
		String createUsersTable = "create table " + TABLE_USERS + " (" +
				USER_ID + " integer primary key autoincrement, " +
				USERNAME + " nvarchar(20) not null, " +
				PASSWORD + " text, " +
				EMAIL + " nvarchar(50), " +
				FIRST_NAME + " nvarchar(40), " +
				LAST_NAME + " nvarchar(40), " +
				BIRTH_DATE + " text, " +
				IS_ADMIN + " boolean);";

		String createHabitsTable = "create table " + TABLE_HABITS + " (" +
				HABIT_ID + " integer primary key autoincrement, " +
				USER_ID + " integer, " +
				HABIT_TITLE + " nvarchar(40) not null, " +
				HABIT_DETAILS + " nvarchar(255), " +
				PERIODICITY + " integer not null, " +
				STREAK + " integer, " +
				LAST_CHECKED_IN + " text, " +
				"foreign key (" + USER_ID + ") " +
				"references " + TABLE_USERS + "(" + USER_ID + "));";

		String createRewardsTable = "create table " + TABLE_REWARDS + " (" +
				REWARD_ID + " integer primary key autoincrement, " +
				REWARD_TITLE + " nvarchar(50) not null, " +
				REWARD_TYPE + " nvarchar(20) not null, " +
				REWARD_DETAILS + " nvarchar(255), " +
				UNLOCK_STREAK + " integer not null);";

		String createFeedbacksTable = "create table " + TABLE_FEEDBACKS + " (" +
				FEEDBACK_ID + " integer primary key autoincrement, " +
				FEEDBACK_TITLE + " nvarchar(40) not null, " +
				USER_ID + " integer not null, " +
				FEEDBACK_CONTENT + " nvarchar(255) not null, " +
				"foreign key (" + USER_ID + ") " +
				"references " + TABLE_USERS + "(" + USER_ID + "));";

		String createFriendsTable = "create table " + TABLE_FRIENDS + " (" +
				FRIEND_1_ID + " integer, " +
				FRIEND_2_ID + " integer, " +
				FRIEND_ADDED_ON + " Text, " +
				"foreign key (" + FRIEND_1_ID + ") " +
				"references " + TABLE_USERS + "(" + USER_ID + "), " +
				"foreign key (" + FRIEND_2_ID + ") " +
				"references " + TABLE_USERS + "(" + USER_ID + "), " +
				"primary key (" + FRIEND_1_ID + ", " + FRIEND_2_ID + "));";

		String createUnlockedRewardsTable = "create table " + TABLE_UNLOCKED_REWARDS + " (" +
				HABIT_ID + " integer not null, " +
				REWARD_ID + " integer not null, " +
				REWARD_UNLOCKED_ON + " text, " +
				"foreign key (" + HABIT_ID + ") " +
				"references " + TABLE_HABITS + "(" + HABIT_ID + "), " +
				"foreign key (" + REWARD_ID + ") " +
				"references " + TABLE_REWARDS + "(" + REWARD_ID + "), " +
				"primary key (" + HABIT_ID + ", " + REWARD_ID + "));";

		sqLiteDatabase.execSQL("PRAGMA foreign_keys = ON;");
		sqLiteDatabase.execSQL(createUsersTable);
		sqLiteDatabase.execSQL(createHabitsTable);
		sqLiteDatabase.execSQL(createRewardsTable);
		sqLiteDatabase.execSQL(createFeedbacksTable);
		sqLiteDatabase.execSQL(createFriendsTable);
		sqLiteDatabase.execSQL(createUnlockedRewardsTable);
	}

	@Override
	public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

	}

	public boolean addUser(User user) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();

		contentValues.put(USERNAME, user.getUsername());
		contentValues.put(EMAIL, user.getEmail());
		contentValues.put(FIRST_NAME, user.getFirstname());
		contentValues.put(LAST_NAME, user.getLastName());
		contentValues.put(BIRTH_DATE, user.getBirthDate().toString());
		contentValues.put(IS_ADMIN, user.isAdmin());

		return true;
	}

}
