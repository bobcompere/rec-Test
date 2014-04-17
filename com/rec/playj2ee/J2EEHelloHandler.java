/*
 * Created on Jul 21, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.rec.playj2ee;

import java.rmi.RemoteException;

import com.swc.fpjava.al.fpALfactory;
import com.swc.fpjava.ui.UIsession;
import com.swc.fpjava.ui.web.fpServletHandler;

/**
 * @author REC
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class J2EEHelloHandler extends fpServletHandler {
	
public J2EEHelloHandler(fpALfactory alf, UIsession sess) throws RemoteException {
	super(alf, sess);
	ALServletHandlerName = "co.rec.playj2ee.J2EEHelloHandlerAL";
	setAnonymousCommands(new String[] {"hello.create","hello.submit"});
}
}
