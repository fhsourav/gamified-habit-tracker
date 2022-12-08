package ace.voidapps.gamifiedhabittracker.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseUser;

import ace.voidapps.gamifiedhabittracker.R;
import ace.voidapps.gamifiedhabittracker.model.Authentication;

public class LaunchActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_launch);
	}

	@Override
	protected void onStart() {
		super.onStart();
		FirebaseUser currentUser = new Authentication(this).checkLogin();
		finish();
		if (currentUser != null) {
			Intent homeIntent = new Intent(getApplicationContext(), HomeActivity.class);
			startActivity(homeIntent);
		} else {
			Intent loginIntent = new Intent(getApplicationContext(), LoginActivity.class);
			startActivity(loginIntent);
		}
	}

}