import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * ******************************************************************************************
 * Copyright 1999-2008 Subject, Wills & Company - All rights reserved
 * 
 * Source File : RegxTest.java
 * 
 * Version : $Revision: 1.1 $
 * 
 * $Log: RegxTest.java,v $
 * Revision 1.1  2009/07/04 14:10:30  rec
 * *** empty log message ***
 *
 * 
 * ******************************************************************************************
 */

public class RegxTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Pattern p1 = Pattern.compile(args[0]);
		
		Matcher m1 = p1.matcher(args[2]);
		
		System.out.println("[" + args[0] + "][" + args[1] + "][" + args[2] + "]---[" + m1.replaceAll(args[1]) + "]");
		
	} 

}
