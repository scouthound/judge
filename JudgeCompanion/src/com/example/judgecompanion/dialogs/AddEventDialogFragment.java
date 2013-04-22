package com.example.judgecompanion.dialogs;

import android.annotation.SuppressLint;

import com.example.judgecompanion.dialogs.abstracts.DialogTemplateFragment;

@SuppressLint("ValidFragment")
public class AddEventDialogFragment extends DialogTemplateFragment {

	@SuppressLint("ValidFragment")
	public AddEventDialogFragment(int layoutport, int layoutland) {
		super(layoutport, layoutland);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void confirm() {
		// If you need to save something somewhere, use this method
		// EditText edtName = (EditText) this.theView.findViewById(R.id.)
	}

	@Override
	public void buildData() {
		// Add stuff here to modify data.
	}

}
