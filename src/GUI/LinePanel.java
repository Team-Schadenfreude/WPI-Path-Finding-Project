package GUI;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.Color;

import javax.swing.JPanel;

public class LinePanel extends JPanel {

	private static final int LINE_WIDTH = 5;
	
	private int[] xPoints;
	private int[] yPoints;
	private int nPoints;
	private BufferedImage img;
	private boolean imageLoaded;
	
	
	public LinePanel() {
		this.xPoints = new int[100];
		this.yPoints = new int[100];
		this.nPoints = 0;
		this.imageLoaded = false;
		
	}
	public void addImage(BufferedImage img){
		this.img = img;
		this.imageLoaded = true;
	}
	
	public void addPoint(int x, int y) {
		this.xPoints[nPoints] = x;
		this.yPoints[nPoints] = y;
		this.nPoints++;
	}

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(imageLoaded){
        	this.setSize(img.getWidth(), img.getHeight());
        	g.drawImage(img, 0, 0, img.getWidth(), img.getHeight(), this);
        }
        g.setColor(Color.RED);
        //for(int i = 0; i < nPoints; i++){
        //	g.fillPolygon(new int[]{xpoints}, yPoints, i);
        //}
        g.drawPolyline(xPoints, yPoints, nPoints); 
    }
}