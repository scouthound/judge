package com.example.judgecompanion.server.fragments;

import java.util.ArrayList;
import java.util.List;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.judgecompanion.JudgeOpenHelper;
import com.example.judgecompanion.R;
import com.example.judgecompanion.SetupEntry;
import com.example.judgecompanion.SetupEntry.EntryType;
import com.example.judgecompanion.database.DBHelper;
import com.example.judgecompanion.database.Events;
import com.example.judgecompanion.database.Judges;
import com.example.judgecompanion.database.Teams;
import com.example.judgecompanion.dialogs.AddEventDialogFragment;
import com.example.judgecompanion.dialogs.AddJudgeDialogFragment;
import com.example.judgecompanion.dialogs.AddTeamDialogFragment;
import com.example.judgecompanion.dialogs.abstracts.DialogTemplateFragment;
import com.example.judgecompanion.server.ServerSetupActivity;

// Pages
@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
public class ServerSetupPageFragment extends Fragment {
	private DBHelper dbs;
	public static final String ARG_PAGE = "page";
	private int mPageNumber;
	private String entryValue = "";
	private List<SetupEntry> values = new ArrayList<SetupEntry>();

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
		dbs = DBHelper.getInstance(getActivity());
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
		addExistingValues(rootView);
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
				// getView().findViewById(R.id.txt_empty_teams).setVisibility(View.GONE);
				dataToAdd = "Teams";
				break;
			case 2:
				// getView().findViewById(R.id.txt_empty_events).setVisibility(View.GONE);
				dataToAdd = "Events";
				break;
			default:
				// getView().findViewById(R.id.txt_empty_judges).setVisibility(View.GONE);
				dataToAdd = "Judges";
				break;
			}

			callNewRowDialog(dataToAdd);
		}

		return super.onOptionsItemSelected(item);
	}

	private void callNewRowDialog(String objType) {
		// When you click on the add button, it will select the proper
		ServerSetupActivity ssa = (ServerSetupActivity) this.getActivity();
		DialogTemplateFragment addDialog = null;
		// Display your ill gotten spoils
		switch (mPageNumber) {
		case 0: // Add Judges
			addDialog = new AddJudgeDialogFragment(R.layout.dialog_add_judge, R.layout.dialog_add_judge);
			break;
		case 1: // Add Teams
			addDialog = new AddTeamDialogFragment(R.layout.dialog_add_team, R.layout.dialog_add_team);
			break;
		case 2: // Add Events
			addDialog = new AddEventDialogFragment(R.layout.dialog_add_event, R.layout.dialog_add_event);
			break;
		}
		if (addDialog != null)
			addDialog.show(ssa.getSupportFragmentManager(), objType);
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
			Log.d("WHOOPS", "View was null.");
		} else {
			mContainerView = view;
			Log.d("WHOOPS", "View was not null.");
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
		Log.d("WHOOPS", "Added: " + ent.getEntry());

		// Set a click listener for the "X" button in the row that will
		// remove the row.
		newView.findViewById(R.id.delete_button).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				// Remove the row from its parent (the container view).
				// Because mContainerView has
				// android:animateLayoutChanges set
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
		// true, adding this view is automatically animated.
		mContainerView.addView(newView, 0);
	}

	private void addExistingValues(ViewGroup view) {
		DBHelper dbs = DBHelper.getInstance(getActivity());
		switch (mPageNumber) {
		case 1:
			ArrayList<Teams> tms = dbs.getAllTeams();
			if (tms.size() > 0) {
				addTeams(tms);
			}
			break;
		case 2:
			ArrayList<Events> evnts = dbs.getAllEvents();
			if (evnts.size() > 0) {
				addEvents(evnts);
			}
			break;
		default:
			ArrayList<Judges> jdgs = dbs.getAllJudges();
			if (jdgs.size() > 0) {
				addJudges(jdgs);
			}
			break;
		}

		/*
		 * existingEntries = db.getEntryCount(objType); if (existingEntries > 0)
		 * { List<SetupEntry> temp = db.getAllEntries(objType); if
		 * (!temp.equals(values)) { values = temp; addingExisting = true; for
		 * (SetupEntry ent : values) { entryValue = ent.getEntry();
		 * Log.d("WHOOPS", "Calling addItems(" + entryValue + ")");
		 * addItems(view, ent); } if (getView() != null) { switch (mPageNumber)
		 * { case 1: if (getView().findViewById(R.id.txt_empty_teams) != null)
		 * getView().findViewById(R.id.txt_empty_teams)
		 * .setVisibility(View.GONE); break; case 2: if
		 * (getView().findViewById(R.id.txt_empty_events) != null)
		 * getView().findViewById(R.id.txt_empty_events)
		 * .setVisibility(View.GONE); break; default: if
		 * (getView().findViewById(R.id.txt_empty_judges) != null)
		 * getView().findViewById(R.id.txt_empty_judges)
		 * .setVisibility(View.GONE); break; } } // addingExisting = false; if
		 * (addingExisting) addingExisting = !addingExisting; } }
		 */
	}

	private void addJudges(ArrayList<Judges> jdgs) {
		getView().findViewById(R.id.txt_empty_judges).setVisibility(View.GONE);
		final ViewGroup mContainerView = (ViewGroup) getView().findViewById(R.id.container_judges);
		final ViewGroup newView = (ViewGroup) LayoutInflater.from(getActivity()).inflate(R.layout.list_item_layout, mContainerView, false);
		for (final Judges js : jdgs) {
			TextView tvJudges = new TextView(getActivity());
			tvJudges.setText(js.getName().toString());
			tvJudges.setTag(js);
			tvJudges.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					AlertDialog.Builder adb = new AlertDialog.Builder(getActivity());
					adb.setTitle(js.getName() + " Details");
					adb.setMessage("Name: " + js.getName() + "\nInstitution: " + js.getInstitution() + "\nEmail: " + js.getEmail()
							+ "\nPassword Assigned: " + js.getPassword());
					adb.show();
				}
			});
			newView.addView(tvJudges);
		}
	}

	private void addEvents(ArrayList<Events> evnts) {
		getView().findViewById(R.id.txt_empty_events).setVisibility(View.GONE);
		final ViewGroup mContainerView = (ViewGroup) getView().findViewById(R.id.container_events);
		final ViewGroup newView = (ViewGroup) LayoutInflater.from(getActivity()).inflate(R.layout.list_item_layout, mContainerView, false);
		for (final Events et : evnts) {
			TextView tvEvent = new TextView(getActivity());
			tvEvent.setText(et.getName().toString());
			tvEvent.setTag(et);
			tvEvent.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					AlertDialog.Builder adb = new AlertDialog.Builder(getActivity());
					adb.setTitle(et.getName() + " Details");
					adb.setMessage("Name: " + et.getName() + "\nDescription: " + et.getDescription() + "\nTimed: " + String.valueOf(et.isTimed())
							+ "\nScored: " + String.valueOf(et.isScored()));
					adb.show();
				}
			});
			newView.addView(tvEvent);
		}
	}

	private void addTeams(ArrayList<Teams> tms) {
		getView().findViewById(R.id.txt_empty_events).setVisibility(View.GONE);
		final ViewGroup mContainerView = (ViewGroup) getView().findViewById(R.id.container_events);
		final ViewGroup newView = (ViewGroup) LayoutInflater.from(getActivity()).inflate(R.layout.list_item_layout, mContainerView, false);
		for (final Teams tm : tms) {
			TextView tvTeam = new TextView(getActivity());
			tvTeam.setText(tm.getTeamName().toString());
			tvTeam.setTag(tm);
			tvTeam.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					StringBuilder sb = new StringBuilder();
					for (String t : tm.getMemberList()) {
						sb.append(t + "\n");
					}
					AlertDialog.Builder adb = new AlertDialog.Builder(getActivity());
					adb.setTitle(tm.getTeamName() + " Details");
					adb.setMessage("Name: " + tm.getTeamName() + "\nInstitution: " + tm.getInstitution() + "\nMembers:\n" + sb.toString());
					adb.show();
				}
			});
			newView.addView(tvTeam);
		}
	}

	/*
	 * public DBHelper getDbs() { return dbs; }
	 * 
	 * 
	 * public void setDbs(DBHelper dbs) { this.dbs = dbs; }
	 */
}
