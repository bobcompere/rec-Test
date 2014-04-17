import com.swc.fpjava.al.fnumber;

/*
 * ******************************************************************************************
 * Copyright 1999-2009 SWC Technology Partners - All rights reserved
 * 
 * Source File : TestFnumber.java
 * 
 * Version : $Revision: 1.1 $
 * 
 * $Log: TestFnumber.java,v $
 * Revision 1.1  2009/12/30 21:29:55  rec
 * *** empty log message ***
 *
 * 
 * ******************************************************************************************
 */

public class TestFnumber {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		fnumber tv = new fnumber(10,0);
		
		String val = "10123456789";
		tv.load(val);
		
		System.out.println("[" + tv.toString() + "]");
	}

}
