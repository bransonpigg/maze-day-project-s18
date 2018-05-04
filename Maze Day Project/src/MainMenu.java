import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import sun.audio.*;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

/* 
 * Creates main menu for the maze game. Displays a set maze graphic, has buttons for starting a game,
 * creating a new map/level/maze, and exiting the program, and has a drop-down box for selecting any
 * maps/levels created already by the user.
 */

public class MainMenu {

	JFrame Menu = new JFrame("Maze");
	JButton Start = new JButton("Play");
	JButton Exit = new JButton("Exit");
	JButton MapMaker = new JButton("Map Creator");
	ImageIcon picture = new ImageIcon("res/Images/MazePicture.png");
	JLabel imageLabel = new JLabel(picture);
	ArrayList<String> mapList = new ArrayList<String>();
	JComboBox<String> lvlList;
	int menuWidth = 100;
	int menuHeight = 30;
	int menuY = 460;
	int WIDTH = 490;
	int HEIGHT = 530;
	
	public MainMenu() {
		getMapList();
		lvlList = new JComboBox<String>(mapList.toArray(new String[mapList.size()]));
		
		// variables for the Menu
		Menu.setResizable(false);
		Menu.setSize(WIDTH, HEIGHT);
		Menu.setLayout(null);
		Menu.setLocationRelativeTo(null);
		Menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// variables for Start button
		Start.setSize(menuWidth, menuHeight);
		Start.setLocation(10, menuY);
		Menu.add(Start);
		Start.addActionListener(new ActionListener () {
			
			@Override
			public void actionPerformed(ActionEvent arg) {
				new Maze(lvlList.getSelectedItem().toString());
				Menu.setVisible(false);
			}
		});
		
		// variables for Map Creator button
		MapMaker.setSize(menuWidth, menuHeight);
		MapMaker.setLocation(120, menuY);
		Menu.add(MapMaker);
		MapMaker.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new MazeMapMaker();
				Menu.setVisible(false);
			}
		});
		
		//Level selection
		lvlList.setSize(menuWidth+35, menuHeight);
		lvlList.setLocation(230, menuY);
		Menu.add(lvlList);
		
		//variables for Exit button
		Exit.setSize(menuWidth, menuHeight);
		Exit.setLocation(375, menuY);
		Menu.add(Exit);
		Exit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		// Set up picture in menu
		imageLabel.setBounds((WIDTH-412)/2, 25, 412, 412);
		imageLabel.setVisible(true);
		Menu.add(imageLabel);
		Menu.setVisible(true);
		Voice voice = VoiceManager.getInstance().getVoice("kevin16");
		try {
            voice.setRate(190);//Setting the rate of the voice
            voice.setPitch(160);//Setting the Pitch of the voice
            voice.setVolume(4);//Setting the volume of the voice 
        } catch (Exception e1) {
            e1.printStackTrace();
        }
		voice.speak("Welcome to the Maze! Press enter to start!");
	}
	
	static boolean levelsExistAlready = false;
	
	public void getMapList() {
		for (int i = 0; i < 99; i++) {
			File map = new File("./Level "+i+".map");
			if (map.exists()) {
				System.out.println("Level "+i+" exists.");
				mapList.add("Level "+i+".map");
				levelsExistAlready = true;
			}
		}
	}
}
