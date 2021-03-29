package onlineTest;

import java.io.Serializable;
import java.util.HashMap;
import java.util.TreeMap;

public class Exam implements Serializable {

	private static final long serialVersionUID = 1L;

	private TreeMap<Integer, Question> questions = new TreeMap<Integer, Question>();
	private HashMap<String, Student> students = new HashMap<String, Student>();
	private int examId;
	private String title;

	public Exam(int examId, String title) {
		this.examId = examId;
		this.title = title;
	}

	public void addStudent(Student student) {
		this.students.put(student.getName(), student);
	}

	public void addQuestion(int questionNumber, Question question) {
		this.questions.put(questionNumber, question);
	}

	public String getTitle() {
		return this.title;
	}

	public TreeMap<Integer, Question> getQuestions() {
		return this.questions;
	}

	public HashMap<String, Student> getExamStudents() {
		return this.students;
	}

	public double getPointTotal() {
		double total = 0;

		for (Question q : this.questions.values()) {
			total += q.getPoints();
		}

		return total;
	}

	public int getId() {
		return this.examId;
	}
}
