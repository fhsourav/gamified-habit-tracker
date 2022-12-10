package ace.voidapps.gamifiedhabittracker.controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import ace.voidapps.gamifiedhabittracker.R;
import ace.voidapps.gamifiedhabittracker.model.AuthenticationActivity;
import ace.voidapps.gamifiedhabittracker.model.LocalStorage;

public class HomeActivity extends AppCompatActivity {

	private LocalStorage localStorage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		localStorage = LocalStorage.getInstance();
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