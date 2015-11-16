package GUI;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JButton;

import static org.junit.Assert.assertEquals;

import java.awt.BorderLayout;

import AStar.Node;
import AStar.main_runner;
import java.awt.Canvas;;

public class Window {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
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
		
		JButton button = new JButton("Run");
		frame.getContentPane().add(button, BorderLayout.NORTH);
		
		Canvas canvas = new Canvas();
		frame.getContentPane().add(canvas, BorderLayout.CENTER);
		
		button.addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent e) {
	        	String nodePath = "src/res/Alonso_Node_Map.csv";
	     		String edgePath = "src/res/Alonso_Edge_Map.csv";
	     		List<Node> map = main_runner.readMap(nodePath, edgePath);
	     		List<Node> path = main_runner.getPathFromNode(map.get(3), map.get(1));
	     		// System.out.println(path);
	     		List<Node> bestPath = new ArrayList<Node>();
	     		bestPath.add(map.get(3));
	     		bestPath.add(map.get(2));
	     		bestPath.add(map.get(1));
	     		System.out.println("Done");
	          }          
	       });

	}

}
