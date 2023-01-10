package ace.voidapps.gamifiedhabittracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalDate;
import java.time.Period;

import ace.voidapps.gamifiedhabittracker.model.Client;
import ace.voidapps.gamifiedhabittracker.model.Habit;
import ace.voidapps.gamifiedhabittracker.model.LocalStorage;

public class HabitActivity extends AppCompatActivity {

	private TextView textViewHabitTitle, textViewHabitStreak, textViewHabitDetails;
	private Button buttonHabitCheckIn;

	private DatabaseReference mDatabase;

	private LocalStorage localStorage;

	private Habit habit;

	private int streak;
	private String userFirstname;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_habit);

		textViewHabitTitle = findViewById(R.id.textViewHabitTitle);
		textViewHabitStreak = findViewById(R.id.textViewHabitStreak);
		textViewHabitDetails = findViewById(R.id.textViewHabitDetails);
		buttonHabitCheckIn = findViewById(R.id.buttonHabitCheckIn);

		localStorage = LocalStorage.getInstance();

		userFirstname = localStorage.getUser().getFirstname();

		mDatabase = FirebaseDatabase.getInstance(LocalStorage.REALTIME_DATABASE).getReference();

		HabitActivity.this.setTitle(userFirstname);

		Task<DataSnapshot> dataSnapshotTaskHabit = mDatabase.child("habits").child(localStorage.getHid()).get();

		dataSnapshotTaskHabit.addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
			@Override
			public void onComplete(@NonNull Task<DataSnapshot> task) {
				if (task.isSuccessful()) {
					String habitTitle = task.getResult().child("Title").getValue(String.class);
					String habitDetails = task.getResult().child("Details").getValue(String.class);
					streak = task.getResult().child("Streak").getValue(Integer.class);
					LocalDate lastCheckedIn = LocalDate.parse(task.getResult().child("LastCheckedIn").getValue(String.class));
					Period periodicity = Period.parse(task.getResult().child("Periodicity").getValue(String.class));
					if (LocalDate.now().isAfter(lastCheckedIn.plus(periodicity).plusDays(1))) {
						streak = 0;
					}
					HabitActivity.this.setTitle(userFirstname + " " + habitTitle);
					textViewHabitTitle.setText(habitTitle);
					textViewHabitDetails.setText(habitDetails);
					textViewHabitStreak.setText(((Integer) streak).toString());
					habit = localStorage.getHabit();
					if (habit == null) {
						habit = new Habit(localStorage.getHid(), (Client) localStorage.getUser(), habitTitle, habitDetails, periodicity, lastCheckedIn);
						localStorage.addHabit(habit);
					}
				} else {
					Toast.makeText(HabitActivity.this, "Error loading habit", Toast.LENGTH_SHORT).show();
				}
			}
		});

		buttonHabitCheckIn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				habit.checkIn();
				mDatabase.child("habits").child(habit.getHabitId()).child("ExperiencePoints").setValue(habit.getExp());
				mDatabase.child("habits").child(habit.getHabitId()).child("LastCheckedIn").setValue(habit.getLastCheckedIn().toString());
				mDatabase.child("habits").child(habit.getHabitId()).child("Streak").setValue(habit.getStreak());
				textViewHabitStreak.setText(((Integer) habit.getStreak()).toString());
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_habit_topbar, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(@NonNull MenuItem item) {

		if (item.getItemId() == R.id.menuItemDeleteHabit) {
			Habit habit = localStorage.getHabit();
			mDatabase.child("habits").child(habit.getHabitId()).removeValue();
			mDatabase.child("users").child(habit.getClient().getUserId()).child("habits").child(habit.getHabitId()).removeValue();
			finish();
		}

		return super.onOptionsItemSelected(item);
	}

}