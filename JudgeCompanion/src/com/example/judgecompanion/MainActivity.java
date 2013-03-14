package com.example.judgecompanion;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // If in portrait, use landscape layout...
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
        	setContentView(R.layout.activity_main_landscape);
        // Else use portrait layout
        else
        	setContentView(R.layout.activity_main_portrait);  
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    public void createEvent(View theView)
    {
    	Toast.makeText(this.getApplicationContext(), "Creating Competition...", Toast.LENGTH_SHORT).show();
    	// buildTables();
    	// Intent i = create something
    	// startActivity(i);
    }
    
    public void joinEvent(View theView)
    {
    	Toast.makeText(this.getApplicationContext(), "Joining Competition...", Toast.LENGTH_SHORT).show();
    	String competition = authenticateUser("userID");
    	Intent intent;
    	
		if(true) // if a valid event is found, join it
		{
			Toast.makeText(this.getApplicationContext(), "Joined Competition ID-" + competition, Toast.LENGTH_SHORT).show();
			intent = new Intent(this, ServerActivity.class);
			startActivity(intent);
		}
    }
    
    // Dummy method: returns competition id
    private String authenticateUser(String userId)
    {
    	return "1234";
    }
}
