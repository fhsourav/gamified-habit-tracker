package ace.voidapps.gamifiedhabittracker.controller;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;

import ace.voidapps.gamifiedhabittracker.R;
import ace.voidapps.gamifiedhabittracker.model.AuthenticationActivity;
import ace.voidapps.gamifiedhabittracker.model.LocalStorage;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {

	private EditText editTextFirstName, editTextLastName, editTextEmail, editTextBirthDate,
			editTextUname, editTextPassword, editTextConfirmPassword;
	private Button buttonSignup;
	private TextView textViewToLogin;

	DatePickerDialog.OnDateSetListener dateSetListener;

	private String firstName;
	private String lastName;
	private String email;
	private String uname;
	private String password;
	private LocalDate birthdate;

	private LocalStorage localStorage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signup);

		editTextFirstName = findViewById(R.id.editTextTextSignupFirstName);
		editTextLastName = findViewById(R.id.editTextTextSignupLastName);
		editTextEmail = findViewById(R.id.editTextTextSignupEmailAddress);
		editTextBirthDate = findViewById(R.id.editTextSignupBirthDate);
		editTextUname = findViewById(R.id.editTextTextSignupUname);
		editTextPassword = findViewById(R.id.editTextSignupPassword);
		editTextConfirmPassword = findViewById(R.id.editTextSignupPasswordConfirm);
		buttonSignup = findViewById(R.id.buttonSignup);
		textViewToLogin = findViewById(R.id.textViewSignupToLogin);

		localStorage = LocalStorage.getInstance();

		buttonSignup.setOnClickListener(this);
		textViewToLogin.setOnClickListener(this);
		editTextBirthDate.setOnClickListener(this);

		dateSetListener = new DatePickerDialog.OnDateSetListener() {
			@Override
			public void onDateSet(DatePicker datePicker, int year, int month, int day) {
				month++;
				String date = String.format("%04d-%02d-%02d", year, month, day);
				editTextBirthDate.setText(date);
			}
		};

	}

	@RequiresApi(api = Build.VERSION_CODES.O)
	@Override
	public void onClick(View view) {

		switch (view.getId()) {

			case R.id.editTextSignupBirthDate:
				LocalDate date = LocalDate.now();
				final int year = date.getYear();
				final int month = date.getMonthValue();
				final int day = date.getDayOfMonth();
				DatePickerDialog datePickerDialog = new DatePickerDialog(this, dateSetListener, year, month, day);
				datePickerDialog.show();
				break;

			case R.id.buttonSignup:
				checkValues();
				addValuesToStorage();
				finish();
				Intent intentAuthentication = new Intent(getApplicationContext(), AuthenticationActivity.class);
				startActivity(intentAuthentication);
				break;

			case R.id.textViewSignupToLogin:
				finish();
				Intent intentLogin = new Intent(getApplicationContext(), LoginActivity.class);
				startActivity(intentLogin);
				break;

		}

	}

	private void addValuesToStorage() {
		localStorage.setAuthAction(0);
		localStorage.setUname(uname);
		localStorage.setEmail(email);
		localStorage.setPassword(password);
		localStorage.setFirstName(firstName);
		localStorage.setLastName(lastName);
		localStorage.setBirthdate(birthdate);
	}

	@RequiresApi(api = Build.VERSION_CODES.O)
	private void checkValues() {
		firstName = editTextFirstName.getText().toString();
		lastName = editTextLastName.getText().toString();
		email = editTextEmail.getText().toString();
		String bDate = editTextBirthDate.getText().toString();
		birthdate = null;
		uname = editTextUname.getText().toString();
		password = editTextPassword.getText().toString();
		String confirmPassword = editTextConfirmPassword.getText().toString();

		if (firstName.isEmpty()) {
			editTextFirstName.setError("First name cannot be empty");
			editTextFirstName.requestFocus();
			return;
		}

		if (lastName.isEmpty()) {
			editTextLastName.setError("Last name cannot be empty");
			editTextLastName.requestFocus();
			return;
		}

		if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
			editTextEmail.setError("Enter a valid email address");
			editTextEmail.requestFocus();
			return;
		}

		if (bDate.isEmpty()) {
			editTextBirthDate.setError("Birthdate cannot be empty");
			editTextBirthDate.requestFocus();
			return;
		}

		try {
			birthdate = LocalDate.parse(bDate);
		} catch (Exception e) {
			editTextBirthDate.setError("Enter date in YYYY-MM-DD format");
			editTextBirthDate.requestFocus();
			return;
		}

		if (birthdate.isAfter(LocalDate.now().minusYears(13))) {
			editTextBirthDate.setError("User under 13 years");
			editTextBirthDate.requestFocus();
			return;
		}

		if (uname.isEmpty()) {
			editTextUname.setError("Username cannot be empty");
			editTextUname.requestFocus();
			return;
		}

		if (password.isEmpty() || password.length() < 8) {
			editTextPassword.setError("Password must be of at least 8 digits");
			editTextPassword.requestFocus();
			return;
		}

		if (!confirmPassword.equals(password)) {
			editTextConfirmPassword.setError("Password doesn't match");
			editTextConfirmPassword.requestFocus();
			return;
		}
	}

}