/*
 * ******************************************************************************************
 * Copyright 1999-2007 Subject, Wills & Company - All rights reserved
 * 
 * Source File : SpilitTest.java
 * 
 * Version : $Revision: 1.1 $
 * 
 * $Log: SpilitTest.java,v $
 * Revision 1.1  2009/07/04 14:10:30  rec
 * *** empty log message ***
 *
 * 
 * ******************************************************************************************
 */

public class SpilitTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		
		String s1 = "abc^ddhjdj^eee^^^^ddd^";
		
		s1 = s1.replace('#',' ');  // if there are existing #'s make them blanks
		s1 = s1.replace('^', '#');  // flip ^ to #
		
		String[] sa1 = s1.split("#");  // split on #
		
		for (int i1=0;i1<sa1.length;i1++) {
			System.out.println(i1 + "[" + sa1[i1] + "]");
		}

	}

}
