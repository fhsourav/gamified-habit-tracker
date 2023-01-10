package ace.voidapps.gamifiedhabittracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalDate;

import ace.voidapps.gamifiedhabittracker.model.Client;
import ace.voidapps.gamifiedhabittracker.model.LocalStorage;
import ace.voidapps.gamifiedhabittracker.model.User;

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
	private static final String TAG = "EmailPassword";

	private LocalDate birthdate;

	private FirebaseAuth mAuth;

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
				if (checkValues()) {
					createAccount();
				}
				break;

			case R.id.textViewSignupToLogin:
				finish();
				Intent intentLogin = new Intent(getApplicationContext(), LoginActivity.class);
				startActivity(intentLogin);
				break;

		}

	}

	private boolean checkValues() {
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
			return false;
		}

		if (lastName.isEmpty()) {
			editTextLastName.setError("Last name cannot be empty");
			editTextLastName.requestFocus();
			return false;
		}

		if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
			editTextEmail.setError("Enter a valid email address");
			editTextEmail.requestFocus();
			return false;
		}

		if (bDate.isEmpty()) {
			editTextBirthDate.setError("Birthdate cannot be empty");
			editTextBirthDate.requestFocus();
			return false;
		}

		try {
			birthdate = LocalDate.parse(bDate);
		} catch (Exception e) {
			editTextBirthDate.setError("Enter date in YYYY-MM-DD format");
			editTextBirthDate.requestFocus();
			return false;
		}

		if (birthdate.isAfter(LocalDate.now().minusYears(13))) {
			editTextBirthDate.setError("User under 13 years");
			editTextBirthDate.requestFocus();
			return false;
		}

		if (uname.isEmpty()) {
			editTextUname.setError("Username cannot be empty");
			editTextUname.requestFocus();
			return false;
		}

		if (password.isEmpty() || password.length() < 8) {
			editTextPassword.setError("Password must be of at least 8 digits");
			editTextPassword.requestFocus();
			return false;
		}

		if (!confirmPassword.equals(password)) {
			editTextConfirmPassword.setError("Password doesn't match");
			editTextConfirmPassword.requestFocus();
			return false;
		}

		return true;
	}

	public void createAccount() {

		mAuth = FirebaseAuth.getInstance();

		mAuth.createUserWithEmailAndPassword(email, password)
				.addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
					@Override
					public void onComplete(@NonNull Task<AuthResult> task) {
						if (task.isSuccessful()) {
							Log.d(TAG, "createUserWithEmail:success");
							updateUI(mAuth.getCurrentUser());
							Toast.makeText(SignupActivity.this, "Authentication successful", Toast.LENGTH_SHORT).show();
						} else {
							Log.w(TAG, "createUserWithEmail:failure", task.getException());
							Toast.makeText(SignupActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
						}
					}
				});

	}

	private void updateUI(FirebaseUser firebaseUser) {
		if (firebaseUser != null) {
			finish();
			addUserToDatabase(firebaseUser);
			Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
			startActivity(intent);
		}
	}

	private void addUserToDatabase(FirebaseUser firebaseUser) {
		DatabaseReference mDatabase = FirebaseDatabase.getInstance(LocalStorage.REALTIME_DATABASE).getReference();
		User user = new Client(firebaseUser.getUid(), uname, email, firstName, lastName, birthdate);
		String uid = firebaseUser.getUid();
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

}