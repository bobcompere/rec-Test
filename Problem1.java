import com.swc.fpjava.al.fdate;
import com.swc.fpjava.al.fnumber;
import com.swc.fpjava.al.fstring;

/*
 * ******************************************************************************************
 * Copyright 1999-2005 Subject, Wills & Company - All rights reserved
 * 
 * Source File : Problem1.java
 * 
 * Version : $Revision: 1.1 $
 * 
 * $Log: Problem1.java,v $
 * Revision 1.1  2007/03/18 20:21:58  rec
 * *** empty log message ***
 *
 * 
 * ******************************************************************************************
 */

/**
 * @author rec
 * 
 * @version $Revision: 1.1 $
 * 
 */
public class Problem1 {

    public static void main(String[] args) {
        
        
        
        fstring myfstring = new fstring(10);
        
        
        myfstring.load("X X X ");
        
        System.out.println("toString[" + myfstring.toString() + "]");
        System.out.println("toStringx[" + myfstring.toStringx() + "]");
        System.out.println("chop[" + myfstring.chop() + "]");        
        System.out.println("squeeze[" + myfstring.squeeze() + "]");
        
        
        fstring goofyBool = new fstring(1);
        
        goofyBool.getfieldinfo().setBooleanTrueValue("K");
        goofyBool.getfieldinfo().setBooleanFalseValue("Z");
 //       goofyBool.getfieldinfo().setBooleanDefault(true);
        
        boolTest(goofyBool,"Z");
        boolTest(goofyBool,"K");
        boolTest(goofyBool,"Q");
        
        fnumber myNum = new fnumber(10,2);
        
        myNum.getfieldinfo().setCommaDisplay(true);
        myNum.getfieldinfo().setCurrency(true);
        myNum.getfieldinfo().setSignTrailing(true);
        
        myNum.load("-10101.33");
        
        System.out.println(myNum.toString());
        System.out.println(myNum.format());
        
        
        fdate myDate = new fdate();
        
        myDate.clock();
        
        System.out.println(myDate.toString());
        System.out.println(myDate.format());
        
        myDate.add(30);
        System.out.println(myDate.format());
        
        myDate.clock();
        int today = myDate.toInt();
        
        myDate.load("20060222");
        
        System.out.println(myDate.toInt() - today + " days till bob's b-day");
        
    }

    /**
     * @param goofyBool
     * @param string
     */
    private static void boolTest(fstring goofyBool, String string) {
        goofyBool.load(string);
        
        System.out.println("GoofyBool=" + goofyBool.toString() + " is " + goofyBool.toBoolean());
    }
}
