package ace.voidapps.gamifiedhabittracker.model;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Authentication extends Activity {

	private static final String TAG = "EmailPassword";

	private String email;
	private String password;
	private final Context context;

	private final FirebaseAuth mAuth;
	private FirebaseUser user = null;

	public Authentication(Context context, String email, String password) {
		this.context = context;
		this.email = email;
		this.password = password;
		mAuth = FirebaseAuth.getInstance();
	}

	public Authentication(Context context) {
		this.context = context;
		mAuth = FirebaseAuth.getInstance();
	}

	public FirebaseUser signIn() {

		mAuth.signInWithEmailAndPassword(email, password)
				.addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
					@Override
					public void onComplete(@NonNull Task<AuthResult> task) {
						if (task.isSuccessful()) {
							Log.d(TAG, "signInWithEmail:success");
							user = mAuth.getCurrentUser();
						} else {
							Log.w(TAG, "signInWithEmail:Failure", task.getException());
							Toast.makeText(context, "Authentication failed.", Toast.LENGTH_SHORT).show();
						}
					}
				});

		return user;

	}

	public FirebaseUser createAccount() {

		mAuth.createUserWithEmailAndPassword(email, password)
				.addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
					@Override
					public void onComplete(@NonNull Task<AuthResult> task) {
						if (task.isSuccessful()) {
							Log.d(TAG, "createUserWithEmail:success");
							user = mAuth.getCurrentUser();
							Toast.makeText(context, "Authentication successful", Toast.LENGTH_SHORT).show();
						} else {
							Log.w(TAG, "createUserWithEmail:failure", task.getException());
							Toast.makeText(context, "Authentication failed.", Toast.LENGTH_SHORT).show();
						}
					}
				});

		return user;

	}

	public FirebaseUser checkLogin() {
		return mAuth.getCurrentUser();
	}

	public void signOut() {
		mAuth.signOut();
	}

}
