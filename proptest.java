import java.io.FileInputStream;
import java.util.Iterator;
import java.util.Properties;

/*
 * ******************************************************************************************
 * Copyright 1999-2007 Subject, Wills & Company - All rights reserved
 * 
 * Source File : proptest.java
 * 
 * Version : $Revision: 1.1 $
 * 
 * $Log: proptest.java,v $
 * Revision 1.1  2009/07/04 14:10:30  rec
 * *** empty log message ***
 *
 * 
 * ******************************************************************************************
 */

public class proptest {

	public static void main(String[] args) {
		
		try {
			Properties prop = new Properties();
			
			prop.load(new FileInputStream("c:/temp/p1.prop"));
			prop.load(new FileInputStream("c:/temp/p2.prop"));
			
			
			Iterator it1 = prop.keySet().iterator();
			
			while (it1.hasNext()) {
				String key = (String)it1.next();
				
				String propval = prop.getProperty(key);
				System.out.println(key + "=" + propval);
			}
		}
		catch (Exception e1) {
			e1.printStackTrace();
		}
	}
}
