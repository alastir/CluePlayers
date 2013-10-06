package clueGame;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import experiment.IntBoard;

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
	
	//ADJACENCY TESTS FOR.............................................................

	//Red: Locations with only walkways as adjacent locations 
	@Test
	public void testAdjOnlyWalkways() {
		testBoard.calcAdjacencies();
		
		int location = testBoard.calcIndex(1, 12);
		int up = testBoard.calcIndex(0, 12);
		int down = testBoard.calcIndex(2, 12);
		int left = testBoard.calcIndex(1, 11);
		int right = testBoard.calcIndex(1, 13);
		LinkedList<Integer> testList = testBoard.getAdjList(location);
		assertTrue(testList.contains(up));
		assertTrue(testList.contains(down));
		assertTrue(testList.contains(left));
		assertTrue(testList.contains(right));
		assertEquals(4, testList.size());
		
		location = testBoard.calcIndex(6, 7);
		up = testBoard.calcIndex(5, 7);
		down = testBoard.calcIndex(7, 7);
		left = testBoard.calcIndex(6, 6);
		right = testBoard.calcIndex(6, 8);
		testList = testBoard.getAdjList(location);
		assertTrue(testList.contains(up));
		assertTrue(testList.contains(down));
		assertTrue(testList.contains(left));
		assertTrue(testList.contains(right));
		assertEquals(4, testList.size());
	}
	
	//Orange: Locations that are at each edge of the board
	@Test
	public void testEdges() {
		testBoard.calcAdjacencies();
		
		int location = testBoard.calcIndex(0, 11);
		int down = testBoard.calcIndex(1, 11);
		int left = testBoard.calcIndex(0, 10);
		int right = testBoard.calcIndex(0, 12);
		LinkedList<Integer> testList = testBoard.getAdjList(location);
		assertTrue(testList.contains(down));
		assertTrue(testList.contains(left));
		assertTrue(testList.contains(right));
		assertEquals(3, testList.size());
		
		location = testBoard.calcIndex(11, 23);
		int up = testBoard.calcIndex(10, 23);
		down = testBoard.calcIndex(12, 23);
		left = testBoard.calcIndex(11, 22);
		testList = testBoard.getAdjList(location);
		assertTrue(testList.contains(up));
		assertTrue(testList.contains(down));
		assertTrue(testList.contains(left));
		assertEquals(3, testList.size());
		
		location = testBoard.calcIndex(23, 12);
		up = testBoard.calcIndex(22, 12);
		left = testBoard.calcIndex(23, 11);
		right = testBoard.calcIndex(23, 13);
		testList = testBoard.getAdjList(location);
		assertTrue(testList.contains(up));
		assertTrue(testList.contains(left));
		assertTrue(testList.contains(right));
		assertEquals(3, testList.size());
		
		location = testBoard.calcIndex(10, 0);
		up = testBoard.calcIndex(9, 0);
		down = testBoard.calcIndex(11, 0);
		right = testBoard.calcIndex(10, 1);
		testList = testBoard.getAdjList(location);
		assertTrue(testList.contains(up));
		assertTrue(testList.contains(down));
		assertTrue(testList.contains(right));
		assertEquals(3, testList.size());
	}
	
	//Green: Locations that are beside a room cell that is not a doorway
	@Test
	public void testAdjNextToRoom() {
		testBoard.calcAdjacencies();

		int location = testBoard.calcIndex(4, 6);
		int up = testBoard.calcIndex(3, 6);
		int down = testBoard.calcIndex(5, 6);
		int left = testBoard.calcIndex(4, 5);
		int right = testBoard.calcIndex(4, 7);
		LinkedList<Integer> testList = testBoard.getAdjList(location);
		assertTrue(testList.contains(up));
		assertTrue(testList.contains(down));
		assertTrue(testList.contains(left));
		assertTrue(testList.contains(right));
		assertEquals(3, testList.size());
		
		location = testBoard.calcIndex(6, 15);
		up = testBoard.calcIndex(5, 15);
		down = testBoard.calcIndex(7, 15);
		left = testBoard.calcIndex(6, 14);
		right = testBoard.calcIndex(6, 16);
		testList = testBoard.getAdjList(location);
		assertTrue(testList.contains(up));
		assertTrue(testList.contains(down));
		assertTrue(testList.contains(left));
		assertTrue(testList.contains(right));
		assertEquals(3, testList.size());
	}

	//Blue: Locations that are adjacent to a doorway with needed direction
	@Test
	public void testAdjToDoorway() {
		testBoard.calcAdjacencies();

		int location = testBoard.calcIndex(3, 7);
		int up = testBoard.calcIndex(2, 7);
		int down = testBoard.calcIndex(4, 7);
		int left = testBoard.calcIndex(3, 6);
		int right = testBoard.calcIndex(3, 8);
		LinkedList<Integer> testList = testBoard.getAdjList(location);
		assertTrue(testList.contains(up));
		assertTrue(testList.contains(down));
		assertTrue(testList.contains(left));
		assertTrue(testList.contains(right));
		assertEquals(3, testList.size());
		
		location = testBoard.calcIndex(5, 2);
		up = testBoard.calcIndex(4, 2);
		down = testBoard.calcIndex(6, 2);
		left = testBoard.calcIndex(5, 1);
		right = testBoard.calcIndex(5, 3);
		testList = testBoard.getAdjList(location);
		assertTrue(testList.contains(up));
		assertTrue(testList.contains(down));
		assertTrue(testList.contains(left));
		assertTrue(testList.contains(right));
		assertEquals(3, testList.size());
	}
	
	//Purple: Locations that are doorways
	@Test
	public void testDoorway() {
		testBoard.calcAdjacencies();

		int location = testBoard.calcIndex(2, 7);
		int up = testBoard.calcIndex(1, 7);
		int down = testBoard.calcIndex(3, 7);
		int left = testBoard.calcIndex(2, 6);
		int right = testBoard.calcIndex(2, 8);
		LinkedList<Integer> testList = testBoard.getAdjList(location);
		assertTrue(testList.contains(up));
		assertTrue(testList.contains(down));
		assertTrue(testList.contains(left));
		assertTrue(testList.contains(right));
		assertEquals(3, testList.size());
		
		location = testBoard.calcIndex(4, 2);
		up = testBoard.calcIndex(3, 2);
		down = testBoard.calcIndex(5, 2);
		left = testBoard.calcIndex(4, 1);
		right = testBoard.calcIndex(4, 3);
		testList = testBoard.getAdjList(location);
		assertTrue(testList.contains(up));
		assertTrue(testList.contains(down));
		assertTrue(testList.contains(left));
		assertTrue(testList.contains(right));
		assertEquals(3, testList.size());
	}
	
	//TARGET TESTS FOR.................................................................
	
	//Pink: Targets along walkways at various distances
	@Test
	public void testTargetAlongWalkway() {
		int location = testBoard.calcIndex(0, 16);
		Set<Integer> targets = testBoard.getTargets(location, 2);
		
		int a = testBoard.calcIndex(0, 14);
		int b = testBoard.calcIndex(0, 18);
		int c = testBoard.calcIndex(1, 15);
		int d = testBoard.calcIndex(2, 16);
		
		assertEquals(4, targets.size());
		assertTrue(targets.contains(a));
		assertTrue(targets.contains(b));
		assertTrue(targets.contains(c));
		assertTrue(targets.contains(d));
		//----------------------------------------------
		location = testBoard.calcIndex(10, 7);
		targets = testBoard.getTargets(location, 3);
		
		a =  testBoard.calcIndex(7, 7);
		b =  testBoard.calcIndex(8, 6);
		c =  testBoard.calcIndex(8, 8);
		d =  testBoard.calcIndex(9, 7);
		int e =  testBoard.calcIndex(9, 9);
		int f =  testBoard.calcIndex(10, 6);
		int g =  testBoard.calcIndex(10, 8);
		int h =  testBoard.calcIndex(10, 10);
		int i =  testBoard.calcIndex(11, 7);
		int j =  testBoard.calcIndex(11, 9);
		int k =  testBoard.calcIndex(12, 6);
		int l =  testBoard.calcIndex(12, 8);
		int m = testBoard.calcIndex(13, 7);
		
		assertEquals(13, targets.size());
		assertTrue(targets.contains(a));
		assertTrue(targets.contains(b));
		assertTrue(targets.contains(c));
		assertTrue(targets.contains(d));
		assertTrue(targets.contains(e));
		assertTrue(targets.contains(f));
		assertTrue(targets.contains(g));
		assertTrue(targets.contains(h));
		assertTrue(targets.contains(i));
		assertTrue(targets.contains(j));
		assertTrue(targets.contains(k));
		assertTrue(targets.contains(l));
		assertTrue(targets.contains(m));
		//----------------------------------------------
		location = testBoard.calcIndex(8, 20);
		targets = testBoard.getTargets(location, 4);
		
		a =  testBoard.calcIndex(7, 23);
		b =  testBoard.calcIndex(8, 16);
		c =  testBoard.calcIndex(9, 23);
		
		assertEquals(3, targets.size());
		assertTrue(targets.contains(a));
		assertTrue(targets.contains(b));
		assertTrue(targets.contains(c));
		//----------------------------------------------
		location = testBoard.calcIndex(18, 8);
		targets = testBoard.getTargets(location, 5);
		
		a =  testBoard.calcIndex(13, 8);
		b =  testBoard.calcIndex(14, 7);
		c =  testBoard.calcIndex(14, 9);
		d =  testBoard.calcIndex(15, 6);
		e =  testBoard.calcIndex(15, 8);
		f =  testBoard.calcIndex(15, 10);
		g =  testBoard.calcIndex(16, 5);
		h =  testBoard.calcIndex(16, 7);
		i =  testBoard.calcIndex(16, 9);
		j =  testBoard.calcIndex(16, 11);
		k =  testBoard.calcIndex(22, 7);
		l =  testBoard.calcIndex(22, 9);
		m = testBoard.calcIndex(23, 8);
		
		assertEquals(13, targets.size());
		assertTrue(targets.contains(a));
		assertTrue(targets.contains(b));
		assertTrue(targets.contains(c));
		assertTrue(targets.contains(d));
		assertTrue(targets.contains(e));
		assertTrue(targets.contains(f));
		assertTrue(targets.contains(g));
		assertTrue(targets.contains(h));
		assertTrue(targets.contains(i));
		assertTrue(targets.contains(j));
		assertTrue(targets.contains(k));
		assertTrue(targets.contains(l));
		assertTrue(targets.contains(m));
	}
	
	//White: Targets that allow the user to enter a room
	@Test
	public void testTargetEnterRoom() {
		int location = testBoard.calcIndex(3, 15);
		Set<Integer> targets = testBoard.getTargets(location, 2);
		
		int a = testBoard.calcIndex(1, 15);
		int b = testBoard.calcIndex(2, 14);
		int c = testBoard.calcIndex(2, 16);
		int d = testBoard.calcIndex(3, 14);
		int e = testBoard.calcIndex(3, 17);
		int f = testBoard.calcIndex(4, 16);
		
		assertEquals(6, targets.size());
		assertTrue(targets.contains(a));
		assertTrue(targets.contains(b));
		assertTrue(targets.contains(c));
		assertTrue(targets.contains(d));
		assertTrue(targets.contains(e));
		assertTrue(targets.contains(f));
		//----------------------------------------------
		location = testBoard.calcIndex(8, 18);
		targets = testBoard.getTargets(location, 3);
		
		a = testBoard.calcIndex(7, 16);
		b = testBoard.calcIndex(8, 15);
		c = testBoard.calcIndex(9, 16);
		d = testBoard.calcIndex(9, 18);
		e = testBoard.calcIndex(8, 21);
		
		assertEquals(5, targets.size());
		assertTrue(targets.contains(a));
		assertTrue(targets.contains(b));
		assertTrue(targets.contains(c));
		assertTrue(targets.contains(d));
		assertTrue(targets.contains(e));
	}
	
	
	//Turquoise: Targets calculated when leaving a room
	@Test
	public void testTargetLeavingRoom() {
		int location = testBoard.calcIndex(17, 20);
		Set<Integer> targets = testBoard.getTargets(location, 3);
		
		int a = testBoard.calcIndex(16, 18);
		int b = testBoard.calcIndex(16, 22);
	
		assertEquals(2, targets.size());
		assertTrue(targets.contains(a));
		assertTrue(targets.contains(b));
		//----------------------------------------------
		location = testBoard.calcIndex(19, 15);
		targets = testBoard.getTargets(location, 2);
		
		a = testBoard.calcIndex(20, 14);
		b = testBoard.calcIndex(20, 16);
		int c = testBoard.calcIndex(21, 15);
		
		assertEquals(3, targets.size());
		assertTrue(targets.contains(a));
		assertTrue(targets.contains(b));
		assertTrue(targets.contains(c));
	}
}
