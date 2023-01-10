package ace.voidapps.gamifiedhabittracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

	private EditText editTextEmail, editTextPassword;
	private Button buttonLogin;
	private TextView textViewToSignup;

	private String email, password;
	private static final String TAG = "EmailPassword";

	private FirebaseAuth mAuth;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		editTextEmail = findViewById(R.id.editTextLoginEmailAddress);
		editTextPassword = findViewById(R.id.editTextLoginPassword);
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
				if (checkValues()) {
					signIn();
				}
				break;

		}
	}

	private boolean checkValues() {
		email = editTextEmail.getText().toString();
		password = editTextPassword.getText().toString();

		if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
			editTextEmail.setError("Enter a valid email address");
			editTextEmail.requestFocus();
			return false;
		}

		if (password.isEmpty() || password.length() < 8) {
			editTextPassword.setError("Password must be of at least 8 digits");
			editTextPassword.requestFocus();
			return false;
		}

		return true;
	}

	public void signIn() {

		mAuth = FirebaseAuth.getInstance();

		mAuth.signInWithEmailAndPassword(email, password)
				.addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
					@Override
					public void onComplete(@NonNull Task<AuthResult> task) {
						if (task.isSuccessful()) {
							Log.d(TAG, "signInWithEmail:success");
							updateUI(mAuth.getCurrentUser());
							Toast.makeText(LoginActivity.this, "User logged in", Toast.LENGTH_SHORT).show();
						} else {
							Log.w(TAG, "signInWithEmail:failure", task.getException());
							Toast.makeText(LoginActivity.this, "Log in failed. Incorrect email or password.", Toast.LENGTH_SHORT).show();
						}
					}
				});

	}

	private void updateUI(FirebaseUser firebaseUser) {
		if (firebaseUser != null) {
			finish();
			Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
			startActivity(intent);
		}
	}

}