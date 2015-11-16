package GUI;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
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
import AStar.main_runner;


import java.awt.Canvas;
import java.awt.GridLayout;
import javax.swing.JLabel;
import java.awt.FlowLayout;
import javax.swing.JTextField;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class Window {

	private JFrame frame;
	static BufferedImage img;
	private JTextField txtStartX;
	private JTextField txtStartY;
	private JTextField txtEndX;
	private JTextField txtEndY;
	private String path;

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
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Canvas canvas = new Canvas();
		canvas.setBounds(0, 63, 434, 198);
		frame.getContentPane().add(canvas);
		
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
						frame.setSize(img.getWidth(),img.getHeight()+63);
						canvas.setSize(img.getWidth(),img.getHeight());
					} catch (IOException ex) {
						
					}
				}
			}
		});
		frame.getContentPane().setLayout(null);
		frame.getContentPane().add(btnLoadMap);
		
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
	         public void actionPerformed(ActionEvent e) {
	        	String nodePath = "src/res/Alonso_Node_Map.csv";
	     		String edgePath = "src/res/Alonso_Edge_Map.csv";
	     		List<Node> map = main_runner.readMap(nodePath, edgePath);
	     		List<Node> path = main_runner.getPathFromNode(map.get(3), map.get(1));
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
		
		JLabel label = new JLabel("");
		label.setBounds(334, 46, 0, 0);
		frame.getContentPane().add(label);
		
		JButton btnSwap = new JButton("Swap");
		btnSwap.setBounds(360, 5, 65, 25);
		frame.getContentPane().add(btnSwap);
		

	}
}


