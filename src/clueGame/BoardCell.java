package clueGame;

public abstract class BoardCell {
	private int row,col;
	
	public BoardCell() {
		row = col = 0;
	}
	
	public BoardCell(int row, int col) {
		super();
		this.row = row;
		this.col = col;
	}

	public int getRow() {
		return row;
	}
	public int getCol() {
		return col;
	}
	
	public boolean isWalkway() {
		return false;	
	}
	
	public boolean isRoom() {
		return false;
	}
	
	public boolean isDoorway() {
		return false;
	}	
}
