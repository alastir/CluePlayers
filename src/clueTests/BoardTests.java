// Will Hayne and Anthony Nguyen

package clueTests;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.util.Map;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.BadConfigFormatException;
import clueGame.Board;
import clueGame.BoardCell;
import clueGame.RoomCell;

public class BoardTests {
	private static Board board;
	public static final int ROOMS = 11;
	public static final int ROWS = 22;
	public static final int COLUMNS = 23;
	
	@BeforeClass
	public static void setUp() {
		board = new Board("ClueLayout.csv", "ClueLegend.txt");
		board.loadConfigFiles();
	}
	
	@Test
	public void testRooms() {
		Map<Character, String> rooms = board.getRooms();
		assertEquals(ROOMS, rooms.size());
		assertEquals("Man Cave", rooms.get('C'));
		assertEquals("Gold Room", rooms.get('G'));
		assertEquals("Spaceship", rooms.get('S'));
		assertEquals("Secret Mine", rooms.get('M'));
		assertEquals("Closet", rooms.get('X'));
	}
	
	@Test
	public void testDimensions() {
		assertEquals(ROWS, board.getNumRows());
		assertEquals(COLUMNS, board.getNumColumns());
	}
	
	@Test
	public void testDoorDirections() {
		RoomCell room = board.getRoomCellAt(17, 13);
		assertTrue(room.isDoorway());
		assertEquals(RoomCell.DoorDirection.RIGHT, room.getDoorDirection());
		
		room = board.getRoomCellAt(4, 10);
		assertTrue(room.isDoorway());
		assertEquals(RoomCell.DoorDirection.DOWN, room.getDoorDirection());
		
		room = board.getRoomCellAt(20, 16);
		assertTrue(room.isDoorway());
		assertEquals(RoomCell.DoorDirection.LEFT, room.getDoorDirection());
		
		room = board.getRoomCellAt(10, 2);
		assertTrue(room.isDoorway());
		assertEquals(RoomCell.DoorDirection.UP, room.getDoorDirection());
		
		//Test that a room cell is not necessarily a door 
		room = board.getRoomCellAt(6, 2);
		assertFalse(room.isDoorway());	
		
		// Test that walkways are not doors
		BoardCell cell = board.getCellAt(board.calcIndex(0, 6));
		assertFalse(cell.isDoorway());	
	}
	
	@Test
	public void testNumberOfDoorways() 
	{
		int numDoors = 0;
		int cellCount = board.getNumColumns() * board.getNumRows();
		assertEquals(506, cellCount);
		for (int i = 0; i < cellCount; ++i)
		{
			BoardCell cell = board.getCellAt(i);
			if (cell.isDoorway())
				numDoors++;
		}
	
		Assert.assertEquals(18, numDoors);
	}
	
	@Test
	public void testCalcIndex() {
		// Test each corner of the board
		assertEquals(0, board.calcIndex(0, 0));
		assertEquals(COLUMNS-1, board.calcIndex(0, COLUMNS-1));
		assertEquals(483, board.calcIndex(ROWS-1, 0));
		assertEquals(505, board.calcIndex(ROWS-1, COLUMNS-1));
		
		// Test a couple others
		assertEquals(24, board.calcIndex(1, 1));
		assertEquals(66, board.calcIndex(2, 20));		
	}
	
	// Test a few room cells to ensure the room initial is correct
	@Test
	public void testRoomInitials() {
		assertEquals('B', board.getRoomCellAt(0, 0).getInitial());
		assertEquals('G', board.getRoomCellAt(4, 8).getInitial());
		assertEquals('T', board.getRoomCellAt(10, 1).getInitial());
		assertEquals('R', board.getRoomCellAt(21, 19).getInitial());
		assertEquals('C', board.getRoomCellAt(21, 0).getInitial());
	}

	//the following down below use loadRoomConfig() and loadBoardConfig from Board
	//problem is that we didn't have that
	//looks like the "loadConfigFiles" would probably use those methods
	//in short, I made two more methods in the Board file, they should me marked with commits

	// Test that an exception is thrown for a bad config file
	@Test (expected = BadConfigFormatException.class)
	public void testBadColumns() throws BadConfigFormatException, FileNotFoundException {
		Board test = new Board("ClueLayoutBadColumns.csv", "ClueLegend.txt");

		test.loadRoomConfig();
		test.loadBoardConfig();
	}

	// Test that an exception is thrown for a bad config file
	@Test (expected = BadConfigFormatException.class)
	public void testBadRoom() throws BadConfigFormatException, FileNotFoundException {
		// overloaded Board ctor takes config file name
		Board test = new Board("ClueLayoutBadRoom.csv", "ClueLegend.txt");

		test.loadRoomConfig();
		test.loadBoardConfig();
	}

	// Test that an exception is thrown for a bad config file
	@Test (expected = BadConfigFormatException.class)
	public void testBadRoomFormat() throws BadConfigFormatException, FileNotFoundException {
		// overloaded Board ctor takes config file name
		Board test = new Board("ClueLayout.csv", "ClueLegendBadFormat.txt");

		test.loadRoomConfig();
		test.loadBoardConfig();
	}

}
