package com.j2deme.dicerace;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class RaceCourse extends Activity {
	static final String STATE_TURN = "currentTurn";
	static final String STATE_POS = "currentPositions";
	static final String STATE_PLAYERS = "numberPlayers";
	static final String STATE_WINNER = "winner";

	static final int RED = Color.parseColor("#E81809");
	static final int BLACK = Color.parseColor("#000000");
	static final int STEPS = 10;
	private int players;
	private int currentTurn;
	private int[] currentPositions;
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_race_course);
		setContentView(R.layout.race_course_grid);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }
		super.onCreate(savedInstanceState);
				
	    if (savedInstanceState != null) {
	        currentTurn = savedInstanceState.getInt(STATE_TURN);
	        currentPositions = savedInstanceState.getIntArray(STATE_POS);
	        players = savedInstanceState.getInt(STATE_PLAYERS);
	    } else {
	    	Intent intent = getIntent();
			players = intent.getIntExtra(MainActivity.PLAYERS,1);
			int cols = (players == 1) ? 2 : players;
	    	currentPositions = new int[cols];
			for (int i = 0; i < currentPositions.length; i++) {
				currentPositions[i] = 0;
			}
			currentTurn = 1;
	    }
	    createGrid();
	}
	
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
	    savedInstanceState.putInt(STATE_TURN, currentTurn);
	    savedInstanceState.putIntArray(STATE_POS, currentPositions);
	    savedInstanceState.putInt(STATE_PLAYERS, players);
	    
	    super.onSaveInstanceState(savedInstanceState);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_race_course, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 1) {
		     if(resultCode == RESULT_OK){
		    	 int result = data.getIntExtra(DiceActivity.RESULT, 1);
		    	 
		    	 currentPositions[currentTurn-1] += result;
		    	 currentTurn++;
		    	 if(currentTurn > currentPositions.length){
		    		 currentTurn = 1;
		    	 }
		    	 
		    	 /*Context context = getApplicationContext();
		    	 CharSequence text;
		    	 int duration = Toast.LENGTH_SHORT;*/
		    	 if(players == 1 && currentTurn == 2){
		    		DiceActivity d = new DiceActivity();
		    		int r = d.random();
		    		//text = "CPU's turn.\nIt got a " + r + ".";
		    		currentPositions[currentTurn-1] += r;
		    		currentTurn = 1;
		    	 } else {
		    		//text = "Player " + currentTurn + "'s turn.";
		    	 }
		 		
		 		//Toast.makeText(context, text, duration).show();
		 		updateGrid();
		     }
		}
		if(resultCode == RESULT_CANCELED) {}
	}
	
	public void createGrid(){
		int cols = (players == 1) ? 2 : players;
		TableLayout tl =  (TableLayout) findViewById(R.id.tableLayout);
		for (int i = 0; i < (STEPS + 2); i++) {
			TableRow tr = new TableRow(this);
			TableLayout.LayoutParams params = new TableLayout.LayoutParams(
	                TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT, 1f);
			for (int j = 0; j < cols; j++) {
				TableRow.LayoutParams cellParams = new TableRow.LayoutParams();
				if(i == 0){
					TextView lbl_player = new TextView(this);
					String title = "";
					if(players == 1 && j == 1){
						title = "CPU";
					} else {
						title = "P" + (j + 1);
					}
					lbl_player.setText(title);
					lbl_player.setLayoutParams(cellParams);
					lbl_player.setId(((i+1)*10)+(j));
					lbl_player.setTag("lblPlayer"+(j+1));
					lbl_player.setTextSize(25);
					if(currentTurn == (j+1)){
						lbl_player.setTextColor(RED);
					}
					tr.addView(lbl_player);
				} else {
					Button step = new Button(this);
					if(currentPositions[j] == (i-1)){
						step.setTextColor(RED);
					}
					step.setText(""+(i-1));
					step.setId(((i+1)*10)+(j));
					step.setTag(""+i+j);
					step.setLayoutParams(cellParams);
					tr.addView(step);
				}
			}
			tr.setLayoutParams(params);
	        tl.addView(tr, params);
		}
		tl.setStretchAllColumns(true);
	}

	public void updateGrid(){
		//Update labels
		for (int i = 0; i < currentPositions.length; i++) {
			TextView lbl = (TextView) findViewById(10 + i);
			lbl.setTextColor(BLACK);
		}
		TextView current = (TextView) findViewById(10 + (currentTurn-1));
		current.setTextColor(RED);
		
		//Update buttons
		int cols = (players == 1) ? 2 : players;
		int x0y0 = 20;
		int xy = (STEPS + 2) * 10;
		for (int i = 0; i < cols; i++) { // 0 -> 3
			for (int j = x0y0; j <= xy;) { // 20 -> 120
				Button step = (Button) findViewById(j+i);
				step.setTextColor(BLACK);
				j += 10;
			}
		}
		//Update positions
		for (int i = 0; i < currentPositions.length; i++) {
			int val = currentPositions[i];
			if(val >= 10){
				Intent endIntent = new Intent(this, EndActivity.class);
				endIntent.putExtra(STATE_WINNER, i+1);
				endIntent.putExtra(STATE_PLAYERS, players);
		        startActivity(endIntent);
			} else {
				Button step = (Button) findViewById(((val+2)*10)+i);
				step.setTextColor(RED);
			}
		}
	}
	
	public void throwDice(View view){
		Intent diceIntent = new Intent(this, DiceActivity.class);
        startActivityForResult(diceIntent, 1);
        //view.invalidate();
	}
}
