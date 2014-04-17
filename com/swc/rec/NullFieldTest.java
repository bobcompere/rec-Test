package com.swc.rec;

import com.swc.fpjava.al.vstring;

public class NullFieldTest {

	public static void main(String[] args) {
		
		vstring test = new vstring();
		
		String nullboy = null;
		
		test.load(nullboy);
		
		System.out.println("[" + test.toString() + "]" + test.toString().length() + " " + test.equals("null"));
		
		
		
	}
}
