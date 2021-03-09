package test;

import java.util.ArrayList;

import org.omg.CORBA.Current;

import model.PokerCard;

public class Test2 {

	private class asdf implements Runnable {
		String name;
		String s;

		public asdf(String name, String s) {
			// TODO Auto-generated constructor stub
			this.name = name;
			this.s = s;
		}

		public void run() {
			for (int i = 0; i < 100; i++) {
				System.out.println(name + ":" + i);
				s="RRR";
			}
		}
	}

	void tt(String s) {
		
		Thread t1 = new Thread(new asdf("tt1", s));
		Thread t2 = new Thread(new asdf("tt2", s));
		t1.start();
		t2.start();
		while(t1.isAlive()||t2.isAlive()){}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String s = "START";
		Test2 test2 = new Test2();
		test2.tt(s);
		System.out.println("TTTTT"+s);
	}

}
