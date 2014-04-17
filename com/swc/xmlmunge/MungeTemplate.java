/*
 * ******************************************************************************************
 * Copyright 1999-2009 SWC Technology Partners - All rights reserved
 * 
 * Source File : MungeTemplate.java
 * 
 * Version : $Revision: 1.1 $
 * 
 * $Log: MungeTemplate.java,v $
 * Revision 1.1  2009/12/30 21:29:55  rec
 * *** empty log message ***
 *
 * 
 * ******************************************************************************************
 */

package com.swc.xmlmunge;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

public class MungeTemplate {

	
	private TreeMap<String,MungeTable> tables;
	private Element source;
	private Element data;
	private Map<String,Mungable> contentMap;
	
	public MungeTemplate(InputStream xml) throws Exception {
		SAXBuilder SAX = new SAXBuilder();
		source = SAX.build(xml).getRootElement();
		contentMap = new HashMap();
		
		tables = new TreeMap<String,MungeTable>();
		
		Iterator it1 = source.getChildren("table").iterator();
		while (it1.hasNext()) {
			Element t1 = (Element)it1.next();
			addTable(t1);
		}
	}
	
	private void loadData(InputStream xml) throws Exception  {
		SAXBuilder SAX = new SAXBuilder();
		data = SAX.build(xml).getRootElement();
		
		loadData(data,"/" + data.getNamespacePrefix() + ":" + data.getName());
	}
	
	private void loadData(Element data, String parentKey) {
		Iterator sections = data.getChildren().iterator();
		while (sections.hasNext()) {
			Element sect = (Element)sections.next();
			String key = parentKey + "/" + sect.getNamespacePrefix() + ":" + sect.getName();
			
			Mungable o1 = contentMap.get(key);
			String mapping = "no mapping";
			
			if (o1 != null) {
				mapping = "Mapped to " + o1.getClass().getName() + " " + o1.hashCode();
				
				loadMappedData(sect,o1);
			}
			System.out.println("Processing data for : " + sect.getName() + " " + key + " " + mapping);
			loadData(sect,key);
		}
	}
	
	public void loadMappedData(Element sect, Mungable o1) {
		o1.munge(sect.getTextTrim());
	}
	
	public void addToMap(String key, Mungable m1) {
		contentMap.put(key,m1);
	}
	
	private void addTable(Element table) throws  Exception {
		String id = table.getAttributeValue("id");
		if (id == null) throw new Exception("Foobar table, no ID " + outputXML(table));
		
		tables.put(id,new MungeTable(this,id, table));
	}
	
	private String outputXML(Element e1) throws IOException {
		XMLOutputter XOUT = new XMLOutputter("  ",true);
		return XOUT.outputString(e1);
	}
	
	public String dump() {
		String retval = "";
		Iterator<String> tbls = tables.keySet().iterator();
		while (tbls.hasNext()){
			MungeTable mt1 = tables.get(tbls.next());
			retval += mt1.dump();
		}
		return retval;
	}
	
	public static void main(String[] args) {
		try {
//			System.out.print("XX");
//			BufferedReader br1 = new BufferedReader(new InputStreamReader(new FileInputStream(args[0])));
//			String s1 = null;
//			while ((s1 = br1.readLine()) != null) System.out.println(s1);

			
			MungeTemplate template = new MungeTemplate(new FileInputStream(args[0]));
			
			System.out.println("DUMP:\n" + template.dump());
			
			template.loadData(new FileInputStream(args[1]));
			
			System.out.println("DUMP again:\n" + template.dump());
		}
		catch (Exception e1) {
			e1.printStackTrace();
		}
	}
}
