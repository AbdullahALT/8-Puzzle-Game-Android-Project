package com.example.abo_tam.game_8puzzel;

public class Blank {
	private int column;
	private int row;
	private Action[] actions;
	private int actnb;

	public Blank(int row, int culmn) {
		_blank(row, culmn);
	}

	public void movedTo(int row, int culmn) {
		_blank(row, culmn);
	}

	private void _blank(int row, int culmn) {
		this.column = culmn;
		this.row = row;
		possibleAction();
	}

	private void possibleAction() {
		actions = new Action[4];
		actnb = 0;
		if (column != 0) {
			actions[actnb++] = Action.LEFT;
		}
		if (column != 2) {
			actions[actnb++] = Action.RIGHT;
		}
		if (row != 0) {
			actions[actnb++] = Action.UP;
		}
		if (row != 2) {
			actions[actnb++] = Action.DOWN;
		}
	}

	public int getColumn() {
		return column;
	}

	public int getRow() {
		return row;
	}

	public Action[] getActions() {
		return actions;
	}

	public int getActnb() {
		return actnb;
	}

}
