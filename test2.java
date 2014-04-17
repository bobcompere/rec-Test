import java.net.Socket;

/**
 * @author rec
 *
 * @Jun 19, 2003
 */
public class test2 {

	public static void main(String[] args) {
		
		try {
			Socket s1 = new Socket("127.0.0.1",20000);
			System.out.println("Client Connected");
		}
		catch (Throwable th1) {
			th1.printStackTrace();
			System.exit(0);
		}
		System.out.println("DONE");
		System.exit(0);
	}
}
