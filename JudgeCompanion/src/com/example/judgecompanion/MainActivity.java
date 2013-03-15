package com.example.judgecompanion;

import com.example.judgecompanion.client.ClientActivity;
import com.example.judgecompanion.server.ServerSetupActivity;

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
        	setContentView(R.layout.activity_main);  
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
    	Intent intent;
    	
    	if(createDB()) // If all of the tables can be built, go to server activity
		{
			intent = new Intent(this, ServerSetupActivity.class);
			startActivity(intent);
		}
    	else
    		Toast.makeText(this.getApplicationContext(), "Cannot build competition.", Toast.LENGTH_SHORT).show();
    }
    
    public void joinEvent(View theView)
    {
    	Intent intent;
    	int compId = authenticateUser("userID");
    	
    	if(compId != -1) // If all of the tables can be built, go to server activity
		{
    		Toast.makeText(this.getApplicationContext(), "Joining Competition " + compId, Toast.LENGTH_SHORT).show();
			intent = new Intent(this, ClientActivity.class);
			startActivity(intent);
		}
    	else
    		Toast.makeText(this.getApplicationContext(), "Cannot join competition", Toast.LENGTH_SHORT).show();
    }
    
    // Dummy method: returns competition id
    private int authenticateUser(String userId)
    {
    	return 1234;
    }
    
    // Sets up the tables to be used for the competition; Currently dummied.
    private boolean createDB()
    {
    	// SQL STATEMENTS!!!!!
    	// Don't forget to log what SQL statements are being used
    	return true;
    }
}
