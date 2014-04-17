/*
 * ******************************************************************************************
 * Copyright 1999-2010 SWC Technology Partners - All rights reserved
 * 
 * Source File : ExcelColTest.java
 * 
 * Version : $Revision: 1.1 $
 * 
 * $Log: ExcelColTest.java,v $
 * Revision 1.1  2010/11/18 04:58:05  rec
 * *** empty log message ***
 *
 * 
 * ************************************************************************
 */

public class ExcelColTest {

	private static String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		test(1);
		test(25);
		test(26);
		test(27);
		test(0);
		test(40);
		test(100);
		test(3 * 26 * 26 + 4* 26 + 5); // CDF?
		test(26 * 26 * 26 * 26 * 26 + 2);
	}
	
	private static void test(int val) {
		System.out.println("Test [" + val + "][" + makeIndexElement(val) + "]");
	}
 
	protected static String makeIndexElement(int indx) {
		indx++; // 1 based not zero
		int[] digits = new int[6];
		for (int i1=0;i1<indx;i1++) {
			int ptr = 0;
			while (digits[ptr] == 26) ptr++;
			digits[ptr]++;
			while (ptr > 0) {
				ptr--;
				digits[ptr] = 1;
			}
		}
		
		String retval = "";
		int ptr = 0;
		while (digits[ptr] > 0) ptr++;
			
		ptr--;
		for (int i1=ptr;i1>=0;i1--) retval += ALPHABET.charAt(digits[i1] -1);
		return retval;
	}
}
