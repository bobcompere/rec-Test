package com.swc.test;

import com.swc.fpjava.fpdateformat;
import com.swc.fpjava.al.ALsession;
import com.swc.fpjava.al.Entity;
import com.swc.fpjava.al.fdate;
import com.swc.fpjava.al.field;
import com.swc.fpjava.al.fnumber;
import com.swc.fpjava.al.fstring;
import com.swc.fpjava.al.vstring;


public class LeagueViewEntity extends Entity {

	public field company_id;				// 0
	public field city_id;					// 1
	public field league_id;					// 2
	public field sport_id;					// 3
	public field sport;						// 4
	public field format;					// 5
	public field level;						// 6
	public field times;						// 7
	public field season;					// 8
	public field start_date;				// 9
	public field end_date;					// 10
	public field team_size;					// 11
	public field max_team_roster;			// 12
	public field priority_fee_until_date;	// 13
	public field team_priority_fee;			// 14
	public field team_standard_fee;			// 15
	public field individual_priority_fee;	// 16
	public field individual_standard_fee;	// 17
	public field non_member_fee;			// 18
	public field name;						// 19
	public field active;					// 20
	public field created;					// 21
	public field modified;					// 22
	public field location;					// 23
	public field current_on_date;			// 24
	public field force_to_current;			// 25
	public field registration_close;		// 26
	public field join_link;					// 27
	public field info_link;					// 28
	public field regular_weeks;				// 29
	public field last_display_date;			// 30
	public field playoff_weeks;				// 31
	public field inclusions;				// 32
		
	private String[] active_desc = {"Yes", "No"};
	private String[] active_vals = {"Y", "N"};
	
	public LeagueViewEntity(ALsession sess) {
		super(sess,"league_view");
		company_id = new fnumber(12, 0, "company_id", "Company ID");
		company_id.getfieldinfo().setdisplayonly(true);
		
		city_id = new fnumber(12, 0, "city_id", "City ID");
		city_id.getfieldinfo().setdisplayonly(true);

		league_id = new fnumber(12, 0, "league_id", "League ID");
		league_id.getfieldinfo().setdisplayonly(true);
	
		sport_id = new fnumber(12, 0, "sport_id", "Sport ID");
		sport_id.getfieldinfo().setdisplayonly(true);
	
		sport = new fstring(40, "sport", "Sport");

		format = new fstring(40, "format", "Format");
		
		level = new fstring(40, "level", "Skill Level");

		times = new fstring(40, "times", "Times");

		season = new fstring(40, "season", "Season");

		start_date = new fdate("start_date", "Start Date");

		end_date = new fdate("end_date", "End Date");

		team_size = new fnumber(6, 0, "team_size", "Team Size");

		max_team_roster = new fnumber(6, 0, "max_team_roster", "Max Team Roster");

		priority_fee_until_date = new fdate("priority_fee_until_date", "Priority Fee Until Date");

		team_priority_fee = new fnumber(9, 2, "team_priority_fee", "Team Priority Fee");
		team_standard_fee = new fnumber(9, 2, "team_standard_fee", "Team Standard Fee");

		individual_priority_fee = new fnumber(9, 2, "individual_priority_fee", "Individual Priority Fee");
		individual_standard_fee = new fnumber(9, 2, "individual_standard_fee", "Individual Standard Fee");

		name = new fstring(50,"name","League");
		name.getfieldinfo().setdisplaylength(20);
		name.getfieldinfo().setrequired(true);

		non_member_fee = new fnumber(9, 2, "non_member_fee", "Non Member Fee");

		active = new fstring(1, "active", "Active");
		active.getfieldinfo().setValidValues(active_vals);
		active.getfieldinfo().setValidValueDescriptions(active_desc);
//		active.setInitialValue("Y");
		active.getfieldinfo().setdisplayonly(true);
		active.getfieldinfo().setFormHidden(true);

		created = new fstring(22, "created", "Created");
		created.getfieldinfo().setdisplayonly(true);
		created.getfieldinfo().setFormHidden(true);

		modified = new fstring(22, "modified", "Modified");
		modified.getfieldinfo().setdisplayonly(true);
		modified.getfieldinfo().setFormHidden(true);
		
		location = new fstring(60, "location", "Location");
		location.getfieldinfo().setdisplaylength(30);
		
		registration_close = new fdate(fpdateformat.FORMAT_YYMD, "registration_close", "Registration Close Date");
		registration_close.getfieldinfo().setrequired(true);
		
		current_on_date = new fdate(fpdateformat.FORMAT_YYMD, "current_on_date", "Date Current");
		current_on_date.getfieldinfo().setrequired(true);
		
		force_to_current = new fstring(1, "force_to_current", "Force To Current");
		force_to_current.getfieldinfo().setValidValues(active_vals);
		force_to_current.getfieldinfo().setValidValueDescriptions(active_desc);
		
		join_link = new vstring("join_link","Register");
		join_link.getfieldinfo().setdisplayonly(true);
		
		info_link = new vstring("info_link", "Pricing");
		info_link.getfieldinfo().setdisplayonly(true);
		
		regular_weeks = new fnumber(6, 0, "regular_weeks", "Regular Weeks");
		regular_weeks.getfieldinfo().setrequired(true);
		
		last_display_date = new fdate(fpdateformat.FORMAT_YYMD, "last_display_date", "Last Display Date");
		last_display_date.getfieldinfo().setrequired(true);
		
		playoff_weeks = new fnumber(6, 0, "playoff_weeks", "Playoff Weeks");
		playoff_weeks.getfieldinfo().setrequired(true);
		
		inclusions = new fstring( 150, "inclusions", "Inclusions");
		inclusions.getfieldinfo().setMultiLine(40,4);
		
		field[] tmp = {company_id, city_id, league_id, sport_id, sport, format, level,
				times, season, start_date, end_date, team_size, max_team_roster,
				priority_fee_until_date, team_priority_fee, team_standard_fee,
				individual_priority_fee, individual_standard_fee, non_member_fee, name,
				active, created, modified, location, current_on_date, force_to_current,
				registration_close,join_link,info_link,regular_weeks,last_display_date,
				playoff_weeks,inclusions};
		field[] tmp2 = {sport,name,format,start_date,level};
		
		field[] pk = {company_id,city_id,league_id};
		
		setFields(tmp,tmp2);
		setPrimaryKey(pk);
				
		
}
} 
