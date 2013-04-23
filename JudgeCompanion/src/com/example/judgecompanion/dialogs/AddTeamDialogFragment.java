package com.example.judgecompanion.dialogs;

import android.annotation.SuppressLint;
import android.widget.EditText;

import com.example.judgecompanion.R.id;
import com.example.judgecompanion.database.DBHelper;
import com.example.judgecompanion.database.Teams;
import com.example.judgecompanion.dialogs.abstracts.DialogTemplateFragment;

@SuppressLint("ValidFragment")
public class AddTeamDialogFragment extends DialogTemplateFragment {
	Teams local;

	@SuppressLint("ValidFragment")
	public AddTeamDialogFragment(int layoutport, int layoutland) {
		super(layoutport, layoutland);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void confirm() {
		// If you need to save something somewhere, use this method
		String name = ((EditText) this.theView.findViewById(id.add_team_name_edit)).getText().toString();
		String members = ((EditText) this.theView.findViewById(id.add_team_member_edit)).getText().toString();
		String inst = ((EditText) this.theView.findViewById(id.add_team_institution_edit)).getText().toString();

		local = new Teams(name, inst, members);
		DBHelper dbs = DBHelper.getInstance(getActivity());
		dbs.addTeam(local.getTeamName(), local.getMemberListString(), local.getInstitution());
	}

	@Override
	public void buildData() {
		// Add stuff here to modify data.
		
	}
}
