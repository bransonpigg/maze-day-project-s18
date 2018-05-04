import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import sun.audio.*;
import javax.swing.*;

/*
 * Accessible Maze Game for Visually-Impaired Players
 * Author: Branson Pigg
 * Adapted from xPlatformer's "Maze Game in Java" on Youtube
 * 
 * 
 * 
 * Forms the maze to be played. Creates a standard 2-dimensional array using integers 0, 1, and 2 to determine which block
 * in the maze is which. The array starts as all 0s (white blocks), but this can be altered using the MazeMapMaker class. The loadMap
 * method loads in a previously created .map file and sets each value in the array to its corresponding value in the file. This class
 * also contains the main method which runs the main menu, allowing for the game to be played.
 */

public class Maze extends JFrame {

	public static int rows = 20;
	public static int columns = 20;
	public static int panelSize = 25;
	public static int map[][] = new int[columns][rows];
	public static int endLevelLocation;
	Player p;
	
	public Maze(String str) {
		loadMap(str);
		this.setResizable(false);
		this.setSize((columns*panelSize) + 50, (rows*panelSize) +70);
		this.setTitle("Maze Day Maze!");
		this.setLayout(null);
		
		this.addKeyListener(new KeyListener() {
			
			@Override
			public void keyPressed(KeyEvent e) {
				int key = e.getKeyCode();
				
				revalidate();
				repaint();
				
				// Takes key press and determines player movement
				if (key == KeyEvent.VK_UP) {
					p.moveUp();
				}
				if (key == KeyEvent.VK_DOWN) {
					p.moveDown();
				}
				if (key == KeyEvent.VK_LEFT) {
					p.moveLeft();
				}
				if (key == KeyEvent.VK_RIGHT) {
					p.moveRight();
				}
				if (p.x == columns-1 && p.y == endLevelLocation) {
					try {
						InputStream in = new FileInputStream("src/cheer.wav");
						AudioStream as = new AudioStream(in);
						AudioPlayer.player.start(as);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					JOptionPane.showMessageDialog(null, "Congrats! You've completed this level!",
							"End Game", JOptionPane.INFORMATION_MESSAGE);
					dispose();
					new MainMenu();
				}
			}
			
			@Override
			public void keyReleased(KeyEvent arg) {
				
			}
			
			@Override
			public void keyTyped(KeyEvent arg) {
				
			}
		});
		
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
		this.setLocationRelativeTo(null);
		
		p = new Player(); // creating new Player object
		p.setVisible(true);
		this.add(p);
		
		// Color of maze map
		for (int y = 0; y < columns; y++) {
			for (int x = 0; x < rows; x++) {
				Tile tile = new Tile(x,y);
				tile.setSize(panelSize, panelSize);
				tile.setLocation((x*panelSize) + 23, (y*panelSize) + 25);
				if (map[x][y] == 0) {
					tile.setBackground(Color.GRAY);
				}
				else if (map[x][y] == 1) {
					tile.setBackground(Color.WHITE);
					tile.setWall(false);
					if (x == 0) {
						p.setLocation((x*panelSize)+23, (y*panelSize)+25);
						p.y = y;
					}
					if (x == columns - 1) {
						endLevelLocation = y;
					}
				}
				else if (map[x][y] == 2) { // "enemy"/question block
					tile.setBackground(Color.RED);
					
				}
				
				tile.setVisible(true);
				this.add(tile);
			}
		}
		
		this.setVisible(true);
	}
	
	public static void main(String args[]) {
		new MainMenu();
	}
	
	public void loadMap(String str) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(str));
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();
			
			while (line != null) {
				sb.append(line);
				sb.append(System.lineSeparator());
				line= br.readLine();
			}
			String mapStr = sb.toString();
			
			int counter = 0;
			for (int y = 0; y < columns; y++) {
				for (int x = 0; x < columns; x++) {
					String mapChar = mapStr.substring(counter, counter+1);
					if (!mapChar.equals("\r\n") && !mapChar.equals("\n") && 
							!mapChar.equals("\r")) {
						map[x][y] = Integer.parseInt(mapChar);
					}
					else {
						x--;
						System.out.println(mapChar);
					}
					counter++;
				}
			}
		} catch (Exception e) {
			System.out.println("Unable to load map, creating new map file.");
		}
	}
}
