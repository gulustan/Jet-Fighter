import java.awt.Graphics;
import java.awt.Image;
import java.awt.LayoutManager;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Background extends JPanel{

	private Image icon;
	int x=0, y=0;
	
	public Background(LayoutManager layout)
	{
		super.setLayout(layout);
	}
	
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		icon = new ImageIcon(getClass().getResource("background.png")).getImage();
		g.drawImage(icon, x, y, icon.getWidth(null), icon.getHeight(null), null);
	}
}
