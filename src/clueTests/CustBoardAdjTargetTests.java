package clueTests;

import java.util.LinkedList;
import java.util.Set;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;

public class CustBoardAdjTargetTests {
	private static Board board;
	@BeforeClass
	public static void setUp() {
		board = new Board("ClueLayout.csv", "ClueLegend.txt");
		board.loadConfigFiles();
		board.calcAdjacencies();

	}

	// Ensure that player does not move around within room
	@Test
	public void testAdjacenciesInsideRooms()
	{
		// Initial
		LinkedList<Integer> testList = board.getAdjList(board.calcIndex(0, 0));

		// Test inside Gold Room (Black at top)
		testList = board.getAdjList(board.calcIndex(0,10));
		Assert.assertEquals(0, testList.size());

	}

	// Ensure that the adjacency list from a doorway is only the
	// walkway. NOTE: This test could be merged with door 
	// direction test. 
	@Test
	public void testAdjacencyRoomExit()
	{
		// Test Initialize 
		LinkedList<Integer> testList = board.getAdjList(board.calcIndex(0,0));

		// TEST DOORWAY RIGHT (Purple, entrance to bedroom)
		testList = board.getAdjList(board.calcIndex(6,4));
		Assert.assertEquals(1, testList.size());
		Assert.assertTrue(testList.contains(board.calcIndex(6, 5)));

		//TEST DOORWAY UP (Purple, entrance to Spaceship)
		testList = board.getAdjList(board.calcIndex(14, 10));
		Assert.assertEquals(1, testList.size());
		Assert.assertTrue(testList.contains(board.calcIndex(13, 10)));

	}

	// Test adjacency at entrance to rooms
	@Test
	public void testAdjacencyDoorways()
	{

		LinkedList<Integer> testList = board.getAdjList(board.calcIndex(0, 0));

		// Test beside a door direction DOWN (Black on Bottom Wall)
		testList = board.getAdjList(board.calcIndex(21,10));
		Assert.assertTrue(testList.contains(board.calcIndex(21, 11)));
		Assert.assertTrue(testList.contains(board.calcIndex(20, 10)));
		Assert.assertTrue(testList.contains(board.calcIndex(21, 9)));
		Assert.assertEquals(3, testList.size());

		// Test beside a door direction DOWN (Green Entrance to Labyrinth)
		testList = board.getAdjList(board.calcIndex(7, 22));
		Assert.assertTrue(testList.contains(board.calcIndex(6,22)));
		Assert.assertTrue(testList.contains(board.calcIndex(7, 21)));
		Assert.assertEquals(2, testList.size());

		// Test beside a door direction LEFT (Green Entrance to R'leyh)
		testList = board.getAdjList(board.calcIndex(20, 15));
		Assert.assertTrue(testList.contains(board.calcIndex(20, 16)));
		Assert.assertTrue(testList.contains(board.calcIndex(20, 14)));
		Assert.assertTrue(testList.contains(board.calcIndex(21, 15)));
		Assert.assertTrue(testList.contains(board.calcIndex(21, 15)));
		Assert.assertEquals(4, testList.size());

	}

