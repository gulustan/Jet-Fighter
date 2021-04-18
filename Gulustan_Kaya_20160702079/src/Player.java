import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Player extends Planes{

	Icon planeImage;
	
	public Player()	{
	
		planeImage =new ImageIcon(getClass().getResource("PlayerImage.png"));
		
		this.unitImage = planeImage;		
		this.x = 350;
		this.y= 600;
		this.healthPoint = 3;
		this.unitLabel = new JLabel(planeImage);

		unitLabel.setBounds(350, 600, planeImage.getIconWidth(), planeImage.getIconHeight());
		GameFrame.background.add(this.unitLabel);
	}

	@Override
	public void run() {
	}
}