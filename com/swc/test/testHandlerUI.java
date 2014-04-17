package com.swc.test;

import java.rmi.RemoteException;

import com.swc.fpjava.fpException;
import com.swc.fpjava.al.fpALfactory;
import com.swc.fpjava.ui.UIsession;
import com.swc.fpjava.ui.web.SimpleServletHandler;
import com.swc.fpjava.ui.web.fpServletHandler;

public class testHandlerUI extends fpServletHandler
{

String[]  cmds = { "kaboom","testform","messagetest","filedownload","sorttest","back","bombout"};
String[] acmds = { "startup" };

public testHandlerUI(fpALfactory alf, UIsession sess) throws fpException,RemoteException {
	super(alf,sess);
	setCommands(cmds);
	setAnonymousCommands(acmds);
	setRestrictedCommand("startup");
	ALServletHandlerName = "com.swc.test.testHandlerAL";
	addSubordinateHandler(new SimpleServletHandler(alf,sess,"com.swc.test.cmSearch"));
	addSubordinateHandler(new SimpleServletHandler(alf,sess,"com.swc.test.SportHierarchy"));
	addSubordinateHandler(new SimpleServletHandler(alf,sess,"com.swc.test.SportHierarchy2"));
	addSubordinateHandler(new SimpleServletHandler(alf,sess,"com.swc.test.SportMaintenanceHandler"));
	addSubordinateHandler(new SimpleServletHandler(alf,sess,"com.swc.test.LeagueMaintenanceHandler"));
	addSubordinateHandler("com.swc.fpjava.ui.web.ServletPageServer");
	debugDispatching();
}


public String getHtmlSuffix() {
	return "TEST";
}

}
