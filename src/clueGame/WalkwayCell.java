package clueGame;

public class WalkwayCell extends BoardCell{
	
	WalkwayCell(int row, int col) {
		super(row, col);
	}
	
	@Override
	public boolean isWalkway() {
		return true;
	}

}
