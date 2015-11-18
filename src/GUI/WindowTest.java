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

import javax.swing.JFrame;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.UIManager;
import AStar.Node;
import AStar.Main;
import javax.swing.JTextField;
import java.awt.image.BufferedImage;



public class WindowTest {

	private JFrame frame;
	static BufferedImage img;
	private JTextField txtStartX;
	private JTextField txtStartY;
	private JTextField txtEndX;
	private JTextField txtEndY;
	private String path;
	private LinePanel linePanel;
	private List<Node> nodeList = new ArrayList<Node>();
	List<String> listOfNodes = new ArrayList<String>();

	private int xScale = 1;
	private int yScale = 1;
	static boolean shouldDraw = false;
	static List<Node> nodes;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		System.out.println("Yo we starting");
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
					WindowTest window = new WindowTest();
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
	public WindowTest() {
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
		frame.setTitle("Randy");
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
						frame.setSize(img.getWidth()+8,img.getHeight()+93);
						linePanel.addImage(img);
						linePanel.repaint();

					} catch (IOException ex) {

					}
					List<Integer> scales = Main.getScaleFromFile(path + "mapScale.csv");
					xScale = scales.get(0);
					yScale = scales.get(1);
					linePanel.setScale(xScale, yScale);

					nodeList = Main.readMap(path + "mapNodes.csv", path + "mapEdges.csv");

					for(Node n: nodeList)
					{
						String nodeDialog = n.nodeName+", "+n.xPos+", "+n.yPos;
						listOfNodes.add(nodeDialog);

					}
					NodeBox nodeBox = new NodeBox(listOfNodes);



					linePanel.repaint();
				}
			}
		});
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
		button.setBounds(360, 34, 65, 25);
		frame.getContentPane().add(button);





		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				boolean validInputs = true;
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
					validInputs = false;
				}
				if (validInputs)
				{
					if (nodeList.isEmpty())
					{
						System.out.println("Must Load a Map First");
					}
					else
					{


						Node startNode = Main.findNodeByXY(nodeList, startX, startY);
						Node endNode = Main.findNodeByXY(nodeList, endX, endY);
						if(startNode == null || endNode == null)
						{
							System.out.println("Those Nodes do not exist");
						}
						else
						{
							System.out.println("*************");
							System.out.println(startNode);
							System.out.println(endNode);
							System.out.println("*************");

							nodes = Main.getPathFromNode(startNode, endNode, nodeList);
							if (nodes.isEmpty())
							{
								System.out.println("There is no path");
							}
							else
							{

								//for(int i = 0; i < nodes.size(); i++) {
								//	linePanel.addPoint(nodes.get(i).xPos, nodes.get(i).yPos);
								//}
								linePanel.repaint();
								System.out.println("A* Complete");
								List<String> directions = Main.getDirectionsList(nodes);
								for(Node n: nodes)
								{
									System.out.println(n.xPos + ", " + n.yPos);


								}
								for(String s: directions)
								{
									System.out.println(s);
								}
								nodeList = Main.readMap(path + "mapNodes.csv", path + "mapEdges.csv");
								shouldDraw = true;
								DirectionsBox directionWindow = new DirectionsBox(directions);
								linePanel.repaint();
							}
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


