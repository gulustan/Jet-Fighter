import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class GameFrame implements KeyListener,Runnable{

	static Icon playerJet = new ImageIcon(GameFrame.class.getResource("PlayerImage.png"));
	
	private Icon livesLeftImage = new ImageIcon(getClass().getResource("heart.png"));
	
	public static JLabel livesLabel1, livesLabel2, livesLabel3;
	
	private int enemySpawnChance, enemySpawnxCordinate;
	
	static ArrayList <Bullet> bulletList = new ArrayList<Bullet>();
	static ArrayList <Planes> enemyList = new ArrayList<Planes>();
	
	static Background background;
	static Player player;
	
	static int enemyNumber=0, playerScore=0, lives=3;
	
	static boolean running=true;
	private boolean isLeft, isRight, isUp, isDown, gameStart=true;
	
	private Thread thread;

	public GameFrame(){
		
		background = new Background(null);	
			
		livesLabel1 = new JLabel(livesLeftImage);
		background.add(livesLabel1);
		livesLabel1.setBounds(4, 10, livesLeftImage.getIconWidth(), livesLeftImage.getIconHeight());

		livesLabel2 = new JLabel(livesLeftImage);
		background.add(livesLabel2);
		livesLabel2.setBounds(58, 10, livesLeftImage.getIconWidth(), livesLeftImage.getIconHeight());
		
		livesLabel3 = new JLabel(livesLeftImage);
		background.add(livesLabel3);
		livesLabel3.setBounds(112, 10, livesLeftImage.getIconWidth(), livesLeftImage.getIconHeight());
	
		JFrame gameScene = new JFrame("Jet Fighter Game");
		
		gameScene.add(background);
		gameScene.setVisible(true);
		gameScene.setSize(760, 800);
		gameScene.setResizable(false);
		gameScene.addKeyListener(this);
		gameScene.setLocationRelativeTo(null);
		gameScene.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		player = new Player();
		
		thread = new Thread(this);
		thread.start();	
	}
	
	public void playerJetMovement() {		
		if (isLeft){
			if (player.x < 9)
				player.x= 3;
			player.x -= 8;
			player.unitLabel.setIcon(player.planeImage);
		}
		if (isRight){
			if (player.x > 696)
				player.x = 702;
			player.x += 8;
			player.unitLabel.setIcon(player.planeImage);
		}
		if (isUp){
			if (player.y < 78)
				player.y = 70;
			player.y -= 8;
			player.unitLabel.setIcon(player.planeImage);
		}
		if (isDown){
			if (player.y > 640)
				player.y = 646;
			player.y += 8;
			player.unitLabel.setIcon(player.planeImage);
		}	
		player.unitLabel.setBounds(player.x, player.y, player.planeImage.getIconWidth(), player.planeImage.getIconHeight());
	}
	
	public void keyPressed(KeyEvent event) {
		int key = event.getKeyCode();	
		if(key == KeyEvent.VK_LEFT && !gameStart)
			isLeft = true;
		if(key == KeyEvent.VK_RIGHT && !gameStart)
			isRight = true;
		if(key == KeyEvent.VK_UP && !gameStart)
			isUp = true;	
		if(key == KeyEvent.VK_DOWN && !gameStart)
			isDown = true;		
		playerJetMovement();
	}

	public void keyReleased(KeyEvent event) {
		int key = event.getKeyCode();		
		if(key == KeyEvent.VK_LEFT)
			isLeft = false;
		if(key == KeyEvent.VK_RIGHT)
			isRight = false;
		if(key == KeyEvent.VK_UP)
			isUp = false;
		if(key == KeyEvent.VK_DOWN)
			isDown = false;
		if((key == KeyEvent.VK_SPACE))
			player.shoot();
		player.unitLabel.setIcon(player.planeImage);
	}

	public void keyTyped(KeyEvent event) 
	{}

	@Override
	public void run() {			 
			while (running)
			{			
				playerJetMovement();
				
				gameStart=false;
				enemySpawnxCordinate = (int) (Math.random() * 500);
				enemySpawnChance = (int) (Math.random() * 1000);
				try {		
					Thread.sleep(30);			
					if(enemyNumber < MenuFrame.enemySpawn){ 
						if (enemySpawnChance < 8){
							Enemy enemyJet = new Enemy(enemySpawnxCordinate, 0);
							enemyList.add(enemyJet);
							enemyNumber++;
						}
					}		
 				}
				catch(InterruptedException e)
				{}
		}
	}
	
	public static void updateLivesLeft() throws InterruptedException, IOException{
		lives--;
		if(lives==2)
			livesLabel3.setIcon(null);
		else if(lives==1)
			livesLabel2.setIcon(null);
		else if(lives==0)
			livesLabel1.setIcon(null);
		else if(lives < 0){
			running = false;
			JOptionPane.showMessageDialog(null, "Your Score is : "+playerScore,"Credits",JOptionPane.PLAIN_MESSAGE);	
			writeScore();
			System.exit(0);	
		}
	}
		
	public static void writeScore() throws IOException{
		Path path = Paths.get("scores.txt");
		Charset charset = StandardCharsets.UTF_8;

		String content = new String(Files.readAllBytes(path), charset);
		String willChange,willReplace;
		willChange = MenuFrame.UserName+"\r\n"+MenuFrame.PassWord+"\r\n"+MenuFrame.PlayerScore;
		willReplace = MenuFrame.UserName+"\r\n"+MenuFrame.PassWord+"\r\n"+playerScore;
		content = content.replace(willChange,willReplace );
		Files.write(path, content.getBytes(charset));
	}
}