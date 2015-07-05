import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

/** 
*Ein Hilfspanel für die Anzeige von Bildern 
*
*@author Abel Hodelin Hernandez
*@author Timur Horn
*@version 1.0
*/


class ImagePanel extends JPanel{
	
	private BufferedImage img = null;
	
	public  ImagePanel() {
		img = null;
	}
	
	public ImagePanel(BufferedImage i){
		img = i;
	}
	
	public void setImage(BufferedImage bi){
		img = bi;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(img, 0, 0, g.getClipBounds().width, g.getClipBounds().height, null);
	
	}
}
