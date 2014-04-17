import com.swc.fpjava.fpdateformat;

/*
 * ******************************************************************************************
 * Copyright 1999-2013 SWC Technology Partners - All rights reserved
 * 
 * Source File : DateTest.java
 * 
 * Version : $Revision: 1.1 $
 * 
 * $Log: DateTest.java,v $
 * Revision 1.1  2013/04/29 10:21:49  rec
 * *** empty log message ***
 *
 * 
 * ************************************************************************
 */

public class DateTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String[] tests = {"20110211",
				"1/1/1900",
				"garbage",
				"01/01/2000",
				"20120229",
				"20130229"
		};
		
		for (int i1=0;i1<tests.length;i1++) {
			System.out.println(tests[i1] + " " + fpdateformat.determineDateFromIndeterminientInput(tests[i1]));
			
		}

	}

}
