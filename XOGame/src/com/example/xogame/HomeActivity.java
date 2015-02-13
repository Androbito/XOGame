package com.example.xogame;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

public class HomeActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_layout);
	}

	// Click action to choose X or O
	public void choice(View v) {
		switch (v.getId()) {
		case R.id.button_O:
			XOGame.PLAYER = 'O';
			XOGame.MOBILE = 'X';
			break;
		case R.id.button_X:
			XOGame.PLAYER = 'X';
			XOGame.MOBILE = 'O';
			break;
		default:
			break;
		}
		Intent startGame = new Intent(this, XOGameActivity.class);
		startActivity(startGame);
	}
}
