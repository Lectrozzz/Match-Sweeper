//Create data record of the recent game.
package logic;

public class GameRecord {
	private String timeTaken;
	private String totalScore;
	private String totalMove;

	public GameRecord(String timeTaken, Integer totalScore, Integer totalMove) {
		setTimeTaken(timeTaken);
		setTotalScore(totalScore);
		setTotalMove(totalMove);
	}

	public String getTimeTaken() {
		return timeTaken;
	}

	public void setTimeTaken(String timeTaken) {
		this.timeTaken = timeTaken;
	}

	public String getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(Integer totalScore) {
		this.totalScore = totalScore.toString();
	}

	public String getTotalMove() {
		return totalMove;
	}

	public void setTotalMove(Integer totalMove) {
		this.totalMove = totalMove.toString();
	}

}
