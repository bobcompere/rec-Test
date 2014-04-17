package com.swc.test;

import com.swc.fpjava.fpdateformat;
import com.swc.fpjava.al.ALsession;
import com.swc.fpjava.al.Entity;
import com.swc.fpjava.al.fdate;
import com.swc.fpjava.al.field;
import com.swc.fpjava.al.fnumber;
import com.swc.fpjava.al.fstring;

public class LeagueEntity extends Entity {
	public field company_id;				// 0
	public field city_id;					// 1
	public field league_id;					// 2
	public field sport_id;					// 3
	public field name;						// 4
	public field format_id;					// 5
	public field level_id;					// 6
	public field game_site_group_id;		// 7
	public field times;						// 8
	public field season;					// 9
	public field registration_close;		// 10
	public field start_date;				// 11
	public field end_date;					// 12
	public field regular_weeks;				// 13
	public field playoff_weeks;				// 14
	public field inclusions;				// 15
	public field last_display_date;			// 16
	public field team_size;					// 17
	public field min_team_roster;			// 18
	public field max_team_roster;			// 19
	public field priority_fee_until_date;	// 20
	public field team_priority_fee;			// 21
	public field team_standard_fee;			// 22
	public field individual_priority_fee;	// 23
	public field individual_standard_fee;	// 24
	public field non_member_fee;			// 25
	public field current_on_date;			// 26
	public field force_to_current;			// 27
	public field force_registration_close;	// 28
	public field active;					// 29
	public field created;					// 30
	public field modified;					// 31
	
//	private String[] season_desc = {"Spring", "Summer", "Fall", "Winter"};
//	private String[] season_vals = {"Spring", "Summer", "Fall", "Winter"};
	
	private String[] active_desc = {"Yes", "No"};
	private String[] active_vals = {"Y", "N"};
	private String[] force_to_current_desc = {"Yes", "No"};
	private String[] force_to_current_vals = {"Y", "N"};
	private String[] force_registration_close_desc = {"Yes", "No"};
	private String[] force_registration_close_vals = {"Y", "N"};


	public LeagueEntity(ALsession sess) {
		super(sess,"league");
	
		company_id = new fnumber(12, 0, "company_id", "Company ID");
		company_id.getfieldinfo().setdisplayonly(true);
		
		city_id = new fnumber(12, 0, "city_id", "City ID");
		city_id.getfieldinfo().setdisplayonly(true);

		league_id = new fnumber(12, 0, "league_id", "League ID");
		league_id.getfieldinfo().setdisplayonly(true);
	
		sport_id = new fnumber(12, 0, "sport_id", "Sport ID");
//		sport_id.getfieldinfo().setdisplayonly(true);
		
		name = new fstring(50,"name","Name");
		name.getfieldinfo().setdisplaylength(35);
		name.getfieldinfo().setrequired(true);
		
		format_id = new fnumber(12, 0, "format_id", "Format ID");
		
		level_id = new fnumber(12, 0, "level_id", "Level ID");

		game_site_group_id = new fnumber(12, 0, "game_site_group_id", "Game Site Group ID");

		times = new fstring(40, "times", "Times");

		season = new fstring(20, "season", "Season");

		registration_close = new fdate(fpdateformat.FORMAT_YYMD, "registration_close", "Registration Close Date");
		registration_close.getfieldinfo().setrequired(true);
		
		start_date = new fdate("start_date", "Start Date");
		start_date.getfieldinfo().setrequired(true);

		end_date = new fdate("end_date", "End Date");
		end_date.getfieldinfo().setrequired(true);

		regular_weeks = new fnumber(6, 0, "regular_weeks", "Regular Season Weeks");
		regular_weeks.getfieldinfo().setrequired(true);
		
		playoff_weeks = new fnumber(6, 0, "playoff_weeks", "Playoff Weeks");
		playoff_weeks.getfieldinfo().setrequired(true);
		
		inclusions = new fstring( 150, "inclusions", "Inclusions");
		inclusions.getfieldinfo().setMultiLine(40,4);
		
		last_display_date = new fdate(fpdateformat.FORMAT_YYMD, "last_display_date", "Last Display Date");
		last_display_date.getfieldinfo().setrequired(true);
		
		team_size = new fnumber(6, 0, "team_size", "Team Size");

		max_team_roster = new fnumber(6, 0, "max_team_roster", "Max Team Roster");

		min_team_roster = new fnumber( 6, 0, "min_team_roster", "Minimum Team Roster");
		min_team_roster.getfieldinfo().setrequired(true);
		
		priority_fee_until_date = new fdate("priority_fee_until_date", "Priority Fee Until Date");
		priority_fee_until_date.getfieldinfo().setrequired(true);

		team_priority_fee = new fnumber(9, 2, "team_priority_fee", "Team Priority Fee");
		team_standard_fee = new fnumber(9, 2, "team_standard_fee", "Team Standard Fee");

		individual_priority_fee = new fnumber(9, 2, "individual_priority_fee", "Individual Priority Fee");
		individual_standard_fee = new fnumber(9, 2, "individual_standard_fee", "Individual Standard Fee");

		non_member_fee = new fnumber(9, 2, "non_member_fee", "Non Member Fee");

		current_on_date = new fdate("current_on_date", "Current On Date");

		force_to_current = new fstring(1, "force_to_current", "Force To Current");
		force_to_current.getfieldinfo().setValidValues(force_to_current_vals);
		force_to_current.getfieldinfo().setValidValueDescriptions(force_to_current_desc);
		force_to_current.setInitialValue("N");
		
		force_registration_close = new fstring(1, "force_registration_close", "Force Registration Close");
		force_registration_close.getfieldinfo().setValidValues(force_registration_close_vals);
		force_registration_close.getfieldinfo().setValidValueDescriptions(force_registration_close_desc);		
		force_registration_close.setInitialValue("N");

		active = new fstring(1, "active", "Active");
		active.getfieldinfo().setValidValues(active_vals);
		active.getfieldinfo().setValidValueDescriptions(active_desc);
		active.setInitialValue("Y");
//		active.getfieldinfo().setdisplayonly(true);
//		active.getfieldinfo().setFormHidden(true);

		created = new fstring(22, "created", "Created");
		created.getfieldinfo().setdisplayonly(true);
//		created.getfieldinfo().setFormHidden(true);

		modified = new fstring(22, "modified", "Modified");
		modified.getfieldinfo().setdisplayonly(true);
//		modified.getfieldinfo().setFormHidden(true);
		
		field[] tmp = {company_id,city_id,league_id,sport_id,
			name,format_id,level_id,game_site_group_id,times,season,registration_close,
			start_date,end_date,regular_weeks,playoff_weeks,inclusions,last_display_date,
			team_size,min_team_roster,max_team_roster,priority_fee_until_date,team_priority_fee,
			team_standard_fee,individual_priority_fee,individual_standard_fee,non_member_fee,
			current_on_date,force_to_current,force_registration_close,
			active,created,modified};
	
		field[] pkey = { company_id, city_id, league_id };
	
		setFields(tmp);
		
		setPrimaryKey(pkey);
	}
}