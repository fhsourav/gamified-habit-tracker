package ace.voidapps.gamifiedhabittracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalDate;
import java.time.Period;

import ace.voidapps.gamifiedhabittracker.model.Client;
import ace.voidapps.gamifiedhabittracker.model.Habit;
import ace.voidapps.gamifiedhabittracker.model.LocalStorage;

public class AddHabitActivity extends AppCompatActivity {

	private EditText editTextHabitTitle, editTextHabitDetails, editTextHabitPeriodicity;
	private CheckBox checkBoxDoneToday;
	private Button buttonAddHabit;

	private int periodicity;
	private boolean doneToday;
	private String habitTitle, habitDetails;

	private DatabaseReference mDatabase;

	private LocalStorage localStorage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_habit);

		editTextHabitTitle = findViewById(R.id.editTextTextHabitTitle);
		editTextHabitDetails = findViewById(R.id.editTextTextHabitDetails);
		editTextHabitPeriodicity = findViewById(R.id.editTextNumberDecimalPeriodicity);
		checkBoxDoneToday = findViewById(R.id.checkBoxDoneItToday);
		buttonAddHabit = findViewById(R.id.buttonAddHabit);

		mDatabase = FirebaseDatabase.getInstance(LocalStorage.REALTIME_DATABASE).getReference();

		localStorage = LocalStorage.getInstance();

		buttonAddHabit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (checkValues()) {
					AddHabitToDatabase();
					finish();
				}
			}
		});
	}

	private boolean checkValues() {
		habitTitle = editTextHabitTitle.getText().toString();
		habitDetails = editTextHabitDetails.getText().toString();
		String periodicityTemp = editTextHabitPeriodicity.getText().toString();
		doneToday = checkBoxDoneToday.isChecked();

		if (habitTitle.isEmpty()) {
			editTextHabitTitle.setError("Title cannot be empty.");
			editTextHabitTitle.requestFocus();
			return false;
		}

		if (habitDetails.isEmpty()) {
			editTextHabitDetails.setError("Details cannot be empty.");
			editTextHabitTitle.requestFocus();
			return false;
		}

		if (periodicityTemp.isEmpty()) {
			editTextHabitPeriodicity.setError("Periodicity cannot be empty.");
			editTextHabitTitle.requestFocus();
			return false;
		} else {
			periodicity = Integer.parseInt(periodicityTemp);
			if (periodicity < 0) {
				editTextHabitPeriodicity.setError("Periodicity cannot be 0 or less.");
				editTextHabitTitle.requestFocus();
				return false;
			}
		}

		return true;
	}

	private void AddHabitToDatabase() {
		String habitKey = mDatabase.child("habits").getKey();
		LocalDate lastCheckedIn = null;
		if (doneToday) {
			lastCheckedIn = LocalDate.now();
		}
		Habit habit = new Habit(habitKey, (Client) localStorage.getUser(), habitTitle, habitDetails, Period.ofDays(periodicity), lastCheckedIn);
		((Client) localStorage.getUser()).addHabit(habit);
		writeNewHabit(habit);
	}

	public void writeNewHabit(Habit habit) {
		String key = mDatabase.child("habits").push().getKey();
		mDatabase.child("habits").child(key).child("Title").setValue(habit.getTitle());
		mDatabase.child("habits").child(key).child("Details").setValue(habit.getDetails());
		mDatabase.child("habits").child(key).child("ExperiencePoints").setValue(habit.getExp());
		mDatabase.child("habits").child(key).child("Periodicity").setValue(habit.getPeriodicity().toString());

		if (habit.getLastCheckedIn() != null) {
			mDatabase.child("habits").child(key).child("LastCheckedIn").setValue(habit.getLastCheckedIn().toString());
		} else {
			mDatabase.child("habits").child(key).child("LastCheckedIn").setValue(LocalDate.MIN.toString());
		}

		mDatabase.child("habits").child(key).child("CreatedOn").setValue(habit.getCreatedOn().toString());
		mDatabase.child("habits").child(key).child("Streak").setValue(habit.getStreak());
		mDatabase.child("habits").child(key).child("UID").setValue(habit.getClient().getUserId());
		mDatabase.child("users").child(habit.getClient().getUserId()).child("habits").child(key).setValue(habit.getTitle());
	}

}