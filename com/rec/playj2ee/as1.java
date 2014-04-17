/*
 * ******************************************************************************************
 * Copyright 1999-2006 Subject, Wills & Company - All rights reserved
 * 
 * Source File : as1.java
 * 
 * Version : $Revision: 1.1 $
 * 
 * $Log: as1.java,v $
 * Revision 1.1  2007/03/18 20:21:58  rec
 * *** empty log message ***
 *
 * 
 * ******************************************************************************************
 */

package com.rec.playj2ee;

import com.swc.fpjava.al.fstring;

public class as1 {
	
	public static void main(String[] args) {
		fstring myBool = new fstring(1);
		
		myBool.getfieldinfo().setBooleanTrueValue("Y");
		myBool.getfieldinfo().setBooleanFalseValue("N");
		
		myBool.load(true);
		
		System.out.println(myBool.toBoolean() + " " + myBool.toString());
		
		myBool.load("N");
		System.out.println(myBool.toBoolean() + " " + myBool.toString());
		
		myBool.load("X");
		System.out.println(myBool.toBoolean() + " " + myBool.toString());
		
		myBool.getfieldinfo().setBooleanDefault(true);
		System.out.println(myBool.toBoolean() + " " + myBool.toString());
		
		myBool.getfieldinfo().setBooleanDefault(false);
		System.out.println(myBool.toBoolean() + " " + myBool.toString());
	}

}
