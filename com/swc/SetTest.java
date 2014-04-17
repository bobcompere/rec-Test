package com.swc;

import java.util.HashSet;

import com.swc.fpjava.al.fstring;

public class SetTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		HashSet<fstring> test = new HashSet<fstring>();
		
		fstring control = new fstring(10);
		control.load("foobar");
		
		for (int i1=0;i1<10;i1++) {
			fstring x = new fstring(10);
			x.load("foobar");
			test.add(x);
			test.add(control);
			System.out.println(test.size() + "");
			control.load(i1 + "");
		}
	}

}
