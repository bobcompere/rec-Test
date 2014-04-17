/*
 * ******************************************************************************************
 * Copyright 1999-2008 Subject, Wills & Company - All rights reserved
 * 
 * Source File : ThreadKillTest.java
 * 
 * Version : $Revision: 1.1 $
 * 
 * $Log: ThreadKillTest.java,v $
 * Revision 1.1  2009/07/04 14:10:30  rec
 * *** empty log message ***
 *
 * 
 * ******************************************************************************************
 */

public class ThreadKillTest extends Thread {

	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		long goal = 1;
		long cur = 0;
		
		while (true) {
			cur++;
			if (cur == goal) {
				System.out.println(cur);
				goal*=10;
			}
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	
		ThreadKillTest tkt = new ThreadKillTest();
		tkt.start();
		
		try {
			Thread.sleep(10000);
			StackTraceElement[] stea = tkt.getStackTrace();
			for (int i1=0;i1<stea.length;i1++) {
				System.out.println("TRace :" + stea[i1].toString());
			}
			tkt.stop();
		}
		catch (Throwable th1) {
			th1.printStackTrace();
		}
		
	}

}
