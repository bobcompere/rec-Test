/*
 * ******************************************************************************************
 * Copyright 1999-2009 SWC Technology Partners - All rights reserved
 * 
 * Source File : DocxDump.java
 * 
 * Version : $Revision: 1.1 $
 * 
 * $Log: DocxDump.java,v $
 * Revision 1.1  2009/12/30 21:29:55  rec
 * *** empty log message ***
 *
 * 
 * ******************************************************************************************
 */

package com.swc.doctest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import org.jdom.Document;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;


public class DocxDump {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		
		try {
			ZipFile zip = new ZipFile(args[0]);
			
			InputStream is1 = zip.getInputStream(zip.getEntry("word/document.xml"));
			
			Enumeration e1 = zip.entries();
			while (e1.hasMoreElements()) {
				ZipEntry ze1 = (ZipEntry)e1.nextElement();
				System.out.println(ze1.getName() + " " + ze1.getSize());
			}
			
			
			SAXBuilder sax = new SAXBuilder();
			
			Document doc1 = sax.build(is1);
			
			XMLOutputter xout = new XMLOutputter(" ",true);
			System.out.println(xout.outputString(doc1));
			
			
			is1 = zip.getInputStream(zip.getEntry("word/media/image1.jpeg"));
			
			byte[] buffer = new byte[4096];
			int bytesRead = 0;
			
			FileOutputStream fos = new FileOutputStream(new File(args[0]).getParentFile().getAbsolutePath() + "/image1.jpeg");
			while ((bytesRead = is1.read(buffer))>0) fos.write(buffer,0,bytesRead);
			fos.close();
			
			
			FileOutputStream rewrite = new FileOutputStream(new File(args[0]).getParentFile().getAbsolutePath() + "/rewrite.docx");
			ZipOutputStream zipOut = new ZipOutputStream(rewrite);
			
			e1 = zip.entries();
			while (e1.hasMoreElements()) {
				ZipEntry ze1 = (ZipEntry)e1.nextElement();
				System.out.println(ze1.getName() + " " + ze1.getSize());
				ZipEntry ze2 = new ZipEntry(ze1.getName());
				zipOut.putNextEntry(ze2);
				InputStream data = null;
				if (ze1.getName().equals("word/media/image1.jpeg")) {
					data = new FileInputStream(new File(args[0]).getParentFile().getAbsolutePath() + "/puppy.jpeg");
				}
				else {
					data = zip.getInputStream(ze1);
				}
				while ((bytesRead = data.read(buffer)) > 0) zipOut.write(buffer, 0, bytesRead);
				zipOut.closeEntry();
			}
			zipOut.close();
		}
		catch (Throwable th1) {
			th1.printStackTrace();
			System.exit(1);
		}

	}

}
