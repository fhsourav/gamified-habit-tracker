package ace.voidapps.gamifiedhabittracker.controller;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;

import java.time.LocalDate;
import java.util.HashMap;

import ace.voidapps.gamifiedhabittracker.R;
import ace.voidapps.gamifiedhabittracker.model.Admin;
import ace.voidapps.gamifiedhabittracker.model.AuthenticationActivity;
import ace.voidapps.gamifiedhabittracker.model.Client;
import ace.voidapps.gamifiedhabittracker.model.DatabaseAssistant;
import ace.voidapps.gamifiedhabittracker.model.LocalStorage;
import ace.voidapps.gamifiedhabittracker.model.User;

public class HomeActivity extends AppCompatActivity {

	private LocalStorage localStorage;
	private DatabaseAssistant databaseAssistant;

	private FirebaseUser fUser;
	private Task<DataSnapshot> dataSnapshotTaskUser;

	private TextView textViewName;
	private FloatingActionButton floatingActionButtonAddHabit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		textViewName = findViewById(R.id.textViewName);
		floatingActionButtonAddHabit = findViewById(R.id.floatingActionButtonAddHabit);

		floatingActionButtonAddHabit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intentAddHabit = new Intent(getApplicationContext(), AddHabitActivity.class);
				startActivity(intentAddHabit);
			}
		});
	}

	@Override
	protected void onStart() {
		super.onStart();
		localStorage = LocalStorage.getInstance();
		fUser = FirebaseAuth.getInstance().getCurrentUser();
		databaseAssistant = new DatabaseAssistant();
		dataSnapshotTaskUser = databaseAssistant.retrieveUser(fUser.getUid());
		dataSnapshotTaskUser.addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
			@RequiresApi(api = Build.VERSION_CODES.O)
			@Override
			public void onComplete(@NonNull Task<DataSnapshot> task) {
				if (task.isSuccessful()) {
					Log.d("firebase", String.valueOf(task.getResult().getValue()));
					setUserToLocalStorage(task);
					String firstName = task.getResult().child("FirstName").getValue(String.class);
					Log.d("test", "First name = " + firstName);
					String habits = "";
					for (DataSnapshot dsHabit:
					task.getResult().child("habits").getChildren()) {
						habits += dsHabit.getValue(String.class);
					}
					if (habits.isEmpty()) {
						habits = "No habits yet!";
					}
					HomeActivity.this.setTitle(firstName);
					textViewName.setText(habits);
				}
				else {
					Log.e("firebase", "Error getting data", task.getException());
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

	@RequiresApi(api = Build.VERSION_CODES.O)
	private void setUserToLocalStorage(Task<DataSnapshot> task) {
		User user;
		boolean isAdmin = Boolean.TRUE.equals(task.getResult().child("IsAdmin").getValue(Boolean.class));
		if (!isAdmin) {
			user = new Client(
					fUser.getUid(),
					task.getResult().child("Username").getValue(String.class),
					task.getResult().child("Email").getValue(String.class),
					task.getResult().child("FirstName").getValue(String.class),
					task.getResult().child("LastName").getValue(String.class),
					LocalDate.parse(task.getResult().child("BirthDate").getValue(String.class)));
			((Client) user).setExp(task.getResult().child("ExperiencePoints").getValue(Integer.class));
			localStorage.setUser(user);
		}
		else {
			user = new Admin(
					fUser.getUid(),
					task.getResult().child("Username").getValue(String.class),
					task.getResult().child("Email").getValue(String.class),
					task.getResult().child("FirstName").getValue(String.class),
					task.getResult().child("LastName").getValue(String.class),
					LocalDate.parse(task.getResult().child("BirthDate").getValue(String.class)));
		}
	}

}