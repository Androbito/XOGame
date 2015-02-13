package com.example.xogame;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class XOGameActivity extends Activity {

	private XOGame xoGame;

	private ImageView gridImageViews[];

	private TextView mInfoTextView;

	private boolean mplayerFirst = true;
	private boolean xoGameOver = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		gridImageViews = new ImageView[9];
		gridImageViews[0] = (ImageView) findViewById(R.id.one);
		gridImageViews[1] = (ImageView) findViewById(R.id.two);
		gridImageViews[2] = (ImageView) findViewById(R.id.three);
		gridImageViews[3] = (ImageView) findViewById(R.id.four);
		gridImageViews[4] = (ImageView) findViewById(R.id.five);
		gridImageViews[5] = (ImageView) findViewById(R.id.six);
		gridImageViews[6] = (ImageView) findViewById(R.id.seven);
		gridImageViews[7] = (ImageView) findViewById(R.id.eight);
		gridImageViews[8] = (ImageView) findViewById(R.id.nine);

		mInfoTextView = (TextView) findViewById(R.id.information);

		xoGame = new XOGame();

		startNewGame();

	}

	private void startNewGame() {
		xoGame.clearGrid();

		for (int i = 0; i < gridImageViews.length; i++) {
			gridImageViews[i].setBackgroundColor(getResources().getColor(
					android.R.color.darker_gray));
			gridImageViews[i].setEnabled(true);
			gridImageViews[i].setOnClickListener(new ImageViewClickListener(i));
		}

		if (mplayerFirst) {
			mInfoTextView.setText(R.string.first_player);
			mplayerFirst = false;
		} else {
			mInfoTextView.setText(R.string.turn_mobile);
			int move = xoGame.getMobileMove();
			setMove(xoGame.MOBILE, move);
			mplayerFirst = true;
		}
	}

	private class ImageViewClickListener implements View.OnClickListener {
		int location;

		public ImageViewClickListener(int location) {
			this.location = location;
		}

		public void onClick(View view) {
			if (!xoGameOver) {
				if (gridImageViews[location].isEnabled()) {
					setMove(xoGame.PLAYER, location);

					int winner = xoGame.whoIsWinner();

					if (winner == 0) {
						mInfoTextView.setText(R.string.turn_mobile);
						int move = xoGame.getMobileMove();
						setMove(xoGame.MOBILE, move);
						winner = xoGame.whoIsWinner();
					}

					if (winner == 0)
						mInfoTextView.setText(R.string.turn_player);
					else if (winner == 1) {
						showDialogMessage(getString(R.string.result_draw));
						xoGameOver = true;
					} else if (winner == 2) {
						showDialogMessage(getString(R.string.result_player_wins));
						xoGameOver = true;
					} else {
						showDialogMessage(getString(R.string.result_mobile_wins));
						xoGameOver = true;
					}
				}
			}
		}
	}

	private void setMove(char player, int location) {
		xoGame.setMove(player, location);
		gridImageViews[location].setEnabled(false);
		if (player == XOGame.MOBILE) {
			gridImageViews[location].setBackgroundColor(Color.RED);
			if (player == 'X') {
				gridImageViews[location].setImageResource(R.drawable.x_mark);
				if (android.os.Build.VERSION.SDK_INT >= 11) {
					ObjectAnimator anim = ObjectAnimator.ofFloat(
							gridImageViews[location], "rotationY", 180);
					anim.start();
				}
			} else {
				gridImageViews[location].setImageResource(R.drawable.o_mark);
				if (android.os.Build.VERSION.SDK_INT >= 11) {
					ObjectAnimator anim = ObjectAnimator.ofFloat(
							gridImageViews[location], "rotationY", 180);
					anim.start();
				}
			}
		}
		if (player == XOGame.PLAYER) {
			gridImageViews[location].setBackgroundColor(Color.GREEN);
			if (player == 'X') {
				gridImageViews[location].setImageResource(R.drawable.x_mark);
				if (android.os.Build.VERSION.SDK_INT >= 11) {
					ObjectAnimator anim = ObjectAnimator.ofFloat(
							gridImageViews[location], "rotationY", 180);
					anim.start();
				}
			} else {
				gridImageViews[location].setImageResource(R.drawable.o_mark);
				if (android.os.Build.VERSION.SDK_INT >= 11) {
					ObjectAnimator anim = ObjectAnimator.ofFloat(
							gridImageViews[location], "rotationY", 180);
					anim.start();
				}
			}
		}

	}

	public void showDialogMessage(final String result) {
		new AlertDialog.Builder(this)
				.setTitle("Result")
				.setMessage(result)
				.setNegativeButton(R.string.again,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.cancel();
								finish();
							}
						})
				.setPositiveButton(R.string.share,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								Intent sharingIntent = new Intent(
										android.content.Intent.ACTION_SEND);
								sharingIntent.setType("text/plain");
								sharingIntent.putExtra(
										android.content.Intent.EXTRA_SUBJECT,
										"XO Game Result");
								sharingIntent.putExtra(
										android.content.Intent.EXTRA_TEXT,
										result);
								startActivity(Intent.createChooser(
										sharingIntent, getResources()
												.getString(R.string.share)));

							}
						}).setIcon(android.R.drawable.ic_dialog_alert).show();
	}
}