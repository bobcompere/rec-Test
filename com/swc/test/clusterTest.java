package com.swc.test;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class clusterTest extends HttpServlet {

String color = "#00080";
String name = "clusterTest";
String URL = "";

public void init(ServletConfig cg) throws ServletException {
	super.init(cg);

	String s1 = cg.getInitParameter("url");
	if (s1 != null) URL = s1;
	
	s1 = cg.getInitParameter("name");
	if (s1 != null) name = s1;

	s1 = cg.getInitParameter("color");
	if (s1 != null) color = s1;
}

public void service(HttpServletRequest req, HttpServletResponse resp) {

	try { 
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		out.println("<html>");
		out.println("<head>");
		out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=windows-1252\">");
		out.println("<meta http-equiv=\"refresh\" content=\"10;url=" + URL +"\">");
		out.println("<title>" + name + "</title>");
		out.println("</head>");
		out.println("<body bgcolor=\"" + color + "\">");
		out.println("<H1>" + name + "</h1>");
		out.println("<H1>" + new Date().toString() + "</H1>");
		out.println("</body>");
		out.println("</html>");

	}
	catch (Exception e) {
		e.printStackTrace();
	}
}
}