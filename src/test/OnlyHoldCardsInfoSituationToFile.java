package test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import model.Desk;
import model.Player;
import model.PokerCard;
import processor.MCprocessor;

public class OnlyHoldCardsInfoSituationToFile {
	// [numOfPlayers-2][isFlush][rank1-2][rank2-2][][][]
	// rank1>=rank2
	// {{},{},{}}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int minNumOfPlayers = 2;
		int maxNumOfPlayers = 9;
		int times = 10000000;
		int numOfThread=1;
		String fileName = "OnlyHoldCardsSituation.txt";
		if(args.length==5){
			minNumOfPlayers=Integer.valueOf(args[0]);
			maxNumOfPlayers=Integer.valueOf(args[1]);
			times=Integer.valueOf(args[2]);
			numOfThread=Integer.valueOf(args[3]);
			fileName=args[4];
		}
		System.out.println("minNumOfPlayers:"+minNumOfPlayers);
		System.out.println("maxNumOfPlayers:"+maxNumOfPlayers);
		System.out.println("times:"+times);
		System.out.println("numOfThread:"+numOfThread);
		System.out.println("fileName:"+fileName);
		FileOutputStream fs;
		try {
			int[][][][][][][] allResult = new int[maxNumOfPlayers - 1][][][][][][];
			for (int npm2 = 0; npm2 <= maxNumOfPlayers - 2; npm2++) {
				allResult[npm2] = new int[2][][][][][];
				for (int isFlush = 0; isFlush <= 1; isFlush++) {
					allResult[npm2][isFlush] = new int[13][][][][];
					for (int rank1m2 = 0; rank1m2 <= 12; rank1m2++) {
						allResult[npm2][isFlush][rank1m2] = new int[13][][][];
					}
				}
			}
			MCprocessor mCprocessor = new MCprocessor();
			System.out.println("Start to computing");
			for(int npm2=minNumOfPlayers-2;npm2<=maxNumOfPlayers-2;npm2++){
				Desk desk=new Desk();
				List<Player> players=new ArrayList<Player>();
				for(int i=0;i<npm2+2;i++){
					Player player=new Player();
					player.setSerialNumber(i);
					players.add(player);
				}
				desk.setPlayers(players);
				for(int rank1m2=0;rank1m2<=12;rank1m2++){
					for(int rank2m2=0;rank2m2<=rank1m2;rank2m2++){
						//not flush
						List<PokerCard> cards=new ArrayList<PokerCard>();
						PokerCard card1=new PokerCard(rank1m2+2, PokerCard.Hearts);
						PokerCard card2=new PokerCard(rank2m2+2, PokerCard.Dimonds);
						cards.add(card1);
						cards.add(card2);
						players.get(0).setHoldCards(cards);
						System.out.print("computing at:"+card1+" "+card2+" when nPlayer="+(npm2+2)+". ");
						long startTime = System.nanoTime();
						allResult[npm2][0][rank1m2][rank2m2]=mCprocessor.MutiThreadMC(times, desk, numOfThread);
						System.out.println("Finished:consumed: "+(System.nanoTime() - startTime) / 1000000 + " ms");
						
						if(rank1m2==rank2m2)
							continue;
						//flush
						cards=new ArrayList<PokerCard>();
						card1=new PokerCard(rank1m2+2, PokerCard.Hearts);
						card2=new PokerCard(rank2m2+2, PokerCard.Hearts);
						cards.add(card1);
						cards.add(card2);
						players.get(0).setHoldCards(cards);
						System.out.print("computing at:"+card1+" "+card2+" when nPlayer="+(npm2+2)+". ");
						startTime = System.nanoTime();
						allResult[npm2][0][rank1m2][rank2m2]=mCprocessor.MutiThreadMC(times, desk, numOfThread);
						System.out.println("Finished:consumed: "+(System.nanoTime() - startTime) / 1000000 + " ms");
					}
				}
			}
			System.out.println("Start to output result to file:" + fileName);
			fs = new FileOutputStream(new File(fileName));
			PrintStream p = new PrintStream(fs);
			p.print("{");
			for (int npm2 = 0; npm2 <= maxNumOfPlayers - 2; npm2++) {
				if (npm2 != 0)
					p.print(",");
				p.print("{");
				for(int isFlush=0;isFlush<=1;isFlush++){
					if (isFlush != 0)
						p.print(",");
					p.print("{");
					for(int rank1m2=0;rank1m2<=12;rank1m2++){
						if (rank1m2 != 0)
							p.print(",");
						p.print("{");
						for(int rank2m2=0;rank2m2<=12;rank2m2++){
							if (rank2m2 != 0)
								p.print(",");
							p.print("{");
							if(allResult[npm2][isFlush][rank1m2][rank2m2]==null){
								p.print("{{}}");
							}else{
								for(int r1=0;r1<allResult[npm2][isFlush][rank1m2][rank2m2].length;r1++){
									if (r1 != 0)
										p.print(",");
									p.print("{");
									for(int r2=0;r2<allResult[npm2][isFlush][rank1m2][rank2m2][r1].length;r2++){
										if (r2 != 0)
											p.print(",");
										p.print("{");
										for(int r3=0;r3<allResult[npm2][isFlush][rank1m2][rank2m2][r1][r2].length;r3++){
											if (r3 != 0)
												p.print(",");
											p.print(allResult[npm2][isFlush][rank1m2][rank2m2][r1][r2][r3]);
										}
										p.print("}");
									}
									p.print("}");
								}
							}
							p.print("}");
						}
						p.print("}");
					}
					p.print("}");
				}
				p.print("}");
				}
			p.print("}");
			p.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		// Double k = new Double(1);
		// System.out.println(k.getClass().getName().charAt(0));
	}

}
