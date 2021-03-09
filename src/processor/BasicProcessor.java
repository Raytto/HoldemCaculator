package processor;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import model.PokerCard;
import test.test1.CardComparator;

public class BasicProcessor {

	private class CardComparator implements Comparator<PokerCard> {

		@Override
		public int compare(PokerCard p1, PokerCard p2) {
			if (p1.getRank() > p2.getRank()) {
				return -1;
			}
			return 1;
		}
	}

	/**
	 * 
	 * @param aCards
	 * @param bCards
	 * @param communityCards
	 * @return 1 for a stronger;2 for b stronger,0 for equal
	 * @throws Exception
	 */
	public int compare(List<PokerCard> aCards, List<PokerCard> bCards, List<PokerCard> communityCards)
			throws Exception {
		List<PokerCard> allACards = new ArrayList<PokerCard>();
		List<PokerCard> allBCards = new ArrayList<PokerCard>();
		allACards.addAll(aCards);
		allACards.addAll(communityCards);
		allBCards.addAll(bCards);
		allBCards.addAll(communityCards);
		allACards.sort(new CardComparator());
		allBCards.sort(new CardComparator());
		int a = StraightFlush(allACards);
		int b = StraightFlush(allBCards);
		if (a > b)
			return 1;
		if (b > a)
			return 2;
		a = FourOfAKind(allACards);
		b = FourOfAKind(allBCards);
		if (a > b)
			return 1;
		if (b > a)
			return 2;
		a = FullHouse(allACards);
		b = FullHouse(allBCards);
		if (a > b)
			return 1;
		if (b > a)
			return 2;
		a = Flush(allACards);
		b = Flush(allBCards);
		if (a > b)
			return 1;
		if (b > a)
			return 2;
		a = Straight(allACards);
		b = Straight(allBCards);
		if (a > b)
			return 1;
		if (b > a)
			return 2;
		a = ThreeOfAKind(allACards);
		b = ThreeOfAKind(allBCards);
		if (a > b)
			return 1;
		if (b > a)
			return 2;
		a = TwoPair(allACards);
		b = TwoPair(allBCards);
		if (a > b)
			return 1;
		if (b > a)
			return 2;
		a = OnePair(allACards);
		b = OnePair(allBCards);
		if (a > b)
			return 1;
		if (b > a)
			return 2;
		a = HighCard(allACards);
		b = HighCard(allBCards);
		if (a > b)
			return 1;
		if (b > a)
			return 2;
		return 0;
	}

	private int StraightFlush(List<PokerCard> cards) throws Exception {
		if (cards.size() != 7)
			throw new Exception("card size error in StraightFlush:" + cards);
		for (int start = 0; start < cards.size() - 3; start++) {
			int straight = 1;
			int last = start;
			int p = last + 1;
			while (true) {
				while (p < cards.size() && cards.get(p).getRank() == cards.get(p - 1).getRank()) {
					p++;
				} // get rid of pairs;
				if (p >= cards.size())
					break;
				if (cards.get(p).getRank() == cards.get(last).getRank() - 1
						&& cards.get(p).getSuit() == cards.get(last).getSuit()) {
					straight++;
					last = p;
					p++;
				} else {
					break;
				}
			}
			// System.out.println(cards.get(start).getRank()+" "+straight );
			if (cards.get(start).getRank() == 5 && straight == 4) {// check if
																	// 5432A
				int i;
				for (i = 0; i < cards.size(); i++) {
					if (cards.get(i).getRank() != 14)
						break;
					if (cards.get(i).getSuit() == cards.get(start).getSuit())
						break;
				}
				if (cards.get(i).getRank() == 14 && cards.get(i).getSuit() == cards.get(start).getSuit()) {
					straight = 5;
				}
			}
			if (straight == 5)
				return cards.get(start).getRank();
		}
		return 0;
	}

