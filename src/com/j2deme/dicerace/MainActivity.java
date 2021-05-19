package com.j2deme.dicerace;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Spinner;

public class MainActivity extends Activity {
	static final String PLAYERS = "players";
	//public final static String PLAYERS = "com.j2deme.DiceRace.PLAYERS";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	public void set_players(View view){
		final Spinner spinnerGameModes = (Spinner) findViewById(R.id.spinner_game_modes);
        //String game_mode = spinnerGameModes.getSelectedItem().toString();
        int players = spinnerGameModes.getSelectedItemPosition() + 1;
        Intent raceCourseIntent = new Intent(this, RaceCourse.class);
        raceCourseIntent.putExtra(PLAYERS, players);
        startActivity(raceCourseIntent);
	}
}
