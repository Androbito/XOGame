package com.example.xogame;

import java.util.Random;

public class XOGame {
	private char xoGrid[];
	private final static int GRID_SIZE = 9;

	public static char PLAYER = ' ';
	public static char MOBILE = ' ';
	public static final char EMPTY = ' ';

	private Random rand;

	public static int getGRID_SIZE() {
		return GRID_SIZE;
	}

	public XOGame() {

		xoGrid = new char[GRID_SIZE];

		for (int i = 0; i < GRID_SIZE; i++)
			xoGrid[i] = EMPTY;

		rand = new Random();
	}

	public void clearGrid() {
		for (int i = 0; i < GRID_SIZE; i++) {
			xoGrid[i] = EMPTY;
		}
	}

	public void setMove(char player, int location) {
		xoGrid[location] = player;
	}

	public int getMobileMove() {
		int move;

		for (int i = 0; i < getGRID_SIZE(); i++) {
			if (xoGrid[i] != PLAYER && xoGrid[i] != MOBILE) {
				char curr = xoGrid[i];
				xoGrid[i] = MOBILE;
				if (whoIsWinner() == 3) {
					setMove(MOBILE, i);
					return i;
				} else
					xoGrid[i] = curr;
			}
		}

		for (int i = 0; i < getGRID_SIZE(); i++) {
			if (xoGrid[i] != PLAYER && xoGrid[i] != MOBILE) {
				char curr = xoGrid[i];
				xoGrid[i] = PLAYER;
				if (whoIsWinner() == 2) {
					setMove(MOBILE, i);
					return i;
				} else
					xoGrid[i] = curr;
			}
		}

		do {
			move = rand.nextInt(getGRID_SIZE());
		} while (xoGrid[move] == PLAYER || xoGrid[move] == MOBILE);

		setMove(MOBILE, move);
		return move;
	}

	public int whoIsWinner() {
		for (int i = 0; i <= 6; i += 3) {
			if (xoGrid[i] == PLAYER && xoGrid[i + 1] == PLAYER
					&& xoGrid[i + 2] == PLAYER)
				return 2;
			if (xoGrid[i] == MOBILE && xoGrid[i + 1] == MOBILE
					&& xoGrid[i + 2] == MOBILE)
				return 3;
		}

		for (int i = 0; i <= 2; i++) {
			if (xoGrid[i] == PLAYER && xoGrid[i + 3] == PLAYER
					&& xoGrid[i + 6] == PLAYER)
				return 2;
			if (xoGrid[i] == MOBILE && xoGrid[i + 3] == MOBILE
					&& xoGrid[i + 6] == MOBILE)
				return 3;
		}

		if ((xoGrid[0] == PLAYER && xoGrid[4] == PLAYER && xoGrid[8] == PLAYER)
				|| xoGrid[2] == PLAYER
				&& xoGrid[4] == PLAYER
				&& xoGrid[6] == PLAYER)
			return 2;
		if ((xoGrid[0] == MOBILE && xoGrid[4] == MOBILE && xoGrid[8] == MOBILE)
				|| xoGrid[2] == MOBILE
				&& xoGrid[4] == MOBILE
				&& xoGrid[6] == MOBILE)
			return 3;

		for (int i = 0; i < getGRID_SIZE(); i++) {
			if (xoGrid[i] != PLAYER && xoGrid[i] != MOBILE)
				return 0;
		}

		return 1;
	}
}
