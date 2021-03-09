package model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PrimitiveIterator.OfDouble;

public class PokerCard {
	public final static int Spades = 0;
	public final static int Hearts = 1;
	public final static int Clubs = 2;
	public final static int Dimonds = 3;
	private int suit;
	private int rank;

	public PokerCard(int rank, int suit) {
		// TODO Auto-generated constructor stub
		this.suit = suit;
		this.rank = rank;
	}

	public int getSuit() {
		return suit;
	}

	public void setSuit(int suit) {
		this.suit = suit;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	/**
	 * without the two jokers
	 * 
	 * @return
	 */
	public static LinkedList<PokerCard> aPackOfCards() {
		LinkedList<PokerCard> result = new LinkedList<PokerCard>();
		for (int suit = Spades; suit <= Dimonds; suit++) {
			for (int rank = 2; rank <= 14; rank++) {
				PokerCard pokerCard = new PokerCard(rank, suit);
				result.add(pokerCard);
			}
		}
		return result;
	}

	public String toString() {
		String result="";
		if (rank < 10) {
			result += rank;
		}else{
			if(rank==10){
				result+="T";
			}else if(rank==11){
				result+="J";
			}else if(rank==12){
				result+="Q";
			}else if(rank==13){
				result+="K";
			}if(rank==14){
				result+="A";
			}	
		}
		
		if (this.suit == Spades)
			result+="s";
		else if (this.suit == Dimonds)
			result+="d";
		else if (this.suit == Clubs)
			result+="c";
		else if (this.suit == Hearts)
			result+="h";
		return result;
	}
}
