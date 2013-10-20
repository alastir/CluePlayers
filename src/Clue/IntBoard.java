// Will Hayne and Anthony Nguyen

package Clue;

import java.util.*;

public class IntBoard {
	private Map<Integer, LinkedList<Integer>> adj;
	private Set<Integer> targets;
	private boolean[] visited;
	
	public IntBoard(){
		adj = new HashMap<Integer, LinkedList<Integer>>(16);
		targets = new HashSet<Integer>();
		calcAdjacencies();
		visited = new boolean[16];
	}
	
	public void calcAdjacencies(){
		for (int i = 0; i < 4; ++i) {
			for (int j = 0; j < 4; ++j) {
				LinkedList<Integer> temp = new LinkedList<Integer>();
				int index = calcIndex(i, j);
				if ((j - 1) >= 0)
					temp.add(calcIndex(i, j-1));
				if ((i + 1) <= 3)
					temp.add(calcIndex(i+1, j));
				if ((j + 1) <= 3)
					temp.add(calcIndex(i, j+1));
				if ((i - 1) >= 0)
					temp.add(calcIndex(i-1, j));
				
				adj.put(index, temp);	
			}
		}
	}
	public void startTargets(int index, int steps){
		//int index = calcIndex(row, col);
		visited[index] = true;
		LinkedList<Integer> adjacentCells = new LinkedList<Integer>();
		for (int cell : adj.get(index)) {
			if (!visited[cell])
				adjacentCells.add(cell);
		}
		for (int adjCell : adjacentCells) {
			visited[adjCell] = true;
			if (steps == 1)
				targets.add(adjCell);
			else {
				steps--;
				startTargets(adjCell, steps);
				steps++;
			}
			visited[adjCell] = false;
		}
			
		
		
	}
	
	
	public Set<Integer> getTargets() {
		return targets;
	}
	
	public LinkedList<Integer> getAdjList(int row, int col) {
		return adj.get(calcIndex(row, col));
	}
	
	public int calcIndex(int row, int col) {
		return row*4 + col;
	}
	
	
	public static void main(String[] args) {
		//IntBoard board = new IntBoard();
		//System.out.println(targets.size());

	}

}

