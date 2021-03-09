package test;

import model.Desk;
import model.Player;
import model.PokerCard;
import processor.MCprocessor;

public class SituationAnalyzeTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Player player1=new Player();
		player1.setSerialNumber(0);
		Player player2=new Player();
		player2.setSerialNumber(1);
		Player player3=new Player();
		player2.setSerialNumber(2);
		Player player4=new Player();
		player2.setSerialNumber(3);
		Player player5=new Player();
		player2.setSerialNumber(4);
		Player player6=new Player();
		player2.setSerialNumber(5);
		player1.getHoldCards().add(new PokerCard(13, PokerCard.Dimonds));
		player1.getHoldCards().add(new PokerCard(13, PokerCard.Hearts));
//		player2.getHoldCards().add(new PokerCard(10, PokerCard.Clubs));
//		player2.getHoldCards().add(new PokerCard(10, PokerCard.Hearts));
//		player3.getHoldCards().add(new PokerCard(3, PokerCard.Spades));
//		player3.getHoldCards().add(new PokerCard(3, PokerCard.Hearts));
		Desk desk=new Desk();
		desk.getPlayers().add(player1);
		desk.getPlayers().add(player2);
		desk.getPlayers().add(player3);
		desk.getPlayers().add(player4);
		desk.getPlayers().add(player5);
		desk.getPlayers().add(player6);
//		desk.getCommunityCards().add(new PokerCard(11,PokerCard.Spades));
//		desk.getCommunityCards().add(new PokerCard(3,PokerCard.Hearts));
//		desk.getCommunityCards().add(new PokerCard(2,PokerCard.Spades));
//		desk.getCommunityCards().add(new PokerCard(2,PokerCard.Dimonds));
//		desk.getCommunityCards().add(new PokerCard(9,PokerCard.Clubs));
		MCprocessor mcprocessor=new MCprocessor();
		mcprocessor.setShowProgress(true);
		mcprocessor.situationAnalyzer(desk, 1000000, 2);
	}

}
