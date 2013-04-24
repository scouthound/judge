package com.example.judgecompanion.server;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.judgecompanion.R;
import com.example.judgecompanion.database.DBHelper;
import com.example.judgecompanion.database.Judges;
import com.example.judgecompanion.dialogs.AddJudgeDialogFragment;
import com.example.judgecompanion.server.fragments.ServerSetupPageFragment;

public class ServerSetupActivity extends FragmentActivity implements AddJudgeDialogFragment.DialogTemplateListener{
	private static int NUM_PAGES = 4;
	private ViewPager mPager;
	private PagerAdapter mPagerAdapter;
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_server);

		mPager = (ViewPager) findViewById(R.id.pager);
		mPagerAdapter = new ServerPagerAdapter(getFragmentManager());
		mPager.setAdapter(mPagerAdapter);
		mPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				// When changing pages, reset the action bar actions since they
				// are dependent
				// on which page is currently active. An alternative approach is
				// to have each
				// fragment expose actions itself (rather than the activity
				// exposing actions),
				// but for simplicity, the activity provides the actions in this
				// sample.
				getActionBar().setSelectedNavigationItem(position);
			}
		});

		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		ActionBar.Tab judgesTab = actionBar.newTab().setText("Judges");
		ActionBar.Tab eventsTab = actionBar.newTab().setText("Events");
		ActionBar.Tab teamsTab = actionBar.newTab().setText("Teams");
		ActionBar.Tab finishTab = actionBar.newTab().setText("Finish");

		judgesTab.setTabListener(new ServerTabsListener(0));
		teamsTab.setTabListener(new ServerTabsListener(1));
		eventsTab.setTabListener(new ServerTabsListener(2));
		finishTab.setTabListener(new ServerTabsListener(3));

		actionBar.addTab(judgesTab);
		actionBar.addTab(teamsTab);
		actionBar.addTab(eventsTab);
		actionBar.addTab(finishTab);

		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setDisplayShowHomeEnabled(false);
	}

	class ServerTabsListener implements ActionBar.TabListener {
		int currentFrag = 0;

		public ServerTabsListener(int frag) {
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

	public void createCompetition(View theView) {
		// Send an email to all judges
		DBHelper dbs = DBHelper.getInstance(this);
		ArrayList<Judges> judges = dbs.getAllJudges();
		String[] emailList = new String[judges.size()];

		for(int it = 0; it < emailList.length; it++)
		{
			Intent i = new Intent(Intent.ACTION_SEND); 
			i.setType("text/plain"); 
			i.putExtra(Intent.EXTRA_EMAIL  , new String[]{judges.get(it).getEmail()});
			i.putExtra(Intent.EXTRA_SUBJECT, "Password for the Competition"); 
			i.putExtra(Intent.EXTRA_TEXT   , "Your password for the event is: " + judges.get(it).getPassword()); 

			try 
			{     
			   startActivity(Intent.createChooser(i, "Sending  Email...")); 
			} 
			catch (android.content.ActivityNotFoundException ex) 
			{     
			   Toast.makeText(ServerSetupActivity.this, "No Email clients",Toast.LENGTH_SHORT ).show(); 
			} 
		}
	}

	/**
	 * A simple pager adapter that represents 5 {@link ScreenSlidePageFragment}
	 * objects, in sequence.
	 */
	private class ServerPagerAdapter extends FragmentStatePagerAdapter {
		public ServerPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			return ServerSetupPageFragment.create(position);
		}

		@Override
		public int getCount() {
			return NUM_PAGES;
		}
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		return super.onOptionsItemSelected(item);
	}
	
	public void onSavedInstanceState(Bundle outState)
	{
		super.onSaveInstanceState(outState);
	}

	@Override
	public void onDialogPositiveClick(DialogFragment dialog, String result) {
		// TODO Auto-generated method stub
		
	}
}
