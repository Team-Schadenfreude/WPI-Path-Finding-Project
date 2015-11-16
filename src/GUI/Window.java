package GUI;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JButton;
import javax.swing.UIManager;

import static org.junit.Assert.assertEquals;

import java.awt.BorderLayout;

import AStar.Node;
import AStar.main_runner;
import java.awt.Canvas;
import java.awt.GridLayout;
import javax.swing.JLabel;
import java.awt.FlowLayout;
import javax.swing.JTextField;;

public class Window {

	private JFrame frame;
	private JTextField txtStartX;
	private JTextField txtStartY;
	private JTextField txtEndX;
	private JTextField txtEndY;

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
		
		JButton btnNewButton = new JButton("Load Map");
		btnNewButton.setBounds(12, 5, 94, 25);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		frame.getContentPane().setLayout(null);
		frame.getContentPane().add(btnNewButton);
		
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
		
		Canvas canvas = new Canvas();
		canvas.setBounds(339, 46, 0, 0);
		frame.getContentPane().add(canvas);
		
		JButton btnSwap = new JButton("Swap");
		btnSwap.setBounds(360, 5, 65, 25);
		frame.getContentPane().add(btnSwap);

	}
}
