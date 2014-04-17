package com.swc.test;

import java.io.FileInputStream;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Vector;

import com.swc.fpjava.fpException;
import com.swc.fpjava.fpdateformat;
import com.swc.fpjava.al.ALServletHandlerObj;
import com.swc.fpjava.al.ALsession;
import com.swc.fpjava.al.CalendarHandler;
import com.swc.fpjava.al.FormProcessor;
import com.swc.fpjava.al.SortedHtmlTable;
import com.swc.fpjava.al.fdate;
import com.swc.fpjava.al.field;
import com.swc.fpjava.al.fieldinfo;
import com.swc.fpjava.al.fnumber;
import com.swc.fpjava.al.fstring;
import com.swc.fpjava.al.messageinfo;
import com.swc.fpjava.ui.Validater;

public class testHandlerAL extends ALServletHandlerObj {


FormProcessor testForm;
fstring cust;
fnumber testNum;
fstring phoneNumber;
fstring goofmask;
fstring timeTest;
fdate myDate;
fdate myDate2;
fdate myDate3;

public testHandlerAL(ALsession sess) throws RemoteException {

super(sess);
//session.setruntimevar("SSL_ONLY","YES");
cust =  new fstring(6,"cust","Customer Number");
cust.getfieldinfo().setHttpPopUp("cmSearch");
testNum = new fnumber(6,2,"test","Number test");
testNum.getfieldinfo().setMinValue(0);
testNum.getfieldinfo().setMaxValue(10.20);

phoneNumber = new fstring(10,"phone","Phone Number");
phoneNumber.getfieldinfo().setEditMask("(NNN)-NNN-NNNN");
phoneNumber.getfieldinfo().setPreferredHttpControl(fieldinfo.HttpSplitMaskControl);

goofmask = new fstring(13,"goofy","Mask Test");
goofmask.getfieldinfo().setEditMask("ANN-AAAAA-NAN-NN");
goofmask.getfieldinfo().setPreferredHttpControl(fieldinfo.HttpSplitMaskControl);

timeTest = new fstring(7,"time","Time Test");
timeTest.getfieldinfo().setEditMask("NN:NNAA");
timeTest.getfieldinfo().setPreferredHttpControl(fieldinfo.HttpSplitMaskControl);

String[][] vvs = {{"01","02","03","04","05","06","07","08","09","10","11","12"},
			{"00","05","10","15","20","25","30","35","40","45","50","55"},
			{"am","pm"}};
timeTest.getfieldinfo().setValidValues(vvs);

myDate = new fdate("tdat","Test Date");
myDate2 = new fdate(fpdateformat.FORMAT_MYY,"xdat","teast date 2");
//myDate.getfieldinfo().setHttpPopUp("cal.Calendar","Cal!");
myDate2.getfieldinfo().setCalendarPopup(true);
addDispatchable(new CalendarHandler(session,"Calendar"),"cal");
System.out.println("[" + testNum.getfieldinfo().getMaxValue() + "][" + testNum.getfieldinfo().getMinValue() + "]");

myDate3 = new fdate(fpdateformat.FORMAT_YYMD,"zdat","test date 3");
myDate3.getfieldinfo().setEditMask("AA/AA/AAAA");
myDate3.getfieldinfo().setPreferredHttpControl(fieldinfo.HttpSplitMaskControl);
//myDate3.getfieldinfo().setzeropad(true);
//myDate3.getfieldinfo().setrightjustify(true);
myDate3.getfieldinfo().setLongFormat(true);
myDate3.getfieldinfo().setrequired(true);
myDate3.getfieldinfo().setNoSqlInsertIfBlank(true);


Validater.setDebug(session.getALdbg());
}

public int handleHttp(String command, String[] pnams, String[][] pvals) throws RemoteException {

	try {
		if (command.equalsIgnoreCase("testform"))  return outputTestForm(pnams,pvals);
		if (command.equalsIgnoreCase("messagetest")) return outputMessageTest();
		if (command.equalsIgnoreCase("filedownload")) return fileDownload(pnams,pvals);
		if (command.equalsIgnoreCase("sorttest")) return sortTest();
		if (command.equalsIgnoreCase("startup")) return beginSession();
		if (command.equalsIgnoreCase("bombout"))  {
			String x = null;
			x.toUpperCase();
			return PAGE_ONLY;
		}
		if (command.equalsIgnoreCase("kaboom")) {
			throw new RemoteException("KABOOM");
		}
	}
	catch (fpException e) {
		session.reportException(e);
		throw new RemoteException(e.toString());
	}
	messageinfo mi = new messageinfo();
	mi.addtext("Unsupported command [" + command + "]");
	try {
		session.message(mi);
	}
	catch (Exception e) {}
	return PAGE_ONLY;
}

private int outputTestForm(String[] pnams, String[][] pvals) throws fpException,RemoteException {

	if (testForm == null)  {
		timeTest.load("0905am");
		field[] flds = { cust, testNum , myDate,myDate2, phoneNumber, goofmask, timeTest, myDate3};

		testForm = new FormProcessor("testform",null,"testform",session,flds);
	}
	testForm.updateForm(pnams,pvals);
	testForm.updateFields();
	
	session.println("mydate2 = [" + myDate2.toString() + "][" + myDate2.format() + "]");
	
	session.println("time=[" + timeTest.toString() + "]");
	messageinfo mi = new messageinfo();
	mi.addtext("Form is Valid = " + testForm.isValid());
	session.message(mi);
	messageinfo mi2 = new messageinfo();
	mi2.addtext("myDate3 = [" + myDate3.toString() + "]");
	session.message(mi2);
	
	testForm.outputForm(html);
	html.addElement(" Phone Number : " + phoneNumber.format() + " [" + phoneNumber.toString() +"]");
	html.addElement("<br>" + goofmask.getfieldinfo().getEditMask());
	return PAGE_WITH_VECTOR;
}


private int outputMessageTest()throws RemoteException {
	session.println("outputMessage");
	try {
		Thread.sleep(20000);
	}
	catch (Exception e) {}
	messageinfo mi = new messageinfo();
	mi.addtext("====Message text====");
	session.message(mi);
	
	Vector otherStuff = new Vector();
	otherStuff.addElement("This is supposed to plug into another Spot");
	otherStuff.addElement("<BR>Did it work");
	
	session.setRegionContent("recrec",otherStuff);
	
	html = new Vector();
	html.addElement("<h3>Regular Servlet Output</h3>");
	return PAGE_WITH_VECTOR;
} 

private int fileDownload(String[] pnams, String[][] pvals) {
	String file = getParmVal("filename",pnams,pvals);

	try {
		FileInputStream fis = new FileInputStream(file);
		setContentType("application/octet-stream");
//		String[] sa1 = {"Content-Disposition"};
//		String[] sa2 = {"attachment; filename=\"" + file + "\""};
//		setHttpHeaders(sa1,sa2);
		setDataStream(fis);
		return DATA_STREAM;
	}
	catch (IOException e) {
		e.printStackTrace();
		e.printStackTrace(session.ALdbg.getPrintWriter());
	}
	return PAGE_ONLY;
	
}

private int sortTest() throws RemoteException {
	SortedHtmlTable sht = new SortedHtmlTable("sorttest",session,this);
	addDispatchable(sht,"sorttest");
	
	String[] cols = {"Initials","First Name","Last Name","Dept"};
	
	sht.setTdClass("regular");
	sht.setBorder(3);
	sht.setCellPadding(2);
	sht.setCellSpacing(1);
	
	sht.setColumns(cols);
	
	sht.addData("REC");
	sht.addData("Bob");
	sht.addData("Compere","red");
	sht.addData("Custom","05");
	
	sht.addData("DAI");
	sht.addData("Dave");
	sht.addData("Ivey");
	sht.addData("Custom","05");
	
	sht.addData("ADG");
	sht.addData("Andy");
	sht.addData("Griffith");
	sht.addData("Custom","05");
	
	sht.addData("BRS");
	sht.addData("Brad");
	sht.addData("Shockley");
	sht.addData("Custom","05");
	
	sht.addData("JKL");
	sht.addData("Jeff");
	sht.addData("Lemmerman");
	sht.addData("Admin","99");
	
	sht.addData("DRD");
	sht.addData("Dave");
	sht.addData("Donat");
	sht.addData("PPro","06");
	
	sht.addData("JSF");
	sht.addData("Jamie");
	sht.addData("Findley");
	sht.addData("DB/c","07");
	
	html = new Vector();
	sht.sort(0);
	
	html.addElement("top 1<br>");
	html.addElement("<A HREF=\"http://" + session.getHostAddress() + session.getPort() + "/"
		+ session.getZone() + "/" + session.getAlias() + "?command=back\">Repeat</a><br>");
	html.addElement("top 2<br>");
	sht.outputTable();
	html.addElement("Bottom 1<br>");
	html.addElement("Bottom 2<br>");
	return PAGE_WITH_VECTOR;
}
}
	