	// Test walkway count at index
	@Test
	public void testAdjacencyWalkways()
	{
		//start off at top corner
		LinkedList<Integer> testList = board.getAdjList(board.calcIndex(0, 0));
		// Test surrounded by 4 walkways
		testList = board.getAdjList(board.calcIndex(15,4));
		Assert.assertTrue(testList.contains(board.calcIndex(15, 5)));
		Assert.assertTrue(testList.contains(board.calcIndex(15, 3)));
		Assert.assertTrue(testList.contains(board.calcIndex(14, 4)));
		Assert.assertTrue(testList.contains(board.calcIndex(16, 4)));
		Assert.assertEquals(4, testList.size());

		//Test has two walkways (Black at Left wall)
		testList = board.getAdjList(board.calcIndex(11,0));
		Assert.assertTrue(testList.contains(board.calcIndex(10, 0)));
		Assert.assertTrue(testList.contains(board.calcIndex(12, 0)));
		Assert.assertEquals(2, testList.size());

		//Test has 3 walkways (Black at right wall)
		testList = board.getAdjList(board.calcIndex(20,22));
		Assert.assertTrue(testList.contains(board.calcIndex(19, 22)));
		Assert.assertTrue(testList.contains(board.calcIndex(20, 21)));
		Assert.assertTrue(testList.contains(board.calcIndex(21, 22)));
		Assert.assertEquals(3, testList.size());

		// Test has one walkway (Dark Blue in Labryinth)
		testList = board.getAdjList(board.calcIndex(3,19));
		Assert.assertTrue(testList.contains(board.calcIndex(4, 19)));
		Assert.assertEquals(1, testList.size());

		// Test has 3 walkways (Dark Blue next to bedroom)
		testList = board.getAdjList(board.calcIndex(8,2));
		Assert.assertTrue(testList.contains(board.calcIndex(8, 3)));
		Assert.assertTrue(testList.contains(board.calcIndex(8, 1)));
		Assert.assertTrue(testList.contains(board.calcIndex(9, 2)));
		Assert.assertEquals(3, testList.size());

	}


	// Tests of just walkways, 1 step, includes on edge of board
	// and beside room
	// Have already tested adjacency lists on all four edges, will
	// only test two edges here
	@Test
	public void testTargetsOneStep() {
		// Test at R'lyeh area, Orange
		board.calcTargets(18, 18, 1);
		Set<BoardCell> targets= board.getTargets();
		Assert.assertEquals(2, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(18, 19))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(19, 18))));	

	}
	
	// Tests of just walkways, 2 steps
	@Test
	public void testTargetsTwoSteps() {
		// Test at (4,0) Orange
		board.calcTargets(1,19, 2);
		Set<BoardCell> targets= board.getTargets();
		Assert.assertEquals(1, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(1, 17))));

	}
	
	// Tests of just walkways, 4 steps
	@Test
	public void testTargetsFourSteps() {
		//Orange on Left wall
		board.calcTargets(15, 0, 4);
		Set<BoardCell> targets= board.getTargets();
		Assert.assertEquals(9, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(11, 0))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(14, 3))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(16, 1))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(17, 2))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(15, 4))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(14, 1))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(15, 2))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(16, 3))));	
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(13, 0))));	
	}	
	
	// Tests of just walkways, 6 steps
	@Test
	public void testTargetsSixSteps() {
		//Test in Labyrinth Orange
		board.calcTargets(1, 19, 6);
		Set<BoardCell> targets= board.getTargets();
		Assert.assertEquals(1, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(5, 17))));

	}	

	// Test getting into a room
	// These are LIGHT BLUE on the planning spreadsheet
	@Test 
	public void testTargetsIntoRoom()
	{
		// One room is exactly 2 away (Brown, into spaceship)
		board.calcTargets(17, 15, 2);
		Set<BoardCell> targets= board.getTargets();
		Assert.assertEquals(5, targets.size());
		for (BoardCell cell : targets)
			System.out.println("targets" +board.getCells().indexOf(cell));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(17, 13))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(18, 14))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(16, 14))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(15, 15))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(19,15))));
	}

	// Test getting into room, doesn't require all steps
	@Test
	public void testTargetsIntoRoomShortcut() 
	{
		//Brown, into mine
		board.calcTargets(8, 15, 3);
		Set<BoardCell> targets= board.getTargets();
		Assert.assertEquals(5, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(5, 15))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(7, 17))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(9, 16))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(10, 16))));		

	}

	// Test getting out of a room
	@Test
	public void testRoomExit()
	{
		// Take one step, essentially just the adj list
		// Maroon, spaceship
		board.calcTargets(17, 7, 1);
		Set<BoardCell> targets= board.getTargets();
		// Ensure doesn't exit through the wall
		Assert.assertEquals(1, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(17, 6))));
		// Take two steps
		//Exit gold Room, maroon
		board.calcTargets(4, 10, 2);
		targets= board.getTargets();
		Assert.assertEquals(2, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(5, 11))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(5, 9))));
	}

}