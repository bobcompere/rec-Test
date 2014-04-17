/*
 * ******************************************************************************************
 * Copyright 1999-2009 SWC Technology Partners - All rights reserved
 * 
 * Source File : MungeTable.java
 * 
 * Version : $Revision: 1.1 $
 * 
 * $Log: MungeTable.java,v $
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

public class MungeTable {
	
	private Element source;
	private List<MungeTableRow> rows;
	private String id;
	private MungeTemplate parent;
	
	public MungeTable(MungeTemplate par, String id, Element tbl) {
		this.id = id;
		source = tbl;
		rows = new ArrayList<MungeTableRow>();
		parent = par;
		
		Iterator it1 = tbl.getChildren("row").iterator();
		while (it1.hasNext()) {
			addRow((Element)it1.next());
		}
 		
	}
	
	private void addRow(Element row) {
		rows.add(new MungeTableRow(this,row));
	}
	
	public void addToMap(String key, Mungable o1) {
		parent.addToMap(key,o1);
	}

	
	public String dump() {
		String retval = "\n\nTable Id=" + id;
		
		Iterator<MungeTableRow> it1 = rows.iterator();
		while (it1.hasNext()) retval += "\n" + it1.next().dump();
		return retval;
	}
}
