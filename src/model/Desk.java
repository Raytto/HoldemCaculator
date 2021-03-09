package model;

import java.util.ArrayList;
import java.util.List;

public class Desk {
	List<Player> players;
	List<PokerCard> communityCards;

	public Desk() {
		// TODO Auto-generated constructor stub
		players = new ArrayList<Player>();
		communityCards = new ArrayList<PokerCard>();
	}

	public List<Player> getPlayers() {
		return players;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}

	public List<PokerCard> getCommunityCards() {
		return communityCards;
	}

	public void setCommunityCards(List<PokerCard> communityCards) {
		this.communityCards = communityCards;
	}

	public ArrayList<PokerCard> getUnShownCards() throws Exception {
		boolean[] hasShown = new boolean[100];
		for (int i = 0; i < hasShown.length; i++)
			hasShown[i] = false;
		for (int i = 0; i < players.size(); i++) {
			Player player = players.get(i);
			for (int i2 = 0; i2 < player.getHoldCards().size(); i2++) {
				int h = player.getHoldCards().get(i2).getRank() + player.getHoldCards().get(i2).getSuit() * 20;
				if (hasShown[h]) {
					System.out.println(player.getHoldCards().get(i2));
					throw new Exception("Double Shown In Desk:" + player.getHoldCards().get(i2));
				}
				hasShown[h] = true;
			}
		}
		for (int i = 0; i < communityCards.size(); i++) {
			int h = communityCards.get(i).getRank() + communityCards.get(i).getSuit() * 20;
			if (hasShown[h]) {
				throw new Exception("Double Shown In Desk:" + communityCards.get(i));
			}
			hasShown[h] = true;
		}
		ArrayList<PokerCard> result = new ArrayList<PokerCard>();
		for (int suit = 0; suit <= 3; suit++) {
			for (int rank = 2; rank <= 14; rank++) {
				if (!hasShown[rank + suit * 20])
					result.add(new PokerCard(rank, suit));
			}
		}
		return result;
	}
}
