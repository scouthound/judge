package com.example.judgecompanion;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.WifiP2pManager.Channel;

public class WifiDirectBroadcastReceiver extends BroadcastReceiver {

	private WifiP2pManager mManager;
	private Channel mChannel;
	//private MyWifiActivity mActivity;
	private boolean wifiEnabled = false;

	public WifiDirectBroadcastReceiver(WifiP2pManager manager, Channel channel){//,
			//MyWifiActivity activity) {
		super();
		this.mManager = manager;
		this.mChannel = channel;
		//this.mActivity = activity;
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();

		if (WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)) {
			// Check to see if Wi-Fi is enabled and notify appropriate activity
			int state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1);
			if(state == WifiP2pManager.WIFI_P2P_STATE_ENABLED)
			{
				wifiEnabled = true;
			}
			else
			{
				wifiEnabled = false;
			}
		} else if (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)) {
			// Call WifiP2pManager.requestPeers() to get a list of current peers
		} else if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION
				.equals(action)) {
			// Respond to new connection or disconnections
		} else if (WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION
				.equals(action)) {
			// Respond to this device's wifi state changing
		}
	}
	
	public boolean checkWifiState()
	{
		return wifiEnabled;
	}

}
