import java.awt.Rectangle;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Enemy extends Planes implements Runnable {
	
	private static Thread thread;

	public Enemy(int x, int y){
		 
		this.x = x;
		this.y= y;
		this.unitImage = new ImageIcon(getClass().getResource("enemyPlane.png"));
		this.unitLabel = new JLabel(unitImage);
		this.unitLabel.setBounds(x, y, unitImage.getIconWidth(), unitImage.getIconHeight());	
		this.setIsAlive(true);
		
		GameFrame.background.add(this.unitLabel);
		
		thread=new Thread(this);
		thread.start();
	}
	
	@Override
	public void run() {
		while (GameFrame.running && this.getIsAlive())
		{
			try{
				Thread.sleep(50);
				move();
				int fireChance = (int) (Math.random() * 50);		
				if (fireChance == 0)
					shoot();	
			}
			catch(InterruptedException exception)
			{} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void move() throws InterruptedException, IOException {
		if (this.y > 800)
		{
			this.setIsAlive(false);
			this.unitLabel.setIcon(null);
			GameFrame.enemyList.remove(this);
			GameFrame.enemyNumber--;
		}
		else
		{
			this.y += 4;
									
			this.unitLabel.setBounds(x, y, this.unitImage.getIconWidth(), this.unitImage.getIconHeight());
			checkForBodyCollisionWithPlayer();	
		}
	}

	private void checkForBodyCollisionWithPlayer() throws InterruptedException, IOException 
	{
		Player player = GameFrame.player;
		Rectangle playerRectangle = new Rectangle(player.x,player.y,player.unitImage.getIconWidth(),player.unitImage.getIconHeight());	
		Rectangle enemyRectangle = new Rectangle(this.x,this.y,this.unitImage.getIconWidth(),this.unitImage.getIconHeight());

		if (enemyRectangle.intersects(playerRectangle))
		{
			GameFrame.player.healthPoint--;
			GameFrame.updateLivesLeft();
			GameFrame.enemyList.remove(this);	
			GameFrame.enemyNumber--;
			
			this.setIsAlive(false);
			this.unitLabel.setIcon(null);
					
		}
	}
}