	private int FourOfAKind(List<PokerCard> cards) throws Exception {
		if (cards.size() != 7)
			throw new Exception("card size error in FourOfAKind:" + cards);
		int FourOfAKind = -1;
		int OtherHighCard = -1;
		int p1 = 0, p2 = 1;
		while (p1 < cards.size()) {
			int num = 1;
			while (p2 < cards.size() && cards.get(p2).getRank() == cards.get(p1).getRank()) {
				num++;
				p2++;
			}
			if (num == 4) {
				FourOfAKind = p1;
			} else {
				if (OtherHighCard == -1) {
					OtherHighCard = p1;
				}
			}
			p1 = p2;
			p2 = p2 + 1;
		}
		if (FourOfAKind != -1) {
			return cards.get(FourOfAKind).getRank() * 100 + cards.get(OtherHighCard).getRank();
		}
		return 0;
	}

	private int FullHouse(List<PokerCard> cards) throws Exception {
		if (cards.size() != 7)
			throw new Exception("card size error in FullHouse:" + cards);
		int triad = -1;
		int pair = -1;
		int p1 = 0, p2 = 1;
		while (p1 < cards.size()) {
			int num = 1;
			while (p2 < cards.size() && cards.get(p2).getRank() == cards.get(p1).getRank()) {
				num++;
				p2++;
			}
			if (num >= 3 && triad == -1) {
				triad = p1;
			} else {
				if (num >= 2 && pair == -1) {
					pair = p1;
				}
			}
			p1 = p2;
			p2 = p2 + 1;
		}
		//System.out.println(triad + " " + pair);
		if (triad != -1 && pair != -1) {
			return cards.get(triad).getRank() * 100 + cards.get(pair).getRank();
		}
		return 0;
	}

	private int Flush(List<PokerCard> cards) throws Exception {
		if (cards.size() != 7)
			throw new Exception("card size error in Flush:" + cards);
		int[] numOfSuit = new int[4];
		for (int p = 0; p < cards.size(); p++) {
			numOfSuit[cards.get(p).getSuit()]++;
		}
		for (int s = 0; s < 4; s++) {
			if (numOfSuit[s] >= 5) {
				int result = 0;
				int leftCards = 5;
				for (int p = 0; p < cards.size(); p++) {
					if (cards.get(p).getSuit() == s) {
						result = result * 20 + cards.get(p).getRank();
						leftCards--;
					}
					if (leftCards == 0)
						break;
				}
				return result;
			}
		}
		return 0;
	}

	private int Straight(List<PokerCard> cards) throws Exception {
		if (cards.size() != 7)
			throw new Exception("card size error in Straight:" + cards);
		for (int start = 0; start < cards.size() - 3; start++) {
			int straight = 1;
			int last = start;
			int p = last + 1;
			while (true) {
				while (p < cards.size() && cards.get(p).getRank() == cards.get(p - 1).getRank()) {
					p++;
				} // get rid of pairs;
				if (p >= cards.size())
					break;
				if (cards.get(p).getRank() == cards.get(last).getRank() - 1) {
					straight++;
					last = p;
					p++;
				} else {
					break;
				}
			}
			// System.out.println(cards.get(start).getRank()+" "+straight );
			if (cards.get(start).getRank() == 5 && straight == 4) {// check if
																	// 5432A
				if (cards.get(0).getRank() == 14)
					straight = 5;
			}
			if (straight == 5)
				return cards.get(start).getRank();
		}
		return 0;
	}

