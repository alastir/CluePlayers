package clueGame;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class BoardTest {
	private static Board testBoard;
	public static final int NUM_ROOMS = 11;
	public static final int NUM_COLUMNS = 24;
	public static final int NUM_ROWS = 24;
	
	@BeforeClass
	public static void init() {
		testBoard = new Board("ClueLayout.csv", "legend.txt");
		testBoard.loadConfigFiles();
	}

	@Test
	public void roomsNumber() {
		Map<Character, String> rooms = testBoard.getRooms();
		assertEquals(NUM_ROOMS, rooms.size());		
	}
	
	@Test
	public void typeOfCell() {
		Map<Character, String> rooms = testBoard.getRooms();
		assertEquals("Garden", rooms.get('G'));
		assertEquals("Library", rooms.get('L'));
		assertEquals("Billiard Room", rooms.get('R'));
		assertEquals("Walkway", rooms.get('W'));
		assertEquals("Kitchen", rooms.get('K'));
	}
	
	@Test
	public void correctRows() {
		assertEquals(testBoard.getNumRows(), NUM_ROWS);
	}
	
	@Test
	public void correctColumns() {
		assertEquals(testBoard.getNumColumns(), NUM_COLUMNS);
	}
	
	@Test
	public void testDoorDirection() {
		RoomCell testRoom = testBoard.getRoomCellAt(4, 2);
		assertTrue(testRoom.isDoorway());
		assertEquals(RoomCell.DoorDirection.DOWN, testRoom.getDoorDirection());
		
		testRoom = testBoard.getRoomCellAt(9, 18);
		assertTrue(testRoom.isDoorway());
		assertEquals(RoomCell.DoorDirection.UP, testRoom.getDoorDirection());
		
		testRoom = testBoard.getRoomCellAt(2, 7);
		assertTrue(testRoom.isDoorway());
		assertEquals(RoomCell.DoorDirection.LEFT, testRoom.getDoorDirection());
		
		testRoom = testBoard.getRoomCellAt(3, 14);
		assertTrue(testRoom.isDoorway());
		assertEquals(RoomCell.DoorDirection.RIGHT, testRoom.getDoorDirection());
		
		testRoom = testBoard.getRoomCellAt(0, 0);
		assertFalse(testRoom.isDoorway());
		
		testRoom = testBoard.getRoomCellAt(1, 1);
		assertFalse(testRoom.isDoorway());
	}
	
	@Test
	public void numberOfDoors() {
		int numDoors = 0;
		int boardSize = testBoard.getNumColumns() * testBoard.getNumRows();
		assertEquals(576, boardSize);
		for (int i = 0; i < NUM_ROWS; i++) {
			for (int j = 0; j < NUM_COLUMNS; j++) {
				BoardCell testCell = testBoard.getRoomCellAt(i, j);
				if (testCell.isDoorway()) {
					numDoors++;
				}
					
			}
		}
		assertEquals(10, numDoors);
	}
	
	@Test
	public void testRoomInitial() {
		assertEquals('G', testBoard.getRoomCellAt(1, 1).getInitial());
		assertEquals('B', testBoard.getRoomCellAt(1, 7).getInitial());
		assertEquals('R', testBoard.getRoomCellAt(3, 12).getInitial());
		assertEquals('O', testBoard.getRoomCellAt(1, 17).getInitial());
		assertEquals('H', testBoard.getRoomCellAt(6, 1).getInitial());
		assertEquals('L', testBoard.getRoomCellAt(9, 17).getInitial());
		assertEquals('D', testBoard.getRoomCellAt(17, 1).getInitial());
		assertEquals('K', testBoard.getRoomCellAt(17, 9).getInitial());
		assertEquals('S', testBoard.getRoomCellAt(17, 17).getInitial());
		assertEquals('X', testBoard.getRoomCellAt(9, 8).getInitial());
	}
	
	@Test
	public void testCalcIndex() {
		assertEquals(testBoard.calcIndex(1,3), 27);
		assertEquals(testBoard.calcIndex(0,0), 0);
		assertEquals(testBoard.calcIndex(18, 16), 448);
	}
	
	@Test (expected = FileNotFoundException.class)
	public void exceptionTestBadColumns() throws BadConfigFormatException, FileNotFoundException {
		Board b = new Board("ClueLayoutBadColumns.csv", "ClueLegend.txt");
		b.loadConfigFiles();
	}
	
	@Test (expected = FileNotFoundException.class)
	public void exceptionTestBadRoom() throws BadConfigFormatException, FileNotFoundException {
		Board b = new Board("ClueLayoutBadRoom.csv", "ClueLegend.txt");
		b.loadConfigFiles();
	}
	
	@Test (expected = FileNotFoundException.class)
	public void exceptionTestBadRoomFormat() throws BadConfigFormatException, FileNotFoundException {
		Board b = new Board("ClueLayout.csv", "ClueLegendBadFormat.txt");
		b.loadConfigFiles();
	}

}
