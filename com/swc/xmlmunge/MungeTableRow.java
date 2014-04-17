/*
 * ******************************************************************************************
 * Copyright 1999-2009 SWC Technology Partners - All rights reserved
 * 
 * Source File : MungeTableRow.java
 * 
 * Version : $Revision: 1.1 $
 * 
 * $Log: MungeTableRow.java,v $
 * Revision 1.1  2009/12/30 21:29:55  rec
 * *** empty log message ***
 *
 * 
 * ******************************************************************************************
 */

package com.swc.xmlmunge;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jdom.Element;

public class MungeTableRow {

	private MungeTable parent;
	private List<MungeTableColumn> columns;
	private Element source;
	private List<MungeRowData> data;
	private String repeat;
	
	public MungeTableRow(MungeTable table, Element e1) {
		parent = table;
		source = e1;
		
		columns = new ArrayList<MungeTableColumn>();
		data = new ArrayList<MungeRowData>();
		
		repeat = source.getAttributeValue("repeat");
		
		Iterator icol = source.getChildren("column").iterator();
		while (icol.hasNext()) {
			Element e2 = (Element)icol.next();
			columns.add(new MungeTableColumn(this, e2));
		}
	}
	
	public String dump() {
		String retval = "Row: repeat=" + repeat + " ";
		
		Iterator<MungeTableColumn> it1 = columns.iterator();
		while (it1.hasNext()) {
			retval+= it1.next().dump();
		}
		
		return retval;
	}
	
	public void addToMap(String key, Mungable o1) {
		parent.addToMap(key,o1);
	}
}
