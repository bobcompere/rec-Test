/*
 * ******************************************************************************************
 * Copyright 1999-2010 SWC Technology Partners - All rights reserved
 * 
 * Source File : TimeStampTest.java
 * 
 * Version : $Revision: 1.1 $
 * 
 * $Log: TimeStampTest.java,v $
 * Revision 1.1  2010/11/18 04:58:05  rec
 * *** empty log message ***
 *
 * 
 * ************************************************************************
 */

package com.swc;

import java.sql.SQLTimeoutException;

import com.swc.fpjava.al.SqlTimeStamp;

public class TimeStampTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	
		
		SqlTimeStamp x = new SqlTimeStamp("x");
		
		x.load("2010-10-18 16:46:11.88428");
		
		
		System.out.println("X = [" + x.toString() + "][" + x.getTime() + "]");
	}

}
