/*
 * ******************************************************************************************
 * Copyright 1999-2005 Subject, Wills & Company - All rights reserved
 * 
 * Source File : KerchunkTestAL.java
 * 
 * Version : $Revision: 1.1 $
 * 
 * $Log: KerchunkTestAL.java,v $
 * Revision 1.1  2007/03/18 20:21:58  rec
 * *** empty log message ***
 *
 * 
 * ******************************************************************************************
 */

package com.swc.test;

import java.rmi.RemoteException;
import java.util.Date;

import com.swc.fpjava.al.ALServletHandlerObj;
import com.swc.fpjava.al.ALsession;

public class KerchunkTestAL extends ALServletHandlerObj {

	public KerchunkTestAL(ALsession sess) throws RemoteException {
		super(sess);
	}
	
	public int handleHttp(String cmd, String[] pnames, String[][] pvals) throws RemoteException {
		
		boolean kerChunk = false;
		if (getParmVal("xx",pnames,pvals) != null) kerChunk = true;
		
		if (kerChunk) {
			prepareIncrementalPageWithVectorResult();
			html.addElement("<h1>KerCHUNK</h1>");
		} else {
			html.addElement("<h1>Regular</h1>");
		}
		
		for (int i1=0;i1<600;i1++) {
			Date d1 = new Date();
			html.addElement(i1 + " " + d1.toString() + html.getClass().getName() + "<br/>");
			try {
				Thread.sleep(100);
			}
			catch (Throwable th1) {
				// no-op
			}
		}
		if (kerChunk) return COMPLETE_INCREMENTAL_PAGE_WITH_VECTOR;
		else return PAGE_WITH_VECTOR;
	}

}
