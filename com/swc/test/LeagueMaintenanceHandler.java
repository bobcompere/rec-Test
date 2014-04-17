package com.swc.test;

import java.rmi.RemoteException;

import com.swc.fpjava.fpException;
import com.swc.fpjava.al.ALsession;
import com.swc.fpjava.al.CachedValidValues;
import com.swc.fpjava.al.SqlTableMaintenanceHandler;
import com.swc.fpjava.al.field;
import com.swc.fpjava.al.fieldinfo;


public class LeagueMaintenanceHandler extends SqlTableMaintenanceHandler  {

public LeagueMaintenanceHandler(ALsession sess) throws RemoteException,fpException {

	super(sess,"sportsmonster","LeagueMaint");
	
	LeagueEntity LEAGUE = new LeagueEntity(session);

	LeagueViewEntity LEAGUE_VIEW = new LeagueViewEntity(session);
	LEAGUE_VIEW.setDebug(true);	
	setDisplayTitle("League Maintenance");
	
	setEditHtmlPage("smc_league.htm");
	
	setDataEntity(LEAGUE);
	
//	setViewEntity(LEAGUE_VIEW);
	
//	setViewEditLinkField(LEAGUE_VIEW.name);
	setViewEditLinkField(LEAGUE.name);
	
	setRowColorEven("#BCBCBC");
	
	String[] edits = { "format_id","level_id","registration_close" };
	setViewEditableFields(edits);
	
	LEAGUE.registration_close.getfieldinfo().setEditMask("AA/AA/AAAA");
	LEAGUE.registration_close.getfieldinfo().setPreferredHttpControl(fieldinfo.HttpSplitMaskControl);
	
	
	String[] fmt_valids = {"0","1","2","3"};
	String[] fmt_desc = { "Hard","Pussy","Turbo","Xtreme"};
	viewEntity.getField("format_id").getfieldinfo().setValidValues(fmt_valids);
	viewEntity.getField("format_id").getfieldinfo().setValidValueDescriptions(fmt_desc);
	
	setPlaceViewCommandsLeft(true);
	
	addViewHeaderCommand("bobscmd","Bobs Cool Command");
	
	String[] ds1 = {"All","Winter","Fall","Summer","Spring" };
	String[] wf1 = {"season"};
	String[][] wv1 = { { null ,"Winter" , "Fall" , "Summer" ,"Spring"}}; 
	
	addFilter(ds1,wf1,wv1);

//	setSearchAvailible(false);
//	setAddAvailible(false);
	
	useLinks();
}

protected void preEditFormCreate(String[] pnams, String[][] pvals) throws fpException {

	session.println("preEditFormCreate - called");
	field sport = dataEntity.getField("sport_id");
	
	String[] wf = { "company_id","city_id" };
	String[] wv = { "1","1" };
	
	CachedValidValues cvv = CachedValidValues.getInstance(session,"sportsmonster",
			"sport","sport_id","description",wf,wv);
			
	sport.getfieldinfo().setValids(cvv);
}

public int bobscmd(String[] pnams, String[][] pvals)  throws fpException {
	java.sql.Connection xcon = session.getDBconnection("sportsmonster");
	
	html.addElement("<h3> I ate a connection</h3>");
	return PAGE_WITH_VECTOR;
}
}