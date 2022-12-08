package ace.voidapps.gamifiedhabittracker.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseUser;

import ace.voidapps.gamifiedhabittracker.R;
import ace.voidapps.gamifiedhabittracker.model.Authentication;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {

	private EditText editTextFirstName, editTextLastName, editTextEmail, editTextBirthDate, editTextUname, editTextPassword;
	private Button buttonSignup;
	private TextView textViewToLogin;

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
		buttonSignup = findViewById(R.id.buttonSignup);
		textViewToLogin = findViewById(R.id.textViewSignupToLogin);

		buttonSignup.setOnClickListener(this);
		textViewToLogin.setOnClickListener(this);

	}

	@Override
	public void onClick(View view) {

		switch (view.getId()) {

			case R.id.buttonSignup:
				String email = editTextEmail.getText().toString();
				String password = editTextPassword.getText().toString();
				FirebaseUser user = new Authentication(this, email, password).createAccount();
				updateUI(user);
				break;

			case R.id.textViewSignupToLogin:
				finish();
				Intent intentLogin = new Intent(getApplicationContext(), LoginActivity.class);
				startActivity(intentLogin);
				break;

		}

	}

	private void updateUI(FirebaseUser user) {
		finish();
		Intent intentHome = new Intent(getApplicationContext(), HomeActivity.class);
		startActivity(intentHome);
	}

}