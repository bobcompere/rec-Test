package com.swc.test;

import java.rmi.RemoteException;

import com.swc.fpjava.fpException;
import com.swc.fpjava.al.fpALfactory;
import com.swc.fpjava.ui.UIsession;
import com.swc.fpjava.ui.web.fpServletHandler;

public class FileDownloadHandler extends fpServletHandler {

String[] cmds = {"pdfdownload"} ;

public FileDownloadHandler(fpALfactory alf, UIsession sess) throws fpException,RemoteException {
	super(alf,sess);
	
	setAnonymousCommands(cmds);
	ALServletHandlerName = "com.swc.test.FileDownloadHandlerAL";
	
}

}