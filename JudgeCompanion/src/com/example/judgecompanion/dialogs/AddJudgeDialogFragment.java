package com.example.judgecompanion.dialogs;

import android.annotation.SuppressLint;
import android.widget.EditText;

import com.example.judgecompanion.R;
import com.example.judgecompanion.database.DBHelper;
import com.example.judgecompanion.database.Judges;
import com.example.judgecompanion.dialogs.abstracts.DialogTemplateFragment;

@SuppressLint("ValidFragment")
public class AddJudgeDialogFragment extends DialogTemplateFragment {

	Judges local;
	
	@SuppressLint("ValidFragment")
	public AddJudgeDialogFragment(int layoutport, int layoutland) {
		super(layoutport, layoutland);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void confirm() {
		// If you need to save something somewhere, use this method
		String name = ((EditText) this.theView.findViewById(R.id.add_judge_name_edit)).getText().toString();
		String institution = ((EditText) this.theView.findViewById(R.id.add_judge_institution_edit)).getText().toString();
		String email = ((EditText) this.theView.findViewById(R.id.add_judge_email_edit)).getText().toString();
		String password = ((EditText) this.theView.findViewById(R.id.add_judge_password_edit)).getText().toString();
		
		local = new Judges(name, institution, email, password);	
		DBHelper dbs = DBHelper.getInstance(getActivity());
		dbs.addJudge(local.getName(), local.getInstitution(), local.getEmail(), local.getPassword());
	}

	@Override
	public void buildData() {
		// Add stuff here to modify data.
			
	}

	

}
