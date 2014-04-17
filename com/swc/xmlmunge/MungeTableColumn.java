/*
 * ******************************************************************************************
 * Copyright 1999-2009 SWC Technology Partners - All rights reserved
 * 
 * Source File : MungeTableColumn.java
 * 
 * Version : $Revision: 1.1 $
 * 
 * $Log: MungeTableColumn.java,v $
 * Revision 1.1  2009/12/30 21:29:55  rec
 * *** empty log message ***
 *
 * 
 * ******************************************************************************************
 */

package com.swc.xmlmunge;

import org.jdom.Element;

public class MungeTableColumn implements Mungable {

	
	private MungeTableRow parent;
	private Element source;
	private int colSpan = 1;
	private String text;
	private String repeat;
	
	public MungeTableColumn(MungeTableRow par, Element source) {
		this.source = source;
		parent = par;
		
		String cspan = source.getAttributeValue("colspan");
		if (cspan != null) {
			try {
				colSpan = Integer.parseInt(cspan.trim());
			}
			catch (NumberFormatException nfe) {}
		}
		
		text = source.getTextTrim();
		parent.addToMap(text,this);
		repeat = source.getAttributeValue("repeat");
	}
	public String dump() {
		return "[" + text + " colspan=" + colSpan +"]";
	}
	/* (non-Javadoc)
	 * @see com.swc.xmlmunge.Mungable#munge(java.lang.String)
	 */
	@Override
	public void munge(String data) {
		
	}
	
	
}
