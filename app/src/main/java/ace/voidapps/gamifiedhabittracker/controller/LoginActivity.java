package ace.voidapps.gamifiedhabittracker.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import ace.voidapps.gamifiedhabittracker.R;
import ace.voidapps.gamifiedhabittracker.model.AuthenticationActivity;
import ace.voidapps.gamifiedhabittracker.model.LocalStorage;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

	private EditText editTextEmail, editTextPassword;
	private CheckBox checkBoxIsAdmin;
	private Button buttonLogin;
	private TextView textViewToSignup;

	private String email, password;

	private LocalStorage localStorage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		editTextEmail = findViewById(R.id.editTextLoginEmailAddress);
		editTextPassword = findViewById(R.id.editTextLoginPassword);
		checkBoxIsAdmin = findViewById(R.id.checkBoxIsAdmin);
		buttonLogin = findViewById(R.id.buttonLogin);
		textViewToSignup = findViewById(R.id.textViewLoginToSignup);

		buttonLogin.setOnClickListener(this);
		textViewToSignup.setOnClickListener(this);
	}

	@Override
	protected void onStart() {
		localStorage = LocalStorage.getInstance();
		super.onStart();
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {

			case R.id.textViewLoginToSignup:
				finish();
				Intent intentSignup = new Intent(getApplicationContext(), SignupActivity.class);
				startActivity(intentSignup);
				break;

			case R.id.buttonLogin:
				checkValues();
				addValuesToStorage();
				finish();
				Intent intentAuth = new Intent(getApplicationContext(), AuthenticationActivity.class);
				startActivity(intentAuth);
				break;

		}
	}

	private void checkValues() {
		email = editTextEmail.getText().toString();
		password = editTextPassword.getText().toString();

		if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
			editTextEmail.setError("Enter a valid email address");
			editTextEmail.requestFocus();
			return;
		}

		if (password.isEmpty() || password.length() < 8) {
			editTextPassword.setError("Password must be of at least 8 digits");
			editTextPassword.requestFocus();
			return;
		}
	}

	private void addValuesToStorage() {
		localStorage.setAuthAction(1);
		localStorage.setEmail(email);
		localStorage.setPassword(password);
	}

}