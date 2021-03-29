package onlineTest;

import java.util.HashMap;

public class FillInTheBlanksQuestion implements Question {

	private static final long serialVersionUID = 1L;

	private String text;
	private String[] answer;
	private double points;
	private HashMap<Student, Double> students = new HashMap<Student, Double>();

	public FillInTheBlanksQuestion(String text, String[] answer, double points) {
		this.text = text;
		this.answer = answer;
		this.points = points;
	}

	public void storeAnswer(Student student, String[] ans) {
		students.put(student, checkAnswer(ans));
	}

	public double checkAnswer(String[] ans) {
		double partial = this.points / this.answer.length;
		double credit = 0;

		for (int i = 0; i < this.answer.length; i++) {
			for (String s : ans) {
				if (s.equals(this.answer[i])) {
					credit += partial;
				}
			}
		}

		return credit;
	}

	public String toString() {
		String ans = "Question Text: " + this.text + "\n" + "Points: " + this.points + "\n" + "Correct Answer: "
				+ ansToString();

		return ans;
	}

	private String ansToString() {
		String ans = "[";

		for (int i = this.answer.length - 1; i >= 0; i--) {
			ans += this.answer[i] + ", ";
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
