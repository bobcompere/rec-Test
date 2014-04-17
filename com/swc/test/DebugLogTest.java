package com.swc.test;

import com.swc.fpjava.DebugLogContributor;
import com.swc.fpjava.DebugLogHandle;
import com.swc.fpjava.DebugLogToken;
import com.swc.fpjava.fpException;

/**
 * @author rec
 *
 * @Apr 23, 2003
 */
public class DebugLogTest extends Thread implements DebugLogContributor {

	DebugLogHandle dlh;
	DebugLogToken dlt;
	private String[] eventNames = { "cus event1","cus event2" };
		

	public static void main(String[] args) {
		
		try {
			DebugLogTest dlt = new DebugLogTest();
			dlt.start();
		}
		catch (Throwable th1) {
			th1.printStackTrace();
		}
	}
	
	public DebugLogTest() throws fpException {
		dlh = new DebugLogHandle(this,"testdl","Test Debug Log");
		dlh.registerContributor(this);
	}
	
	public void run() {
		int count = 0;
		for (;;) {
			count++;
			dlh.writeLog(DEBUG,dlt,"Attempt Number 1/" + count +" = debug");
			dlh.writeLog(WARNING,dlt,"Attempt Number 2" + count +" = warning");
			dlh.writeLog(DEBUG,dlt,"Attempt Number 3" + count +" = debug");
			dlh.writeLog(WARNING,dlt,"Attempt Number 4" + count +" = warning");
			dlh.writeLog(DEBUG,dlt,"Attempt Number 5" + count +" = debug");
			dlh.writeLog(WARNING,dlt,"Attempt Number 6" + count +" = warning");
			try {
				sleep(30000);
			}
			catch (Exception e) {}
		}
	}
	/**
	 * @see com.swc.fpjava.DebugLogContributor#getDebugLogEventNames()
	 */
	public String[] getDebugLogEventNames() {
		return eventNames;
	}

	/**
	 * @see com.swc.fpjava.DebugLogContributor#setDebugLogToken(DebugLogToken)
	 */
	public void setDebugLogToken(DebugLogToken dlt) {
		this.dlt = dlt;
	}
	public void setDebugLogHandle(DebugLogHandle dlh) {
		this.dlh = dlh;
	}
}
