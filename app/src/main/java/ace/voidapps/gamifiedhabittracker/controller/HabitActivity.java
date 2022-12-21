package ace.voidapps.gamifiedhabittracker.controller;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;

import java.time.LocalDate;
import java.time.Period;

import ace.voidapps.gamifiedhabittracker.R;
import ace.voidapps.gamifiedhabittracker.model.Client;
import ace.voidapps.gamifiedhabittracker.model.DatabaseAssistant;
import ace.voidapps.gamifiedhabittracker.model.Habit;
import ace.voidapps.gamifiedhabittracker.model.LocalStorage;

public class HabitActivity extends AppCompatActivity {

	private TextView textViewHabitTitle, textViewHabitStreak, textViewHabitDetails;
	private Button buttonHabitCheckIn;

	private DatabaseAssistant databaseAssistant;
	private LocalStorage localStorage;

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

		HabitActivity.this.setTitle(userFirstname);

	}

	@Override
	protected void onStart() {
		super.onStart();

		databaseAssistant = new DatabaseAssistant();
		Task<DataSnapshot> dataSnapshotTaskHabit = databaseAssistant.retrieveHabit(localStorage.getHid());

		dataSnapshotTaskHabit.addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
			@RequiresApi(api = Build.VERSION_CODES.O)
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
					Habit habit = new Habit(localStorage.getHid(), (Client) localStorage.getUser(), habitTitle, habitDetails, periodicity, lastCheckedIn);
					localStorage.setHabit(habit);
				} else {

				}
			}
		});

		buttonHabitCheckIn.setOnClickListener(new View.OnClickListener() {
			@RequiresApi(api = Build.VERSION_CODES.O)
			@Override
			public void onClick(View v) {
				Habit habit = localStorage.getHabit();
				habit.checkIn();
				databaseAssistant.habitCheckIn(localStorage.getHabit());
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
			databaseAssistant.deleteHabit(localStorage.getHabit());
			finish();
			Intent intentHome = new Intent(getApplicationContext(), HomeActivity.class);
			startActivity(intentHome);
		}

		return super.onOptionsItemSelected(item);
	}

}