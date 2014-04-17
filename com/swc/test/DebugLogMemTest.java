/*
 * Created on Sep 18, 2003
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.swc.test;

import com.swc.fpjava.DebugLogContributor;
import com.swc.fpjava.al.ALsession;

/**
 * @author rec
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class DebugLogMemTest {

	public static void main(String[] args) {
		try {
			ALsession sess = new ALsession();
			int count = 0;
//			PrintWriter br = new PrintWriter(new FileOutputStream("x.x"));
			for(;;){
				count++;
				sess.getDebugLogHandle().writeLog(DebugLogContributor.WARNING,
					sess.getDebugLogToken(),"test " + count );
//				br.println("test " + count);
				if (count % 1000 == 0) {
					Runtime.getRuntime().gc();
					System.out.println(count + " " + Runtime.getRuntime().totalMemory() +
						" " + Runtime.getRuntime().freeMemory());
				}
			}
		}
		catch (Throwable th1) {
			th1.printStackTrace();
		}
	}
}
