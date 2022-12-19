package ace.voidapps.gamifiedhabittracker.controller;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseUser;

import java.time.LocalDateTime;
import java.time.Period;

import ace.voidapps.gamifiedhabittracker.R;
import ace.voidapps.gamifiedhabittracker.model.Client;
import ace.voidapps.gamifiedhabittracker.model.DatabaseAssistant;
import ace.voidapps.gamifiedhabittracker.model.Habit;
import ace.voidapps.gamifiedhabittracker.model.LocalStorage;
import ace.voidapps.gamifiedhabittracker.model.User;

public class AddHabitActivity extends AppCompatActivity {

	private EditText editTextHabitTitle, editTextHabitDetails, editTextHabitPeriodicity;
	private CheckBox checkBoxDoneToday;
	private Button buttonAddHabit;

	private int periodicity;
	private boolean doneToday;
	private String habitTitle, habitDetails;

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

		localStorage = LocalStorage.getInstance();

		buttonAddHabit.setOnClickListener(new View.OnClickListener() {
			@RequiresApi(api = Build.VERSION_CODES.O)
			@Override
			public void onClick(View v) {
				checkValues();
				AddHabitToDatabase();
				finish();
			}
		});
	}

	private void checkValues() {
		habitTitle = editTextHabitTitle.getText().toString();
		habitDetails = editTextHabitDetails.getText().toString();
		String periodicityTemp = editTextHabitPeriodicity.getText().toString();
		doneToday = checkBoxDoneToday.isChecked();

		if (habitTitle.isEmpty()) {
			editTextHabitTitle.setError("Title cannot be empty.");
			editTextHabitTitle.requestFocus();
		}

		if (habitDetails.isEmpty()) {
			editTextHabitDetails.setError("Details cannot be empty.");
			editTextHabitTitle.requestFocus();
		}

		if (periodicityTemp.isEmpty()) {
			editTextHabitPeriodicity.setError("Periodicity cannot be empty.");
			editTextHabitTitle.requestFocus();
		} else {
			periodicity = Integer.parseInt(periodicityTemp);
			if (periodicity <= 0) {
				editTextHabitPeriodicity.setError("Periodicity cannot be 0 or less.");
				editTextHabitTitle.requestFocus();
			}
		}
	}

	@RequiresApi(api = Build.VERSION_CODES.O)
	private void AddHabitToDatabase() {
		DatabaseAssistant databaseAssistant = new DatabaseAssistant();
		String habitKey = databaseAssistant.getHabitKey();
		LocalDateTime lastCheckedIn = null;
		if (doneToday) {
			lastCheckedIn = LocalDateTime.now();
		}
		Habit habit = new Habit(habitKey, (Client) localStorage.getUser(), habitTitle, habitDetails, Period.ofDays(periodicity), lastCheckedIn);
		((Client) localStorage.getUser()).addHabit(habit);
		databaseAssistant.writeNewHabit(localStorage.getUser().getUserId(), habit);
	}

}