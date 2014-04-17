package com.swc.rec;

import java.io.*;
import javax.swing.*;

import java.sql.Savepoint;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import com.swc.fpjava.al.time;

public class logger extends Thread {	


JComboBox clientbox;
JButton newClient;
JTextArea logText;
JButton logButton;
JButton hideButton;
JButton quitButton;
JFrame dlog;
worklog wrklog;
int sleepsec = 1800000;
int remindsec = 300000;
boolean doLog;
boolean newclnt;
boolean terminate;
boolean hideNow;
boolean nc_OK;
boolean nc_Can;

time curtime;

JDialog EnterNewClient;
JTextField newClientName;
JButton ncOK;
JButton ncCancel;
JLabel[] times;

HashMap<String,String> savedLogMess;

AnnoyThread bugger;
long lastTime;

String path;
String lastClient;
Vector clients;

public static void main(String[] args) {

	
	if (args.length != 1) {
		System.err.println("Format> javaw com.swc.rec.logger logpath");
		System.exit(1);
	}
	System.out.println("Logger starting");
	logger lgr = new logger(args[0]);
	lgr.start();
	System.out.println("Logger started");
}

public logger(String xpath) {
	try {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	}
	catch (Exception e1) {} // no-opt
	path = xpath;
	wrklog = new worklog(path);
	
	try {
		wrklog.log("Logger Started");
	}
	catch (IOException e) {
		e.printStackTrace();
		System.exit(1);
	}
	
	curtime = new time();
	
	clients = new Vector();
	savedLogMess = new HashMap<String, String>();
	
	try {
		BufferedReader rdr = new BufferedReader(new FileReader(path + "\\clientlist"));
		String line;
		for(;;) {
			line = rdr.readLine();
			if (line ==  null) break;
			clients.add(line);
		}
	}
	catch (Exception e) {}
	dlog = new JFrame();
	dlog.addWindowListener(new loggerWindowListener(this));
	dlog.setTitle("Work Log");
	dlog.getContentPane().setLayout(new BorderLayout());
	
	JPanel topPanel = new JPanel();
	JPanel btnPanel = new JPanel();
	topPanel.setLayout(new BorderLayout());
	
	clientbox = new JComboBox(clients);
	lastClient = (String)clientbox.getSelectedItem();
	
	newClient = new JButton("New Client");
	logText = new JTextArea(10,40);
	logText.setLineWrap(true);
	logText.setWrapStyleWord(true);
	logButton = new JButton("Log");
	hideButton = new JButton("Hide");
	quitButton = new JButton("Quit");
	
	loggerActionListener lal = new loggerActionListener(this);
	newClient.addActionListener(lal);
	logButton.addActionListener(lal);
	hideButton.addActionListener(lal);
	quitButton.addActionListener(lal);
	clientbox.addActionListener(lal);
	
	topPanel.add(clientbox,"Center");
	topPanel.add(newClient,"East");
	topPanel.add(new JLabel("Client:"),"West");
	
	btnPanel.add(logButton);
	btnPanel.add(hideButton);
	btnPanel.add(quitButton);
	
	dlog.getContentPane().add(topPanel,"North");
	dlog.getContentPane().add(logText,"Center");
	dlog.getContentPane().add(btnPanel,"South");
	
	JPanel timePanel = new JPanel();
	times = new JLabel[10];
	for(int i1=0;i1<10;i1++) 
		times[i1] = new JLabel("    ");
	timePanel.setLayout(new GridLayout(10,1));
	for(int i1=0;i1<10;i1++)
		timePanel.add(times[i1]);
	
	dlog.getContentPane().add(timePanel,"East");
	
	dlog.pack();
	
	EnterNewClient = new JDialog();
	EnterNewClient.setTitle("New Client");
	JPanel ncBtm = new JPanel();
	JPanel ncTop = new JPanel();
	ncBtm.setLayout(new FlowLayout());
	ncTop.setLayout(new FlowLayout());
	newClientName = new JTextField(10);
	ncTop.add(new JLabel("New Client:"));
	ncTop.add(newClientName);
	ncOK = new JButton("Ok");
	ncOK.addActionListener(lal);
	ncCancel = new JButton("Cancel");
	ncCancel.addActionListener(lal);
	ncBtm.add(ncOK);
	ncBtm.add(ncCancel);
	
	EnterNewClient.getContentPane().setLayout(new BorderLayout());
	EnterNewClient.getContentPane().add(ncTop,"North");
	EnterNewClient.getContentPane().add(ncBtm,"South");
	EnterNewClient.pack();
	
	Dimension scrnsize = Toolkit.getDefaultToolkit().getScreenSize();
	Dimension dlogsize = dlog.getSize();
    	Point parloc = new Point(0,0);
    
    	parloc.translate((scrnsize.width - dlogsize.width)/2,
                     (scrnsize.height - dlogsize.height)/2);
    	dlog.setLocation(parloc);

	dlogsize = EnterNewClient.getSize();
    parloc = new Point(0,0);
    
    parloc.translate((scrnsize.width - dlogsize.width)/2,
                     (scrnsize.height - dlogsize.height)/2);
    EnterNewClient.setLocation(parloc);
	
	bugger = new AnnoyThread(this);
	bugger.start();
}

public void run() {
	clearFlags();
	dlog.setVisible(true);
	for (;;) {
		try {
			synchronized(this) {
				wait();
			}
		}
		catch (InterruptedException e) {
			e.printStackTrace(System.err);
		}
		if (terminate) {
			try {
				wrklog.log("Logger Stopped");
			}
			catch (IOException e1) {
				e1.printStackTrace();
				System.exit(1);
			}

			dlog.setVisible(false);
        		dlog.dispose();
			System.exit(0);
		}
		if (doLog) logit();
		else if (hideNow) dlog.setState(Frame.ICONIFIED);
		else if (newclnt) getNewClient();
		else if (nc_Can) {
			EnterNewClient.setVisible(false);
		}
		else if (nc_OK) {
			insertNewClient();
			EnterNewClient.setVisible(false);
		}
		clearFlags();
	}
}

void getNewClient() {
	EnterNewClient.setVisible(true);
}

void insertNewClient() {
	String nc = newClientName.getText();
	if (nc.trim().equals("")) return;
	
	Enumeration eclnt = clients.elements();
	
	int indx = 0;
	String clnt = "";
	
	while (eclnt.hasMoreElements()) {
		clnt = (String)eclnt.nextElement();
		int cmp = clnt.compareTo(nc);
		if (cmp == 0) return;
		if (cmp > 0) {
			break;
		}
		indx++;
	}
	clientbox.insertItemAt(nc,indx);
	clientbox.setSelectedIndex(indx);
	
	eclnt = clients.elements();
	
	try {
		PrintWriter pw = new PrintWriter(new FileOutputStream(path + "clientlist"));
		while (eclnt.hasMoreElements())
			pw.println(((String)eclnt.nextElement()));
		pw.flush();
		pw.close();
	}
	catch (IOException e) {
		e.printStackTrace(System.err);
		System.exit(1);
	}
		
}
	
		 
	

void logit() {
	try {
		String logmsg = logText.getText();
		String xclnt = (String)clientbox.getSelectedItem();
		wrklog.setClient(xclnt);
		wrklog.log(logmsg);
		logText.selectAll();
	}
	catch (IOException e) {
		e.printStackTrace(System.err);
		System.exit(1);
		}
	for(int i1=9;i1>0;i1--)
		times[i1].setText(times[i1 - 1].getText());
	curtime.clock();
	times[0].setText(curtime.toString());
	dlog.setState(Frame.ICONIFIED);
	lastTime = new Date().getTime();
}


void clearFlags() {
	terminate = false;
	doLog = false;
	hideNow = false;
	newclnt  = false;
	nc_OK = false;
	nc_Can = false;
}

}

