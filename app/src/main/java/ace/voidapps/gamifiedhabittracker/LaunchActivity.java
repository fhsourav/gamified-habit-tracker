package ace.voidapps.gamifiedhabittracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LaunchActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_launch);
	}

	@Override
	protected void onStart() {
		super.onStart();
		finish();
		FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
		if (currentUser != null) {
			Intent homeIntent = new Intent(getApplicationContext(), HomeActivity.class);
			startActivity(homeIntent);
		} else {
			Intent loginIntent = new Intent(getApplicationContext(), LoginActivity.class);
			startActivity(loginIntent);
		}
	}

}