//Store all of the data that has been recorded from logic.GameRecord.
package logic;

import java.util.ArrayList;
import java.util.List;

public class ScoreData {
	private static ArrayList<GameRecord> gameScoreData = new ArrayList<GameRecord>();

	// Store that data and sort only 10 most recent record.
	public static void saveData(GameRecord gameRecord) {
		gameScoreData.add(gameRecord);
		if (gameScoreData.size() > 10) {
			List<GameRecord> sliceData = gameScoreData.subList(1, 11);
			ArrayList<GameRecord> slicedArray = new ArrayList<GameRecord>(sliceData);
			gameScoreData = slicedArray;
		}
	}

	// Load the data
	public static ArrayList<GameRecord> loadData() {
		return gameScoreData;
	}
}
