package ass08.gameoflife;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class GameOfLifePanel extends JPanel {

	private BufferedImage image;
	
	public GameOfLifePanel(int w, int h){
		this.image = new BufferedImage(w,h,BufferedImage.TYPE_INT_RGB);
	}

	public void updateImage(int[] rgbData){
		int w = image.getWidth();
		int h = image.getHeight();
        image.setRGB(0, 0, w, h, rgbData, 0, w);
        repaint();
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);   
        Graphics2D g2 = (Graphics2D)g;
        g2.drawImage(image, 0, 0, null);  
	}

}
