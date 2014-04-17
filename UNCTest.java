import java.io.File;
import java.io.FileOutputStream;

/*
 * ******************************************************************************************
 * Copyright 1999-2007 Subject, Wills & Company - All rights reserved
 * 
 * Source File : UNCTest.java
 * 
 * Version : $Revision: 1.1 $
 * 
 * $Log: UNCTest.java,v $
 * Revision 1.1  2009/07/04 14:10:30  rec
 * *** empty log message ***
 *
 * 
 * ******************************************************************************************
 */

public class UNCTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		try {
		File dir = new File("//shadow/shared/custom");
		
		System.out.println("Exists " + dir.exists());
		System.out.println("Is Directory " + dir.isDirectory());
		
		String[] contents = dir.list();
		for (int i1=0;i1<contents.length;i1++) System.out.println(contents[i1]);
		
		
		File makeIt = new File("//shadow/shared/custom/TEST.TST");
		
		System.out.println("Exists " + makeIt.exists());
		System.out.println("Is Directory " + makeIt.isDirectory());
		
		FileOutputStream fis = new FileOutputStream(makeIt);
		
		String data = "DATA";
		
		fis.write(data.getBytes());
		
		fis.close();
		
		System.out.println("Exists " + makeIt.exists());
		System.out.println("Is Directory " + makeIt.isDirectory());
		}
		catch (Exception e1) {
			e1.printStackTrace();
		}
	}

}
