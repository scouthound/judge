package com.example.judgecompanion.dialogs;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.judgecompanion.R;
import com.example.judgecompanion.dialogs.abstracts.DialogTemplateFragment;

@SuppressLint("ValidFragment")
public class TeamDetailDialogFragment extends DialogTemplateFragment {

	@SuppressLint("ValidFragment")
	public TeamDetailDialogFragment(int layoutport, int layoutland) {
		super(layoutport, layoutland);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void confirm() {
		// If you need to save something somewhere, use this method
	}

	@Override
	public void buildData() {
		// TODO Auto-generated method stub
		List<String> listOfEvents = new ArrayList<String>();
		listOfEvents.add("Science Fair: 100 pts");
		listOfEvents.add("Stuff: 50 pts");
		
		TextView name = (TextView) theView.findViewById(R.id.team_name);
		TextView teammates = (TextView) theView.findViewById(R.id.team_members);
		name.setText("Team Discovery Channel");
		teammates.setText("\nMembers:\nSimpson, Bart\nPrince, Martin");
		
		ListView events = (ListView) theView.findViewById(R.id.event_list);
		ArrayAdapter<String> eventAdapter = new ArrayAdapter<String>(theView.getContext(), android.R.layout.simple_list_item_1, listOfEvents);
		events.setAdapter(eventAdapter);
		events.setTextFilterEnabled(true);
	}

	

}
