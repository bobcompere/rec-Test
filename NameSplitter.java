/*
 * ******************************************************************************************
 * Copyright 1999-2007 SWC Technology Partners - All rights reserved
 * 
 * Source File : NameSplitter.java
 * 
 * Version : $Revision: 1.1 $
 * 
 * $Log: NameSplitter.java,v $
 * Revision 1.1  2009/07/04 14:10:30  rec
 * *** empty log message ***
 *
 * 
 * ******************************************************************************************
 */

public class NameSplitter {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		
		NameSplitter ns = new NameSplitter();
		
		ns.nameSplit("Smith");
		ns.nameSplit("John Smith");
		ns.nameSplit("John Q Adams");
		ns.nameSplit("Mr John Q Adams");
		
	}
	
	
	public void nameSplit(String name) {
		
		String[] parts = name.split(" ");
		String first_name = "";
		String last_name = "";
		
		switch (parts.length) {
		case 1:
			last_name = parts[0];
			break;
		case 2:
			first_name = parts[0];
			last_name = parts[1];
			break;
		case 3:
			last_name = parts[2];
			first_name = parts[0] + " " + parts[1];
			break;
		case 4:
			last_name = parts[2] +" " + parts[3];
			first_name = parts[0] + " " + parts[1];
			break;
		default:
			last_name = name;
		}
		
		System.out.println("Input=[" + name + "] last Name=[" + last_name + "] first name=[" + first_name + "]");
	}

}
