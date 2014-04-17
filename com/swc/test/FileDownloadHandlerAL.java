package com.swc.test;

import java.io.FileInputStream;
import java.io.IOException;
import java.rmi.RemoteException;

import com.swc.fpjava.al.ALServletHandlerObj;
import com.swc.fpjava.al.ALsession;

public class FileDownloadHandlerAL extends ALServletHandlerObj {

public FileDownloadHandlerAL(ALsession sess) throws RemoteException {
	super(sess);
}

public int handleHttp(String cmd, String[] pnams, String[][] pvals) throws RemoteException {
	String file = getParmVal("filename",pnams,pvals);
	
	try {
		FileInputStream fis = new FileInputStream(file);
		setContentType("application/pdf");
//		String[] sa1 = {"Content-Disposition"};
//		String[] sa2 = {"attachment; filename=\"" + file + "\""};
//		setHttpHeaders(sa1,sa2);
		setDataStream(fis);
		return DATA_STREAM;
	}
	catch (IOException e) {
		session.reportException(e);
		html.addElement("Error : " + e.toString());
	}
	return PAGE_ONLY;
	
}
}