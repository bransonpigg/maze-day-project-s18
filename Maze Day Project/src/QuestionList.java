import java.io.*;
import java.util.*;

public class QuestionList {

	private ArrayList<Question> qList;
	private final String FILENAME = "src/question list.txt"; // KEEP THIS FILE IN src FOLDER!!!
	
	public QuestionList() {
		this.qList = new ArrayList<Question>();
	}
	
	public void fillQList() throws FileNotFoundException {
		try {
			BufferedReader br = new BufferedReader(new FileReader(FILENAME));
			String currentLine;
			while ((currentLine = br.readLine()) != null) {
				String q = currentLine;
				String a = br.readLine();
				String b = br.readLine();
				String c = br.readLine();
				int ans = Integer.parseInt(br.readLine());
				Question question = new Question(q, a, b, c, ans);
				qList.add(question);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void printList() {
		for (int i = 0; i < qList.size(); i++) {
			System.out.print(qList.get(i).toString());
		}
	}
	
	public ArrayList<Question> getList() {
		return qList;
	}
}
