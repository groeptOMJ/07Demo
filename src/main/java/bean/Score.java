package bean;

/**
 * Bean to keep user and score
 * 
 * @author Stijn Heylen
 *
 */
public class Score {
	private String name;
	private int score;

	public Score(String name) {
		this.setName(name);
		setScore(0);
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
