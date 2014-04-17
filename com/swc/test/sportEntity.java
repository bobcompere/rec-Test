package com.swc.test;

import com.swc.fpjava.fpException;
import com.swc.fpjava.al.ALsession;
import com.swc.fpjava.al.Entity;
import com.swc.fpjava.al.field;
import com.swc.fpjava.al.fnumber;
import com.swc.fpjava.al.fstring;
import com.swc.fpjava.al.result;

public class sportEntity extends Entity {
	public field company_id;			// 0
	public field city_id;				// 1
	public field sport_id;				// 2
	public field overview_page;			// 3
	public field description;			// 4
	public field rules_page;			// 5
	public field links_page;			// 6
	public field no_current_league_msg;	// 7
	public field no_upcoming_league_msg;// 8
	public field admin_email;
	public field max_group_size;		// 9
	public field active;				// 10
	public field created;				// 11
	public field modified;				// 12

	private String[] active_desc = {"Yes", "No"};
	private String[] active_vals = {"Y", "N"};

	public sportEntity(ALsession sess) throws fpException {
		super(sess,"sport");
		company_id = new fnumber(12, 0, "company_id", "Company ID");
		company_id.getfieldinfo().setdisplayonly(true);
		
		city_id = new fnumber(12, 0, "city_id", "City ID");
		city_id.getfieldinfo().setdisplayonly(true);
		
		sport_id = new fnumber(12, 0, "sport_id", "Sport ID");
		sport_id.getfieldinfo().setdisplayonly(true);
		
		overview_page = new fstring( 50, "overview_page", "Overview Page");

		
		description = new fstring(40, "description", "Description");
		description.getfieldinfo().setdisplaylength(20);
		description.getfieldinfo().setrequired(true);
		
		rules_page = new fstring( 50, "rules_page", "Rules Page");
		rules_page.getfieldinfo().setdisplay(true);
		rules_page.displayexit("<a href\"command=loadpage&page=" + rules_page.chop() 
				+ "&prototype=rules_0_0.htm&Type=ServletPage\">"
				+ rules_page.chop() + "</a>");
	
		links_page = new fstring( 50, "links_page", "Links Page");
		links_page.getfieldinfo().setdisplay(true);
		links_page.displayexit("<a href\"command=loadpage&page=" + links_page.chop() 
				+ "&prototype=overview_0_0.htm&Type=ServletPage\">"
				+ links_page.chop() + "</a>");

		no_current_league_msg = new fstring( 50, "no_current_league_msg", "No Current League Message");
		
		no_upcoming_league_msg = new fstring( 50, "no_upcoming_league_msg", "No Upcoming League Message");
		
		max_group_size = new fnumber(6, 0, "max_group_size", "Max Group Size");
		
		admin_email = new fstring(40,"admin_email","Administrative Email");
		
		active = new fstring(1, "active", "Active");
		active.getfieldinfo().setValidValues(active_vals);
		active.getfieldinfo().setValidValueDescriptions(active_desc);
		active.setInitialValue("Y");
//		active.getfieldinfo().setdisplayonly(true);
//		active.getfieldinfo().setFormHidden(true);

		created = new fstring(22, "created", "Created");
		created.getfieldinfo().setdisplayonly(true);
		created.getfieldinfo().setSqlReadOnly(true);

//		created.getfieldinfo().setFormHidden(true);

		modified = new fstring(22, "modified", "Modified");
		modified.getfieldinfo().setdisplayonly(true);
		modified.getfieldinfo().setSqlReadOnly(true);
//		modified.getfieldinfo().setFormHidden(true);
		
		field[] tmp = {company_id, city_id, sport_id, overview_page, description,
			rules_page, links_page, 
			no_current_league_msg, no_upcoming_league_msg, admin_email,max_group_size, active, created, modified};

		field[] tmp2 = {description, overview_page, rules_page, links_page};
		
		field[] key = { company_id, city_id , sport_id };
		
		setFields(tmp,tmp2);
		setPrimaryKey(key);
		
		
		description.setupDisplayExit(this,"testexit");
		description.getfieldinfo().setSortOnFormat(true);
	}

public result testexit(String val,field fld,Boolean bval) {
	StringBuffer sb1 = new StringBuffer();
	sb1.append(val);
	sb1.reverse();
	result retval = new result(sb1.toString() + " " + val.toLowerCase());
	return retval;
}
}	