/*
 * Created on Jul 21, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.rec.playj2ee;

import java.rmi.RemoteException;

import com.swc.fpjava.al.ALServletHandlerObj;
import com.swc.fpjava.al.ALsession;

/**
 * @author REC
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class J2EEHelloHandlerAL extends ALServletHandlerObj {

	/* (non-Javadoc)
	 * @see com.swc.fpjava.al.dispatchable#handleHttp(java.lang.String, java.lang.String[], java.lang.String[][])
	 */
public J2EEHelloHandlerAL(ALsession sess) throws RemoteException {
	super(sess);
}
	
public int handleHttp(String cmd, String[] pnames, String[][] pvals)
		throws RemoteException {
	// TODO Auto-generated method stub
	if (cmd.toLowerCase().endsWith("create")) return create(pnames,pvals);
	else if (cmd.toLowerCase().endsWith("submit")) return submit(pnames,pvals);
	else {
		html.addElement("<h1>" + cmd + " huh?</h1>");
		return PAGE_WITH_VECTOR;
	}
}

private int create(String[] pnames, String[][] pvals) {
	html.addElement("<h1>CREATE</h1>");
	return PAGE_WITH_VECTOR;
}

private int submit(String[] pnames, String[][] pvals) {
	html.addElement("<h1>SUBMIT</h1>");
	return PAGE_WITH_VECTOR;
}

}
