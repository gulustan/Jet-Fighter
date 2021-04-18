import javax.swing.Icon;
import javax.swing.JLabel;

public abstract class Planes implements Runnable
{ 
	Icon unitImage;
	JLabel unitLabel;
	
	int x, y, healthPoint;

	private boolean isAlive;

	public void shoot(){
		Bullet bullet = new Bullet(this.x, this.y, this);
	}
	
	public boolean getIsAlive() {
		return this.isAlive;
	}
	
	public void setIsAlive(boolean alive) {
		this.isAlive = alive;
	}

}
