/*
 * ******************************************************************************************
 * Copyright 1999-2005 Subject, Wills & Company - All rights reserved
 * 
 * Source File : kerchunkTest.java
 * 
 * Version : $Revision: 1.1 $
 * 
 * $Log: KerchunkTest.java,v $
 * Revision 1.1  2007/03/18 20:21:58  rec
 * *** empty log message ***
 *
 * 
 * ******************************************************************************************
 */

package com.swc.test;

import com.swc.fpjava.al.fpALfactory;
import com.swc.fpjava.ui.UIsession;
import com.swc.fpjava.ui.web.fpServletHandler;

public class KerchunkTest extends fpServletHandler {
	public KerchunkTest(fpALfactory alf, UIsession sess) {
		super(alf,sess);
		
		ALServletHandlerName = this.getClass().getName() + "AL";
		setCommands(new String[] {"kerchunk"});
	}
}
