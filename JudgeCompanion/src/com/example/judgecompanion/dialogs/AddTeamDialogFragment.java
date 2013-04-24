package com.example.judgecompanion.dialogs;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
	}

	@Override
	public void confirm() {
		// If you need to save something somewhere, use this method
		String name = ((EditText) this.theView.findViewById(id.add_team_name_edit)).getText().toString();
		String members = ((EditText) this.theView.findViewById(id.add_team_member_edit)).getText().toString();
		String inst = ((EditText) this.theView.findViewById(id.add_team_institution_edit)).getText().toString();
		boolean error = false;

		if (name.isEmpty()) {
			error = true;
			((EditText) this.theView.findViewById(id.add_team_name_edit)).requestFocus();
		}
		if (members.isEmpty()) {
			error = true;
			((EditText) this.theView.findViewById(id.add_team_member_edit)).requestFocus();
		}
		if (inst.isEmpty()) {
			error = true;
			((EditText) this.theView.findViewById(id.add_team_institution_edit)).requestFocus();
		}
		if (!error) {
			local = new Teams(name, inst, members);
			DBHelper dbs = DBHelper.getInstance(getActivity());
			dbs.addTeam(local.getTeamName(), local.getMemberListString(), local.getInstitution());
		} else {
			AlertDialog abr = new AlertDialog.Builder(getActivity()).create();
			abr.setTitle("Error Encountered!");
			abr.setMessage("You left a field empty. Please enter data in all three fields.");
			abr.setButton(DialogInterface.BUTTON_POSITIVE, "OK",new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					return;
				}
			});
			abr.show();
		}
	}

	@Override
	public void buildData() {
		// Add stuff here to modify data.

	}
}
