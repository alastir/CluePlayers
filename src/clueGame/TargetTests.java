package clueGame;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

public class TargetTests {
	private static Board testBoard;
	public static final int NUM_ROOMS = 11;
	public static final int NUM_COLUMNS = 24;
	public static final int NUM_ROWS = 24;
	
	@BeforeClass
	public static void init() throws Exception {
		testBoard = new Board("ClueLayout.csv", "legend.txt");
		testBoard.loadLegend();
		testBoard.loadBoard();
	}

	@Test
	public void testAdjOnlyWalkways() {
		
	}
	
	@Test
	public void testEdges() {
		
	}
	
	@Test
	public void testAdjNextToRoom() {
		
	}
	
	@Test
	public void testAdjToDoorway() {
		
	}
	
	@Test
	public void testDoorway() {
		
	}
	
	@Test
	public void testTargetAlongWalkway() {
		
	}
	
	@Test
	public void testTargetEnterRoom() {
		
	}
	
	@Test
	public void testTargetLeavingRoom() {
		
	}
}
