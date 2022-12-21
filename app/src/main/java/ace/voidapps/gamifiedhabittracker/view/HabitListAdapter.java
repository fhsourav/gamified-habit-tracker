package ace.voidapps.gamifiedhabittracker.view;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;
import java.util.Map;

import ace.voidapps.gamifiedhabittracker.R;

public class HabitListAdapter extends ArrayAdapter<String> {

	private List<String> habitList;
	private Activity context;

	/**
	 * Constructor
	 *
	 * @param context  The current context.
//	 * @param resource The resource ID for a layout file containing a TextView to use when
//	 *                 instantiating views.
	 * @param objects  The objects to represent in the ListView.
	 */
	public HabitListAdapter(@NonNull Activity context, @NonNull List<String> objects) {
		super(context, R.layout.layout_home_habitlist, objects);
		this.habitList = objects;
		this.context = context;
	}

	@NonNull
	@Override
	public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
		LayoutInflater layoutInflater = context.getLayoutInflater();
		View view = layoutInflater.inflate(R.layout.layout_home_habitlist, null, true);
		TextView textView = view.findViewById(R.id.textViewHomeHabitTitle);
		textView.setText(habitList.get(position));
		return view;
	}
}
