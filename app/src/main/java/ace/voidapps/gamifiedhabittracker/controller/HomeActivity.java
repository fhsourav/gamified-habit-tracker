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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import ace.voidapps.gamifiedhabittracker.R;
import ace.voidapps.gamifiedhabittracker.model.Admin;
import ace.voidapps.gamifiedhabittracker.model.AuthenticationActivity;
import ace.voidapps.gamifiedhabittracker.model.Client;
import ace.voidapps.gamifiedhabittracker.model.DatabaseAssistant;
import ace.voidapps.gamifiedhabittracker.model.LocalStorage;
import ace.voidapps.gamifiedhabittracker.model.User;
import ace.voidapps.gamifiedhabittracker.view.HabitListAdapter;

public class HomeActivity extends AppCompatActivity {

	private List<String> habitList;
	private List<String> habitKeys;

	private LocalStorage localStorage;
	private DatabaseAssistant databaseAssistant;

	private FirebaseUser fUser;
	private Task<DataSnapshot> dataSnapshotTaskUser;
	private Task<DataSnapshot> dataSnapShotHabit;

	private TextView textViewNoHabits;
	private FloatingActionButton floatingActionButtonAddHabit;
	private ListView listViewHabitList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		textViewNoHabits = findViewById(R.id.textViewNoHabits);
		floatingActionButtonAddHabit = findViewById(R.id.floatingActionButtonAddHabit);
		listViewHabitList = findViewById(R.id.listViewHabitList);

		floatingActionButtonAddHabit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intentAddHabit = new Intent(getApplicationContext(), AddHabitActivity.class);
				startActivity(intentAddHabit);
			}
		});

		listViewHabitList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				localStorage.setHid(habitKeys.get(position));
				Intent intentHabit = new Intent(getApplicationContext(), HabitActivity.class);
				startActivity(intentHabit);
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
					HomeActivity.this.setTitle(firstName);
					habitList = new ArrayList<>();
					habitKeys = new ArrayList<>();
					for (DataSnapshot dsHabit:
					task.getResult().child("habits").getChildren()) {
						habitList.add(dsHabit.getValue(String.class));
						habitKeys.add(dsHabit.getKey());
					}
					if (!habitList.isEmpty()) {
						textViewNoHabits.setVisibility(View.INVISIBLE);
						listViewHabitList.setAdapter(new HabitListAdapter(HomeActivity.this, habitList));
					}
				}
				else {
					Log.e("firebase", "Error getting data", task.getException());
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_home_topbar, menu);
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