class AnnoyThread extends Thread {

logger owner;
long lastAnnoy;

AnnoyThread(logger own) {
	owner = own;
}

public void run() {
	lastAnnoy = new Date().getTime();
	for (;;)  {
		try {
			sleep (60000);
		}
		catch (Exception e) {}
		
		long nowTime = new Date().getTime();
		
		if (nowTime - owner.lastTime > owner.sleepsec)  {
			if (nowTime - lastAnnoy > owner.remindsec) annoy();
		}
	}
}

private void annoy() {
	owner.dlog.setState(Frame.NORMAL);
	owner.dlog.toFront();
	lastAnnoy = new Date().getTime();
}
			
}			
	


class loggerActionListener implements ActionListener {
	
logger owner;

public loggerActionListener(logger par) {
	owner = par;
}


public void actionPerformed(ActionEvent evt) {
    
    	Object src = evt.getSource();
   	if (src == owner.quitButton) {
    		owner.clearFlags();
        owner.terminate = true;
		synchronized (owner) {
			owner.notify();
		}
   	}
	if (src == owner.hideButton) {
    		owner.clearFlags();
		owner.hideNow = true;
		synchronized (owner) {
			owner.notify();
		}
	}
	if (src == owner.logButton) {
    		owner.clearFlags();
		owner.doLog = true;
		synchronized (owner) {
			owner.notify();
		}
	}
	if (src == owner.newClient) {
    		owner.clearFlags();
		owner.newclnt = true;
		synchronized (owner) {
			owner.notify();
		}
	}
	if (src == owner.ncOK) {
    		owner.clearFlags();
		owner.nc_OK = true;
		synchronized (owner) {
			owner.notify();
		}
	}
	if (src == owner.ncCancel) {
    		owner.clearFlags();
		owner.nc_Can = true;
		synchronized (owner) {
			owner.notify();
		}
	}
	
	if (src == owner.clientbox) {
		owner.savedLogMess.put(owner.lastClient, owner.logText.getText());
		owner.lastClient = (String)owner.clientbox.getSelectedItem();
		String s1 = owner.savedLogMess.get(owner.lastClient);
		if (s1 == null) s1 = "";
		owner.logText.setText(s1);
	}
}
}

class loggerWindowListener extends WindowAdapter {

logger owner;

loggerWindowListener(logger xx) {
	owner = xx;
}
	
public void windowClosing(WindowEvent e) {
	try {
		owner.wrklog.log("Logger Stopped");
	}
	catch (IOException e1) {
		e1.printStackTrace();
		System.exit(1);
	}
	System.exit(0);
}
}