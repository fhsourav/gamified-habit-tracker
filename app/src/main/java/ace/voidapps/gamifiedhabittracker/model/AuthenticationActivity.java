package ace.voidapps.gamifiedhabittracker.model;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.time.LocalDate;

import ace.voidapps.gamifiedhabittracker.R;
import ace.voidapps.gamifiedhabittracker.controller.HomeActivity;
import ace.voidapps.gamifiedhabittracker.controller.LoginActivity;
import ace.voidapps.gamifiedhabittracker.controller.SignupActivity;

public class AuthenticationActivity extends AppCompatActivity {

	private static final String TAG = "EmailPassword";

	private LocalStorage localStorage;
	private FirebaseAuth mAuth;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_authentication);
	}

	@Override
	protected void onStart() {
		super.onStart();

		localStorage = LocalStorage.getInstance();
		mAuth = FirebaseAuth.getInstance();
		switch (localStorage.getAuthAction()) {
			case 0:
				createAccount();
				break;
			case 1:
				signIn();
				break;
			case 2:
				signOut();
				break;
		}
	}

	public void createAccount() {

		mAuth.createUserWithEmailAndPassword(localStorage.getEmail(), localStorage.getPassword())
				.addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
					@Override
					public void onComplete(@NonNull Task<AuthResult> task) {
						if (task.isSuccessful()) {
							Log.d(TAG, "createUserWithEmail:success");
							updateUI(mAuth.getCurrentUser());
							Toast.makeText(AuthenticationActivity.this, "Authentication successful", Toast.LENGTH_SHORT).show();
						} else {
							Log.w(TAG, "createUserWithEmail:failure", task.getException());
							updateUI(null);
							Toast.makeText(AuthenticationActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
						}
					}
				});

	}

	public void signIn() {

		mAuth.signInWithEmailAndPassword(localStorage.getEmail(), localStorage.getPassword())
				.addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
					@Override
					public void onComplete(@NonNull Task<AuthResult> task) {
						if (task.isSuccessful()) {
							Log.d(TAG, "signInWithEmail:success");
							updateUI(mAuth.getCurrentUser());
							Toast.makeText(AuthenticationActivity.this, "User logged in", Toast.LENGTH_SHORT).show();
						} else {
							Log.w(TAG, "signInWithEmail:failure", task.getException());
							updateUI(null);
							Toast.makeText(AuthenticationActivity.this, "Log in failed. Incorrect email or password.", Toast.LENGTH_SHORT).show();
						}
					}
				});

	}

	public void signOut() {
		mAuth.signOut();
		finish();
		updateUI(null);
	}


	private void updateUI(FirebaseUser firebaseUser) {
		finish();
		Intent intent;
		if (firebaseUser != null) {
			if (localStorage.getAuthAction() == 0) {
				addUserToDatabase(firebaseUser);
			}
			intent = new Intent(getApplicationContext(), HomeActivity.class);
		} else if (localStorage.getAuthAction() == 0) {
			intent = new Intent(getApplicationContext(), SignupActivity.class);
		} else {
			intent = new Intent(getApplicationContext(), LoginActivity.class);
		}
		startActivity(intent);
	}

	private void addUserToDatabase(FirebaseUser firebaseUser) {
		User user = new Client(firebaseUser.getUid(), localStorage.getUname(), localStorage.getEmail(), localStorage.getFirstName(), localStorage.getLastName(), localStorage.getBirthdate());
		DatabaseAssistant databaseAssistant = new DatabaseAssistant();
		databaseAssistant.writeNewUser(firebaseUser.getUid(), user);
	}

}