import java.awt.Point;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Connection {
	public static final int PORT = 6789;
	
	public static ArrayList<ClientConnection> connections = new ArrayList<ClientConnection>();

	public Connection() throws Exception {
		ServerSocket ss = new ServerSocket(PORT);
		while (true) {
			Socket cs = ss.accept();
			
			System.out.println("Connection from " + cs.getInetAddress() + " port " + cs.getPort());
			ClientConnection cc = new ClientConnection(cs);
			cc.start();
			
		} 
		
		
	} 
	
	

	static class ClientConnection extends Thread {
		private final Socket client;
		

		public ClientConnection(Socket s) {
			client = s;
		}
		
		

		@Override
		public void run() {
			String userName = "anon";
			String nameCommand ="/name";
			try {
				System.out.println("Spawning thread ...");
				InputStream iS = client.getInputStream();
				OutputStream oS = client.getOutputStream();
				oS.flush();
				ObjectOutputStream oOut = new ObjectOutputStream(oS);
				ObjectInputStream oIn = new ObjectInputStream(iS);
				try {
					while (true) {
						
						String msg =  (String) oIn.readObject();
						
						if (msg.substring(0,5).equals("/name")) {
							userName = msg.substring(5,msg.length()-1);
						}
						
						GUI.showMessage(userName+": "+msg);
						oOut.writeObject(userName+": "+msg);
						oOut.flush();
						client.getOutputStream().flush();
					}
				} catch (IOException e) {
					oIn.close();
					oOut.close();
					client.close();
				}
			} catch (Exception e) {
				throw new Error(e.toString());
			}
			System.out.println("... thread done.");
		} 
	} 
} 