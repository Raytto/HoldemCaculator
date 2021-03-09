package processor;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import model.Desk;
import model.Player;
import model.PokerCard;

public class MCprocessor {
	private Desk desk;

	private String name;
	private boolean showProgress;
	private boolean checkRandDesk;

	public MCprocessor() {
		// TODO Auto-generated constructor stub
		showProgress = false;
		checkRandDesk = false;
	}

	public void setDesk(Desk desk) {
		this.desk = desk;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setShowProgress(boolean showProgress) {
		this.showProgress = showProgress;
	}

	public void setCheckRandDesk(boolean checkRandDesk) {
		this.checkRandDesk = checkRandDesk;
	}

	public Desk getDesk() {
		return desk;
	}

	public boolean isShowProgress() {
		return showProgress;
	}

	/**
	 * need to dec numOfAvailableCards outside when finish once
	 * 
	 * @param undealedCards
	 * @param aimCards
	 * @param numOfAvailableCards
	 */
	private void randDealACard(List<PokerCard> undealedCards, ArrayList<PokerCard> aimCards, int numOfAvailableCards) {
		int pCard = (int) (numOfAvailableCards * Math.random());
		PokerCard pokerCard = undealedCards.get(pCard);
		undealedCards.set(pCard, undealedCards.get(numOfAvailableCards - 1));
		undealedCards.set(numOfAvailableCards - 1, pokerCard);
		aimCards.add(pokerCard);
	}

	private Desk randDeal(Desk presentDesk, List<PokerCard> undealedCards) throws Exception {
		int numOfAvailableCards = undealedCards.size();
		Desk result = new Desk();
		for (int p = 0; p < presentDesk.getPlayers().size(); p++) {
			Player player = new Player();
			ArrayList<PokerCard> holdCards = new ArrayList<PokerCard>();
			holdCards.addAll(presentDesk.getPlayers().get(p).getHoldCards());
			while (holdCards.size() < 2) {
				randDealACard(undealedCards, holdCards, numOfAvailableCards);
				numOfAvailableCards--;
			}
			player.setHoldCards(holdCards);
			player.setSerialNumber(presentDesk.getPlayers().get(p).getSerialNumber());
			result.getPlayers().add(player);
		}
		ArrayList<PokerCard> communityCards = new ArrayList<PokerCard>();
		communityCards.addAll(presentDesk.getCommunityCards());
		while (communityCards.size() < 5) {
			randDealACard(undealedCards, communityCards, numOfAvailableCards);
			numOfAvailableCards--;
		}
		result.setCommunityCards(communityCards);
		return result;
	}

	/**
	 * int[0][][] win info ;int[1][][] win of kind ;int[2][][] lose by
	 * kind;int[3][][] lose kind;int[4][][] kind;int[5][][] kind win
	 * 
	 * @param totalTimes
	 * @param desk
	 * @return
	 * @throws Exception
	 */
	public int[][][] timesOfWinOfEachPlayers(int totalTimes, Desk desk) throws Exception {
		setDesk(desk);
		int[][][] result = new int[5][][];
		result[0] = new int[desk.getPlayers().size()][];
		result[1] = new int[desk.getPlayers().size()][];
		result[2] = new int[desk.getPlayers().size()][];
		result[3] = new int[desk.getPlayers().size()][];
		result[4] = new int[desk.getPlayers().size()][];
		for (int i = 0; i < desk.getPlayers().size(); i++) {
			result[0][i] = new int[desk.getPlayers().size() + 1];
			result[1][i] = new int[9];
			result[2][i] = new int[9];
			result[3][i] = new int[9];
			result[4][i] = new int[9];
		}
		BasicProcessor basicProcessor = new BasicProcessor();
		List<PokerCard> undealedCards = desk.getUnShownCards();
		for (; totalTimes > 0; totalTimes--) {
			if (showProgress && (totalTimes % 250000 == 0)) {
				System.out.println(totalTimes + "times left in " + name);
			}
			Desk aRandDesk = randDeal(desk, undealedCards);
			if (checkRandDesk)
				check(aRandDesk);
			boolean[] lose = new boolean[aRandDesk.getPlayers().size()];
			for (int p1 = 0; p1 < aRandDesk.getPlayers().size() - 1; p1++)
				for (int p2 = p1 + 1; p2 < aRandDesk.getPlayers().size(); p2++)
					if ((!lose[p1]) && (!lose[p2])) {
						int compareResult = basicProcessor.compare(aRandDesk.getPlayers().get(p1).getHoldCards(),
								aRandDesk.getPlayers().get(p2).getHoldCards(), aRandDesk.getCommunityCards());
						if (compareResult == 1) {
							lose[p2] = true;
							continue;
						}
						if (compareResult == 2) {
							lose[p1] = true;
							continue;
						}
					}
			int numOfWiner = 0;
			int winKind = -1;
			int[] kinds = new int[aRandDesk.getPlayers().size()];
			for (int p = 0; p < aRandDesk.getPlayers().size(); p++) {
				kinds[p] = basicProcessor.kind(aRandDesk.getPlayers().get(p).getHoldCards(),
						aRandDesk.getCommunityCards());
				result[4][p][kinds[p]]++;
				if (!lose[p]) {
					numOfWiner++;
					result[1][p][kinds[p]]++;
					if (winKind == -1) {
						winKind = kinds[p];
					} else {
						if (winKind != kinds[p]) {
							throw new Exception("ERROR:win kind not matched");
						}
					}
				} else {
					result[3][p][kinds[p]]++;
				}
			}
			if (winKind == -1) {
				throw new Exception("ERROR:no winer");
			}
			for (int p = 0; p < aRandDesk.getPlayers().size(); p++) {
				if (!lose[p]) {
					result[0][p][numOfWiner]++;
				} else {
					result[2][p][winKind]++;
				}
			}
		}
		for (int p = 0; p < desk.getPlayers().size(); p++) {
			for (int i = 1; i <= desk.getPlayers().size(); i++)
				result[0][p][0] += result[0][p][i];
		}
		return result;
	}

	private void check(Desk desk) throws Exception {
		// check num
		for (int i = 0; i < desk.getPlayers().size(); i++)
			if (desk.getPlayers().get(i).getHoldCards().size() != 2) {
				throw new Exception(
						"RandDealError in check,HoldCard.size:" + desk.getPlayers().get(i).getHoldCards().size());
			}
		if (desk.getCommunityCards().size() != 5) {
			throw new Exception("RandDealError in check,CommunityCards.size:" + desk.getCommunityCards().size());
		}
		boolean[] hasShown = new boolean[100];
		for (int i = 0; i < hasShown.length; i++)
			hasShown[i] = false;
		for (int i = 0; i < desk.getPlayers().size(); i++) {
			Player player = desk.getPlayers().get(i);
			for (int i2 = 0; i2 < player.getHoldCards().size(); i2++) {
				int h = player.getHoldCards().get(i2).getRank() + player.getHoldCards().get(i2).getSuit() * 20;
				if (hasShown[h]) {
					System.out.println(player.getHoldCards().get(i2));
					throw new Exception("ERROR in check:Double Shown In Desk:" + player.getHoldCards().get(i2));
				}
				hasShown[h] = true;
			}
		}
		for (int i = 0; i < desk.getCommunityCards().size(); i++) {
			int h = desk.getCommunityCards().get(i).getRank() + desk.getCommunityCards().get(i).getSuit() * 20;
			if (hasShown[h]) {
				throw new Exception("ERROR in check:Double Shown In Desk:" + desk.getCommunityCards().get(i));
			}
			hasShown[h] = true;
		}
	}

	private class MCThread implements Runnable {
		Desk desk;
		boolean showProgress;
		int times;
		int[][][] result;
		String name;
		boolean finished;

		public MCThread(Desk desk, int times, String name, boolean showProgress) {
			// TODO Auto-generated constructor stub
			this.desk = desk;
			this.times = times;
			this.name = name;
			result = null;
			finished = false;
			this.showProgress = showProgress;
		}

		public void run() {
			MCprocessor mCprocessor = new MCprocessor();
			this.finished = false;
			mCprocessor.setName(this.name);
			mCprocessor.setShowProgress(showProgress);
			try {
				this.result = mCprocessor.timesOfWinOfEachPlayers(this.times, this.desk);
				// System.out.println("got result in " + this.name);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println("ERROR in :" + this.name + ":" + e.getMessage());
			}
			this.finished = true;
		}

		public Desk getDesk() {
			return desk;
		}

		public void setDesk(Desk desk) {
			this.desk = desk;
		}

		public int[][][] getResult() {
			return result;
		}

		public void setResult(int[][][] result) {
			this.result = result;
		}

		public int getTimes() {
			return times;
		}

		public void setTimes(int times) {
			this.times = times;
		}

		public boolean isFinished() {
			return finished;
		}

	}

	public int[][][] MutiThreadMC(int totalTimes, Desk desk, int numOfThread) throws Exception {
		int[][][] result = new int[5][][];
		result[0] = new int[desk.getPlayers().size()][];
		result[1] = new int[desk.getPlayers().size()][];
		result[2] = new int[desk.getPlayers().size()][];
		result[3] = new int[desk.getPlayers().size()][];
		result[4] = new int[desk.getPlayers().size()][];
		// result[3] = new int[desk.getPlayers().size()][];
		for (int i = 0; i < desk.getPlayers().size(); i++) {
			result[0][i] = new int[desk.getPlayers().size() + 1];
			result[1][i] = new int[9];
			result[2][i] = new int[9];
			result[3][i] = new int[9];
			result[4][i] = new int[9];
		}
		Thread[] thread = new Thread[numOfThread];
		MCThread[] mcThread = new MCThread[numOfThread];
		for (int i = 0; i < numOfThread; i++) {
			if (i != numOfThread - 1) {
				mcThread[i] = new MCThread(desk, totalTimes / numOfThread, "Thread" + i, showProgress);
				thread[i] = new Thread(mcThread[i]);
			} else {
				mcThread[i] = new MCThread(desk, totalTimes - (totalTimes / numOfThread) * (numOfThread - 1),
						"Thread" + i, showProgress);
				thread[i] = new Thread(mcThread[i]);
			}
			thread[i].start();
			// System.out.println("Thread" + i + " start");
		}
		while (true) {
			boolean allFinished = true;
			for (int i = 0; i < numOfThread; i++) {
				if (!mcThread[i].finished) {
					allFinished = false;
					break;
				}
			}
			if (allFinished) {
				break;
			}
			Thread.sleep(100);
		}
		for (int i = 0; i < numOfThread; i++) {
			for (int i2 = 0; i2 < result.length; i2++)
				for (int i3 = 0; i3 < result[i2].length; i3++)
					for (int i4 = 0; i4 < result[i2][i3].length; i4++) {
						result[i2][i3][i4] += mcThread[i].getResult()[i2][i3][i4];
					}
		}
		return result;
	}

	public int[][][] situationAnalyzer(Desk desk, int totalTimes, int numOfThread) {
		System.out.println("------------start-----------");
		long startTime = System.nanoTime();
		int[][][] result = null;
		try {
			result = MutiThreadMC(totalTimes, desk, numOfThread);
			System.out.println("------------win info-----------");
			for (int p = 0; p < desk.getPlayers().size(); p++) {
				for (int i = 0; i <= desk.getPlayers().size(); i++) {
					System.out.print(result[0][p][i] + "(" + (double) result[0][p][i] / totalTimes + ")  ");
				}
				System.out.println();
			}
			System.out.println("------------win kind-----------");
			for (int p = 0; p < desk.getPlayers().size(); p++) {
				for (int i = 0; i <= 8; i++) {
					System.out.print(result[1][p][i] + "(" + (double) result[1][p][i] / result[0][p][0] + ")  ");
				}
				System.out.println();
			}
			System.out.println("-----------lose by kind-----------");
			for (int p = 0; p < desk.getPlayers().size(); p++) {
				for (int i = 0; i <= 8; i++) {
					System.out.print(result[2][p][i] + "(" + (double) result[2][p][i] / totalTimes + ")  ");
				}
				System.out.println();
			}
			System.out.println("-----------lose kind-----------");
			for (int p = 0; p < desk.getPlayers().size(); p++) {
				for (int i = 0; i <= 8; i++) {
					System.out.print(result[3][p][i] + "(" + (double) result[3][p][i] / totalTimes + ")  ");
				}
				System.out.println();
			}
			System.out.println("-------------kind--------------");
			for (int p = 0; p < desk.getPlayers().size(); p++) {
				for (int i = 0; i <= 8; i++) {
					System.out.print(result[4][p][i] + "(" + (double) result[4][p][i] / totalTimes + ")  ");
				}
				System.out.println();
			}
			System.out.println("-------------kind win--------------");
			for (int p = 0; p < desk.getPlayers().size(); p++) {
				for (int i = 0; i <= 8; i++) {
					if (result[4][p][i] != 0) {
						System.out.print(result[1][p][i] + "(" + (double) result[1][p][i] / result[4][p][i] + ")  ");
					} else {
						System.out.print(result[1][p][i] + "(0.000000)  ");
					}
				}
				System.out.println();
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		long consumingTime = System.nanoTime() - startTime;
		System.out.println(consumingTime / 1000000 + " ms");
		return result;
	}
}
