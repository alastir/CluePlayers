package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class RoomCell extends BoardCell {
	enum DoorDirection {UP, DOWN, LEFT, RIGHT, NONE};
	
	private DoorDirection doorDirection;
	protected char roomInitial;
	
	public RoomCell(int row, int column) {
		super(row, column);
	}
	
	public RoomCell(int row, int column, char roomInitial) {
		super(row, column);
		this.roomInitial = roomInitial;
	}
	
	public RoomCell(int row, int column, char roomInitial, DoorDirection doorDirection) {
		super(row, column);
		this.roomInitial = roomInitial;
		this.doorDirection = doorDirection;
	}

	public boolean isRoom() {
		return true;
	}
	
	public boolean isDoorWay() {
		if (doorDirection != DoorDirection.NONE) {
			return true;
		}
		return false;
	}
	
	public DoorDirection getDoorDirection() {
		return doorDirection;
	}
	
	public char getInitial() {
		return roomInitial;
	}
}
