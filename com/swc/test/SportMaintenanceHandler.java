package com.swc.test;

import java.rmi.RemoteException;

import com.swc.fpjava.fpException;
import com.swc.fpjava.al.ALsession;
import com.swc.fpjava.al.SqlTableMaintenanceHandler;


public class SportMaintenanceHandler extends SqlTableMaintenanceHandler  {

public SportMaintenanceHandler(ALsession sess) throws RemoteException,fpException {

	super(sess,"sportsmonster","SportMaint");
	
	sportEntity SPORT = new sportEntity(session);
	
	setDisplayTitle("Sport Maintenance");
	
	setEditHtmlPage("smc_sport.htm");
	
	setViewCellPadding("10");
	
	setDataEntity(SPORT);
	
	setDeleteAvailable(true);
	useLinks();
	
	setViewHelpPage("layout.htm");
	setAddHelpPage("red.htm");
	setModHelpPage("swcInet.htm");
	
	setViewEditLinkField(SPORT.description);
	
//	setEntitySelectable(true);
	
//	setSearchAvailable(false);
}
}