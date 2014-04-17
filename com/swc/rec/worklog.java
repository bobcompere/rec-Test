package com.swc.rec;

import java.io.FileOutputStream;
import java.io.IOException;
import com.swc.fpjava.al.*;

import java.text.DecimalFormat;
import java.util.*;

public class worklog {	

private String path;
private String filename;
private fdate date;
private time time;
private fstring elapsed;
private fstring client;
private fstring dow;
private Date lastLog;
private DecimalFormat dFmt = new DecimalFormat("00");

private static int charMax = 40;

byte hex0D = 0x0d;
byte hex0A = 0x0a;
byte hex20 = 0x20;

public worklog(String xpath) {
	path = xpath;
	filename = xpath + "\\jwrk.log";
	date = new fdate();
	time = new time();
	client = new fstring(10);
	elapsed = new fstring(8);
	dow = new fstring(10);
	lastLog = new Date();
}

public void log(String mesg) throws IOException {
	FileOutputStream out = new FileOutputStream(filename,true);
	
	
	Date now = new Date();
	
	long millis = now.getTime() - lastLog.getTime();
	lastLog = now;
	
	int tsec = (int)(millis/1000);
	
	int sec = tsec % 60;
	tsec  -= sec;
	int tmin = tsec / 60;
	int min = tmin % 60;
	
	tmin -= min;
	
	int hours = tmin / 60;
	
	elapsed.load(dFmt.format(hours) + ":" + dFmt.format(min) + ":" + dFmt.format(sec));
	
	date.clock();
	time.clock();
	dow.load(date.getDOW());
	String prefix = dow.toStringx() + date.format() + " " + time.toString() + " [" + elapsed.toString() + "] " + client.toStringx() + " ";
	byte[] bprefix = prefix.getBytes();
	StringTokenizer st1 = new StringTokenizer(mesg);
	int pos = 0;
	out.write(bprefix);
	for (int i1 = 0;i1<bprefix.length;i1++) bprefix[i1] = hex20;
	String s1 = null;
	while (st1.hasMoreTokens())  {
		s1 = st1.nextToken();
		int len = s1.length();
		if (len + pos > charMax)  {
			out.write(hex0D);
			out.write(hex0A);
			out.write(bprefix);
			pos = 0;
		}
		out.write(s1.getBytes());
		out.write(hex20);
		pos+=len;
		pos++;
	}
	out.write(hex0D);
	out.write(hex0A);
	out.flush();
	out.close();
}

public void setClient(String clnt) {
	client.load(clnt);
}
}