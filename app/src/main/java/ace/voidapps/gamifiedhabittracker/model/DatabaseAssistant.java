package ace.voidapps.gamifiedhabittracker.model;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalDate;

public class DatabaseAssistant {

	private DatabaseReference mDatabase;

	public DatabaseAssistant() {
		mDatabase = FirebaseDatabase.getInstance("https://gamified-habit-tracker-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();
	}

	public void writeNewUser(String uid, User user) {
		mDatabase.child("users").child(uid).child("Username").setValue(user.getUsername());
		mDatabase.child("users").child(uid).child("Email").setValue(user.getEmail());
		mDatabase.child("users").child(uid).child("FirstName").setValue(user.getFirstname());
		mDatabase.child("users").child(uid).child("LastName").setValue(user.getLastName());
		mDatabase.child("users").child(uid).child("BirthDate").setValue(user.getBirthDate().toString());
		mDatabase.child("users").child(uid).child("IsAdmin").setValue(user.isAdmin());
		if (!user.isAdmin()) {
			mDatabase.child("users").child(uid).child("ExperiencePoints").setValue(((Client)user).getExp());
		}
	}

	public void writeNewHabit(Habit habit) {
		String key = mDatabase.child("habits").push().getKey();
		mDatabase.child("habits").child(key).child("Title").setValue(habit.getTitle());
		mDatabase.child("habits").child(key).child("Details").setValue(habit.getDetails());
		mDatabase.child("habits").child(key).child("ExperiencePoints").setValue(habit.getExp());
		mDatabase.child("habits").child(key).child("Periodicity").setValue(habit.getPeriodicity().toString());
		mDatabase.child("habits").child(key).child("LastCheckedIn").setValue(habit.getLastCheckedIn().toString());
		mDatabase.child("habits").child(key).child("CreatedOn").setValue(habit.getCreatedOn().toString());
		mDatabase.child("habits").child(key).child("Streak").setValue(habit.getStreak());
		mDatabase.child("habits").child(key).child("UID").setValue(habit.getClient().getUserId());
		mDatabase.child("users").child(habit.getClient().getUserId()).child("habits").child(key).setValue(habit.getTitle());
	}

	public void habitCheckIn(Habit habit) {
		mDatabase.child("habits").child(habit.getHabitId()).child("ExperiencePoints").setValue(habit.getExp());
		mDatabase.child("habits").child(habit.getHabitId()).child("LastCheckedIn").setValue(habit.getLastCheckedIn().toString());
		mDatabase.child("habits").child(habit.getHabitId()).child("Streak").setValue(habit.getStreak());
	}

	public void deleteHabit(Habit habit) {
		mDatabase.child("habits").child(habit.getHabitId()).removeValue();
		mDatabase.child("users").child(habit.getClient().getUserId()).child("habits").child(habit.getHabitId()).removeValue();
	}

//	public void writeNewReward(String uid, Habit habit) {
//		String key = mDatabase.child("habits").push().getKey();
//		mDatabase.child("habits").child(key).child("Title").setValue(habit.getTitle());
//		mDatabase.child("habits").child(key).child("Details").setValue(habit.getDetails());
//		mDatabase.child("habits").child(key).child("ExperiencePoints").setValue(habit.getExp());
//		mDatabase.child("habits").child(key).child("Periodicity").setValue(habit.getPeriodicity().toString());
//		mDatabase.child("habits").child(key).child("LastCheckedIn").setValue(habit.getLastCheckedIn().toString());
//		mDatabase.child("habits").child(key).child("Streak").setValue(habit.getStreak());
//	}

	public Task<DataSnapshot> retrieveUser(String uid) {
		Task<DataSnapshot> userTask = mDatabase.child("users").child(uid).get();
		return userTask;
	}

	public Task<DataSnapshot> retrieveHabit(String hid) {
		Task<DataSnapshot> habitTask = mDatabase.child("habits").child(hid).get();
		return habitTask;
	}

	public String getHabitKey() {
		return mDatabase.child("habits").getKey();
	}

}
