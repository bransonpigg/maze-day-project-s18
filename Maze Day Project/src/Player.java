import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import sun.audio.*;
import java.io.*;
import javax.swing.*;
import java.applet.*;
import java.net.*;

import java.util.ArrayList;
import java.util.Random;




/*
 * Controls the "player" block within the maze. Allows for movement in the positive x- and y-directions (right and up) and in the
 * negative x- and y-directions (left and down). Plays audio clips to help visually impaired players get a sense of where they are.
 */

public class Player extends JPanel {
	
	int x,y;
	private QuestionList qList;
	private ArrayList<Question> questions;
	private final String WALLFILE = "src/wall hit.wav";
	private final String WALKFILE = "src/walking.wav";
	
	public Player() {
		this.setBackground(Color.BLUE);
		this.setSize(Maze.panelSize, Maze.panelSize);
		this.qList = new QuestionList(); // creates new question list from file in source folder every time game launches
		try {	
			qList.fillQList();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		this.questions = qList.getList();
	}
	
	public void moveLeft() {
		if (x == 0 || Maze.map[x-1][y] == 0) {
			playWallSound();
		}
		else if (x > 0 && Maze.map[x-1][y] == 1) {
			playWalkSound();
			this.setLocation(this.getX()-25, this.getY());
			x--;
		}
		else if (x > 0 && Maze.map[x-1][y] == 2) {
			this.setLocation(this.getX()-25, this.getY());
			x --;
			askQuestion();
			this.setLocation(this.getX()-25, this.getY());
			x --;
		}
	}
	
	public void moveRight() {
		if (x == Maze.columns - 1 || Maze.map[x+1][y] == 0) {
			playWallSound();
		}
		else if (x < Maze.columns-1 && Maze.map[x+1][y] == 1) {
			playWalkSound();
			this.setLocation(this.getX()+25, this.getY());
			x++;
		}
		else if (x < Maze.columns-1 && Maze.map[x+1][y] == 2) {
			this.setLocation(this.getX()+25, this.getY());
			x++;
			askQuestion();
			this.setLocation(this.getX()+25, this.getY());
			x++;
		}
	}
	
	public void moveUp() {
		if (y == 0 || Maze.map[x][y-1] == 0) {
			playWallSound();
		}
		else if (y > 0 && Maze.map[x][y-1] == 1) {
			playWalkSound();
			this.setLocation(this.getX(), this.getY()-25);
			y--;
			
		}
		else if (y > 0 && Maze.map[x][y-1] == 2) {
			this.setLocation(this.getX(), this.getY()-25);
			y--;
			askQuestion();
			this.setLocation(this.getX(), this.getY()-25);
			y--;
		}
	}
	
	public void moveDown() {
		if (y == Maze.rows-1 || Maze.map[x][y+1] == 0 ) {
			playWallSound();
		}
		else if (y < Maze.rows -1 && Maze.map[x][y+1] == 1) {
			playWalkSound();
			this.setLocation(this.getX(), this.getY() +25);
			y++;
		}
		else if (y < Maze.rows-1 && Maze.map[x][y+1] == 2) {
			this.setLocation(this.getX(), this.getY()+25);
			y++;
			askQuestion();
			this.setLocation(this.getX(), this.getY()+25);
			y++;
		}
	}
	
	public void askQuestion() {
		Voice voice = VoiceManager.getInstance().getVoice("kevin16");
		if (voice != null) {
            voice.allocate();//Allocating Voice
        }
        try {
            voice.setRate(165);//Setting the rate of the voice
            voice.setPitch(160);//Setting the Pitch of the voice
            voice.setVolume(4);//Setting the volume of the voice 
        } catch (Exception e1) {
            e1.printStackTrace();
        }
		Random rand = new Random();
		int questionNum = rand.nextInt(questions.size());
		String question = questions.get(questionNum).getQuestion();
		String [] options = {questions.get(questionNum).getChoiceA(), questions.get(questionNum).getChoiceB(), questions.get(questionNum).getChoiceC()};
		voice.speak(question);
		try {
			Thread.sleep(400);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		voice.speak(questions.get(questionNum).getChoiceA());
		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		voice.speak(questions.get(questionNum).getChoiceB());
		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		voice.speak("or " +questions.get(questionNum).getChoiceC());
		int answer = 0;
		System.out.println(questions.get(questionNum).getAnswer());
		while (answer != questions.get(questionNum).getAnswer()) {
			answer = JOptionPane.showOptionDialog(null, question, "Enemy encountered!", JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.QUESTION_MESSAGE, null, options, options[0]) + 1;
			  if (answer != questions.get(questionNum).getAnswer()) {
				voice.speak("Incorrect! Try Again.");
				try {
					Thread.sleep(400);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				voice.speak(question);
				try {
					Thread.sleep(400);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				voice.speak(questions.get(questionNum).getChoiceA());
				try {
					Thread.sleep(400);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				voice.speak(questions.get(questionNum).getChoiceB());
				try {
					Thread.sleep(400);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				voice.speak("or " +questions.get(questionNum).getChoiceC());
				try {
					Thread.sleep(750);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		voice.speak("Correct! Continue in the maze,");
	}
	
	public void playWallSound() {
		try {
			InputStream in = new FileInputStream(WALLFILE);
			AudioStream as = new AudioStream(in);
			AudioPlayer.player.start(as);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void playWalkSound() {
		try {
			InputStream in = new FileInputStream(WALKFILE);
			AudioStream as = new AudioStream(in);
			AudioPlayer.player.start(as);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
