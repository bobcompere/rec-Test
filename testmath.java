/*
 * ******************************************************************************************
 * Copyright 1999-2012 SWC Technology Partners - All rights reserved
 * 
 * Source File : testmath.java
 * 
 * Version : $Revision: 1.1 $
 * 
 * $Log: testmath.java,v $
 * Revision 1.1  2013/04/29 10:21:49  rec
 * *** empty log message ***
 *
 * 
 * ************************************************************************
 */

public class testmath {

	
	public static void main(String [] args) {
		
		double L = 5000;
		double N = 60;
		double R = 0.01;
		
		double factor = (1- Math.pow((1 + R),-1 * N)) / R;
		
		double payment = L / factor;
		
		System.out.println(factor + " " + payment);
	}
  }
