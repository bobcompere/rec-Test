import com.swc.fpjava.mail.Mailer;

/*
 * ******************************************************************************************
 * Copyright 1999-2008 Subject, Wills & Company - All rights reserved
 * 
 * Source File : MailTest.java
 * 
 * Version : $Revision: 1.1 $
 * 
 * $Log: MailTest.java,v $
 * Revision 1.1  2009/07/04 14:10:30  rec
 * *** empty log message ***
 *
 * 
 * ******************************************************************************************
 */

public class MailTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Mailer myMailer = new Mailer("genoa.swc.com");
		
		myMailer.sendMail("rec@swc.com", "rec@swc.com", "TEST", null, null, "return-path: <ams@swc.com>\nGOOD?", null, null, null, null, "jdk@swc.com");
	}

}
