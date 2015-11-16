package GUI;

import java.awt.Graphics;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

import javax.swing.JPanel;

public class LinePanel extends JPanel {
	
	private int[] xPoints;
	
	private int[] yPoints;
	
	private int nPoints;
	
	public LinePanel() {
		this.xPoints = new int[100];
		this.yPoints = new int[100];
		this.nPoints = 0;
	}
	
	public void addPoint(int x, int y) {
		this.xPoints[nPoints] = x;
		this.yPoints[nPoints] = y;
		this.nPoints++;
	}

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawPolyline(xPoints, yPoints, nPoints);  
    }
}
/*
public class ImagePanel extends JPanel{

    private BufferedImage image;

    public ImagePanel() {
       try {                
          image = ImageIO.read(new File("image name and path"));
       } catch (IOException ex) {
            // handle exception...
       }
    }


}*/