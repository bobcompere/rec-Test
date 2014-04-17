package com.swc.test;

import java.rmi.RemoteException;

import com.swc.fpjava.al.ALsession;
import com.swc.fpjava.al.HierarchyHandler;
import com.swc.fpjava.al.HierarchyMember;

public class SportHierarchy2 extends HierarchyHandler {

static String[] sportWhere  = {};
static String[] leagueWhere  = {"company_id","city_id","sport_id"};
static String[] teamWhere = {"company_id","city_id","league_id"};
static String[] rosterWhere = {"company_id","city_id","team_id"};
static String[] gameWhere = {"company_id","city_id","team_id"};

public SportHierarchy2(ALsession sess) throws RemoteException {
	super(sess);
	setCommandPrefix("SportHierarchy2");
	
	HierarchyMember sport = new HierarchyMember("sportmaint.view","Sports",sportWhere);
	HierarchyMember league = new HierarchyMember("leaguemaint.view","Leagues",leagueWhere);
	HierarchyMember team = new HierarchyMember("teammaint.view","Teams",teamWhere);
	HierarchyMember roster = new HierarchyMember("rostermaint.view","Team Roster",rosterWhere);
	HierarchyMember game = new HierarchyMember("gamemaint.view","Games",gameWhere);
	
	setRootMember(sport);
	sport.addChild(league);
	league.addChild(team);
	team.addChild(roster);
	team.addChild(game);
}

}

