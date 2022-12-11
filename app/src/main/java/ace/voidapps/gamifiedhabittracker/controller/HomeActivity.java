package ace.voidapps.gamifiedhabittracker.controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;

import ace.voidapps.gamifiedhabittracker.R;
import ace.voidapps.gamifiedhabittracker.model.AuthenticationActivity;
import ace.voidapps.gamifiedhabittracker.model.DatabaseAssistant;
import ace.voidapps.gamifiedhabittracker.model.LocalStorage;
import ace.voidapps.gamifiedhabittracker.model.User;

public class HomeActivity extends AppCompatActivity {

	private LocalStorage localStorage;
	private DatabaseAssistant databaseAssistant;

	private FirebaseUser fUser;

	private TextView textViewName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		textViewName = findViewById(R.id.textViewName);
	}

	@Override
	protected void onStart() {
		super.onStart();
		localStorage = LocalStorage.getInstance();
		fUser = FirebaseAuth.getInstance().getCurrentUser();
		databaseAssistant = new DatabaseAssistant();
		Task<DataSnapshot> dataSnapshotTask = databaseAssistant.retrieveUser(fUser.getUid());
		dataSnapshotTask.addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
			@Override
			public void onComplete(@NonNull Task<DataSnapshot> task) {
				if (!task.isSuccessful()) {
					Log.e("firebase", "Error getting data", task.getException());
				}
				else {
					Log.d("firebase", String.valueOf(task.getResult().getValue()));
					textViewName.setText(String.valueOf(task.getResult().getValue()));
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_topbar, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(@NonNull MenuItem item) {

		if (item.getItemId() == R.id.menuItemSignOut) {
			localStorage.setAuthAction(2);
			finish();
			Intent authenticationIntent = new Intent(getApplicationContext(), AuthenticationActivity.class);
			startActivity(authenticationIntent);
		}

		return super.onOptionsItemSelected(item);
	}
}