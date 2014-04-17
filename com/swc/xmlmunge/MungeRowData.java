/*
 * ******************************************************************************************
 * Copyright 1999-2009 SWC Technology Partners - All rights reserved
 * 
 * Source File : MungeRowData.java
 * 
 * Version : $Revision: 1.1 $
 * 
 * $Log: MungeRowData.java,v $
 * Revision 1.1  2009/12/30 21:29:55  rec
 * *** empty log message ***
 *
 * 
 * ******************************************************************************************
 */

package com.swc.xmlmunge;

import java.util.ArrayList;
import java.util.List;

public class MungeRowData {

	String key;
	List<String> data;
	
	public MungeRowData(String key) {
		this.key = key;
		data = new ArrayList<String>();
	}
}
