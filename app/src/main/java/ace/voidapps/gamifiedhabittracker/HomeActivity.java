package ace.voidapps.gamifiedhabittracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import ace.voidapps.gamifiedhabittracker.model.Client;
import ace.voidapps.gamifiedhabittracker.model.LocalStorage;
import ace.voidapps.gamifiedhabittracker.model.User;
import ace.voidapps.gamifiedhabittracker.adapters.HabitListAdapter;

public class HomeActivity extends AppCompatActivity {

	private List<String> habitList;
	private List<String> habitKeys;

	private FirebaseUser fUser;
	private FirebaseAuth mAuth;
	private DatabaseReference mDatabase;

	private LocalStorage localStorage;

	private Task<DataSnapshot> dataSnapshotTaskUser;

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

		fUser = FirebaseAuth.getInstance().getCurrentUser();
		mDatabase = FirebaseDatabase.getInstance(LocalStorage.REALTIME_DATABASE).getReference();

		localStorage = LocalStorage.getInstance();

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
		dataSnapshotTaskUser = mDatabase.child("users").child(fUser.getUid()).get();
		dataSnapshotTaskUser.addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
			@Override
			public void onComplete(@NonNull Task<DataSnapshot> task) {
				if (task.isSuccessful()) {
					Log.d("firebase", String.valueOf(task.getResult().getValue()));
					String firstName = task.getResult().child("FirstName").getValue(String.class);
					if (localStorage.getUser() == null) {
						String lastName = task.getResult().child("LastName").getValue(String.class);
						String username = task.getResult().child("Username").getValue(String.class);
						String email = task.getResult().child("Email").getValue(String.class);
						String birthdateString = task.getResult().child("BirthDate").getValue(String.class);
						LocalDate birthdate = LocalDate.parse(birthdateString);
						User user = new Client(fUser.getUid(), username, email, firstName, lastName, birthdate);
						localStorage.setUser(user);
					}
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
				} else {
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
			localStorage.clearAll();
			mAuth = FirebaseAuth.getInstance();
			mAuth.signOut();
			finish();
		}

		return super.onOptionsItemSelected(item);
	}

}