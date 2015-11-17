package GUI;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.UIManager;

import static org.junit.Assert.assertEquals;

import java.awt.BorderLayout;

import AStar.Node;
import AStar.Main;


import java.awt.Canvas;
import java.awt.GridLayout;
import javax.swing.JLabel;
import java.awt.FlowLayout;
import javax.swing.JTextField;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.Color;
import javax.swing.JTextArea;
import java.awt.SystemColor;
import javax.swing.DropMode;
import javax.swing.JScrollBar;

public class Window {

	private JFrame frame;
	static BufferedImage img;
	private JTextField txtStartX;
	private JTextField txtStartY;
	private JTextField txtEndX;
	private JTextField txtEndY;
	private String path;
	private LinePanel linePanel;
	private List<Node> nodeList = new ArrayList<Node>();
	private Main main = new Main();
	private JScrollPane scroll;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
					Window window = new Window();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Create the application.
	 */
	public Window() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 50, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.getContentPane().setLayout(null);

		linePanel = new LinePanel();
		linePanel.setBounds(0, 63, 444, 208);
		frame.getContentPane().add(linePanel);
		
		JButton btnLoadMap = new JButton("Load Map");
		btnLoadMap.setBounds(12, 5, 94, 25);
		btnLoadMap.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				
				
				int returnValue = fileChooser.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fileChooser.getSelectedFile();
					path = selectedFile.getAbsolutePath();
					path = path+"\\";
					try {
						img = ImageIO.read(new File(Paths.get(path+"map.png").toString()));
						System.out.println(path);
						//contentPane.add(lblMap);
						frame.setSize(img.getWidth()+8,img.getHeight()+93);

						/*ImagePanel imgPanel = new ImagePanel(img);
						imgPanel.setBounds(0, 63, 434, 199);
						frame.getContentPane().add(imgPanel);
						frame.setVisible(true);*/
						//picLabel.setIcon(new ImageIcon(img));
						//picLabel.setSize(img.getWidth(),img.getHeight());
						//frame.getContentPane().add(picLabel);
						linePanel.addImage(img);
						//linePanel.addPoint(100, 100);
						//linePanel.addPoint(150, 100);
						linePanel.repaint();

					} catch (IOException ex) {

					}
					nodeList = Main.readMap(path + "mapNodes.csv", path + "mapEdges.csv");
				}
			}
		});
		frame.getContentPane().add(btnLoadMap);
		

		//linePanel.addPoint(100, 100);
		//linePanel.addPoint(150, 100);

		//frame.getContentPane().add(txtStartX);
		
		txtStartX = new JTextField();
		txtStartX.setBounds(111, 6, 116, 22);
		txtStartX.setText("Start X");
		frame.getContentPane().add(txtStartX);
		txtStartX.setColumns(10);
		
		txtStartY = new JTextField();
		txtStartY.setBounds(232, 6, 116, 22);
		txtStartY.setText("Start Y");
		txtStartY.setColumns(10);
		frame.getContentPane().add(txtStartY);
		
		JButton button = new JButton("Run");
		button.setBounds(360, 34, 55, 25);
		frame.getContentPane().add(button);
		button.addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent ae) {
	        	 boolean goodToGo = true;
	        	 int startX = 0;
	        	 int startY = 0;
	        	 int endX = 0;
	        	 int endY = 0;
	        	 // Try parsing int values from the coordinate text fields
	        	 try {
	        		 startX = Integer.parseInt(txtStartX.getText());
	        		 startY = Integer.parseInt(txtStartY.getText());
	        		 endX = Integer.parseInt(txtEndX.getText());
	        		 endY = Integer.parseInt(txtEndY.getText());
	        	 }
	        	 catch (NumberFormatException e){
	        		 goodToGo = false;
	        	 }
	        	 if (goodToGo)
	        	 {
	        		 if (nodeList.isEmpty())
	        		 {
	 					System.out.println("Must Load a Map First");
	        		 }
	        		 else
	        		 {
	        			 Node startNode = Main.findNodeByXY(nodeList, startX, startY);
	        			 Node endNode = Main.findNodeByXY(nodeList, endX, endY);
	        			 List<Node> nodes = Main.getPathFromNode(startNode, endNode);
	        			 Iterator<Node> nodePath = nodes.iterator();
	        			 Node nodeHolder;
	        			 for(int i = 0; i < nodes.size(); i++) {
	        				 nodeHolder = nodePath.next();
	        				 linePanel.addPoint(nodeHolder.xPos, nodeHolder.yPos);
	        			 }
	        			 System.out.println("A* Complete");
	        			 List<String> directions = Main.getDirectionsList(nodes);
	        			 System.out.println(nodes.size());
	        			 for(Node n: nodes)
	        			 {
	        				 System.out.println(n.xPos + ", " + n.yPos);
	        			 }
	        			 for(String s: directions)
	        			 {
	        				 System.out.println(s);
	        			 }
	        		 }
	        	 }
	        	 else
	        	 {
	        		 System.out.println("Must Enter Valid Start/End Node Coordinates");
	        	 }
	          }          
	       });
		
		txtEndX = new JTextField();
		txtEndX.setBounds(111, 35, 116, 22);
		txtEndX.setText("End X");
		txtEndX.setColumns(10);
		frame.getContentPane().add(txtEndX);
		
		txtEndY = new JTextField();
		txtEndY.setBounds(232, 35, 116, 22);
		txtEndY.setText("End Y");
		txtEndY.setColumns(10);
		frame.getContentPane().add(txtEndY);
		
		JButton btnSwap = new JButton("Swap");
		btnSwap.setBounds(360, 5, 65, 25);
		frame.getContentPane().add(btnSwap);
		btnSwap.addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent ae) {
	        	 String temp;
	        	 temp = txtStartX.getText();
	        	 if (!txtEndX.getText().equals("End X")) {
	        		 txtStartX.setText(txtEndX.getText());
	        	 }
	        	 else {
	        		 txtStartX.setText("Start X");
	        	 }
	        	 
	        	 if (!temp.equals("Start X")) {
	        		 txtEndX.setText(temp);
	        	 }
	        	 else {
	        		 txtEndX.setText("End X");
	        	 }
	        	 
	        	 temp = txtStartY.getText();
	        	 if (!txtEndY.getText().equals("End Y")) {
	        		 txtStartY.setText(txtEndY.getText());
	        	 }
	        	 else {
	        		 txtStartY.setText("Start Y");
	        	 }
	        	 if (!temp.equals("Start Y")) {
	        		 txtEndY.setText(temp);
	        	 }
	        	 else {
	        		 txtEndY.setText("End Y");
	        	 }
	          }          
	       });

	}
}


