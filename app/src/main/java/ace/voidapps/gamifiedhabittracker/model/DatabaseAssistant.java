package ace.voidapps.gamifiedhabittracker.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DatabaseAssistant {

	private DatabaseReference mDatabase;

	public DatabaseAssistant() {
		mDatabase = FirebaseDatabase.getInstance("https://gamified-habit-tracker-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();
	}

	public void writeNewUser(String uid, User user) {
		mDatabase.child("users").child(uid).child("Username").setValue(user.getUsername());
		mDatabase.child("users").child(uid).child("Email").setValue(user.getEmail());
		mDatabase.child("users").child(uid).child("FirstName").setValue(user.getFirstname());
		mDatabase.child("users").child(uid).child("LastName").setValue(user.getLastName());
		mDatabase.child("users").child(uid).child("BirthDate").setValue(user.getBirthDate().toString());
	}

}
