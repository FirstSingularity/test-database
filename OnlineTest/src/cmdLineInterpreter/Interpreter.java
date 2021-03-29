package cmdLineInterpreter;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import onlineTest.Manager;
import onlineTest.SystemManager;

/**
 * 
 * By running the main method of this class we will be able to execute commands
 * associated with the SystemManager. This command interpreter is text based.
 *
 */
public class Interpreter {

	public static void main(String[] args) throws Exception {

		SystemManager manager = initManager();

		int choice;
		Scanner scanner = new Scanner(System.in);

		do {
			String menu = "";
			menu += "Enter 0 to quit the manager\n";
			menu += "Enter 1 to add and exam\n";
			menu += "Enter 2 to add a true or false question\n";
			menu += "Enter 3 to add a multiple choice question\n";
			menu += "Enter 4 to add a fill in the blanks question\n";
			menu += "Enter 5 to get an exam key\n";
			menu += "Enter 6 to add a student\n";
			menu += "Enter 7 to answer a true or false question\n";
			menu += "Enter 8 to answer a multiple choice question\n";
			menu += "Enter 9 to answer a fill in the blanks question\n";
			menu += "Enter 10 to get an exam score\n";
			menu += "Enter 11 to get a grading report\n";
			menu += "Enter 12 to set letter grade cutoffs\n";
			menu += "Enter 13 to get a student's numeric grade\n";
			menu += "Enter 14 to get a student's letter grade\n";
			menu += "Enter 15 to get all students' grades\n";
			menu += "Enter 16 to get the maximum score for a test\n";
			menu += "Enter 17 to get the minimum score for a test\n";
			menu += "Enter 18 to get the average score for a test\n";

			System.out.println(menu);

			choice = scanner.nextInt();

			switch (choice) {
			case 0:
				break;
			case 1:
				System.out.println("Enter an exam ID");
				int id = scanner.nextInt();
				System.out.println("Enter an exam title");
				String title = scanner.next();
				manager.addExam(id, title);
				break;
			case 2:
				System.out.println("Enter an exam ID");
				id = scanner.nextInt();
				System.out.println("Enter the question number");
				int questionNumber = scanner.nextInt();
				System.out.println("Enter the question's text");
				String text = scanner.next();
				System.out.println("Enter a point value");
				double points = scanner.nextDouble();
				System.out.println("Enter an answer (true/false)");
				boolean tfAnswer = scanner.nextBoolean();
				manager.addTrueFalseQuestion(id, questionNumber, text, points, tfAnswer);
				break;
			case 3:
				System.out.println("Enter an exam ID");
				id = scanner.nextInt();
				System.out.println("Enter the question number");
				questionNumber = scanner.nextInt();
				System.out.println("Enter the question's text");
				text = scanner.next();
				System.out.println("Enter a point value");
				points = scanner.nextDouble();
				System.out.println("How many answers are there?");
				int ansLength = scanner.nextInt();
				String[] mcAnswer = new String[ansLength];
				for (int i = 0; i < ansLength; i++) {
					System.out.println("Enter an answer");
					mcAnswer[i] = scanner.next();
				}
				manager.addMultipleChoiceQuestion(id, questionNumber, text, points, mcAnswer);
				break;
			case 4:
				System.out.println("Enter an exam ID");
				id = scanner.nextInt();
				System.out.println("Enter the question number");
				questionNumber = scanner.nextInt();
				System.out.println("Enter the question's text");
				text = scanner.next();
				System.out.println("Enter a point value");
				points = scanner.nextDouble();
				System.out.println("How many answers are there?");
				ansLength = scanner.nextInt();
				String[] fbAnswer = new String[ansLength];
				for (int i = 0; i < ansLength; i++) {
					System.out.println("Enter an answer");
					fbAnswer[i] = scanner.next();
				}
				manager.addFillInTheBlanksQuestion(id, questionNumber, text, points, fbAnswer);
				break;
			case 5:
				System.out.println("Enter an exam ID");
				id = scanner.nextInt();
				manager.getKey(id);
				break;
			case 6:
				System.out.println("Enter a name");
				String name = scanner.next();
				manager.addStudent(name);
				break;
			case 7:
				System.out.println("Enter a student name");
				name = scanner.next();
				System.out.println("Enter an exam ID");
				id = scanner.nextInt();
				System.out.println("Enter the question's number");
				questionNumber = scanner.nextInt();
				System.out.println("Enter an answer (true/false)");
				tfAnswer = scanner.nextBoolean();
				manager.answerTrueFalseQuestion(name, id, questionNumber, tfAnswer);
				break;
			case 8:
				System.out.println("Enter a student name");
				name = scanner.next();
				System.out.println("Enter an exam ID");
				id = scanner.nextInt();
				System.out.println("Enter the question's number");
				questionNumber = scanner.nextInt();
				System.out.println("How many answers are there?");
				ansLength = scanner.nextInt();
				mcAnswer = new String[ansLength];
				for (int i = 0; i < ansLength; i++) {
					System.out.println("Enter an answer");
					mcAnswer[i] = scanner.next();
				}
				manager.answerMultipleChoiceQuestion(name, id, questionNumber, mcAnswer);
				break;
			case 9:
				System.out.println("Enter a student name");
				name = scanner.next();
				System.out.println("Enter an exam ID");
				id = scanner.nextInt();
				System.out.println("Enter the question's number");
				questionNumber = scanner.nextInt();
				System.out.println("How many answers are there?");
				ansLength = scanner.nextInt();
				fbAnswer = new String[ansLength];
				for (int i = 0; i < ansLength; i++) {
					System.out.println("Enter an answer");
					fbAnswer[i] = scanner.next();
				}
				manager.answerFillInTheBlanksQuestion(name, id, questionNumber, fbAnswer);
				break;
			case 10:
				System.out.println("Enter a student name");
				name = scanner.next();
				System.out.println("Enter an exam ID");
				id = scanner.nextInt();
				manager.getExamScore(name, id);
				break;
			case 11:
				System.out.println("Enter a student name");
				name = scanner.next();
				System.out.println("Enter an exam ID");
				id = scanner.nextInt();
				manager.getGradingReport(name, id);
				break;
			case 12:
				System.out.println("How many letter grades are there?");
				int grades = scanner.nextInt();
				System.out.println("Enter letter grades (descending order)");
				String[] letterGrades = new String[grades];
				for (int i = 0; i < grades; i++) {
					System.out.println("Enter letter");
					letterGrades[i] = scanner.next();
				}
				System.out.println("Enter cutoffs (descending order)");
				double[] cutoffs = new double[grades];
				for (int i = 0; i < grades; i++) {
					System.out.println("Enter cutoff");
					cutoffs[i] = scanner.nextDouble();
				}
				manager.setLetterGradesCutoffs(letterGrades, cutoffs);
				break;
			case 13:
				System.out.println("Enter a student name");
				name = scanner.next();
				manager.getCourseNumericGrade(name);
				break;
			case 14:
				System.out.println("Enter a student name");
				name = scanner.next();
				manager.getCourseLetterGrade(name);
				break;
			case 15:
				manager.getCourseGrades();
				break;
			case 16:
				System.out.println("Enter an exam ID");
				id = scanner.nextInt();
				manager.getMaxScore(id);
				break;
			case 17:
				System.out.println("Enter an exam ID");
				id = scanner.nextInt();
				manager.getMinScore(id);
				break;
			case 18:
				System.out.println("Enter an exam ID");
				id = scanner.nextInt();
				manager.getAverageScore(id);
				break;
			default:
				System.out.println("Invalid Input");
			}

			for (int i = 0; i < 20; i++) {
				System.out.println();
			}

		} while (choice != 0);

		scanner.close();

		saveManager(manager);
	}

	private static SystemManager initManager() throws IOException, ClassNotFoundException {
		File file = new File("manager.info");

		if (!file.exists()) {
			System.out.print("New Manager Created");
			return new SystemManager();
		} else {
			SystemManager temp = new SystemManager();

			System.out.println("Manager Reloaded");

			return (SystemManager) temp.restoreManager("manager.info");
		}

	}

	private static void saveManager(Manager manager) throws Exception {
		manager.saveManager(manager, "manager.info");
		System.out.print("Manager Saved");
	}
}
