package onlineTest;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.TreeMap;

public class SystemManager implements Manager, Serializable {

	private static final long serialVersionUID = 1L;

	private String[] letterGrades = new String[] { "A", "B", "C", "D", "F" };
	private double[] cutoffs = new double[] { 90, 80, 70, 60, 0 };

	private HashMap<Integer, Exam> examDatabase = new HashMap<Integer, Exam>();
	private TreeMap<String, Student> studentDatabase = new TreeMap<String, Student>();

	/*
	 * Adds the specified exam to the database.
	 * 
	 * @param examId
	 * 
	 * @param title
	 * 
	 * @return false is exam already exists.
	 */
	public boolean addExam(int examId, String title) {
		if (examDatabase.containsKey(examId)) {
			return false;
		}

		examDatabase.put(examId, new Exam(examId, title));

		return true;
	}

	/**
	 * Adds a true and false question to the specified exam. If the question already
	 * exists it is overwritten.
	 * 
	 * @param examId
	 * @param questionNumber
	 * @param text           Question text
	 * @param points         total points
	 * @param answer         expected answer
	 */
	public void addTrueFalseQuestion(int examId, int questionNumber, String text, double points, boolean answer) {
		examDatabase.get(examId).addQuestion(questionNumber, new TrueFalseQuestion(text, answer, points));
	}

	/**
	 * Adds a multiple choice question to the specified exam. If the question
	 * already exists it is overwritten.
	 * 
	 * @param examId
	 * @param questionNumber
	 * @param text           Question text
	 * @param points         total points
	 * @param answer         expected answer
	 */
	public void addMultipleChoiceQuestion(int examId, int questionNumber, String text, double points, String[] answer) {
		examDatabase.get(examId).addQuestion(questionNumber, new MultipleChoiceQuestion(text, answer, points));
	}

	/**
	 * Adds a fill-in-the-blanks question to the specified exam. If the question
	 * already exits it is overwritten. Each correct response is worth
	 * points/entries in the answer.
	 * 
	 * @param examId
	 * @param questionNumber
	 * @param text           Question text
	 * @param points         total points
	 * @param answer         expected answer
	 */
	public void addFillInTheBlanksQuestion(int examId, int questionNumber, String text, double points,
			String[] answer) {
		examDatabase.get(examId).addQuestion(questionNumber, new FillInTheBlanksQuestion(text, answer, points));
	}

	/**
	 * Returns a string with the following information per question:<br />
	 * "Question Text: " followed by the question's text<br />
	 * "Points: " followed by the points for the question<br />
	 * "Correct Answer: " followed by the correct answer. <br />
	 * The format for the correct answer will be: <br />
	 * a. True or false question: "True" or "False"<br />
	 * b. Multiple choice question: [ ] enclosing the answer (each entry separated
	 * by commas) and in sorted order. <br />
	 * c. Fill in the blanks question: [ ] enclosing the answer (each entry
	 * separated by commas) and in sorted order. <br />
	 * 
	 * @param examId
	 * @return "Exam not found" if exam not found, otherwise the key
	 */
	public String getKey(int examId) {
		String answer = "";

		for (Question q : examDatabase.get(examId).getQuestions().values()) {
			answer += q.toString() + "\n";
		}

		return answer;
	}

	/**
	 * Adds the specified student to the database. Names are specified in the format
	 * LastName,FirstName
	 * 
	 * @param name
	 * @return false if student already exists.
	 */
	public boolean addStudent(String name) {
		if (studentDatabase.containsKey(name)) {
			return false;
		}

		studentDatabase.put(name, new Student(name));

		return true;
	}

	/**
	 * Enters the question's answer into the database.
	 * 
	 * @param studentName
	 * @param examId
	 * @param questionNumber
	 * @param answer
	 */
	public void answerTrueFalseQuestion(String studentName, int examId, int questionNumber, boolean answer) {
		linkStudentToExam(studentName, examId);

		TrueFalseQuestion question = (TrueFalseQuestion) examDatabase.get(examId).getQuestions().get(questionNumber);
		question.storeAnswer(studentDatabase.get(studentName), answer);
	}

	/**
	 * Enters the question's answer into the database.
	 * 
	 * @param studentName
	 * @param examId
	 * @param questionNumber
	 * @param answer
	 */
	public void answerMultipleChoiceQuestion(String studentName, int examId, int questionNumber, String[] answer) {
		linkStudentToExam(studentName, examId);

		MultipleChoiceQuestion question = (MultipleChoiceQuestion) examDatabase.get(examId).getQuestions().get(questionNumber);
		question.storeAnswer(studentDatabase.get(studentName), answer);
	}

	/**
	 * Enters the question's answer into the database.
	 * 
	 * @param studentName
	 * @param examId
	 * @param questionNumber
	 * @param answer
	 */
	public void answerFillInTheBlanksQuestion(String studentName, int examId, int questionNumber, String[] answer) {
		linkStudentToExam(studentName, examId);

		FillInTheBlanksQuestion question = (FillInTheBlanksQuestion) examDatabase.get(examId).getQuestions()
				.get(questionNumber);
		question.storeAnswer(studentDatabase.get(studentName), answer);
	}

	/**
	 * Returns the score the student got for the specified exam.
	 * 
	 * @param studentName
	 * @param examId
	 * @return score
	 */
	public double getExamScore(String studentName, int examId) {
		double score = 0;

		for (Question q : examDatabase.get(examId).getQuestions().values()) {
			score += q.studentPoints(studentDatabase.get(studentName));
		}

		return score;
	}

