package onlineTest;

import java.util.Arrays;
import java.util.HashMap;

public class MultipleChoiceQuestion implements Question {

	private static final long serialVersionUID = 1L;

	private String text;
	private String[] answer;
	private double points;
	private HashMap<Student, Double> students = new HashMap<Student, Double>();

	public MultipleChoiceQuestion(String text, String[] answer, double points) {
		this.text = text;
		this.answer = answer;
		this.points = points;
	}

	public void storeAnswer(Student student, String[] ans) {
		students.put(student, checkAnswer(ans));
	}

	public double checkAnswer(String[] ans) {
		if (Arrays.equals(ans, this.answer)) {
			return this.points;
		}

		return 0;
	}

	public String toString() {
		String ans = "Question Text: " + this.text + "\n" + "Points: " + this.points + "\n" + "Correct Answer: "
				+ ansToString();

		return ans;
	}

	private String ansToString() {
		String ans = "[";

		for (String s : this.answer) {
			ans += s + ", ";
		}

		ans = ans.substring(0, ans.length() - 2);
		ans += "]";

		return ans;
	}

	public double studentPoints(Student student) {
		if (this.students.containsKey(student)) {
			return this.students.get(student);
		}

		return 0;
	}

	public double getPoints() {
		return this.points;
	}
}
