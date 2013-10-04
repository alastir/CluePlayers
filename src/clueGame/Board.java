package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import clueGame.RoomCell.DoorDirection;

public class Board {
	private ArrayList<BoardCell> cells = new ArrayList<BoardCell>();
	private Map<Character, String> rooms = new HashMap<Character, String>();
	private int numRows, numColumns;
	private String csvConfig, legendConfig;
	
	public Board() {
		this.numRows = 0;
		this.numColumns = 0;
	}
	
	public Board(String csvFile, String legendFile) {
		super();
		this.csvConfig = csvFile;
		this.legendConfig = legendFile;
	}

	public void loadConfigFiles() {
		try {
			FileReader reader = new FileReader(csvConfig);
			Scanner in = new Scanner(reader);
			String[] line;
			int currentRow = 0;
			while (in.hasNextLine()) {
				line = in.nextLine().split(",");
				numColumns = line.length;
				for (int i = 0; i < numColumns; i++) {
					if (line[i] == "W") {
						WalkwayCell wCell = new WalkwayCell(currentRow, i);
						cells.add(wCell);
					} else {
						//TODO
						//Added everything from here--------
						char initial = line[i].charAt(0);
//						char charD = line[i].charAt(1);
//						DoorDirection doorDirection = DoorDirection.NONE;
//						if (charD == 'U') {
//							doorDirection = DoorDirection.UP;
//						} else if (charD == 'D') {
//							doorDirection = DoorDirection.DOWN;
//						} else if (charD == 'L') {
//							doorDirection = DoorDirection.LEFT;
//						} else if (charD == 'R') {
//							doorDirection = DoorDirection.RIGHT;
//						}
						//To here---------------------------
						RoomCell rCell = new RoomCell(currentRow, i, initial);
						cells.add(rCell);
					}
				}
				currentRow++;
			}
			numRows = currentRow;
			in.close();
		} catch (FileNotFoundException e1) {
			System.out.println(e1);
		}
		
		try {
			FileReader reader = new FileReader(legendConfig);
			Scanner in = new Scanner(reader);
			String[] line;
			while (in.hasNextLine()) {
				line = in.nextLine().split(", ");
				rooms.put(line[0].charAt(0), line[1]);
			}
			in.close();
		} catch (FileNotFoundException e1) {
			System.out.println(e1);
		}
	}
	
	public RoomCell getRoomCellAt(int row, int col) {
		int index = calcIndex(row, col);
		BoardCell target = cells.get(index);
		if (target.isRoom() == true) {
			//TODO
			RoomCell rTarget = new RoomCell(row, col);
			return rTarget;
		} 
		return null;
	}
	
	public int calcIndex(int row, int col) {
		int index = 0;
		index = row*numRows + col;
		return index;
	}

	public ArrayList<BoardCell> getCells() {
		return cells;
	}

	public Map<Character, String> getRooms() {
		return rooms;
	}

	public int getNumRows() {
		return numRows;
	}

	public int getNumColumns() {
		return numColumns;
	}
	
}
