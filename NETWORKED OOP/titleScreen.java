
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;


import javax.swing.ImageIcon;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


import java.awt.Font;


public class titleScreen extends JApplet {

	public static final int WIDTH = 700, HEIGHT = 600;

	private JFrame window;
	private Container con;
	private JPanel titleNamePanel, startButtonPanel;
	private JLabel titleNameLabel;
	private Font titleFont = new Font("Times New Roman", Font.PLAIN, 40);
	private Font normalFont = new Font("Times New Roman", Font.PLAIN, 30);
	private Font font = new Font("Verdana", Font.BOLD, 32);
	
	private JButton startButton;
	
	TitleScreenHandler tsHandler = new TitleScreenHandler();
	
	private ImageIcon titleImage;
	private JPanel titleImagePanel;
	private JLabel titleImageLabel;
	
	
	public static BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	public static int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
	
	public titleScreen() {
		
		
	}
	public void screen() {
		window = new JFrame();
		window.setSize(800,600);
		titleImage = new ImageIcon(getClass().getClassLoader().getResource("background.png"));
		window.setResizable(false);
		window.setLocationRelativeTo(null);
		//window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.getContentPane().setBackground(Color.black);
		window.setLayout(null);
		window.setVisible(true);
		con = window.getContentPane();
				
		titleImagePanel= new JPanel();
		titleImagePanel.setBounds(0,0,800,600);
		
		titleImageLabel = new JLabel();
		titleImageLabel.setIcon(titleImage);
		titleImagePanel.add(titleImageLabel);
			
		titleNamePanel = new JPanel();
		titleNamePanel.setBounds(100, 100, 600, 150);
		titleNamePanel.setBackground(Color.black);
		titleNamePanel.setOpaque(false);
		titleNameLabel = new JLabel("Connect Four");
		titleNameLabel.setForeground(Color.white);
		titleNameLabel.setFont(titleFont);
		
		startButtonPanel = new JPanel();
		startButtonPanel.setBounds(300,400,200,100);
		startButtonPanel.setBackground(Color.black);
		startButtonPanel.setOpaque(false);
		
		startButton = new JButton("START");
		startButton.setBackground(Color.black);
		startButton.setForeground(Color.white);
		startButton.setFont(normalFont);
		startButton.addActionListener(tsHandler);
		startButton.setFocusPainted(false);
		startButton.setOpaque(false);
				
		titleNamePanel.add(titleNameLabel);
		startButtonPanel.add(startButton);
		
		con.add(titleNamePanel);
		con.add(startButtonPanel);
		con.add(titleImagePanel);
		
		//nagpa connect sa ika duhang player 
	}
	public class TitleScreenHandler implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			window.dispose();
			
			JFrame frame = new JFrame("CONNECT FOUR ");
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
	}	
}
