package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import clueGame.RoomCell.DoorDirection;

public class Board {
	private ArrayList<BoardCell> cells = new ArrayList<BoardCell>();
	private Map<Character, String> rooms = new HashMap<Character, String>();
	private int numRows, numColumns, boardSize;
	private String csvConfig, legendConfig;
	
	private Map<Integer, LinkedList<Integer>> adjMatrix = new HashMap<Integer, LinkedList<Integer>>();
	private Set<Integer> targets = new HashSet<Integer>();
	private boolean[] visited = new boolean[576];
	
	
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
		boardSize = numRows * numColumns;
		
		if (cells.size() != (numRows * numColumns)) {
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
	
	public BoardCell getCellAt(int cellIndex) {
		BoardCell target = cells.get(cellIndex);
		return target;
	}
	
	public BoardCell getBoardCellAt(int row, int col) {
		int index = calcIndex(row, col);
		BoardCell target = cells.get(index);
		return target;
	}
	
	public RoomCell getRoomCellAt(int row, int col) {
		int index = calcIndex(row, col);
		BoardCell target = cells.get(index);
		if (target.isRoom() == true) {
			return (RoomCell) target;
		}
		return null;
	}
	
	public int calcIndex(int row, int col) {
		int index = 0;
		index = row*numColumns + col;
		return index;
	}
	
	public int[] calcRowandColumn(int index) {
		int row = 0;
		int col = 0;
		row = index % numRows;
		col = index - (index % numRows);
		int[] result = new int[2];
		result[0] = row;
		result[1] = col;
		return result;
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
	
	public char cellRelation(int startR, int startC, int row, int col) {
		char adjCellToThe = 'N';
		if ((row == startR - 1) && (col == startC))
			adjCellToThe = 'U';
		else if ((row == startR + 1) && (col == startC))
			adjCellToThe = 'D';
		else if ((row == startR) && (col == startC + 1))
			adjCellToThe = 'R';
		else if ((row == startR) && (col == startC - 1))
			adjCellToThe = 'L';
		return adjCellToThe;
			
	}
	
	public LinkedList<Integer> addValid(LinkedList<Integer> adjList, int startR, int startC, int row, int col) {
		BoardCell cell = getBoardCellAt(row, col);
		if (cell.isWalkway()) {
			adjList.add(calcIndex(row, col));
		} else if (cell.isRoom()) {
			RoomCell rCell = (RoomCell) cell;
		
			if (cell.isDoorway()) {
				char adjCellToThe = cellRelation(startR, startC, row, col);
				if ((adjCellToThe == 'U') && (rCell.doorDirection == DoorDirection.DOWN))
					adjList.add(calcIndex(row, col));
				else if ((adjCellToThe == 'D') && (rCell.doorDirection == DoorDirection.UP))
					adjList.add(calcIndex(row, col));
				else if ((adjCellToThe == 'R') && (rCell.doorDirection == DoorDirection.LEFT))
					adjList.add(calcIndex(row, col));
				else if ((adjCellToThe == 'L') && (rCell.doorDirection == DoorDirection.RIGHT))
					adjList.add(calcIndex(row, col));
			}
		}
		return adjList;
	}
	
	public Map<Integer, LinkedList<Integer>> calcAdjacencies() {
		for (int row = 0; row < numRows; row++) {
			for (int col = 0; col < numColumns; col++){
				LinkedList<Integer> adjList = new LinkedList<Integer>();
				if (row == 0) {
					if (col == 0) {
						adjList = addValid(adjList, row, col, row + 1, col);
						adjList = addValid(adjList, row, col, row, col + 1);
					} else if (col == 23) {
						adjList = addValid(adjList, row, col, row + 1, col);
						adjList = addValid(adjList, row, col, row, col - 1);
					} else {
						adjList = addValid(adjList, row, col, row + 1, col);
						adjList = addValid(adjList, row, col, row, col + 1);
						adjList = addValid(adjList, row, col, row, col - 1);
					}	
				} else if (row == 23) {
					if (col == 0) {
						adjList = addValid(adjList, row, col, row - 1, col);
						adjList = addValid(adjList, row, col, row, col - 1);
					} else if (col == 23) {
						adjList = addValid(adjList, row, col, row - 1, col);
						adjList = addValid(adjList, row, col, row, col - 1);
					} else {
						adjList = addValid(adjList, row, col, row - 1, col);
						adjList = addValid(adjList, row, col, row, col + 1);
						adjList = addValid(adjList, row, col, row, col - 1);
					}
				} else if (col == 0) {
					adjList = addValid(adjList, row, col, row + 1, col);
					adjList = addValid(adjList, row, col, row - 1, col);
					adjList = addValid(adjList, row, col, row, col + 1);
				} else if (col == 23) {
					adjList = addValid(adjList, row, col, row + 1, col);
					adjList = addValid(adjList, row, col, row - 1, col);
					adjList = addValid(adjList, row, col, row, col - 1);
				} else {
					adjList = addValid(adjList, row, col, row + 1, col);
					adjList = addValid(adjList, row, col, row - 1, col);
					adjList = addValid(adjList, row, col, row, col + 1);
					adjList = addValid(adjList, row, col, row, col - 1);
				}
				adjMatrix.put(calcIndex(row, col), adjList);
			}
		}
		return adjMatrix;
	}
	
	public void startTargets(int location, int steps) {
		//populate visited array
		for (int i = 0; i < 575; i++) 
			visited[i] = false;
		//set visited[start location] to true
		visited[location] = true;
		//adjacency matrix calculated
		this.calcAdjacencies();
		//System.out.println(this.calcAdjacencies());
		//calcTargets with location and steps 
		this.calcTargets(location, steps);
	}
	
	public Set<Integer> calcTargets(int thisCell, int numSteps) {
		LinkedList<Integer> adjCells = new LinkedList<Integer>();
		LinkedList<Integer> temp = getAdjList(thisCell);
		for (int value : temp) {
			if (!visited[value])
				adjCells.add(value);
		}
		//System.out.println("AdjList for " + thisCell + ": " + adjCells);
		for (int adjCell : adjCells) {
			visited[adjCell] = true;
			//System.out.println("Num steps: " + numSteps);
			if (numSteps == 1) {
				targets.add(adjCell);
				//System.out.println("Added " + adjCell + " to targets.");
			}
			else
				calcTargets(adjCell, (numSteps-1));
			visited[adjCell] = false;
		}
		//System.out.println("Targets for " + thisCell + " taking " + numSteps + " steps: " + targets);
		return targets;
	}
	
	public Set<Integer> getTargets(int location, int steps) {
		Set<Integer> set = new HashSet<Integer>();
		this.startTargets(location, steps);
		set = calcTargets(location, steps);
		return set;
	}
	
	public LinkedList<Integer> getAdjList(int index) {
		return adjMatrix.get(index);
	}
	
}
