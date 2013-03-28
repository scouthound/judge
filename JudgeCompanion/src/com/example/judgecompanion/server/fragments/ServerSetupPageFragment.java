package com.example.judgecompanion.server.fragments;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

import com.example.judgecompanion.JudgeOpenHelper;
import com.example.judgecompanion.R;
import com.example.judgecompanion.SetupEntry;
import com.example.judgecompanion.SetupEntry.EntryType;

// Pages
@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
public class ServerSetupPageFragment extends Fragment {
	public static final String ARG_PAGE = "page";
	private int mPageNumber;
	private String entryValue = "";
	private List<SetupEntry> values = new ArrayList<SetupEntry>();
	private boolean addingExisting = false;

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
	}

	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		getActivity().getMenuInflater().inflate(R.menu.server_create, menu);
	}

	public void onSavedInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
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
		/* addExistingValues(rootView); */
		return rootView;
	}

	public int getPageNumber() {
		return mPageNumber;
	}

	@Override
	public void onStart() {
		super.onStart();
		addExistingValues(null);
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
		callNewRowDialog(objType, null);
	}

	private void callNewRowDialog(final String objType, String msg) {
		AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

		alert.setTitle("Please enter the name for " + objType + " to enter.");
		if (msg != null) {
			alert.setMessage(msg);
		}

		// Set an EditText view to get user input
		final EditText input = new EditText(getActivity());
		alert.setView(input);

		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				Editable value = input.getText();
				entryValue = value.toString();

				if (validateInput(entryValue) && !entryValue.isEmpty()) {
					addItems(null, null);
				} else {
					callNewRowDialog(objType, "Only letters, digits and spaces are allowed for input.");
				}
			}

			private boolean validateInput(String entryValue) {
				Pattern p = Pattern.compile("^[^a-z0-9 ]+$", Pattern.CASE_INSENSITIVE);
				Matcher m = p.matcher(entryValue);
				return !m.find();
			}
		});

		alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				// Canceled.
				switch (mPageNumber) {
				case 1:
					getView().findViewById(R.id.txt_empty_teams).setVisibility(View.VISIBLE);
					break;
				case 2:
					getView().findViewById(R.id.txt_empty_events).setVisibility(View.VISIBLE);
					break;
				default:
					getView().findViewById(R.id.txt_empty_judges).setVisibility(View.VISIBLE);
					break;
				}
			}
		});

		alert.show();
	}

	public void addItems(ViewGroup view, SetupEntry ent) {
		final ViewGroup mContainerView;

		if (view == null) {
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
			Log.d("FUCK", "View was null.");
		} else {
			mContainerView = view;
			Log.d("FUCK", "View was not null.");
			return;
		}

		// Instantiate a new "row" view.
		final ViewGroup newView = (ViewGroup) LayoutInflater.from(getActivity()).inflate(R.layout.list_item_layout, mContainerView, false);

		if (entryValue.isEmpty()) {
			entryValue = "No value got passed. :(";
		}

		if (ent == null) {
			switch (mPageNumber) {
			case 1:
				ent = new SetupEntry(entryValue, EntryType.TEAM);
				break;
			case 2:
				ent = new SetupEntry(entryValue, EntryType.EVENT);
				break;
			default:
				ent = new SetupEntry(entryValue, EntryType.JUDGE);
				break;
			}
		}

		if (!values.contains(ent)) {
			JudgeOpenHelper db = new JudgeOpenHelper(getActivity());
			db.addEntry(ent);
		}

		// Set the text in the new row to a random country.
		((TextView) newView.findViewById(R.id.text1)).setText(ent.getEntry());
		Log.d("FUCK", "Added: " + ent.getEntry());

		// Set a click listener for the "X" button in the row that will
		// remove the row.
		newView.findViewById(R.id.delete_button).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				// Remove the row from its parent (the container view).
				// Because mContainerView has android:animateLayoutChanges set
				// to true,
				// this removal is automatically animated.

				String str = (String) ((TextView) newView.findViewById(R.id.text1)).getText();
				SetupEntry ent;
				switch (mPageNumber) {
				case 1:
					ent = new SetupEntry(str, EntryType.TEAM);
					break;
				case 2:
					ent = new SetupEntry(str, EntryType.EVENT);
					break;
				default:
					ent = new SetupEntry(str, EntryType.JUDGE);
					break;
				}
				JudgeOpenHelper db = new JudgeOpenHelper(getActivity());
				db.deleteEntry(ent);

				mContainerView.removeView(newView);
				// If there are no rows remaining, show the empty
				// view.
				if (mContainerView.getChildCount() == 0) {
					switch (mPageNumber) {
					case 1:
						getView().findViewById(R.id.txt_empty_teams).setVisibility(View.VISIBLE);
						break;
					case 2:
						getView().findViewById(R.id.txt_empty_events).setVisibility(View.VISIBLE);
						break;
					default:
						getView().findViewById(R.id.txt_empty_judges).setVisibility(View.VISIBLE);
						break;
					}
				}
			}
		});

		// Because mContainerView has android:animateLayoutChanges set to
		// true,
		// adding this view is automatically animated.
		mContainerView.addView(newView, 0);
	}

	private void addExistingValues(ViewGroup view) {
		JudgeOpenHelper db = new JudgeOpenHelper(getActivity());
		int existingEntries = -1;
		EntryType objType;
		switch (mPageNumber) {
		case 1:
			objType = EntryType.TEAM;
			break;
		case 2:
			objType = EntryType.EVENT;
			break;
		default:
			objType = EntryType.JUDGE;
			break;
		}
		existingEntries = db.getEntryCount(objType);
		if (existingEntries > 0) {
			List<SetupEntry> temp = db.getAllEntries(objType);
			if (!temp.equals(values)) {
				values = temp;
				addingExisting = true;
				for (SetupEntry ent : values) {
					entryValue = ent.getEntry();
					Log.d("FUCK", "Calling addItems(" + entryValue + ")");
					addItems(view, ent);
				}
				if (getView() != null) {
					switch (mPageNumber) {
					case 1:
						if (getView().findViewById(R.id.txt_empty_teams) != null)
							getView().findViewById(R.id.txt_empty_teams).setVisibility(View.GONE);
						break;
					case 2:
						if (getView().findViewById(R.id.txt_empty_events) != null)
							getView().findViewById(R.id.txt_empty_events).setVisibility(View.GONE);
						break;
					default:
						if (getView().findViewById(R.id.txt_empty_judges) != null)
							getView().findViewById(R.id.txt_empty_judges).setVisibility(View.GONE);
						break;
					}
				}
				//addingExisting = false;
				if(addingExisting)
					addingExisting = !addingExisting;
			}
		}
	}
}
