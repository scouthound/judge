package com.example.judgecompanion.client.fragments;

import com.example.judgecompanion.R;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

// Pages
public class ClientCompetitionPageFragment extends Fragment {
	public static final String ARG_PAGE = "page";
	private int mPageNumber;
	
	public static ClientCompetitionPageFragment create(int pageNumber) {
        ClientCompetitionPageFragment fragment = new ClientCompetitionPageFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pageNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public ClientCompetitionPageFragment() {
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPageNumber = getArguments().getInt(ARG_PAGE);
    }
    
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		ViewGroup rootView;
		switch(mPageNumber)
		{
			case 1:
				rootView = (ViewGroup) inflater.inflate(R.layout.fragment_client_teams, container, false);
				break;
			case 2:
				rootView = (ViewGroup) inflater.inflate(R.layout.fragment_client_submit, container, false);
				break;
			default:
				rootView = (ViewGroup) inflater.inflate(R.layout.fragment_client_events, container, false);
		}
		
		return rootView;
	}
	
	public int getPageNumber() {
		return mPageNumber;
	}
}
