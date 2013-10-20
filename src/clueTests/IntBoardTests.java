// Will Hayne and Anthony Nguyen

package clueTests;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import Clue.IntBoard;

public class IntBoardTests {
	private IntBoard board;
	
	@Before
	public void setUp() {
		System.out.println("In @Before");
		board = new IntBoard();
	}
	@Test
	public void testCalcIndex() {
		int index = board.calcIndex(2,2);
		Assert.assertEquals(10, index);
		index = board.calcIndex(2, 3);
		Assert.assertEquals(11, index);
	}
	
	@Test
	public void testAdjacency0() {
		LinkedList<Integer> testList = board.getAdjList(0, 0);
		Assert.assertTrue(testList.contains(4));
		Assert.assertTrue(testList.contains(1));
		Assert.assertEquals(2, testList.size());
	}
	
	@Test
	public void testAdjacency15() {
		LinkedList<Integer> testList = board.getAdjList(3, 3);
		Assert.assertTrue(testList.contains(14));
		Assert.assertTrue(testList.contains(11));
		Assert.assertEquals(2, testList.size());
	}

	@Test
	public void testAdjacency7() {
		LinkedList<Integer> testList = board.getAdjList(3, 1);
		Assert.assertTrue(testList.contains(12));
		Assert.assertTrue(testList.contains(9));
		Assert.assertTrue(testList.contains(14));
		Assert.assertEquals(3, testList.size());
	}
	
	@Test
	public void testAdjacency8() {
		LinkedList<Integer> testList = board.getAdjList(0,2);
		Assert.assertTrue(testList.contains(1));
		Assert.assertTrue(testList.contains(6));
		Assert.assertTrue(testList.contains(3));
		Assert.assertEquals(3, testList.size());
	}
	
	@Test
	public void testAdjacency5() {
		LinkedList<Integer> testList = board.getAdjList(1,1);
		Assert.assertTrue(testList.contains(4));
		Assert.assertTrue(testList.contains(9));
		Assert.assertTrue(testList.contains(1));
		Assert.assertTrue(testList.contains(6));
		Assert.assertEquals(4, testList.size());
	}
	
	@Test
	public void testAdjacency10() {
		LinkedList<Integer> testList = board.getAdjList(2,2);
		Assert.assertTrue(testList.contains(6));
		Assert.assertTrue(testList.contains(11));
		Assert.assertTrue(testList.contains(14));
		Assert.assertTrue(testList.contains(9));
		Assert.assertEquals(4, testList.size());
	}
	
	// testing Target calculation
	
	@Test
	public void testTargets0_3() {
		board.startTargets(board.calcIndex(0,0),3);
		Set<Integer> targets = board.getTargets();
		Assert.assertEquals(6, targets.size());
		Assert.assertTrue(targets.contains(12));
		Assert.assertTrue(targets.contains(9));
		Assert.assertTrue(targets.contains(3));
		Assert.assertTrue(targets.contains(4));
		Assert.assertTrue(targets.contains(6));
		Assert.assertTrue(targets.contains(1));
	}
	
	@Test
	public void testTargets15_2() {
		board.startTargets(board.calcIndex(3,3),2);
		Set<Integer> targets = board.getTargets();
		System.out.println(targets);
		Assert.assertEquals(3, targets.size());
		Assert.assertTrue(targets.contains(13));
		Assert.assertTrue(targets.contains(10));
		Assert.assertTrue(targets.contains(7));
	}
	
	@Test
	public void testTargets7_4() {
		board.startTargets(board.calcIndex(1,3),4);
		Set<Integer> targets = board.getTargets();
		Assert.assertEquals(7, targets.size());
		Assert.assertTrue(targets.contains(0));
		Assert.assertTrue(targets.contains(5));
		Assert.assertTrue(targets.contains(10));
		Assert.assertTrue(targets.contains(8));
		Assert.assertTrue(targets.contains(13));
		Assert.assertTrue(targets.contains(2));
		Assert.assertTrue(targets.contains(15));
	}
	
	@Test
	public void testTargets8_4() {
		board.startTargets(board.calcIndex(2,0),4);
		Set<Integer> targets = board.getTargets();
		Assert.assertEquals(7, targets.size());
		Assert.assertTrue(targets.contains(2));
		Assert.assertTrue(targets.contains(5));
		Assert.assertTrue(targets.contains(7));
		Assert.assertTrue(targets.contains(10));
		Assert.assertTrue(targets.contains(15));
		Assert.assertTrue(targets.contains(0));
		Assert.assertTrue(targets.contains(13));
	}
	
	@Test
	public void testTargets9_3() {
		board.startTargets(board.calcIndex(2,1),3);
		Set<Integer> targets = board.getTargets();
		Assert.assertEquals(8, targets.size());
		Assert.assertTrue(targets.contains(0));
		Assert.assertTrue(targets.contains(5));
		Assert.assertTrue(targets.contains(13));	
		Assert.assertTrue(targets.contains(8));
		Assert.assertTrue(targets.contains(15));
		Assert.assertTrue(targets.contains(10));
		Assert.assertTrue(targets.contains(7));
		Assert.assertTrue(targets.contains(2));
	}
	
	@Test
	public void testTargets6_2() {
		board.startTargets(board.calcIndex(1,2),2);
		Set<Integer> targets = board.getTargets();
		Assert.assertEquals(6, targets.size());
		Assert.assertTrue(targets.contains(1));
		Assert.assertTrue(targets.contains(3));
		Assert.assertTrue(targets.contains(11));
		Assert.assertTrue(targets.contains(14));
		Assert.assertTrue(targets.contains(9));
		Assert.assertTrue(targets.contains(4));
	}
}
