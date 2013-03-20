package com.example.judgecompanion.client.fragments;

import java.util.ArrayList;
import java.util.List;

import com.example.judgecompanion.R;
import com.example.judgecompanion.client.ClientActivity;
import com.example.judgecompanion.dialogs.TeamDetailDialogFragment;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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
				rootView = (ViewGroup) inflater.inflate(R.layout.fragment_client_events, container, false);
				break;
			case 2:
				rootView = (ViewGroup) inflater.inflate(R.layout.fragment_client_submit, container, false);
				break;
			default:
				rootView = (ViewGroup) inflater.inflate(R.layout.fragment_client_teams, container, false);
				List<String> listOfTeams = new ArrayList<String>();
				listOfTeams.add("Team Discovery Channel: 150 pts");
				listOfTeams.add("Purple Monkey Dishwasher: 50 pts");
				listOfTeams.add("Pin Pals: 50 pts");
				listOfTeams.add("Holy Rollers: 100 pts");
				listOfTeams.add("Isotopes: 50 pts");
				ListView events = (ListView) rootView.findViewById(R.id.client_teams);
				ArrayAdapter<String> eventAdapter = new ArrayAdapter<String>(rootView.getContext(), android.R.layout.simple_list_item_1, listOfTeams);
				events.setAdapter(eventAdapter);
				events.setTextFilterEnabled(true);
				/*events.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
					}
				});*/
		}
		
		return rootView;
	}
	
	public int getPageNumber() {
		return mPageNumber;
	}
}
