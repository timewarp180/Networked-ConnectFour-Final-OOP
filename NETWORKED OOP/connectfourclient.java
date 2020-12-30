import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.LineBorder;

//import titleScreen.TitleScreenHandler;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class connectfourclient extends JApplet implements Runnable, connectfourconstraints {
  private boolean myTurn = false;

  private char myToken = ' ';
  private Scanner scanner = new Scanner(System.in);

  private char otherToken = ' ';

  private Cell[][] cell =  new Cell[6][7];

  private JLabel jlblTitle = new JLabel();

  private JLabel jlblStatus = new JLabel();

  private int rowSelected;
  private int columnSelected;

  private DataInputStream fromServer;
  private DataOutputStream toServer;

  private boolean continueToPlay = true;

  private boolean waiting = true;

  boolean isStandAlone = false;

  public static String host = "localhost";
  public static int port = 22222;
  public static String name = "";
  
	JTextField getName,getIP, getPort;
	JLabel p,n,i;
	JTextField iport;
	JPanel p1, p2, submitButtonPanel;
	public static int redScore=0;
	
	public static int yellowScore=0;

	
	//IPhandler ipHandler = new IPhandler();


  
  /** Initialize UI */
  public void init() {
	  
    // panel p to hold cells
	  
    JPanel p = new JPanel();
    p.setLayout(new GridLayout(6, 7, 0, 0));
    for (int i = 0; i < 6; i++)
      for (int j = 0; j < 7; j++)
        p.add(cell[i][j] = new Cell(i, j, cell));

    //   para labels and borders for labels and panel
    p.setBorder(new LineBorder(Color.black, 1));
    jlblTitle.setHorizontalAlignment(JLabel.CENTER);
    jlblTitle.setFont(new Font("SansSerif", Font.BOLD, 16));
    jlblTitle.setBorder(new LineBorder(Color.black, 1));
    jlblStatus.setBorder(new LineBorder(Color.black, 1));

    
    // Place the panel and the labels to the applet
    add(jlblTitle, BorderLayout.NORTH);
    add(p, BorderLayout.CENTER);
    add(jlblStatus, BorderLayout.SOUTH);

    // Connect to the server
    connectToServer();
  }

 
 public void getIP() {
	 JFrame f=new JFrame("GetPLayerIP"); 
     //submit button
JButton b=new JButton("Submit");    
b.setBounds(100,250,140, 40);    
     //enter name label
JLabel label = new JLabel();  
JLabel label2 = new JLabel();        
JLabel label3 = new JLabel();        

label.setText("Enter Name :");
label.setBounds(10, 10, 100, 100);

label2.setText("Enter IP :");
label2.setBounds(10, 60, 110, 100);

label3.setText("Enter Port :");
label3.setBounds(10, 110, 120, 100);
     //empty label which will show event after button clicked
JLabel label1 = new JLabel();
label1.setBounds(10, 110, 200, 100);
     //textfield to enter name
JTextField textfield= new JTextField();
JTextField textfield2= new JTextField();
JTextField textfield3= new JTextField();

textfield.setBounds(110, 50, 130, 30);
textfield2.setBounds(110, 100, 130, 30);
textfield3.setBounds(110, 150, 130, 30);

     //add to frame
f.add(label1);
f.add(label2);
f.add(label3);

f.add(textfield);
f.add(textfield2);
f.add(textfield3);

f.add(label);
f.add(b);    
f.setSize(400,400);    
f.setLayout(null);    
f.setVisible(true);    
f.setLocationRelativeTo(null);

f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   

b.addActionListener(new ActionListener() {
    
    @Override
    public void actionPerformed(ActionEvent arg0) {
    		
            name = textfield.getText();
     //      host =Integer.parseInt(textfield2.getText());
            host = textfield2.getText();
            port = Integer.parseInt(textfield3.getText());
            f.dispose();
        	titleScreen t = new titleScreen(); 
        	t.screen();
    }          
  });
}    
  
 public void restart () {
	 JFrame f=new JFrame("GAME OVER"); 
     //submit button
	 JButton bb=new JButton("Restart");    
	 bb.setBounds(75, 100, 130, 30);  
//	 bb.setBounds(x, y, width, height);
	 
	 f.add(bb);    
	 f.setSize(300,300);    
	 f.setLayout(null);    
	 f.setVisible(true);    
	 f.setLocationRelativeTo(null);
	 
	 bb.addActionListener(new ActionListener() {
		    
		    @Override
		    public void actionPerformed(ActionEvent arg0) {
		    		
		            f.dispose();			          
		        	JFrame frame = new JFrame("CONNECT FOUR");
					  connectfourclient applet = new connectfourclient();
					    applet.isStandAlone = true;

					    // Get host
					 //   if (args.length == 1) applet.host = args[0];

					    // Add the applet instance to the frame
					    frame.getContentPane().add(applet, BorderLayout.CENTER);

					    // Invoke init() and start()
					    applet.init();
					   applet.start();

					    // Display the frame
					    frame.setSize(640, 600);
					   // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					    frame.setVisible(true);					    
		    
		    }          
		  });
 }
 
  private void connectToServer() {
//	  	System.out.println("Please input Your Name: ");
//		name = scanner.nextLine();
//	  	System.out.println("Please input the IP: ");
//		host = scanner.nextLine();
//		System.out.println("Please input the port: ");
//		port = scanner.nextInt();
//	  
    try {
      // Create a socket to connect to the server	
      Socket socket;
      if (isStandAlone)
        socket = new Socket(host, port);
      else
        socket = new Socket(getCodeBase().getHost(), port);

      // Create an input stream to receive data from the server
      fromServer = new DataInputStream(socket.getInputStream());

      // Create an output stream to send data to the server
      toServer = new DataOutputStream(socket.getOutputStream());
    }
    catch (Exception ex) {
      System.err.println(ex);
    }

    // Control the game on a separate thread
    Thread thread = new Thread(this);
    thread.start();
  }

  public void run() {
    try {
      // get notif from the server
      int player = fromServer.readInt();

      if (player == PLAYER1) {
        myToken = 'r';
        otherToken = 'y';
        jlblTitle.setText("Player 1:  " + name +" color red SCORE: " +redScore);
        jlblStatus.setText("Waiting for player 2 to join");

        // Receive startup notification from the server
        fromServer.readInt(); // Whatever read is ignored

        // The other player has joined
        jlblStatus.setText("Player 2 has joined. I start first");

        
        myTurn = true;
      }
      else if (player == PLAYER2) {
        myToken = 'y';
        otherToken = 'r';
        jlblTitle.setText("Player 2:  " + name + " color yellow SCORE: " +yellowScore);
        jlblStatus.setText("Waiting for player 1 to move");
      }
      // Continue to play
      while (continueToPlay) {
        if (player == PLAYER1) {
          waitForPlayerAction(); // Wait for player 1 to move
          sendMove(); // Send the move to the server
          receiveInfoFromServer(); // Receive info from the server
        }
        else if (player == PLAYER2) {
          receiveInfoFromServer(); // Receive info from the server
          waitForPlayerAction(); // Wait for player 2 to move
          sendMove(); // Send player 2 move to the server
        }
      }
    }
    catch (Exception ex) {
    }
  }

  // buffering cells gikan sa server
  private void waitForPlayerAction() throws InterruptedException {
    while (waiting) {
      Thread.sleep(100);
     // Thread.sleep(500);

    }

    waiting = true;
  }

  // Send this player's move to the server 
  private void sendMove() throws IOException {
    toServer.writeInt(rowSelected); // isend sa server row
    toServer.writeInt(columnSelected); // isend sa server col
  }

  // Receive info from the server 
  private void receiveInfoFromServer() throws IOException {
    // Receive game status
    int status = fromServer.readInt();
    
    if (status == PLAYER1_WON) {
        redScore++;
    }else if (status == PLAYER2_WON){
        yellowScore++;
    }
    
    if (status == PLAYER1_WON) {
      continueToPlay = false;
      if (myToken == 'r') {
        jlblStatus.setText("YOU WON! ");
        restart ();
      
      } else if (myToken == 'y') {
        jlblStatus.setText("PLAYER 1 HAS WON");
        receiveMove();
        restart ();
      }
    }
    else if (status == PLAYER2_WON) {
      continueToPlay = false;
      if (myToken == 'y') {
        jlblStatus.setText("YOU WON!");
        restart ();

      }
      else if (myToken == 'r') {
        jlblStatus.setText("PLAYER 2 HAS WON");
        receiveMove();
        restart ();
      }
    }
    else if (status == DRAW) {
      continueToPlay = false;
      jlblStatus.setText("Game is over, no winner!");
      restart ();
      if (myToken == 'y') {
        receiveMove();
      }
    }
    else {
      receiveMove();
      jlblStatus.setText("My turn");
      myTurn = true;
    }
  }

  private void receiveMove() throws IOException {
    // Get the other player's move
    int row = fromServer.readInt();
    int column = fromServer.readInt();
    cell[row][column].setToken(otherToken);
  }

  public class Cell extends JPanel {
    private int row;
    private int column;
    private Cell[][] cell;

    private char token = ' ';

    public Cell(int row, int column, Cell[][] cell) {
      this.row = row;
      this.cell = cell;
      this.column = column;
      setBorder(new LineBorder(Color.black, 1));
      addMouseListener(new ClickListener());

    }

    public char getToken() {
      return token;
    }

    public void setToken(char c) {
      token = c;
      repaint();
    }

    protected void paintComponent(Graphics g) {
      super.paintComponent(g);

      if (token == 'r') {
      //  g.drawOval(10, 10, getWidth() - 20, getHeight() - 20);
        g.setColor(Color.red);
        g.fillOval(10 ,10,  getWidth() - 20, getHeight() - 20);


      }
      else if (token == 'y') {
       // g.drawOval(10, 10, getWidth() - 20, getHeight() - 20);
        g.setColor(Color.yellow);
        g.fillOval(10 ,10,  getWidth() - 20, getHeight() - 20);
      }
    }

    /** Handle mouse click on a cell */
    private class ClickListener extends MouseAdapter {
      public void mouseClicked(MouseEvent e) {
      	int r= -1;
      	for(int x =5; x>= 0; x--){
      		if(cell[x][column].getToken() == ' '){

      			r=x;
      			break;
      		}
      	}
        if ((r != -1) && myTurn) {
          cell[r][column].setToken(myToken);
          myTurn = false;
          rowSelected = r;
          columnSelected = column;
          jlblStatus.setText("Waiting for the other player to move");
          waiting = false; // Just completed a successful move
        }
      }
    }
  }

  public static void main(String[] args) {
	  
		connectfourclient c = new connectfourclient();
		c.getIP();

//	titleScreen t = new titleScreen(); 
	
	//t.screen();
    
  
		
		
		
  }
}
