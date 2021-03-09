package test;

import java.util.ArrayList;
import java.util.List;

import model.Desk;
import model.PokerCard;
import processor.BasicProcessor;

public class compareTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<PokerCard> aCards=new ArrayList<PokerCard>();
		List<PokerCard> bCards=new ArrayList<PokerCard>();
		List<PokerCard> cCards=new ArrayList<PokerCard>();
		List<PokerCard> tCards=new ArrayList<PokerCard>();
		aCards.add(new PokerCard(12, PokerCard.Spades));
		aCards.add(new PokerCard(10, PokerCard.Spades));
		cCards.add(new PokerCard(12, PokerCard.Dimonds));
		cCards.add(new PokerCard(11, PokerCard.Hearts));
		cCards.add(new PokerCard(9, PokerCard.Dimonds));
		cCards.add(new PokerCard(2, PokerCard.Hearts));
		cCards.add(new PokerCard(6, PokerCard.Clubs));
		BasicProcessor basicProcessor=new BasicProcessor();
		tCards.addAll(aCards);
		tCards.addAll(cCards);
		Desk desk=new Desk();
		try {
			System.out.println(basicProcessor.kind(aCards,cCards));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}

	}

}
