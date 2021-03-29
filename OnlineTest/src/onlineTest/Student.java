package onlineTest;

import java.io.Serializable;
import java.util.HashMap;

public class Student implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;
	private HashMap<Integer, Exam> exams = new HashMap<Integer, Exam>();

	public Student(String name) {
		this.name = name;
	}

	public boolean addExam(Exam exam) {
		this.exams.put(exam.getId(), exam);

		return true;
	}

	public HashMap<Integer, Exam> getStudentExams() {
		return this.exams;
	}

	public String getName() {
		return this.name;
	}
}
