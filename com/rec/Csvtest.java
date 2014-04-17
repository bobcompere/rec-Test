/*
 * ******************************************************************************************
 * Copyright 1999-2006 Subject, Wills & Company - All rights reserved
 * 
 * Source File : Csvtest.java
 * 
 * Version : $Revision: 1.1 $
 * 
 * $Log: Csvtest.java,v $
 * Revision 1.1  2007/03/18 20:21:58  rec
 * *** empty log message ***
 *
 * 
 * ******************************************************************************************
 */

package com.rec;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import com.swc.fpjava.al.field;
import com.swc.fpjava.al.fieldlist;
import com.swc.fpjava.al.fstring;

public class Csvtest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		field[] fa1 = new field[10];
		
		for (int i1=0;i1<10;i1++) fa1[i1] = new fstring(160);
		
		fieldlist fl1 = new fieldlist(fa1);
		
		try {
			BufferedReader br1 = new BufferedReader(new InputStreamReader(new FileInputStream("c:/temp/x.x")));
			
			String s1 = null;
			while ((s1 = br1.readLine()) != null) {
				fl1.loadCommaDelimited(s1,true);
				if (fa1[1].chop().equals("78500")) {
					System.out.println("[" + fa1[1].toString() + "][" + fa1[9].toString() + "]");
					fl1.loadCommaDelimited(s1,false);
					System.out.println("[" + fa1[1].toString() + "][" + fa1[9].toString() + "]");
				}
			}
		}
		catch (Throwable th1) {
			th1.printStackTrace();
		}
		
		
	}

}
