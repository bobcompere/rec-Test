/*
 * ******************************************************************************************
 * Copyright 1999-2007 Subject, Wills & Company - All rights reserved
 * 
 * Source File : MailTest.java
 * 
 * Version : $Revision: 1.1 $
 * 
 * $Log: MailTest.java,v $
 * Revision 1.1  2007/03/18 20:21:58  rec
 * *** empty log message ***
 *
 * 
 * ******************************************************************************************
 */

package com.swc.rec;

import java.util.Date;

import com.swc.fpjava.mail.Mailer;
import com.swc.fpjava.mail.MailerAttachment;

public class MailTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Mailer cliff = new Mailer("genoa.swc.com");
		
		MailerAttachment[] atts = new MailerAttachment[1];
		
		try {
			atts[0] = cliff.prepareAttachment("c:/temp/goodyear.csv","goodyear.csv","text/csv",null,false);
			
			cliff.sendMail("bob.compere@swc.com","rec@genoa.swc.com","test",null,null,"test body " + new Date(),atts,null);
			System.out.println("Mail Sent");
		}
		catch (Throwable th1) {
			th1.printStackTrace();
		}
		System.exit(0);
	}

}
