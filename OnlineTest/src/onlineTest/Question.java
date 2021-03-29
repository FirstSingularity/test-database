package onlineTest;

import java.io.Serializable;

public interface Question extends Serializable {

	public String toString();

	public double studentPoints(Student student);

	public double getPoints();
}
