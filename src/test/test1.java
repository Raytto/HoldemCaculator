package test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import model.PokerCard;
import processor.BasicProcessor;

public class test1 {

	public static class CardComparator implements Comparator<PokerCard> {

		@Override
		public int compare(PokerCard p1, PokerCard p2) {
			if (p1.getRank() > p2.getRank()) {
				return -1;
			} 
			return 1;
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<PokerCard> t1=new ArrayList<PokerCard>();
		t1.add(new PokerCard(2, PokerCard.Clubs));
		t1.add(new PokerCard(3, PokerCard.Clubs));
		t1.add(new PokerCard(4, PokerCard.Dimonds));
		t1.add(new PokerCard(3, PokerCard.Clubs));
		t1.add(new PokerCard(10, PokerCard.Clubs));
		t1.add(new PokerCard(7, PokerCard.Hearts));
		t1.add(new PokerCard(8, PokerCard.Clubs));
		System.out.println(t1);
		t1.sort(new CardComparator());
		System.out.println(t1);
		System.out.println(t1.contains(new PokerCard(5, PokerCard.Clubs)));
//		BasicProcessor basicProcessor=new BasicProcessor();
//		try{
//		System.out.println(basicProcessor.OnePair(t1));
//		}catch(Exception e){
//			System.out.println(e.getMessage());
//		}
	}

}
