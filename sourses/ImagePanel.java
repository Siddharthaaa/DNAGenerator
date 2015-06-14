import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;


class ImagePanel extends JPanel{
	
	private BufferedImage img = null;
	
	public  ImagePanel() {
		img = null;
	}
	
	public ImagePanel(BufferedImage i){
		img = i;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		g.drawImage(img, 0, 0, g.getClipBounds().width, g.getClipBounds().height, null);
	
	}
}
