package com.example.judgecompanion.server.fragments;

import java.util.ArrayList;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.judgecompanion.R;
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
	@SuppressWarnings("unused")
	private DBHelper dbs;
	public static final String ARG_PAGE = "page";
	private int mPageNumber;

	// private String entryValue = "";
	// private List<SetupEntry> values = new ArrayList<SetupEntry>();

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
		// addExistingValues(null);
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

	/**
	 * Checks DB if there are values to be added to the current view.
	 * 
	 * @param view
	 *            - Tabgroup to which values are to be added.
	 */
	private void addExistingValues(ViewGroup view) {
		if (view != null) {
			DBHelper dbs = DBHelper.getInstance(getActivity());
			switch (mPageNumber) {
			case 1:
				if (dbs.getTeamCount() > 0) {
					addTeams(dbs.getAllTeams(), view);
				}
				break;
			case 2:
				if (dbs.getEventCount() > 0) {
					addEvents(dbs.getAllEvents(), view);
				}
				break;
			default:
				if (dbs.getJudgeCount() > 0) {
					addJudges(dbs.getAllJudges(), view);
				}
				break;
			}
		}
	}

	/**
	 * Add Judges from the DB
	 * 
	 * @param jdgs
	 *            - List of Judges
	 * @param view
	 *            - View to add judges to.
	 */
	private void addJudges(ArrayList<Judges> jdgs, ViewGroup view) {
		if (view != null && view.findViewById(R.id.txt_empty_judges) != null) {
			view.findViewById(R.id.txt_empty_judges).setVisibility(View.GONE);
			final ListView mContainerView = (ListView) view.findViewById(R.id.container_judges);

			final ArrayAdapter<Judges> judgeAdapter = new ArrayAdapter<Judges>(view.getContext(), android.R.layout.simple_list_item_1, jdgs);
			mContainerView.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
					int pos = arg2;
					if (pos >= 0) {
						Judges j = judgeAdapter.getItem(pos);
						AlertDialog.Builder abr = new AlertDialog.Builder(getActivity());
						abr.setTitle("Judge Information");
						abr.setMessage("Name: " + j.getName() + "\nInstitution: " + j.getInstitution() + "\nEmail: " + j.getEmail());
						abr.show();
					}
				}
			});

			mContainerView.setOnItemLongClickListener(new OnItemLongClickListener() {
				@Override
				public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
					int pos = arg2;
					if (pos >= 0) {
						Judges j = judgeAdapter.getItem(pos);
						Toast.makeText(getActivity(), "Passowrd: " + j.getPassword(), Toast.LENGTH_LONG).show();
					}
					return false;
				}
			});
			mContainerView.setAdapter(judgeAdapter);
			mContainerView.setTextFilterEnabled(true);

		}
	}

	private void addEvents(ArrayList<Events> evnts, ViewGroup view) {
		if (view != null && view.findViewById(R.id.txt_empty_events) != null) {
			view.findViewById(R.id.txt_empty_events).setVisibility(View.GONE);
			final ListView mContainerView = (ListView) view.findViewById(R.id.container_events);
			final ArrayAdapter<Events> eventsAdapter = new ArrayAdapter<Events>(view.getContext(), android.R.layout.simple_list_item_1, evnts);
			mContainerView.setAdapter(eventsAdapter);
			mContainerView.setTextFilterEnabled(true);

			mContainerView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
					if (arg2 >= 0) {
						Events et = eventsAdapter.getItem(arg2);
						AlertDialog.Builder abr = new AlertDialog.Builder(getActivity());
						abr.setTitle("Event Information");
						abr.setMessage("Name: " + et.getName() + "\nDescription: " + et.getDescription() + "\n Timed: "
								+ (et.isTimed() ? "Yes" : "No") + "\n Scored:" + (et.isScored() ? "Yes" : "No"));
						abr.show();
					}
				}
			});

			mContainerView.setOnItemLongClickListener(new OnItemLongClickListener() {

				@Override
				public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
					if (arg2 >= 0) {
						Events et = eventsAdapter.getItem(arg2);
						AlertDialog.Builder abr = new AlertDialog.Builder(getActivity());
						abr.setTitle("Event Information");
						abr.setMessage("Name: " + et.getName() + "\nDescription: " + et.getDescription() + "\n Timed: "
								+ (et.isTimed() ? "Yes" : "No") + "\n Scored:" + (et.isScored() ? "Yes" : "No"));
						abr.show();
					}
					return false;
				}
			});
		}
	}

	private void addTeams(ArrayList<Teams> tms, ViewGroup view) {
		if (view != null && view.findViewById(R.id.txt_empty_events) != null) {
			view.findViewById(R.id.txt_empty_events).setVisibility(View.GONE);
			final ListView mContainerView = (ListView) view.findViewById(R.id.container_events);
			final ArrayAdapter<Teams> teamsAdapter = new ArrayAdapter<Teams>(view.getContext(), android.R.layout.simple_list_item_1, tms);
			mContainerView.setAdapter(teamsAdapter);
			mContainerView.setTextFilterEnabled(true);
			
			mContainerView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
					if(arg2 >= 0)
					{
						Teams t = teamsAdapter.getItem(arg2);
						AlertDialog.Builder abr = new AlertDialog.Builder(getActivity());
						abr.setTitle("Team Details");
						ArrayList<String> mList = t.getMemberList();
						String memList = "";
						for(int i = 0; i <= mList.size(); i++)
						{
							memList += "\n" + (i+1) + ". " + mList.get(i);
						}
						
						abr.setMessage("Team Name: " + t.getTeamName() + "\nInstitution: " + t.getInstitution() + "\nTeam Members: " + memList);
						abr.show();
					}
					
				}
			});

		}
	}
}
