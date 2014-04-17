/*
 * ******************************************************************************************
 * Copyright 1999-2005 Subject, Wills & Company - All rights reserved
 * 
 * Source File : GenVulcanData.java
 * 
 * Version : $Revision: 1.1 $
 * 
 * $Log: GenVulcanData.java,v $
 * Revision 1.1  2007/03/18 20:21:58  rec
 * *** empty log message ***
 *
 * 
 * ******************************************************************************************
 */

package com.rec;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Random;

import com.swc.fpjava.al.fdate;
import com.swc.fpjava.al.field;
import com.swc.fpjava.al.fieldlist;

/**
 * @author REC
 * 
 * @version $Revision: 1.1 $
 * 
 */
public class GenVulcanData {

fdate day,weekstart,today;	

public void doIt() throws Exception {
	day = new fdate();
	weekstart = new fdate();
	today = new fdate();
	today.clock();
	
	ArrayList dataElem = new ArrayList();
	
	for (int i1=1;i1<14;i1++) {
		dataElem.add(new plantSeg(i1,1,250.0,220.0,.90,.90,16.0,20.0,2.0));
		dataElem.add(new plantSeg(i1,2,400.0,350.0,.90,.90,16.0,20.0,2.0));
		dataElem.add(new plantSeg(i1,3,650.0,575.0,.90,.90,16.0,20.0,2.0));
		dataElem.add(new plantSeg(i1,4,650.0,600.0,.90,.90,16.0,20.0,2.0));
		dataElem.add(new plantSeg(i1,5,650.0,700.0,.90,.90,16.0,20.0,2.0));
	}
	
	fieldlist flDate = new fieldlist(new field[] {day,weekstart});
	
	day.load("20040101");
	weekstart.load(day);
	while (weekstart.getDOWint() != Calendar.MONDAY) weekstart.add(-1);
	
	BufferedWriter dates = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("/temp/dates.csv")));
	BufferedWriter details = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("/temp/details.csv")));
	
	while (day.toInt() < today.toInt()) {
		System.out.println(day.format());
		Iterator it1 = dataElem.iterator();
		while (it1.hasNext()) {
			plantSeg ps = (plantSeg)it1.next();
			details.write(ps.output(day) + "\n");
		}
		dates.write(flDate.toCommaDelimited(true,true,true) + "\n");
		day.add(1);
		if (day.getDOWint() == Calendar.MONDAY) weekstart.load(day);
	}
	System.out.println("DONE");
	dates.flush();
	details.flush();
}
	
public static void main(String[] args) {
	GenVulcanData gvd = new GenVulcanData();
	try {
		gvd.doIt();
	} catch (Exception e) {
		e.printStackTrace();
	}
}
}

class plantSeg {

int plant;
int segment;
double target;
double actual;
double target_util;
double actual_util;
double target_sched_hours;
double actual_hours;
double delay_hours;
static int id;
static DecimalFormat dFmt1 =  new DecimalFormat("#######.00");
static Random rnd = new Random(new Date().getTime());
	
plantSeg(int plt,int seg,double targ, double act, double tutil,double autil, double tsh,double ah,double dh) {
	plant = plt;
	segment = seg;
	target = targ + 25 * (int)(rnd.nextInt(7) - 3) ;
	actual = act + 25 * (int) (rnd.nextInt(7) - 3);
	target_util = tutil + 0.05 * (int)(rnd.nextInt(5) - 2);
	actual_util = target_util - .05 * (int)(rnd.nextInt(2));
	target_sched_hours = tsh + 2 * (int) (rnd.nextInt(7) - 3);;
	actual_hours = target_sched_hours + 2 * (int) (rnd.nextInt(5) - 3);
	delay_hours = dh +  0.05 * (int)(rnd.nextInt(5) - 2);
}

String output(fdate dt) {
	id++;
	String retval = id + "," + plant + "," + segment + "," + dt.format() + "," + dFmt1.format(target) +
	"," + dFmt1.format(slush(actual, (double) .2)) + "," + dFmt1.format(target_util) + "," +
	dFmt1.format(slush(actual_util,(double) .2)) + "," + dFmt1.format(target_sched_hours) + "," +
	dFmt1.format(slush(actual_hours,(double) .2)) + "," + dFmt1.format(slush(delay_hours,(double) .2));
	return retval;
}

double slush(double input,double variation) {
	return (double) (input * (1 + variation * (1 - rnd.nextDouble() * 2.0)));
}
}