	/**
	 * Generates a grading report for the specified exam. The report will include
	 * the following information for each exam question:<br />
	 * "Question #" {questionNumber} {questionScore} " points out of "
	 * {totalQuestionPoints}<br />
	 * The report will end with the following information:<br />
	 * "Final Score: " {score} " out of " {totalExamPoints};
	 * 
	 * @param studentName
	 * @param examId
	 * @return report
	 */
	public String getGradingReport(String studentName, int examId) {
		String ans = "";

		for (Integer i : examDatabase.get(examId).getQuestions().keySet()) {
			Question q = examDatabase.get(examId).getQuestions().get(i);
			ans += "Question #" + i + " " + q.studentPoints(studentDatabase.get(studentName)) + 
					" points out of " + q.getPoints() + "\n";
		}

		ans += "Final Score: " + getExamScore(studentName, examId) + " out of " + 
				examDatabase.get(examId).getPointTotal();

		return ans;
	}

	/**
	 * Sets the cutoffs for letter grades. For example, a typical curve we will have
	 * new String[]{"A","B","C","D","F"}, new double[] {90,80,70,60,0}. Anyone with
	 * a 90 or above gets an A, anyone with an 80 or above gets a B, etc. Notice we
	 * can have different letter grades and cutoffs (not just the typical curve).
	 * 
	 * @param letterGrades
	 * @param cutoffs
	 */
	public void setLetterGradesCutoffs(String[] letterGrades, double[] cutoffs) {
		this.letterGrades = letterGrades;
		this.cutoffs = cutoffs;
	}

	/**
	 * Computes a numeric grade (value between 0 and a 100) for the student taking
	 * into consideration all the exams. All exams have the same weight.
	 * 
	 * @param studentName
	 * @return grade
	 */
	public double getCourseNumericGrade(String studentName) {
		double totalPoints = 0;
		int tests = 0;

		for (Integer i : studentDatabase.get(studentName).getStudentExams().keySet()) {
			Exam e = studentDatabase.get(studentName).getStudentExams().get(i);

			totalPoints += getExamScore(studentName, i) / e.getPointTotal();
			
			tests++;
		}

		return 100 * (totalPoints / tests);
	}

	/**
	 * Computes a letter grade based on cutoffs provided. It is assumed the cutoffs
	 * have been set before the method is called.
	 * 
	 * @param studentName
	 * @return letter grade
	 */
	public String getCourseLetterGrade(String studentName) {
		double numericalGrade = getCourseNumericGrade(studentName);

		for (int i = 0; i < this.cutoffs.length; i++) {
			if (numericalGrade >= this.cutoffs[i]) {
				return this.letterGrades[i];
			}
		}

		return this.letterGrades[this.letterGrades.length - 1];
	}

	/**
	 * Returns a listing with the grades for each student. For each student the
	 * report will include the following information: <br />
	 * {studentName} {courseNumericGrade} {courseLetterGrade}<br />
	 * The names will appear in sorted order.
	 * 
	 * @return grades
	 */
	public String getCourseGrades() {
		String ans = "";

		for (String s : studentDatabase.keySet()) {
			ans += s + " " + getCourseNumericGrade(s) + " " + getCourseLetterGrade(s) + "\n";
		}

		return ans;
	}

	/**
	 * Returns the maximum score (among all the students) for the specified exam.
	 * 
	 * @param examId
	 * @return maximum
	 */
	public double getMaxScore(int examId) {
		Exam exam = examDatabase.get(examId);
		double max = 0;

		for (String s : exam.getExamStudents().keySet()) {
			if (getExamScore(s, examId) > max) {
				max = getExamScore(s, examId);
			}
		}

		return max;
	}

	/**
	 * Returns the minimum score (among all the students) for the specified exam.
	 * 
	 * @param examId
	 * @return minimum
	 */
	public double getMinScore(int examId) {
		Exam exam = examDatabase.get(examId);
		double min = Integer.MAX_VALUE;

		for (String s : exam.getExamStudents().keySet()) {
			if (getExamScore(s, examId) < min) {
				min = getExamScore(s, examId);
			}
		}

		return min;
	}

	/**
	 * Returns the average score for the specified exam.
	 * 
	 * @param examId
	 * @return average
	 */
	public double getAverageScore(int examId) {
		Exam exam = examDatabase.get(examId);
		double totalPoints = 0;
		int numOfStudents = 0;

		for (String s : exam.getExamStudents().keySet()) {
			numOfStudents++;
			totalPoints += getExamScore(s, examId);
		}

		return totalPoints / numOfStudents;
	}

	/**
	 * It will serialize the Manager object and store it in the specified
	 * file. @throws
	 */
	public void saveManager(Manager manager, String fileName) {
		try {
			FileOutputStream file = new FileOutputStream(fileName);
			ObjectOutputStream out = new ObjectOutputStream(file);

			out.writeObject(manager);

			out.close();
			file.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * It will return a Manager object based on the serialized data found in the
	 * specified file.
	 */
	public Manager restoreManager(String fileName) {
		try {
			FileInputStream file = new FileInputStream(fileName);
			ObjectInputStream in = new ObjectInputStream(file);

			Manager manager = (Manager) in.readObject();

			in.close();
			file.close();

			return manager;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private void linkStudentToExam(String studentName, int examId) {
		if (!studentDatabase.get(studentName).getStudentExams().containsKey(examId)) {
			studentDatabase.get(studentName).addExam(examDatabase.get(examId));
		}

		if (!examDatabase.get(examId).getExamStudents().containsKey(studentName)) {
			examDatabase.get(examId).addStudent(studentDatabase.get(studentName));
		}
	}
}
