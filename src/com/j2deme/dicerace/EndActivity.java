package com.j2deme.dicerace;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class EndActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		int winner, players;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_end);
		Intent intent = getIntent();
		winner = intent.getIntExtra(RaceCourse.STATE_WINNER,1);
		players = intent.getIntExtra(RaceCourse.STATE_PLAYERS, 1);
		String msg;
		if(players == 1 && winner == 2){
			msg = "CPU wins!";
		} else {
			msg = "Player " + winner + " wins!";
		}
		
		TextView banner = (TextView) findViewById(R.id.msg);
		banner.setText(msg);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_end, menu);
		return true;
	}
	
	public void restart(View view){
		Intent mainIntent = new Intent(this, MainActivity.class);
        startActivity(mainIntent);
        finish();
	}

}
