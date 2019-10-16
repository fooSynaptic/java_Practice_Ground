

public class PrimaryStudent extends Student{
	protected int grades;
	
	public PrimaryStudent(String name, int age, int score, int grade) {
		super(name, age, score);
		this.score = score;
		this.grades = grade;
	}
	
	public int getGrade() {
		return this.grades;
	}
}
