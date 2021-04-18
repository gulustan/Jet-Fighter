import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Rectangle;
import java.io.IOException;
import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Bullet implements Runnable {
	
	int xBullet, yBullet;

	Icon bulletImage;
	JLabel bulletLabel;
	Planes firingUnit;

	boolean bulletHit=false;
	
	private static Thread thread;
	
	public Bullet(int xCoordinate, int yCoordinate, Planes shooter) {
				
		this.firingUnit = shooter;
		
		if (shooter instanceof Enemy)
		{
			this.bulletImage = new ImageIcon(getClass().getResource("EnemyMissile.png"));
			this.bulletLabel = new JLabel(bulletImage);
			this.xBullet = xCoordinate + 28;
			this.yBullet = yCoordinate + this.firingUnit.unitImage.getIconHeight() + 10;
		}
		else if (shooter instanceof Player)
		{
				this.bulletImage = new ImageIcon(getClass().getResource("PlayerMissile.png"));
				this.bulletLabel = new JLabel(bulletImage);
				this.xBullet = xCoordinate +18;
				this.yBullet = yCoordinate - 30;	
			
		}
		GameFrame.background.add(bulletLabel);
		GameFrame.bulletList.add(this);
		
		thread=new Thread(this);
		thread.start();
	}
	
	public int getyBullet(){
		return this.yBullet;
	}
	
	public int getxBullet(){
		return this.xBullet;
	}
	
	@Override
	public void run()
	{
		while (GameFrame.running && !this.bulletHit)
		{
			try{
				Thread.sleep(50);
				move();
			}
			catch(InterruptedException exception)
			{} 
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void move() throws InterruptedException, IOException 
	{ 	
		if (this.firingUnit instanceof Player)
		{
			if (this.yBullet < 0)
			{
				this.bulletLabel.setIcon(null);
				this.bulletHit = true;
				GameFrame.bulletList.remove(this);
				
			}
			else{
				this.yBullet -=  10;
				checkForEnemyCollision();
			}
		}
		else
		{	
			if (this.yBullet > 800)
			{
				this.bulletLabel.setIcon(null);
				this.bulletHit = true;
				GameFrame.bulletList.remove(this);
				
			}
			else{
				this.yBullet += 10;
				checkForPlayerCollision();
			}
		}
		this.bulletLabel.setBounds(this.xBullet, this.yBullet, bulletImage.getIconWidth(), bulletImage.getIconHeight());
	}
	
	private void checkForPlayerCollision() throws InterruptedException, IOException	
	{
		Player player = GameFrame.player;
		
		for(int i = 0; i < GameFrame.bulletList.size(); i++)
		{
			Bullet bullet = GameFrame.bulletList.get(i);	
			Rectangle bulletRectangle = new Rectangle(bullet.xBullet, bullet.yBullet, bullet.bulletImage.getIconWidth(), bullet.bulletImage.getIconHeight());
			Rectangle playerRectangle = new Rectangle(player.x, player.y, player.unitImage.getIconWidth(), player.unitImage.getIconHeight());
				
			if (bulletRectangle.intersects(playerRectangle))
			{
				this.bulletHit = true;
				GameFrame.bulletList.remove(i);
				this.bulletLabel.setIcon(null);
				GameFrame.player.healthPoint--;
				GameFrame.updateLivesLeft();
				
				break;	
			}
		}
	}
	
	private void checkForEnemyCollision() throws InterruptedException 
	{		
		Rectangle bulletRectangle = new Rectangle(this.xBullet, this.yBullet, this.bulletImage.getIconWidth(), this.bulletImage.getIconHeight());
		for(int i = 0; i < GameFrame.enemyList.size(); i++)
		{
			Planes enemy = GameFrame.enemyList.get(i);
			Rectangle enemyRectangle = new Rectangle(enemy.x, enemy.y,enemy.unitImage.getIconWidth(), enemy.unitImage.getIconWidth());
			
			if (bulletRectangle.intersects(enemyRectangle))
			{
				enemy.setIsAlive(false);
				GameFrame.enemyList.remove(enemy);
				GameFrame.enemyNumber--;
				
				this.bulletHit = true;	
				this.bulletLabel.setIcon(null);
				GameFrame.bulletList.remove(this);

				enemy.unitLabel.setIcon(null);						
				GameFrame.playerScore += 100;
				break;
			}	
		}
	}
}