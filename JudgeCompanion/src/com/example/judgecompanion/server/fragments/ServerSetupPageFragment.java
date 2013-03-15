package com.example.judgecompanion.server.fragments;

import com.example.judgecompanion.R;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

// Pages
public class ServerSetupPageFragment extends Fragment {
	public static final String ARG_PAGE = "page";
	private int mPageNumber;
	
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
        mPageNumber = getArguments().getInt(ARG_PAGE);
    }
    
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		ViewGroup rootView;
		switch(mPageNumber)
		{
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
		
		return rootView;
	}
	
	public int getPageNumber() {
		return mPageNumber;
	}
}
