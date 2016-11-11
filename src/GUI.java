import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class GUI extends JFrame {

	private static JTextArea ta;
	private static String latestMessage;

	public GUI() {

		super("Server");

		ta = new JTextArea();
		ta.setEditable(false);
		add(new JScrollPane(ta), BorderLayout.CENTER);
		setSize(400,400);
		setVisible(true);
		
		
		
		

	}
	
	public static void showMessage(String msg) {
		ta.append(msg);
		latestMessage = msg;
	}
	
	public void startConnection() throws Exception {
		Connection connection = new Connection();
	}
}