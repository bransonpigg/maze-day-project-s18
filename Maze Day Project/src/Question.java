import java.util.ArrayList;

public class Question {

	private String question;
	private String choiceA;
	private String choiceB;
	private String choiceC;
	private int answer;
	
	public Question (String q, String a, String b, String c, int correct) {
		this.question = q;
		this.choiceA = a;
		this.choiceB = b;
		this.choiceC = c;
		this.answer = correct; // an integer from 1 to 3 corresponding to each answer choice
	}
	
	public String getQuestion() {
		return question;
	}
	
	public String getChoiceA() {
		return choiceA;
	}
	
	public String getChoiceB() {
		return choiceB;
	}
	
	public String getChoiceC() {
		return choiceC;
	}
	
	
	public int getAnswer() {
		return answer;
	}
	
	public String toString() {
		return "Question: "+question+" A: "+choiceA+" B: "+choiceB+" C: "+choiceC+"\nAnswer: "+answer;
	}
}
