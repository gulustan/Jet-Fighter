import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MenuFrame extends JFrame implements ActionListener{

	static String UserName, PassWord, PlayerScore;
	static int enemySpawn;

	public static void main(String[] args){
			
		JButton register = new JButton("Register");
		JButton play = new JButton("Play Game");
		JButton scoreboard = new JButton("Score");
		JButton exit = new JButton("Exit");
		
		Icon menu = new ImageIcon(MenuFrame.class.getResource("menu.jpg"));
		JLabel background = new JLabel(menu);
			
		JFrame loginFrame = new JFrame("Login");
		loginFrame.add(register);
		loginFrame.add(play);
		loginFrame.add(scoreboard);
		loginFrame.add(exit);
		
		loginFrame.setLayout(new FlowLayout());
		
		loginFrame.add(background);
		loginFrame.setVisible(true);
		loginFrame.setSize(850, 550);
		loginFrame.setResizable(false);
		loginFrame.setLocationRelativeTo(null);
		loginFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
	
		register.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent a)
			{
				JTextField userName = new JTextField(5);
				JTextField Password = new JTextField(5);

				JPanel panel = new JPanel();
				panel.add(new JLabel("Name:"));
				panel.add(userName);
				panel.add(Box.createHorizontalStrut(50));
				panel.add(new JLabel("Password:"));
				panel.add(Password);

				int result = JOptionPane.showConfirmDialog(null, panel,"Register", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
				if (result == JOptionPane.OK_OPTION) 
				{
					String UserName = userName.getText();
					String PassWord = Password.getText();
					String Score = "0";
	
					try {
						BufferedWriter wr = new BufferedWriter(new FileWriter("scores.txt", true));
						wr.append(UserName);
						wr.newLine();
						wr.append(PassWord);
						wr.newLine();
						wr.append(Score);
						wr.newLine();
						wr.close();
					}		      
					catch (IOException e) {
						System.out.println("No file");
					}
				    
				}
			}	
		});
	
		play.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent b)
			{
				JTextField userName = new JTextField(5);
				JTextField Password = new JTextField(5);

				JPanel myPanel = new JPanel();
				myPanel.add(new JLabel("Name:"));
				myPanel.add(userName);
				myPanel.add(Box.createHorizontalStrut(50));
				myPanel.add(new JLabel("Password:"));
				myPanel.add(Password);
				    
				int result = JOptionPane.showConfirmDialog(null, myPanel,"Play", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
				    
				if (result == JOptionPane.OK_OPTION) 
				{
					UserName = userName.getText();
					PassWord = Password.getText();		
					String line;
					boolean nameFlag = false;
					boolean passwordFlag = false;
					boolean Authentication = false;
					int counter = 0; 
										
					try {
						BufferedReader reader = new BufferedReader(new FileReader("scores.txt"));
							
						while((line = reader.readLine()) != null) {
							counter ++;
										
							if ((counter == 1) && (line.equals(UserName)))
								nameFlag = true;
				                	
				            else if ((counter == 2) && (line.equals(PassWord)))
				                passwordFlag = true;
				                
				            else if (counter == 3){
								counter = 0;	
								nameFlag = false;
								passwordFlag = false;
							}
				                
				            if ((nameFlag == true) && (passwordFlag == true)){
			                	enemySpawn = 3;	                
				                PlayerScore = reader.readLine();
				                GameFrame gameFrame = new GameFrame();
				                Authentication = true;
								loginFrame.setVisible(false);
				            }	
						}
						if (!Authentication)
							JOptionPane.showMessageDialog(null, "register first","Error",JOptionPane.PLAIN_MESSAGE);
						reader.close();
					}				        
					catch (IOException e){ 
						System.out.println("No File");
					}
				}	     
			}	
		});
	
		scoreboard.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent c)
			{				
				String line, str="";
				int counter=1;
				try {
					BufferedReader rd = new BufferedReader(new FileReader("scores.txt"));
						
					while((line = rd.readLine()) != null) {
						counter ++;
						if (counter % 3 != 0)
			                str+= line+"\n"; 
					}
					rd.close();
					JOptionPane.showMessageDialog(null, str,"Score",JOptionPane.PLAIN_MESSAGE);	
				}
				catch (IOException e) {
					System.out.println("No File");
				}
			}	
		});
	
		exit.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent d){
				System.exit(0);
			}	
		});

	}
		@Override
		public void actionPerformed(ActionEvent e) 
		{}
}