	private int ThreeOfAKind(List<PokerCard> cards) throws Exception {
		if (cards.size() != 7)
			throw new Exception("card size error in ThreeOfAKind:" + cards);
		int threeOfAKind = -1;
		int p1 = 0, p2 = 1;
		while (p1 < cards.size()) {
			int num = 1;
			while (p2 < cards.size() && cards.get(p2).getRank() == cards.get(p1).getRank()) {
				num++;
				p2++;
			}
			if (num == 3 && threeOfAKind == -1) {
				threeOfAKind = p1;
			}
			p1 = p2;
			p2 = p2 + 1;
		}
		if (threeOfAKind != -1) {
			int result = cards.get(threeOfAKind).getRank();
			int cardsLeft = 2;
			for (int p = 0; p < cards.size(); p++) {
				if (cards.get(p).getRank() != cards.get(threeOfAKind).getRank()) {
					result = result * 20 + cards.get(p).getRank();
					cardsLeft--;
					if (cardsLeft == 0)
						break;
				}
			}
			return result;
		}
		return 0;
	}

	private int TwoPair(List<PokerCard> cards) throws Exception {
		if (cards.size() != 7)
			throw new Exception("card size error in TwoPair:" + cards);
		int pair1 = -1;
		int pair2 = -1;
		int otherHigh = -1;
		int p1 = 0, p2 = 1;
		while (p1 < cards.size()) {
			int num = 1;
			while (p2 < cards.size() && cards.get(p2).getRank() == cards.get(p1).getRank()) {
				num++;
				p2++;
			}
			if (num >= 2) {
				if (pair1 == -1) {
					pair1 = p1;
				} else {
					if (pair2 == -1) {
						pair2 = p1;
					} else {
						if (otherHigh == -1) {
							otherHigh = p1;
						}
					}
				}
			} else {
				if (otherHigh == -1) {
					otherHigh = p1;
				}
			}
			p1 = p2;
			p2 = p2 + 1;
		}
		if (pair1 != -1 && pair2 != -1 && otherHigh != -1) {
			return cards.get(pair1).getRank() * 400 + cards.get(pair2).getRank() * 20 + cards.get(otherHigh).getRank();
		}
		return 0;
	}

	private int OnePair(List<PokerCard> cards) throws Exception {
		if (cards.size() != 7)
			throw new Exception("card size error in OnePair:" + cards);
		int pair = -1;
		int p1 = 0, p2 = 1;
		while (p1 < cards.size()) {
			int num = 1;
			while (p2 < cards.size() && cards.get(p2).getRank() == cards.get(p1).getRank()) {
				num++;
				p2++;
			}
			if (num == 2 && pair == -1) {
				pair = p1;
			}
			p1 = p2;
			p2 = p2 + 1;
		}
		if (pair != -1) {
			int result = cards.get(pair).getRank();
			int cardsLeft = 3;
			for (int p = 0; p < cards.size(); p++) {
				if (cards.get(p).getRank() != cards.get(pair).getRank()) {
					result = result * 20 + cards.get(p).getRank();
					cardsLeft--;
					if (cardsLeft == 0)
						break;
				}
			}
			return result;
		}
		return 0;
	}

	private int HighCard(List<PokerCard> cards) throws Exception {
		int result = 0;
		int cardsLeft = 5;
		for (int p = 0; p < cards.size(); p++) {
			result = result * 20 + cards.get(p).getRank();
			cardsLeft--;
			if (cardsLeft == 0)
				break;
		}
		return result;
	}

	private int kind(List<PokerCard> cards) throws Exception{
		if(StraightFlush(cards)>0)
			return 0;
		if(FourOfAKind(cards)>0)
			return 1;
		if(FullHouse(cards)>0)
			return 2;
		if(Flush(cards)>0)
			return 3;
		if(Straight(cards)>0)
			return 4;
		if(ThreeOfAKind(cards)>0)
			return 5;
		if(TwoPair(cards)>0)
			return 6;
		if(OnePair(cards)>0)
			return 7;
		if(HighCard(cards)>0)
			return 8;
		return -1;
	}

	public int kind(List<PokerCard> holdCards,List<PokerCard> communityCards) throws Exception{
		List<PokerCard> allCards=new ArrayList<PokerCard>();
		allCards.addAll(holdCards);
		allCards.addAll(communityCards);
		allCards.sort(new CardComparator());
		return kind(allCards);
	}
}
