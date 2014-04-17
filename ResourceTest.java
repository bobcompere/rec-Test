import javax.annotation.Resource;

/*
 * ******************************************************************************************
 * Copyright 1999-2009 SWC Technology Partners - All rights reserved
 * 
 * Source File : ResourceTest.java
 * 
 * Version : $Revision: 1.1 $
 * 
 * $Log: ResourceTest.java,v $
 * Revision 1.1  2009/12/30 21:29:55  rec
 * *** empty log message ***
 *
 * 
 * ******************************************************************************************
 */

public class ResourceTest {
	
	@Resource(name="test",mappedName="test")
	private String value;
	
	public String toString() {
		return "Value  = " + value;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
	
		ResourceTest rt = new ResourceTest();
		
		System.out.println(rt.toString());

	}

}
