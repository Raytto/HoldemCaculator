package model;

import java.util.ArrayList;
import java.util.List;

public class Player {
	private List<PokerCard> holdCards;
	private int serialNumber;
	
	boolean onTable;
	double quitP;
	
	public Player() {
		// TODO Auto-generated constructor stub
		holdCards=new ArrayList<PokerCard>();
		serialNumber=-1;
		onTable=true;
		quitP=0;
	}
	public List<PokerCard> getHoldCards() {
		return holdCards;
	}
	public void setHoldCards(List<PokerCard> holdCards) {
		this.holdCards = holdCards;
	}
	public int getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(int serialNumber) {
		this.serialNumber = serialNumber;
	}
	public boolean isOnTable() {
		return onTable;
	}
	public void setOnTable(boolean onTable) {
		this.onTable = onTable;
	}
	public double getQuitP() {
		return quitP;
	}
	public void setQuitP(double quitP) {
		this.quitP = quitP;
	}
	
	
	

}
