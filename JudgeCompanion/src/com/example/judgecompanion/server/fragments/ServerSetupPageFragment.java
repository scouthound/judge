package com.example.judgecompanion.server.fragments;

import java.util.ArrayList;
import java.util.Set;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.judgecompanion.R;

// Pages
@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
public class ServerSetupPageFragment extends Fragment {
	public static final String ARG_PAGE = "page";
	private int mPageNumber;
	private String entryValue = "";
	private ArrayList<String> values = new ArrayList<String>();

	public static ServerSetupPageFragment create(int pageNumber) {
		ServerSetupPageFragment fragment = new ServerSetupPageFragment();
		Bundle args = new Bundle();
		args.putInt(ARG_PAGE, pageNumber);
		fragment.setArguments(args);
		return fragment;
	}

	public ServerSetupPageFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		mPageNumber = getArguments().getInt(ARG_PAGE);
		if (savedInstanceState != null) {
			switch (mPageNumber) {
			case 1:
				values = savedInstanceState.getStringArrayList("Teams");
				break;
			case 2:
				values = savedInstanceState.getStringArrayList("Events");
				break;
			default:
				if (savedInstanceState.containsKey("Judges")) {
					values = new ArrayList<String>(savedInstanceState.getStringArrayList("Judges"));
				} else {
					Set<String> st = savedInstanceState.keySet();
					String outString = "Outputting Keys...";
					for (String str : st) {
						outString += str + "\n";
					}
					outString += "Done...";
					Toast.makeText(getActivity(), outString, Toast.LENGTH_LONG).show();
				}
				break;
			}

			addExistingValues();
		}
	}

	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		getActivity().getMenuInflater().inflate(R.menu.server_create, menu);
	}

	public void onSavedInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		switch (mPageNumber) {
		case 1:
			outState.putStringArrayList("Teams", values);
			break;
		case 2:
			outState.putStringArrayList("Events", values);
			break;
		default:
			outState.putStringArrayList("Judges", values);
			break;
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		ViewGroup rootView;
		switch (mPageNumber) {
		case 1:
			rootView = (ViewGroup) inflater.inflate(R.layout.fragment_server_teams, container, false);
			break;
		case 2:
			rootView = (ViewGroup) inflater.inflate(R.layout.fragment_server_events, container, false);

			break;
		case 3:
			rootView = (ViewGroup) inflater.inflate(R.layout.fragment_server_create, container, false);
			break;
		default:
			rootView = (ViewGroup) inflater.inflate(R.layout.fragment_server_judges, container, false);
		}

		return rootView;
	}

	public int getPageNumber() {
		return mPageNumber;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		String dataToAdd = "";
		switch (item.getItemId()) {
		case R.id.add_new_row:
			// Hide the "empty" view since there is now at least one item in the
			// list.

			switch (mPageNumber) {
			case 1:
				getView().findViewById(R.id.txt_empty_teams).setVisibility(View.GONE);
				dataToAdd = "Teams";
				break;
			case 2:
				getView().findViewById(R.id.txt_empty_events).setVisibility(View.GONE);
				dataToAdd = "Events";
				break;
			default:
				getView().findViewById(R.id.txt_empty_judges).setVisibility(View.GONE);
				dataToAdd = "Judges";
				break;
			}

			callNewRowDialog(dataToAdd);
		}

		return super.onOptionsItemSelected(item);
	}

	private void callNewRowDialog(String objType) {
		AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

		alert.setTitle("Please enter the name for " + objType + " to enter.");
		// alert.setMessage("Message");

		// Set an EditText view to get user input
		final EditText input = new EditText(getActivity());
		alert.setView(input);

		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				Editable value = input.getText();
				entryValue = value.toString();
				addItems();
			}
		});

		alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				// Canceled.
			}
		});

		alert.show();
	}

	public void addItems() {
		final ViewGroup mContainerView;

		switch (mPageNumber) {
		case 1:
			mContainerView = (ViewGroup) getView().findViewById(R.id.container_teams);
			break;
		case 2:
			mContainerView = (ViewGroup) getView().findViewById(R.id.container_events);
			break;
		default:
			mContainerView = (ViewGroup) getView().findViewById(R.id.container_judges);
			break;
		}

		Log.d("Event", "Event adding...");

		// Instantiate a new "row" view.
		final ViewGroup newView = (ViewGroup) LayoutInflater.from(getActivity()).inflate(R.layout.list_item_layout, mContainerView, false);

		if (entryValue.isEmpty()) {
			entryValue = "No value got passed. :(";
		}
		if (!values.contains(entryValue))
			values.add(entryValue);
		// Set the text in the new row to a random country.
		((TextView) newView.findViewById(R.id.text1)).setText(entryValue);

		// Set a click listener for the "X" button in the row that will
		// remove the row.
		newView.findViewById(R.id.delete_button).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				// Remove the row from its parent (the container
				// view).
				// Because mContainerView has
				// android:animateLayoutChanges set to true,
				// this removal is automatically animated.

				String str = (String) ((TextView) newView.findViewById(R.id.text1)).getText();
				values.remove(str);
				mContainerView.removeView(newView);
				// If there are no rows remaining, show the empty
				// view.
				if (mContainerView.getChildCount() == 0) {
					getView().findViewById(android.R.id.empty).setVisibility(View.VISIBLE);
				}
			}
		});

		// Because mContainerView has android:animateLayoutChanges set to
		// true,
		// adding this view is automatically animated.
		mContainerView.addView(newView, 0);
	}

	private void addExistingValues() {
		if (values != null) {
			for (String s : values) {
				entryValue = s;
				addItems();
			}
		}
	}
}
