/*
 * ******************************************************************************************
 * Copyright 1999-2009 SWC Technology Partners - All rights reserved
 * 
 * Source File : testPullSlash.java
 * 
 * Version : $Revision: 1.1 $
 * 
 * $Log: testPullSlash.java,v $
 * Revision 1.1  2009/12/30 21:29:55  rec
 * *** empty log message ***
 *
 * 
 * ******************************************************************************************
 */

package com.swc.test;

import com.swc.fpjava.fpdateformat;

public class testPullSlash {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		test("1/1/2001");
		test("01/01/2001");
		test("2001/1/01");
		test("2001/01");
		test("");
		test("junk");
	}
	
	public static void test(String x1) {
		System.out.println("testing [" + x1 + "][" + fpdateformat.pullSlashes(x1) + "]");
	}

}
