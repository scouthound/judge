// Splash screen based on http://stackoverflow.com/questions/15452061/android-splash-screen
package com.example.judgecompanion;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

public class StartupActivity extends Activity {

	private static long SLEEP_TIME = 2; // Sleep for some time

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.requestWindowFeature(Window.FEATURE_NO_TITLE); // Removes title bar
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN); // Removes
																// notification
																// bar

		setContentView(R.layout.activity_startup);

		// Start timer and launch main activity
		IntentLauncher launcher = new IntentLauncher();
		launcher.start();
	}

	private class IntentLauncher extends Thread {
		@Override
		/**
		 * Sleep for some time and than start new activity.
		 */
		public void run() {
			try {
				// Sleeping
				Thread.sleep(SLEEP_TIME * 1000);
			} catch (Exception e) {
				Log.e("WHOOPS", e.getMessage());
			}

			// Start main activity
			Intent intent = new Intent(StartupActivity.this, MainActivity.class);
			StartupActivity.this.startActivity(intent);
			StartupActivity.this.finish();
		}
	}
}