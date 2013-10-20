package clueGame;

public class RoomCell extends BoardCell {
	
	public enum DoorDirection {UP, DOWN, LEFT, RIGHT, NONE}
	private DoorDirection doorDirection;
	private char initial;
	
	public RoomCell(int row, int col) {
		super(row, col);
		doorDirection = DoorDirection.NONE;
	}
	
	public RoomCell(String token) {
		initial = token.charAt(0);
		doorDirection = DoorDirection.NONE;
		if (token.length() == 2) {
			char direction = token.charAt(1);
			if (direction == 'D')
				doorDirection = DoorDirection.DOWN;
			else if (direction == 'L')
				doorDirection = DoorDirection.LEFT;
			else if (direction == 'R')
				doorDirection = DoorDirection.RIGHT;
			else if (direction == 'U')
				doorDirection = DoorDirection.UP;
		}		
	}
	
	@Override
	public boolean isRoom() {
		return true;
	}
	
	@Override
	public boolean isDoorway() {
		if (doorDirection != DoorDirection.NONE)
			return true;
		else
			return false;
	}
	
	public DoorDirection getDoorDirection() {
		return doorDirection;
	}
	
	public char getInitial() {
		return initial;
	}

}
