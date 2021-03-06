package com.example.judgecompanion.client;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.View;

import com.example.judgecompanion.R;
import com.example.judgecompanion.client.fragments.ClientCompetitionPageFragment;
import com.example.judgecompanion.dialogs.TeamDetailDialogFragment;

public class ClientActivity extends FragmentActivity implements TeamDetailDialogFragment.DialogTemplateListener{
	private static int NUM_PAGES = 3;
	private ViewPager mPager;
	private PagerAdapter mPagerAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_server);
		
		mPager = (ViewPager) findViewById(R.id.pager);
		mPagerAdapter = new ClientPagerAdapter(getFragmentManager());
		mPager.setAdapter(mPagerAdapter);
		mPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // When changing pages, reset the action bar actions since they are dependent
                // on which page is currently active. An alternative approach is to have each
                // fragment expose actions itself (rather than the activity exposing actions),
                // but for simplicity, the activity provides the actions in this sample.
                getActionBar().setSelectedNavigationItem(position);
            }
        });
		
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		ActionBar.Tab eventsTab = actionBar.newTab().setText("Events");
		ActionBar.Tab teamsTab = actionBar.newTab().setText("Teams");
		ActionBar.Tab submitTab = actionBar.newTab().setText("Submit");
		
		teamsTab.setTabListener(new ClientTabsListener(0));
		eventsTab.setTabListener(new ClientTabsListener(1));
		submitTab.setTabListener(new ClientTabsListener(2));
		
		actionBar.addTab(teamsTab);
		actionBar.addTab(eventsTab);
		actionBar.addTab(submitTab);
		
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setDisplayShowHomeEnabled(false);
	}
	
	class ClientTabsListener implements ActionBar.TabListener {
		int currentFrag = 0;
		
		public ClientTabsListener(int frag)
		{
			currentFrag = frag;
		}
		
		@Override
		public void onTabReselected(Tab tab, FragmentTransaction ft) {
			// TODO Auto-generated method stub
			mPager.setCurrentItem(currentFrag);
		}

		@Override
		public void onTabSelected(Tab tab, FragmentTransaction ft) {
			// TODO Auto-generated method stub
			mPager.setCurrentItem(currentFrag);
		}

		@Override
		public void onTabUnselected(Tab tab, FragmentTransaction ft) {
			// TODO Auto-generated method stub
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_server, menu);
		return true;
	}
	
	public void createCompetition(View theView)
	{
		// Do what you need to do then kick back to main to go to edit pages
		this.finish();
	}
	
	public void viewTeam(View theView)
	{
		// When you click on a team name or button, populate info and open dialog box
		TeamDetailDialogFragment teamDetail = new TeamDetailDialogFragment(R.layout.activity_team_detail_dialog_fragment, R.layout.activity_team_detail_dialog_fragment);
		// Display your ill gotten spoils
        teamDetail.show(getSupportFragmentManager(), "tddf");
	}
	
	/**
     * A simple pager adapter that represents 5 {@link ScreenSlidePageFragment} objects, in
     * sequence.
     */
    private class ClientPagerAdapter extends FragmentStatePagerAdapter {
        public ClientPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return ClientCompetitionPageFragment.create(position);
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }

	@Override
	public void onDialogPositiveClick(DialogFragment dialog, String result) {
		// TODO Auto-generated method stub
		
	}
}
