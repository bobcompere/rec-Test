/*
 * ******************************************************************************************
 * Copyright 1999-2009 SWC Technology Partners - All rights reserved
 * 
 * Source File : SearchTokenTest.java
 * 
 * Version : $Revision: 1.1 $
 * 
 * $Log: SearchTokenTest.java,v $
 * Revision 1.1  2009/12/30 21:29:55  rec
 * *** empty log message ***
 *
 * 
 * ******************************************************************************************
 */

package com.swc.test;

import com.swc.fpjava.fpstringformat;

public class SearchTokenTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		test("     ");
		test("abc def");
		test("abc def hij");
		test("abc def hij ");
		test(" abc def    hij");
		test("'abc def' hij");
		test("abc '   def hij   '");
		test("abc 'def hij");
	}

	
	private static void test(String s1) {
		System.out.println("Testing[" + s1 +"]");
		String[] tokens = fpstringformat.tokenizeSearchTerms(s1);
		for (int i1=0;i1<tokens.length;i1++) {
			System.out.println("Term " + i1 + " [" + tokens[i1] + "]");
		}
		
	}
}
