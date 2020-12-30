import java.io.*;
import java.net.*;
import javax.swing.*;
import java.awt.*;
import java.util.Date;
import java.awt.event.*;

public class connectfourserver extends JFrame implements connectfourconstraints {
	JButton jbtConnect;
	JTextField iport;
	static JTextArea jtaLog;
	JLabel e;


  private class ButtonListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {

      int port1 =Integer.parseInt(iport.getText());
		listen(port1);

    }
  }

 private void listen(int port1){

    try {
      // Create a server socket
      System.out.println("CONNECT");
      ServerSocket serverSocket = new ServerSocket(port1);
      System.out.println("DONE");
      System.out.println(new Date() + ": Server started at socket: \n" +port1);

      // Number a session
      int sessionNo = 1;

      // Ready to create a session for every two players
      while (true) {
        jtaLog.append(new Date() + ": Wait for players to join session " + sessionNo + '\n');

        // Connect to player 1
        System.out.println("RUN connectfourclient.java now");
        Socket player1 = serverSocket.accept();
        System.out.println("DONE");
        System.out.println(new Date() + ": Player 1 joined session " + sessionNo + '\n');
        System.out.println("Player 1's IP address" + player1.getInetAddress().getHostAddress() + '\n');

        // Notify that the player is Player 1
        new DataOutputStream(player1.getOutputStream()).writeInt(PLAYER1);

        // Connect to player 2
        Socket player2 = serverSocket.accept();

        System.out.println(new Date() + ": Player 2 joined session " + sessionNo + '\n');
        System.out.println("Player 2's IP address" + player2.getInetAddress().getHostAddress() + '\n');

        // Notify that the player is Player 2
        new DataOutputStream(player2.getOutputStream()).writeInt(PLAYER2);

        // Display this session and increment session number
        System.out.println(new Date() + ": Start a thread for session " + sessionNo++ + '\n');

        // Create a new task for this session of two players
        HandleASession task = new HandleASession(player1, player2);

        // Start the new thread
        new Thread(task).start();
        
 
      }
    }
    catch(IOException ex) {
      System.err.println(ex);
    }
    }



  public static void main(String[] args) {


   	connectfourserver frame = new connectfourserver();
  }


  public connectfourserver() {
	 
	JPanel p = new JPanel();
  	JPanel p1 = new JPanel();
	JPanel p2 = new JPanel(new GridLayout(1,2));
	JPanel x = new JPanel();
	iport = new JTextField(4);

	jbtConnect= new JButton("Connect");
	
	

	jtaLog = new JTextArea();
	JLabel putport = new JLabel("Server Port:");
	e = new JLabel("Port:");
	
	p.add(putport);
	p2.add(e);
	x.add(jtaLog);
	p2.add(iport);
	p2.add(jbtConnect);
	
	x.setBounds(75, 100, 130, 30);
    add(p1, BorderLayout.NORTH);
    add(p, BorderLayout.CENTER);
	add(p2, BorderLayout.SOUTH);

	jbtConnect.addActionListener(new ButtonListener());
	
	setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    setSize(500, 400);
    setTitle("Connect Four Server");
    setVisible(true);
  }
}



