import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author rec
 *
 * @Jun 19, 2003
 */
public class test1 {

	public static void main(String[] args) {
		
		int count = 0;
		try {
			ServerSocket ss1 = new ServerSocket(20000);
		
			for (;;) {
				Socket client = ss1.accept();
				count++;
				System.out.println("Connect : " + count);
				ss1.close();
			}
		}
		catch (Throwable th1) {
			th1.printStackTrace();
		}
	}
}
