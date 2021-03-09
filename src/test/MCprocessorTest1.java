package test;

import model.Desk;
import model.Player;
import model.PokerCard;
import processor.MCprocessor;

public class MCprocessorTest1 {

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
		player1.getHoldCards().add(new PokerCard(14, PokerCard.Hearts));
		player1.getHoldCards().add(new PokerCard(10, PokerCard.Dimonds));
		player2.getHoldCards().add(new PokerCard(3, PokerCard.Dimonds));
		player2.getHoldCards().add(new PokerCard(5, PokerCard.Dimonds));
//		player3.getHoldCards().add(new PokerCard(3, PokerCard.Spades));
//		player3.getHoldCards().add(new PokerCard(3, PokerCard.Hearts));
		Desk desk=new Desk();
		desk.getPlayers().add(player1);
		desk.getPlayers().add(player2);
//		desk.getPlayers().add(player3);
//		desk.getPlayers().add(player4);
//		desk.getPlayers().add(player5);
//		desk.getPlayers().add(player6);
//		desk.getCommunityCards().add(new PokerCard(7,PokerCard.Dimonds));
//		desk.getCommunityCards().add(new PokerCard(9,PokerCard.Dimonds));
//		desk.getCommunityCards().add(new PokerCard(11,PokerCard.Dimonds));
//		desk.getCommunityCards().add(new PokerCard(12,PokerCard.Dimonds));
//		desk.getCommunityCards().add(new PokerCard(13,PokerCard.Dimonds));
		MCprocessor mc=new MCprocessor();
		System.out.println("------------start-----------");
		long startTime = System.nanoTime();
		try {
			int time=10000000;
			int[][][]result=mc.timesOfWinOfEachPlayers(time, desk);
			System.out.println("------------win info-----------");
			for(int p=0;p<desk.getPlayers().size();p++){
				for(int i=0;i<=desk.getPlayers().size();i++){
					System.out.print(result[0][p][i]+"("+(double)result[0][p][i]/time+")  ");
				}
				System.out.println();
			}
			System.out.println("------------win kind-----------");
			for(int p=0;p<desk.getPlayers().size();p++){
				for(int i=0;i<=8;i++){
					System.out.print(result[1][p][i]+"("+(double)result[1][p][i]/time+")  ");
				}
				System.out.println();
			}
			System.out.println("-----------lose kind-----------");
			for(int p=0;p<desk.getPlayers().size();p++){
				for(int i=0;i<=8;i++){
					System.out.print(result[2][p][i]+"("+(double)result[2][p][i]/time+")  ");
				}
				System.out.println();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
		long consumingTime = System.nanoTime() - startTime;
		System.out.println(consumingTime/1000000+" ms");
	}

}
