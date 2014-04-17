import com.swc.fpjava.al.fdate;

/*
 * Created on Jan 8, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */

/**
 * @author rec
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class DateTester {

	public static void main(String[] args) {
		
		fdate d1 = new fdate();
		d1.clock();
		System.out.println(d1.format()  + " " + d1.toInt());
		fdate d2 = new fdate();
		d2.load(d1.toInt() - 8);
		System.out.println(d2.format()  + " " + d2.toInt());
		
		
		d1.load("19920101");
		while (!d1.toString().equals("20200101")) {
			int days = d1.toInt();
			try {
				d2.load(days);
			}
			catch (Exception e) {
				System.out.println("FAILED d2 : " + days + d1.format());
				break;
			}
			if (!d1.toString().equals(d2.toString())) {
					System.out.println("Error : "+ 
					d1.toString() + " " + d1.toInt() + " " + d2.toString() + d2.toInt());
					break;
			}
			days++;
			try {
				d1.load(days);
			}
			catch (Exception e) {
				System.out.println("FAILED d1 : " + days);
				break;
			}
			if (days % 100 == 0) System.out.println("OK : "+ 
			d1.toString() + " " + d1.toInt() + " " + d2.toString() +" " + d2.toInt());
		}
	}
}
