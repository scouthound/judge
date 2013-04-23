package com.example.judgecompanion.dialogs;

import android.annotation.SuppressLint;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.judgecompanion.R;
import com.example.judgecompanion.database.DBHelper;
import com.example.judgecompanion.database.Events;
import com.example.judgecompanion.dialogs.abstracts.DialogTemplateFragment;

@SuppressLint("ValidFragment")
public class AddEventDialogFragment extends DialogTemplateFragment {
	Events local;

	@SuppressLint("ValidFragment")
	public AddEventDialogFragment(int layoutport, int layoutland) {
		super(layoutport, layoutland);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void confirm() {
		// If you need to save something somewhere, use this method
		String edtName = ((EditText) this.theView.findViewById(R.id.add_event_name_edit)).getText().toString();
		String edtDescription = ((EditText) this.theView.findViewById(R.id.add_event_description_edit)).getText().toString();
		CheckBox time = (CheckBox) this.theView.findViewById(R.id.add_event_time);
		CheckBox points = (CheckBox) this.theView.findViewById(R.id.add_event_points);
		local = new Events(edtName, edtDescription, time.isChecked(), points.isChecked());
		
		DBHelper dbs = DBHelper.getInstance(getActivity());
		dbs.addEvent(local.getName(), local.getDescription(), local.isScored(), local.isTimed());
	}

	@Override
	public void buildData() {
		// Add stuff here to modify data.
		
	}

}
