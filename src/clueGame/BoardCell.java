package clueGame;

import clueGame.RoomCell.DoorDirection;

public abstract class BoardCell {
	private int row, column;
	
	public BoardCell(int row, int column) {
		super();
		this.row = row;
		this.column = column;
	}

	public boolean isWalkway() {
		Board board = new Board();
		board.loadConfigFiles();
		int index = board.calcIndex(row, column);
		BoardCell target = board.getCells().get(index);
		if (target.isWalkway()) {
			return true;
		}
		return false;
	}
	
	public boolean isRoom() {
		Board board = new Board();
		board.loadConfigFiles();
		int index = board.calcIndex(row, column);
		BoardCell target = board.getCells().get(index);
		if (target.isRoom()) {
			return true;
		}
		return false;
	}
	
	public boolean isDoorway() {
		Board board = new Board();
		board.loadConfigFiles();
		int index = board.calcIndex(row, column);
		BoardCell target = board.getCells().get(index);
		if (target.isDoorway()) {
			return true;
		}
		return false;
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}
}
