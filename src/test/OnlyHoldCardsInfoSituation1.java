package test;

import java.util.ArrayList;
import java.util.List;

import model.Desk;
import model.Player;
import model.PokerCard;
import processor.MCprocessor;

public class OnlyHoldCardsInfoSituation1 {

	public static void main(String[] args) {
		Player[] players;
		for(int numOfPlayers=2;numOfPlayers<=9;numOfPlayers++){
			System.out.println("NumOfPlayers : "+numOfPlayers);
			players=new Player[numOfPlayers];
			Desk desk=new Desk();
			for(int p=0;p<numOfPlayers;p++){
				players[p]=new Player();
				players[p].setSerialNumber(p);
				desk.getPlayers().add(players[p]);
			}
			MCprocessor mCprocessor=new MCprocessor();
			System.out.println("Flush:");
			double[][] result = new double[15][];
			int times=1000000;
			try {
				for (int card1Rank = 2; card1Rank <= 14; card1Rank++) {
					result[card1Rank] = new double[15];
					System.out.print(card1Rank+" : ");
					for (int card2Rank = 2; card2Rank < card1Rank; card2Rank++) {
						List<PokerCard> holdCards=new ArrayList<PokerCard>();
						holdCards.add(new PokerCard(card1Rank, PokerCard.Hearts));
						holdCards.add(new PokerCard(card2Rank, PokerCard.Hearts));
						desk.getPlayers().get(0).setHoldCards(holdCards);
						int[][][] tResult = mCprocessor.MutiThreadMC(1000000, desk, 2);
						if(tResult!=null){
							result[card1Rank][card2Rank]=(double)tResult[0][0][0]/times;
						}
						System.out.print(result[card1Rank][card2Rank]+"  ");
					}
					System.out.println();
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			System.out.println("Not Flush:");
			result = new double[15][];
			try {
				for (int card1Rank = 2; card1Rank <= 14; card1Rank++) {
					result[card1Rank] = new double[15];
					System.out.print(card1Rank+" : ");
					for (int card2Rank = 2; card2Rank <= card1Rank; card2Rank++) {
						List<PokerCard> holdCards=new ArrayList<PokerCard>();
						holdCards.add(new PokerCard(card1Rank, PokerCard.Hearts));
						holdCards.add(new PokerCard(card2Rank, PokerCard.Dimonds));
						desk.getPlayers().get(0).setHoldCards(holdCards);
						int[][][] tResult = mCprocessor.MutiThreadMC(1000000, desk, 2);
						if(tResult!=null){
							result[card1Rank][card2Rank]=(double)tResult[0][0][0]/times;
						}
						System.out.print(result[card1Rank][card2Rank]+"  ");
					}
					System.out.println();
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}

}
