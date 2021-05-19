package com.j2deme.dicerace;

import java.util.Random;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class DiceActivity extends Activity {
	static final String RESULT = "Result";
	private Button Dado;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dice);
		Dado = (Button) findViewById(R.id.Dado);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_dice, menu);
		return true;
	}
	
	@SuppressWarnings("deprecation")
	@SuppressLint("NewApi")
	public void lanzar(View view){
		int r = random();
		Resources res = getResources();
		int currentapiVersion = android.os.Build.VERSION.SDK_INT;
		Drawable[] sides = {
			res.getDrawable(R.drawable.dice_1),
			res.getDrawable(R.drawable.dice_2),
			res.getDrawable(R.drawable.dice_3),
			res.getDrawable(R.drawable.dice_4),
			res.getDrawable(R.drawable.dice_5),
			res.getDrawable(R.drawable.dice_6),
		};
		
    	Drawable drawable = sides[r-1];
		if (currentapiVersion >= android.os.Build.VERSION_CODES.JELLY_BEAN){
			Dado.setBackground(drawable);
		} else{
			Dado.setBackgroundDrawable(drawable);
		}
		
		Context context = getApplicationContext();
		CharSequence text = "You got a " + r + "!";
		int duration = Toast.LENGTH_SHORT;
		
		Toast.makeText(context, text, duration).show();
		
		Intent returnIntent = new Intent();
		returnIntent.putExtra(RESULT,r);
		setResult(RESULT_OK,returnIntent);     
		finish();
	}
	
	public int random(){
		int r;
		Random rand = new Random();
    	r = rand.nextInt(6) + 1;
    	return r;
	}

}
