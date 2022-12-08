package ace.voidapps.gamifiedhabittracker.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseUser;

import ace.voidapps.gamifiedhabittracker.R;
import ace.voidapps.gamifiedhabittracker.model.Authentication;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

	private EditText editTextEmail, editTextPassword;
	private CheckBox checkBoxIsAdmin;
	private Button buttonLogin;
	private TextView textViewToSignup;

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
	public void onClick(View view) {
		switch (view.getId()) {

			case R.id.textViewLoginToSignup:
				finish();
				Intent intentSignup = new Intent(getApplicationContext(), SignupActivity.class);
				startActivity(intentSignup);
				break;

			case R.id.buttonLogin:
				String email = editTextEmail.getText().toString();
				String password = editTextPassword.getText().toString();
				FirebaseUser user = new Authentication(this, email, password).signIn();
				updateUI(user);
				break;

		}
	}

	private void updateUI(FirebaseUser user) {
		finish();
		Intent intentHome = new Intent(getApplicationContext(), HomeActivity.class);
		startActivity(intentHome);
	}

}