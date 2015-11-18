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
	private int xScale = 1;
	private int yScale = 1;
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
		this.xPoints[nPoints] = x * this.xScale;
		this.yPoints[nPoints] = y * this.yScale;
		this.nPoints++;
	}
	public void setScale(int xScale, int yScale)
	{
		this.xScale = xScale;
		this.yScale = yScale;
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
		//g.drawPolyline(xPoints, yPoints, nPoints); 
		if(Window.shouldDraw){

			int xPos1;
			int yPos1;
			int xPos2;
			int yPos2;

			System.out.println("Running Paint");
			System.out.println(Window.nodes);
			for(int i =0;i< Window.nodes.size()-1;i++){



				xPos1 = Window.nodes.get(i).xPos*this.xScale + (this.xScale / 2);
				yPos1 = Window.nodes.get(i).yPos*this.yScale + (this.xScale / 2);
				xPos2 = Window.nodes.get(i+1).xPos*this.xScale + (this.xScale / 2);
				yPos2 = Window.nodes.get(i+1).yPos*this.yScale + (this.xScale / 2);
				g.setColor(Color.red);
				g.drawLine(xPos1, yPos1, xPos2, yPos2);
			}
		}

		Window.shouldDraw = false;


	}
}