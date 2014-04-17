import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;


/*
 * ******************************************************************************************
 * Copyright 1999-2010 SWC Technology Partners - All rights reserved
 * 
 * Source File : ByteTest.java
 * 
 * Version : $Revision: 1.1 $
 * 
 * $Log: ByteTest.java,v $
 * Revision 1.1  2010/11/18 04:58:05  rec
 * *** empty log message ***
 *
 * 
 * ************************************************************************
 */

public class ByteTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		byte[] test = new byte[4];
		
		test[1] = fix(126);
		test[2] = fix(129);
		test[3] = fix(80);
		
		String s1 = new String(test);
		
		try {
			
			OutputStream os1 = new FileOutputStream("C:/temp/kcs.out");
			
			BufferedWriter bw1 = new BufferedWriter(new OutputStreamWriter(os1));
			bw1.write("BLA");
			bw1.flush();
			os1.write(test);
			os1.flush();
			bw1.write("yada");
			bw1.close();
		}
		catch (Throwable th1) {
			th1.printStackTrace();
		}
	}

	
	private static byte fix(int val) {
		if (val < 128) return (byte)val;
		
		int tmp = val - 256;
		return (byte)tmp;
	}
}
