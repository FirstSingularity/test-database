package onlineTest;

import java.util.HashMap;

public class TrueFalseQuestion implements Question {

	private static final long serialVersionUID = 1L;

	private String text;
	private boolean answer;
	private double points;
	private HashMap<Student, Double> students = new HashMap<Student, Double>();

	public TrueFalseQuestion(String text, boolean answer, double points) {
		this.text = text;
		this.answer = answer;
		this.points = points;
	}

	public void storeAnswer(Student student, boolean ans) {
		students.put(student, checkAnswer(ans));
	}

	public double checkAnswer(boolean ans) {
		if (ans == this.answer) {
			return this.points;
		}

		return 0;
	}

	public String toString() {
		String ans = "Question Text: " + this.text + "\n" + "Points: " + this.points + "\n" + "Correct Answer: "
				+ (this.answer ? "True" : "False");

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
