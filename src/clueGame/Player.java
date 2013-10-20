package clueGame;

import java.util.ArrayList;

public class Player {
	public String name;
	public ArrayList<Card> myCards = new ArrayList<Card>();
	
	public Player() {
		
	}
	
	public Player(String name) {
		super();
		this.name = name;
	}
	
	public Card disproveSuggestion(String person, String room, String weapon) {
		Card testCard = new Card();
		return testCard;
	}
	
	
}
