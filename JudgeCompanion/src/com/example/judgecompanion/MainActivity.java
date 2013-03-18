package com.example.judgecompanion;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.WifiP2pManager.Channel;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.example.judgecompanion.client.ClientActivity;
import com.example.judgecompanion.server.ServerSetupActivity;

public class MainActivity extends Activity {

	// Following are required for Wifi-Direct functionality.
	WifiP2pManager mManager; // Wifi p2p manager
	Channel mChannel; // Wifi Channel
	BroadcastReceiver mReceiver; // Broadcast Receiver.
	IntentFilter mIntentFilter;

	// END-WIFI-DIRECT

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// If in portrait, use landscape layout...
		if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
			setContentView(R.layout.activity_main_landscape);
		// Else use portrait layout
		else
			setContentView(R.layout.activity_main);

		mManager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
		mChannel = mManager.initialize(this, getMainLooper(), null);
		mReceiver = new WifiDirectBroadcastReceiver(mManager, mChannel);
		// mReceiver = new WifiDirectBroadcastReceiver(mManager, mChannel,
		// this);
		mIntentFilter = new IntentFilter();
		mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
		mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
		mIntentFilter
				.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
		mIntentFilter
				.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	public void createEvent(View theView) {
		Toast.makeText(this.getApplicationContext(), "Creating Competition...",
				Toast.LENGTH_SHORT).show();
		Intent intent;

		if (createDB()) // If all of the tables can be built, go to server
						// activity
		{
			intent = new Intent(this, ServerSetupActivity.class);
			startActivity(intent);
		} else
			Toast.makeText(this.getApplicationContext(),
					"Cannot build competition.", Toast.LENGTH_SHORT).show();
	}

	public void joinEvent(View theView) {
		Intent intent;
		int compId = authenticateUser("userID");

		if (compId != -1) // If all of the tables can be built, go to server
							// activity
		{
			Toast.makeText(this.getApplicationContext(),
					"Joining Competition " + compId, Toast.LENGTH_SHORT).show();
			intent = new Intent(this, ClientActivity.class);
			startActivity(intent);
		} else
			Toast.makeText(this.getApplicationContext(),
					"Cannot join competition", Toast.LENGTH_SHORT).show();
	}

	// Dummy method: returns competition id
	private int authenticateUser(String userId) {
		return 1234;
	}

	// Sets up the tables to be used for the competition; Currently dummied.
	private boolean createDB() {
		// SQL STATEMENTS!!!!!
		// Don't forget to log what SQL statements are being used
		Log.d("SQL", "Initialize SQL DB");
		JudgeOpenHelper jDB = new JudgeOpenHelper(this);
		
		return true;
	}

	protected void onResume() {
		super.onResume();
		registerReceiver(mReceiver, mIntentFilter);
	}

	protected void onPause() {
		super.onPause();
		unregisterReceiver(mReceiver);
	}
}
