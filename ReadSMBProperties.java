import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Properties;


/*
 * ******************************************************************************************
 * Copyright 1999-2008 Subject, Wills & Company - All rights reserved
 * 
 * Source File : ReadSMBProperties.java
 * 
 * Version : $Revision: 1.1 $
 * 
 * $Log: ReadSMBProperties.java,v $
 * Revision 1.1  2009/07/04 14:10:30  rec
 * *** empty log message ***
 *
 * 
 * ******************************************************************************************
 */

public class ReadSMBProperties {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		try {
			
			System.out.println(System.getProperty("file.encoding"));
			
			if (1==1) throw new Exception("Stop");
			
			String file = "c:/eclipse_projects/Axiom/servers/srvr-web1/safer-web-inf/safer.options";
			
			int lineMax = 1;
			
			for(;;) {
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				PrintWriter pw1 = new PrintWriter(baos);
				
				BufferedReader rdr1 = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
				
				int curLine = 0;
				
				String line = null;
				System.out.println("---------------------------------");
				System.out.println("Max:" + lineMax);
				
				while (curLine < lineMax) {
					line = rdr1.readLine();
					if (line == null) break;
					curLine++;
					pw1.println(line);
					System.out.println(line);
				}
				if (curLine < lineMax) break;
				
				pw1.close();
				Properties prop = new Properties();
				prop.load(new ByteArrayInputStream(baos.toByteArray()));
				
				lineMax++;
			}
		}
		catch (Throwable th1) {
			th1.printStackTrace();
		}
	}

}
