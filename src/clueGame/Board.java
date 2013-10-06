package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import clueGame.RoomCell.DoorDirection;

public class Board {
	public static int NUM_ROWS = 24;
	public static int NUM_COLS = 24;
	
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
		this.numRows = 0;
		this.numColumns = 0;
		this.csvConfig = csvFile;
		this.legendConfig = legendFile;
	}
	
	public void loadLegend() throws FileNotFoundException, BadConfigFormatException {
		FileReader reader = new FileReader(legendConfig);
		Scanner in = new Scanner(reader);
		String[] line;
		while (in.hasNextLine()) {
			line = in.nextLine().split(", ");
			if (line.length > 2) {
				throw new BadConfigFormatException("Bad Legend File");
			}
			rooms.put(line[0].charAt(0), line[1]);
		}
		in.close();
	}
	
	public boolean validRoom(char initial) {
		boolean valid = false;
		for (char roomInitials : rooms.keySet()) {
			if (initial == roomInitials) {
				valid = true;
			}
		}
		return valid;
	}
	
	public void loadBoard() throws FileNotFoundException, BadConfigFormatException {
		FileReader reader = new FileReader(csvConfig);
		Scanner in = new Scanner(reader);
		String[] line;
		int currentRow = 0;
		while (in.hasNextLine()) {
			line = in.nextLine().split(",");
			numColumns = line.length; //numColumns == number of cells found in one line of our board
			for (int i = 0; i < numColumns; i++) {
				if (line[i].length() == 1) { //If cell contains only one initial
					char initial = line[i].charAt(0);
					if (initial == 'W') {
						WalkwayCell wCell = new WalkwayCell(currentRow, i);
						cells.add(wCell);
					} else {
						if (validRoom(initial) == false) {
							throw new BadConfigFormatException("Invalid room initial found");
						}
						RoomCell rCell = new RoomCell(currentRow, i, initial, DoorDirection.NONE);
						cells.add(rCell);
					}
				} else if (line[i].length() == 2) { //If cell contains two initials (it must be a door!)
					char initial = line[i].charAt(0);
					char charD = line[i].charAt(1);
					DoorDirection doorDirection = DoorDirection.NONE;
					if (charD == 'U') {
						doorDirection = DoorDirection.UP;
					} else if (charD == 'D') {
						doorDirection = DoorDirection.DOWN;
					} else if (charD == 'L') {
						doorDirection = DoorDirection.LEFT;
					} else if (charD == 'R') {
						doorDirection = DoorDirection.RIGHT;
					}
					RoomCell rCell = new RoomCell(currentRow, i, initial, doorDirection);
					cells.add(rCell);
				} else {
					throw new BadConfigFormatException("Invalid room initials- too many characters present");
				}
			}
			currentRow++;
		}
		numRows = currentRow;
		in.close();
		
		if (cells.size() != (NUM_ROWS * NUM_COLS)) {
			throw new BadConfigFormatException("Invalid config file- uneven rows or columns");
		}
	}

	public void loadConfigFiles() throws FileNotFoundException, BadConfigFormatException {
		try {
			loadBoard();
			loadLegend();
		} catch (FileNotFoundException e1) {
			System.out.println(e1);
		} catch (BadConfigFormatException e2) {
			System.out.println(e2);
		}
	}
	
	public BoardCell getCellAt(int row, int col) {
		int index = calcIndex(row, col);
		BoardCell target = cells.get(index);
		return target;
	}
	
	public RoomCell getRoomCellAt(int row, int col) {
		int index = calcIndex(row, col);
		BoardCell target = cells.get(index);
		if (target.isRoom() == true) {
			return (RoomCell) target;
		} else {
			System.out.println("Cell at " + row + "," + col + " is not a room");
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
