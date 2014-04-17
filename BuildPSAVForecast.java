import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Random;


/*
 * ******************************************************************************************
 * Copyright 1999-2008 Subject, Wills & Company - All rights reserved
 * 
 * Source File : BuildPSAVForecast.java
 * 
 * Version : $Revision: 1.1 $
 * 
 * $Log: BuildPSAVForecast.java,v $
 * Revision 1.1  2009/07/04 14:10:30  rec
 * *** empty log message ***
 *
 * 
 * ******************************************************************************************
 */

public class BuildPSAVForecast {

	
private static String[] propertyList = {"6304",
	"6655",
	"4642",
	"3074",
	"6344",
	"6279",
	"1304",
	"1344",
	"1301",
	"6302",
	"6315",
	"6308",
	"6346",
	"1325",
	"1238",
	"1306",
	"1346",
	"6642",
	"6326",
	"6270",
	"6301",
	"2027",
	"1570",
	"6305",
	"6306",
	"1499",
	"1305",
	"6640",
	"2083",
	"1199",
	"6564",
	"6314",
	"1303",
	"1315",
	"1308",
	"1326",
	"6027",
	"2003",
	"1564",
	"4655",
	"6183",
	"6199",
	"1302",
	"4279",
	"1183",
	"6325",
	"2010",
	"4270",
	"6238",
	"4640",
	"1339",
	"1314",
	"6499"};


	static String[] months = {
		"6/2008",
		"7/2008",
		"8/2008",
		"9/2008",
		"10/2008",
		"11/2008",
		"12/2008",
		"1/2009",
		"2/2009",
		"3/2009"
	};
 
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try {
			Random rnd = new Random();
		
			PrintWriter pw1 = new PrintWriter(new FileOutputStream("c:/temp/forecast.csv"));
			
			
			
			for (int i1=0;i1<propertyList.length;i1++) {
				int avMonthy = (500000 + rnd.nextInt(1000000)) / 12;
				int hsiaMonthly = (int)(avMonthy * (.3 - (rnd.nextFloat() * (.2))));
				int powerMonthly = (int)(avMonthy * (.2 - (rnd.nextFloat() * (.15))));
				int riggingMonthly = (int)(avMonthy * (.2 - (rnd.nextFloat() * (.15))));
				
				for (int i2=0;i2<months.length;i2++) {
					double factorTenative =  (((float)i2 + 1.0)/(10.0)) * (1.2 - 0.4 * rnd.nextFloat());
					double factorConfirmed = 1 - factorTenative;
					if (factorConfirmed < 0) factorConfirmed *= -1;
					pw1.println(propertyList[i1] + "," +
							"AV," +
							"Committed" + "," +
							months[i2] + "," +
							figure(avMonthy,rnd,i2,factorConfirmed));
					pw1.println(propertyList[i1] + "," +
							"HSIA," +
							"Committed" + "," +
							months[i2] + "," +
							figure(hsiaMonthly,rnd,i2,factorConfirmed));
					pw1.println(propertyList[i1] + "," +
							"Power," +
							"Committed" + "," +
							months[i2] + "," +
							figure(powerMonthly,rnd,i2,factorConfirmed));
					pw1.println(propertyList[i1] + "," +
							"Rigging," +
							"Committed" + "," +
							months[i2] + "," +
							figure(riggingMonthly,rnd,i2,factorConfirmed));
					pw1.println(propertyList[i1] + "," +
							"Business Center," +
							"Committed" + "," +
							months[i2] + "," +
							figure(500 + rnd.nextInt(2000),rnd,i2,factorConfirmed));
					
					pw1.println(propertyList[i1] + "," +
							"AV," +
							"Tenative" +  "," +
							months[i2] + "," +
							figure(avMonthy,rnd,i2,factorTenative));
					pw1.println(propertyList[i1] + "," +
							"HSIA," +
							"Tenative" + "," +
							months[i2] + "," +
							figure(hsiaMonthly,rnd,i2,factorTenative));
					pw1.println(propertyList[i1] + "," +
							"Power," +
							"Tenative" + "," +
							months[i2] + "," +
							figure(powerMonthly,rnd,i2,factorTenative));
					pw1.println(propertyList[i1] + "," +
							"Rigging," +
							"Tenative" + "," +
							months[i2] + "," +
							figure(riggingMonthly,rnd,i2,factorTenative));
					pw1.println(propertyList[i1] + "," +
							"Business Center," +
							"Tenative" + "," +
							months[i2] + "," +
							figure(500 + rnd.nextInt(2000),rnd,i2,factorTenative));
					
				}
				
			}
			pw1.close();
		}
		catch (Throwable th1) {
			th1.printStackTrace();
		}
		
	}
	
	static int figure(int inp,Random rnd, int decay, double factor) {
		double decayFactor = .05;
		if (decay < 10) decayFactor =1.0 - decay/10.0;
		System.out.println(decay + " " + decayFactor);
		int retval = (int)((double)inp * (1.2 - .4 * rnd.nextFloat()) * decayFactor * factor);
		retval = retval/100;
		retval = retval * 100;
		System.out.println(decay + " " + decayFactor + " " + inp + " " + retval);
		return retval;
	}

}
